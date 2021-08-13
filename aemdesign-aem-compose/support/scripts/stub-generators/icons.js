const { existsSync, readFileSync, writeFileSync } = require('fs')
const { resolve }                                 = require('path')
const { safeLoad }                                = require('js-yaml')

const duplicateRefs = {}

const brandIcons = []
const solidIcons = []

/**
 * Checks if the given `refName` exists already in the `list`.
 *
 * @param {Array<{ refName: string }>} list Icons list
 * @param {string} refName Name of the icon reference
 * @return {boolean}
 */
function hasDuplicateIcon(list, refName) {
  return list.filter((icon) => icon.refName === refName).length
}

/**
 * Substitute the reference name of the icon with a deduped version so icons across categories can
 * be imported at the same time.
 *
 * @param {{ refName: string, suffix: string }} icon Said icon to check
 * @param {boolean} forImport ES6 import statement?
 * @return {string}
 */
function substituteIconRefName(icon, forImport) {
  if (duplicateRefs[icon.refName]) {
    const substitute = `${icon.refName}${icon.suffix}`

    return forImport ? `${icon.refName} as ${substitute}` : substitute
  }

  return icon.refName
}

/**
 * Generates the correct list of icons for the given `forImport` context.
 *
 * @param {string[]} icons List of icons
 * @param {boolean} [forImport=false] ES6 import statement?
 * @return {string}
 */
function generateIconsList(icons, forImport = false) {
  return icons.map((icon) => substituteIconRefName(icon, forImport)).sort().join(', ')
}

/**
 * Build the icon lists from the pre-defined icons YAML configuration.
 *
 * @param {string} filename Configuration file name for the icons
 * @throws {Error} When the icons file path is invalid
 */
function buildIcons(filename) {
  const iconsFilePath = resolve(__dirname, `../../config/${filename}.yml`)

  if (!existsSync(iconsFilePath)) {
    throw new Error("Icons configuration file doesn't exist!")
  }

  const icons = safeLoad(readFileSync(iconsFilePath))

  for (const icon of icons) {
    const iconClass = icon.class.split('-').map((word) => (
      word.charAt(0).toUpperCase() + word.substr(1)
    )).join('')

    const refName = `fa${iconClass}`

    const suffix = icon.prefix.charAt(0).toUpperCase() + icon.prefix.substr(1)

    // Check if the reference name already exists
    if (
      hasDuplicateIcon(solidIcons, refName) ||
      hasDuplicateIcon(brandIcons, refName)
    ) {
      duplicateRefs[refName] = true
    }

    // Assign the icon to a specific category based on its prefix
    if (icon.prefix === 'fas') {
      solidIcons.push({ refName, suffix })
    } else if (icon.prefix === 'fab') {
      brandIcons.push({ refName, suffix })
    }
  }
}

/**
 * Save the generated icons file to the given `path`.
 *
 * @param {string} path Location to store the generated file
 * @throws {Error} When the path is invalid
 */
function saveIcons(path) {
  if (!path) {
    throw new Error(`Unable to save icons as 'path' is invalid: ${path}`)
  }

  const stubbedOutput = readFileSync(resolve(__dirname, '../../stubs/icons.ts')).toString()
    .replace('%%brands-import%%', generateIconsList(brandIcons, true))
    .replace('%%solid-import%%', generateIconsList(solidIcons, true))
    .replace('%%all-icons%%', [
      generateIconsList(brandIcons),
      generateIconsList(solidIcons),
    ].filter((x) => x).join(', '))

  writeFileSync(path, stubbedOutput)
}

/**
 * Build the icon lists from the pre-defined icons YAML configuration.
 *
 * @param {string} [path=null] Location to store the generated file
 * @param {string} [filename='icons'] Configuration file name for the icons
 */
function buildAndSaveIcons(path = null, filename = 'icons') {
  buildIcons(filename)
  saveIcons(path)
}

module.exports = {
  buildAndSaveIcons,
}
