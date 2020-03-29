const { existsSync, readFileSync, writeFileSync } = require('fs')
const { resolve }  = require('path')
const { safeLoad } = require('js-yaml')

let categories

function generateColours() {
  const colourCategories = []

  for (const category of Object.keys(categories)) {
    const colours = (categories[category].colours || [])
      .filter((colour) => !(colour.background && colour.deprecated))
      .map((colour) => `    '${colour.class}': #${colour.hex},`).join('\n')

    colourCategories.push(`  ${category}: (
${colours}
  ),`)
  }

  return colourCategories.join('\n\n')
}

function generateMappedColours() {
  const coloursMapped = []

  for (const category of Object.keys(categories)) {
    coloursMapped.push(...(categories[category].colours || [])
      .filter((colour) => !(colour.background && colour.deprecated))
      .map((colour) => `  '${colour.class}': map-deep-get($brand-colours, '${category}', '${colour.class}'),`))
  }

  return coloursMapped.join('\n')
}

/**
 * Save the generated colours file to the given `path`.
 *
 * @param {string} path Location to store the generated file
 * @throws {Error} When the path is invalid
 */
function saveColours(path) {
  if (!path) {
    throw new Error(`Unable to save colours as 'path' is invalid!\n${path}`)
  }

  const stubbedOutput = readFileSync(resolve(__dirname, '../stubs/colours.scss')).toString()
    .replace('%%colours%%', generateColours())
    .replace('%%colours-mapped%%', generateMappedColours())

  writeFileSync(path, stubbedOutput)
}

/**
 * Build the colours lists from the pre-defined colours YAML configuration.
 *
 * @param {string} [path=null] Location to store the generated file
 * @param {string} [filename='colours'] Configuration file name for the colours
 * @throws {Error} When the colours file path is invalid
 */
function buildAndSaveColours(path = null, filename = 'colours') {
  const coloursFilePath = resolve(__dirname, `../config/${filename}.yml`)

  if (!existsSync(coloursFilePath)) {
    throw new Error(`Colours configuration file doesn't exist!\n${coloursFilePath}`)
  }

  categories = safeLoad(readFileSync(coloursFilePath))

  saveColours(path)
}

module.exports = {
  buildAndSaveColours,
}
