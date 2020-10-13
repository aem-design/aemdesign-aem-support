import kebabCase from 'lodash/kebabCase'
import path from 'path'
import { chromium, Page } from 'playwright'

const instances: Record<string, BrowserInstance> = {}

/**
 * Log into AEM and wait for the loading state of the page to change.
 */
async function authenticateWithAEM(instance: BrowserInstance): Promise<void> {
  await instance.page.fill('input[name="j_username"]', 'admin')
  await instance.page.fill('input[name="j_password"]', 'admin')
  await instance.page.click('#submit-button')

  await instance.page.waitForLoadState('load')
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
 * Launch a browser session with the given `identifier` which automatically logs in when needed
 * and returns a new `BrowserInstance` object.
 */
export async function launchBrowser(identifier: string, path: string): Promise<BrowserInstance> {
  if (Object.keys(instances).includes(identifier)) {
    return instances[identifier]
  }

  const browser   = await chromium.launch()
  const page      = await browser.newPage()
  const instance  = instances[identifier] = { browser, page }
  const needsAuth = (!!process.env.TEST_NO_AUTH) === false

  await page.goto(generatePageUrl(path, needsAuth))

  if (needsAuth) {
    await authenticateWithAEM(instance)
  }

  return instance
}

/**
 * Closes the active browser session for the given `identifier`.
 */
export async function closeBrowser(identifier: string): Promise<void> {
  const instance = instances[identifier]

  await instance.page.close()
  await instance.browser.close()
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
  const filename         = kebabCase(options.filename || `${path.basename(resolvedTestFile)}-${currentTestName}`)

  const savePath = path.join(
    path.resolve(resolvedBasePath, 'screenshots', path.dirname(resolvedTestFile)),
    `${filename}.png`,
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
