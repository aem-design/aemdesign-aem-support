import {
  closeBrowser,
  launchBrowser,
  takeScreenshot,
} from '@/support/browser-utils'

let instance: BrowserInstance

describe('Page Details', () => {
  beforeAll(async () => {
    instance = await launchBrowser(
      __filename,
      '/content/aemdesign-showcase/au/en/dls/components/details/page-details'
    )
  })

  it('should load the page details dls page', async () => {
    expect(await instance.page.title()).toContain('Page Details')
  })

  describe('title only', () => {
    it('should exist', async () => {
      await expect(instance.page).toHaveSelector('#page_details_default_component')

      expect(await takeScreenshot(await instance.page.$('#page_details_default_component')))
        .toMatchImageSnapshot()
    })

    it('should have the correct title text', async () => {
      await expect(instance.page).toHaveText('#page_details_default_component .c-page-details__title', 'Heading')
    })
  })

  describe('title + description', () => {
    it('should exist', async () => {
      await expect(instance.page).toHaveSelector('#page_details_with_description_component')

      expect(await takeScreenshot(await instance.page.$('#page_details_with_description_component')))
        .toMatchImageSnapshot()
    })

    it('should have the correct title and body text', async () => {
      await expect(instance.page).toHaveText(
        '#page_details_with_description_component .c-page-details__title',
        'Heading',
      )

      await expect(instance.page).toHaveText(
        '#page_details_with_description_component .c-page-details__description',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
      )
    })
  })

  describe('breadcrumb', () => {
    it('should exist', async () => {
      await expect(instance.page).toHaveSelector('#page_details_with_breadcrumb_component')

      expect(await takeScreenshot(await instance.page.$('#page_details_with_breadcrumb_component')))
        .toMatchImageSnapshot()
    })

    it('should have breadcrumbs', async () => {
      await expect(instance.page).toHaveText(
        '#page_details_with_breadcrumb_component .breadcrumb-item:nth-child(2)',
        'DLS',
      )
    })
  })

  describe('parsys', () => {
    it('should exist', async () => {
      await expect(instance.page).toHaveSelector('#page_details_with_parsys_component')

      expect(await takeScreenshot(await instance.page.$('#page_details_with_parsys_component')))
        .toMatchImageSnapshot()
    })

    it('should have breadcrumbs', async () => {
      await expect(instance.page).toHaveText(
        '#page_details_with_parsys_component_text',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
      )
    })
  })

  describe('without container', () => {
    it('should exist', async () => {
      await expect(instance.page).toHaveSelector('#page_details_without_container_component')

      expect(await takeScreenshot(await instance.page.$('#page_details_without_container_component')))
        .toMatchImageSnapshot()
    })

    it('should not have a container', async () => {
      expect(await instance.page.$('#page_details_without_container_component > .container')).toBeNull()
    })
  })

  afterAll(async () => {
    await closeBrowser(__filename)
  })
})
