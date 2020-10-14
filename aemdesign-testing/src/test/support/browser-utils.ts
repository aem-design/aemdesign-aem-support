import kebabCase from 'lodash/kebabCase'
import path from 'path'

import {
  devices,

  BrowserContext,
  Page,
} from 'playwright'

const desktopDeviceDefaults = {
  defaultBrowserType : 'chromium',
  deviceScaleFactor  : 1,
  hasTouch           : false,
  isMobile           : false,
  userAgent          : `Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/${browser.version()} Safari/537.36`,
} as PlaywrightDeviceDescriptor

const availableDevices: Record<string, PlaywrightDeviceDescriptor> = {
  'mobile'             : devices['iPhone SE'],
  'mobile (landscape)' : devices['iPhone SE landscape'],
  'tablet'             : devices['iPad (gen 6)'],
  'tablet (landscape)' : devices['iPad (gen 6) landscape'],

  'small desktop': {
    ...desktopDeviceDefaults,

    viewport: {
      height : 1366,
      width  : 1024,
    },
  },

  'large desktop': {
    ...desktopDeviceDefaults,

    viewport: {
      height : 1920,
      width  : 1080,
    },
  },
}

const instances: Record<string, PlaywrightBrowserInstance> = {}

export { BrowserContext, Page }

/**
 * Retrieve the unique device identifiers along with the browser they are being run in.
 */
export function getDeviceKeys(): [device: string, browserName: string][] {
  return Object.keys(availableDevices).map(device => [device, browserName])
}

/**
 * Log into AEM and wait for the loading state of the page to change.
 */
async function authenticateWithAEM(page: Page): Promise<void> {
  await page.fill('input[name="j_username"]', 'admin')
  await page.fill('input[name="j_password"]', 'admin')
  await page.click('#submit-button')

  await page.waitForLoadState('load')
}

/**
 * Generates the URL to access pages in AEM.
 */
function generatePageUrl(path: string, needsAuth: boolean): string {
  const host     = process.env.TEST_HOST     || 'localhost'
  const port     = process.env.TEST_PORT     || '4502'
  const protocol = process.env.TEST_PROTOCOL || 'http'

  return `${protocol}://${host}:${port}${path}${needsAuth ? '.html?wcmmode=disabled' : ''}`
}

/**
 * Opens a new page with the given `BrowserContext` and `path`. Authentication is handled automatically
 * by detecting if the `TEST_NO_AUTH` environment variable exists which skips authentication when found.
 */
export async function openNewPageInContext(path: string, context: BrowserContext): Promise<Page> {
  const page      = await context.newPage()
  const needsAuth = (!!process.env.TEST_NO_AUTH) === false

  await page.goto(generatePageUrl(path, needsAuth))

  if (needsAuth) {
    await authenticateWithAEM(page)
  }

  return page
}

/**
 * Launch a browser context for the given `device` which will attach a new context to the browser
 * that is currently active. It then opens the given `path` in a new tab.
 */
export async function launchContextForDevice(device: string, path: string): Promise<PlaywrightBrowserInstance> {
  const context = await browser.newContext(availableDevices[device])
  const page    = await openNewPageInContext(path, context)

  return (instances[device] = { context, page })
}

/**
 * Closes all of the active context instances.
 */
export async function closeContextForAllInstances(): Promise<void> {
  for (const instance of Object.keys(instances)) {
    await instances[instance].context.close()

    delete instances[instance]
  }
}

/**
 * Takes and stores a screenshot for the given `reference` object. Screenshots can be taken for
 * elements and pages and options will automatically refer to the type of `reference` object given.
 *
 * By default all screenshots are saved as PNG as they are the only file type supported by 'jest-image-snapshot'.
 */
export async function takeScreenshot<T extends PlaywrightScreenshotConstraints>(
  reference: T | null,
  options: PlaywrightScreenshotOptions<T> = {},
): Promise<Buffer> {
  const { currentTestName, testPath } = expect.getState()

  if (reference === null) {
    throw new Error(`Unable to take screenshot for '${currentTestName}' as reference is null`)
  }

  const resolvedBasePath = path.resolve(__dirname, '..')
  const resolvedTestFile = path.relative(resolvedBasePath, testPath)
  const filename         = options.filename || `${path.basename(resolvedTestFile)}-${currentTestName}`

  const savePath = path.join(
    path.resolve(resolvedBasePath, 'screenshots', path.dirname(resolvedTestFile)),
    `${kebabCase(filename)}.png`,
  )

  let additionalOptions: PlaywrightScreenshotOptions<PlaywrightScreenshotConstraints> = {}

  if (typeof (reference as Page).url === 'function') {
    const pageOptions = options as PlaywrightScreenshotOptions<Page>

    additionalOptions = {
      clip     : pageOptions.clip,
      fullPage : pageOptions.fullPage,
    }
  }

  return await reference.screenshot({
    ...additionalOptions,

    omitBackground : options.omitBackground || false,
    path           : savePath,
    timeout        : options.timeout || 10000,
    type           : 'png',
  })
}
