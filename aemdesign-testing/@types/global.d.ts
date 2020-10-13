import type {
  Browser,
  ElementHandle,
  Page,
} from 'playwright'

declare global {
  type PlaywrightElementScreenshotOptions =
    & Parameters<ElementHandle['screenshot']>[0]
    & { filename?: string }

  interface BrowserInstance {
    browser: Browser;
    page: Page;
  }
}
