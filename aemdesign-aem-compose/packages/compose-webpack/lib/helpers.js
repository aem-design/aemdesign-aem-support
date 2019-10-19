const { readFileSync } = require('fs')
const { resolve } = require('path')

const { get } = require('lodash')
const xml2js = require('xml2js')

// Internal
const mavenConfigs = {}
const xmlParser = new xml2js.Parser()

/**
 * Retrieve the Maven configuration using the given `filePath`.
 *
 * @private
 * @param filePath Path to load the Maven configuration
 */
function getMavenConfigurationFromFile(filePath) {
  if (mavenConfigs[filePath]) {
    return mavenConfigs[filePath]
  }

  return (mavenConfigs[filePath] = readFileSync(resolve(__dirname, filePath), 'utf-8'))
}

/**
 * Gets the Maven configuration from the file system and returns the value requested.
 * 
 * @return {string} Found value or the given `fallback`
 */
function getMavenConfigurationValueByPath({ fallback, parser, path: propPath, pom }) {
  let value = null

  xmlParser.parseString(getMavenConfigurationFromFile(pom), (_, { project }) => {
    const properties = project.properties[0]

    value = get(properties, propPath, fallback)

    if (parser) {
      value = parser(value)
    }
  })

  return value
}

module.exports = {
  getMavenConfigurationValueByPath,
}
