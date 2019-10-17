import * as fs from 'fs'
import * as path from 'path'

import _ from 'lodash'
import * as xml2js from 'xml2js'

// Internal
const mavenConfigs: SavedMavenConfig = {}
const xmlParser: xml2js.Parser = new xml2js.Parser()

/**
 * Retrieve the Maven configuration using the given `filePath`.
 *
 * @param filePath Path to load the Maven configuration
 */
function getMavenConfigurationFromFile(filePath: string): string {
  if (mavenConfigs[filePath]) {
    return mavenConfigs[filePath]
  }

  return (mavenConfigs[filePath] = fs.readFileSync(path.resolve(__dirname, filePath), 'utf-8'))
}

export function getMavenConfigurationValueByPath<T, R = T>({ fallback, parser, path: propPath, pom }: MavenConfig<T>): R {
  let value = null

  xmlParser.parseString(getMavenConfigurationFromFile(pom), (_: any, { project }: any) => {
    const properties = project.properties[0]

    if (_.has(properties, propPath)) {
      value = _.get(properties, propPath)

      if (parser) {
        value = parser(value)
      }
    } else {
      value = fallback
    }
  })

  return value
}
