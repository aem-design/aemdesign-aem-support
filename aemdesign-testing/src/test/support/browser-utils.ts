import path from 'path'
import { chromium, ElementHandle, Page } from 'playwright'

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
 * Launch a browser session with the given `identifier` which automatically logs in when
 * needed and returns a new `BrowserInstance` object.
 */
export async function launchBrowser(identifier: string, path: string): Promise<BrowserInstance> {
  if (Object.keys(instances).includes(identifier)) {
    return instances[identifier]
  }

  const browser   = await chromium.launch()
  const page      = await browser.newPage()
  const instance  = instances[identifier] = { browser, page }
  const needsAuth = (!!process.env.TEST_NO_AUTH) === false

  await page.goto(generatePageUrl(path, needsAuth), { waitUntil: 'load' })

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
 * Takes and stores a screenshot for the given `reference` object. By default all screenshots
 * are saved as JPEG but this can be overidden in `options`.
 */
export async function takeScreenshot(
  reference: ElementHandle | Page | null,
  filename: string,
  context: string,
  options: PlayrightScreenshotOptions = {},
): Promise<void> {
  const imageType = options.type ?? 'jpeg'

  const savePath = path.join(
    __dirname,
    '../screenshots/',
    context,
    `${filename}.${imageType}`,
  )

  await reference?.screenshot({
    path    : savePath,
    quality : 85,
    type    : imageType,

    ...options,
  })
}
