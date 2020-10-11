const { pathsToModuleNameMapper } = require('ts-jest/utils')

const tsConfig = require('./tsconfig.json')

/** @typedef {import('ts-jest')} */
/** @type {import('@jest/types').Config.InitialOptions} */
const config = {
  displayName: {
    color : 'blue',
    name  : '@aem-design/compose',
  },

  moduleNameMapper: {
    ...pathsToModuleNameMapper(tsConfig.compilerOptions.paths, { prefix : '<rootDir>/' }),
  },
}

module.exports = config
