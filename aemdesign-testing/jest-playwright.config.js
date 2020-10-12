module.exports = {
  browsers : ['chromium'],
  devices  : [],

  contextOptions: {
    ignoreHTTPSErrors: false,

    viewport: {
      width: 1920,
      height: 1080
    }
  },

  launchOptions: {
    headless: true,
  },
}
