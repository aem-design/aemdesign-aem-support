import type {
  Browser,
  ElementHandle,
  Page,
} from 'playwright'

declare global {
  type PlayrightScreenshotOptions = Parameters<Pick<ElementHandle, 'screenshot'>['screenshot']>['0']

  interface BrowserInstance {
    browser: Browser;
    page: Page;
  }
}
