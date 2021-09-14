import type {
  BrowserContext,
  ElementHandle,
  Page,
  ViewportSize,
} from 'playwright'

declare global {
  type PlaywrightDeviceDescriptor = {
    defaultBrowserType: 'chromium' | 'firefox' | 'webkit';
    deviceScaleFactor: number;
    hasTouch: boolean;
    isMobile: boolean;
    userAgent: string;
    viewport: ViewportSize;
  }

  type PlaywrightScreenshotConstraints = ElementHandle | Page

  type PlaywrightScreenshotOptions<T extends PlaywrightScreenshotConstraints> =
    & Parameters<T['screenshot']>[0]
    & { filename?: string }

  interface PlaywrightBrowserInstance {
    context: BrowserContext;
    page: Page;
  }
}
