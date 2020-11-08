const { defaults: tsjPreset }     = require('ts-jest/presets')
const { pathsToModuleNameMapper } = require('ts-jest/utils')

const { compilerOptions } = require('./tsconfig.json')

/** @typedef {import('ts-jest')} */
/** @type {import('@jest/types').Config.InitialOptions} */
const config = {
  preset  : 'jest-playwright-preset',
  verbose : true,

  displayName: {
    color : 'yellow',
    name  : '@aem-design/testing (e2e)',
  },

  moduleNameMapper: {
    ...pathsToModuleNameMapper(compilerOptions.paths, { prefix : '<rootDir>/' }),
  },

  setupFilesAfterEnv: [
    '<rootDir>/jest-setup.js',
    'expect-playwright',
  ],

  transform: {
    ...tsjPreset.transform,
  },
}

module.exports = config
