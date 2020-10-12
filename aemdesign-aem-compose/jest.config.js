const { pathsToModuleNameMapper } = require('ts-jest/utils')

const tsConfig = require('./tsconfig.json')

/** @typedef {import('ts-jest')} */
/** @type {import('@jest/types').Config.InitialOptions} */
const config = {
  preset: 'ts-jest/presets/js-with-ts',

  collectCoverageFrom: [
    'src/core/**/*.{ts,vue}',
    '!**/node_modules/**',
  ],

  displayName: {
    color : 'blue',
    name  : '@aem-design/compose',
  },

  globals: {
    'ts-jest': {
      packageJson : '<rootDir>/package.json',
      tsConfig    : '<rootDir>/tsconfig.json',
    },
  },

  moduleNameMapper: {
    ...pathsToModuleNameMapper(tsConfig.compilerOptions.paths, { prefix: '<rootDir>/' }),
  },

  roots: [
    '<rootDir>/test',
  ],

  transform: {
    '^.+\\.vue$': 'vue-jest',
  },
}

module.exports = config
