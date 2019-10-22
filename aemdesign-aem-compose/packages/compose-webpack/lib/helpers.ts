import { readFileSync } from 'fs'
import { resolve } from 'path'

import { get } from 'lodash'
import xml2js from 'xml2js'

interface MavenConfig<R> {
  fallback?: R;
  parser?: (value: any) => R;
  path: string;
  pom: string;
}

interface SavedMavenConfig {
  [key: string]: string;
}

// Internal
const mavenConfigs: SavedMavenConfig = {}
const xmlParser: xml2js.Parser = new xml2js.Parser()

/**
 * Retrieve the Maven configuration using the given `filePath`.
 *
 * @private
 * @param {string} filePath Path to load the Maven configuration
 */
function getMavenConfigurationFromFile(filePath: string): string {
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
export function getMavenConfigurationValueByPath<R>({ fallback, parser, path: propPath, pom }: MavenConfig<R>): R {
  let value: R = null

  xmlParser.parseString(getMavenConfigurationFromFile(pom), (_: any, { project }: any) => {
    const properties = project.properties[0]

    value = get(properties, propPath, fallback)

    if (parser) {
      value = parser(value)
    }
  })

  return value
}
