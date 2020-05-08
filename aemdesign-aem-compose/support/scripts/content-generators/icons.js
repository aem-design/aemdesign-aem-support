const { readFileSync } = require('fs')
const { resolve }      = require('path')
const { safeLoad }     = require('js-yaml')

const icons = safeLoad(readFileSync(resolve(__dirname, '../../config/icons.yml')))

const {
  replaceHtmlContent,
  syncFileToAEM,
} = require('../support/utilities')

const { generateIconPanel } = require('../support/icon-panel')

// Define the path to path in which we need to override
const iconsContentPath = 'dls/guidelines/icons/.content.xml'

// Generate the DLS icons page
replaceHtmlContent(iconsContentPath, 'contentblock_icons_all_component', generateIconPanel(icons))

// Sync the page...
// TODO: Try and work out why this doesn't work!
// syncFileToAEM(iconsContentPath)
