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
const backgroundContentPath = 'dls/guidelines/backgrounds/.content.xml'
const coloursContentPath    = 'dls/guidelines/colours/.content.xml'

// Define the background colours
replaceHtmlContent(backgroundContentPath, 'contentblock_backgrounds_component', generateColourPanel(colours, true))
replaceHtmlContent(coloursContentPath, 'contentblock_colours_component', generateColourPanel(colours))

// Sync the pages...
// TODO: Try and work out why this doesn't work!
// syncFileToAEM(backgroundContentPath)
// syncFileToAEM(coloursContentPath)
