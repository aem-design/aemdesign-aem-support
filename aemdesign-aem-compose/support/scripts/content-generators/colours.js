const { readFileSync } = require('fs')
const { resolve }      = require('path')
const { safeLoad }     = require('js-yaml')

const colours = safeLoad(readFileSync(resolve(__dirname, '../../config/colours.yml')))

const {
  replaceHtmlContent,
  syncFileToAEM,
} = require('../support/utilities')

const { generateColourPanel } = require('../support/colour-panel')

// Define the path to path in which we need to override
const coloursContentPath = 'dls/guidelines/colours/.content.xml'

// Generate the DLS colours page
replaceHtmlContent(coloursContentPath, 'contentblock_colours_component', generateColourPanel(colours))

// Sync the page...
// TODO: Try and work out why this doesn't work!
// syncFileToAEM(coloursContentPath)
