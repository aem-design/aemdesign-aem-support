import type {
  Browser,
  ElementHandle,
  Page,
} from 'playwright'

declare global {
  type PlaywrightScreenshotConstraints = ElementHandle | Page

  type PlaywrightScreenshotOptions<T extends PlaywrightScreenshotConstraints> =
    & Parameters<T['screenshot']>[0]
    & { filename?: string }

  interface BrowserInstance {
    browser: Browser;
    page: Page;
  }
}
