const { union } = require('lodash')

const cypressTypeScriptPreprocessor = require('./cy-ts-preprocessor')

module.exports = (on) => {
  on('file:preprocessor', cypressTypeScriptPreprocessor)

  on('before:browser:launch', (browser = {}, args) => {
    if (browser.name === 'chrome') {
      args.push('--start-maximized')
      args.push('--disable-web-security')
      args.push('--allow-file-access-from-files')
      args.push('--allow-running-insecure-content')
      args.push('--allow-cross-origin-auth-prompt')
      args.push('--allow-file-access')
      args.push('--no-sandbox')
      args.push('--ignore-certificate-errors')
      args.push('--disable-infobars')
      args.push('--hide-scrollbars')
    }

    return union(args)
  })
}
