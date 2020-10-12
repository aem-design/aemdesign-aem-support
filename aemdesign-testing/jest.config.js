const { defaults: tsjPreset } = require('ts-jest/presets')

/** @typedef {import('ts-jest')} */
/** @type {import('@jest/types').Config.InitialOptions} */
const config = {
  preset: 'jest-playwright-preset',

  displayName: {
    color : 'yellow',
    name  : '@aem-design/testing',
  },

  roots: [
    '<rootDir>/src/test',
  ],

  setupFilesAfterEnv: [
    'expect-playwright',
  ],

  transform: {
    ...tsjPreset.transform,
  },
}

module.exports = config
