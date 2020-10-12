import { chromium, Browser, Page } from 'playwright'

let browser: Browser
let page: Page

describe('Foo', () => {
  beforeAll(async () => {
    browser = await chromium.launch()
  })

  beforeEach(async () => {
    page = await browser.newPage()
  });

  test('2 is greater than 1', () => {
    expect(2).toBeGreaterThan(1)
  })

  it('should work', async () => {
    await page.goto('https://www.google.com.au')

    expect(await page.title()).toBe('Google')
  })

  afterEach(async () => {
    await page.close()
  })

  afterAll(async () => {
    await browser.close()
  })
})
