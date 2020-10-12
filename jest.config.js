const { defaults } = require('jest-config')

/** @type {import('@jest/types').Config.InitialOptions} */
const config = {
  collectCoverage      : process.env.NODE_ENV === 'test',
  moduleFileExtensions : [...defaults.moduleFileExtensions],
  testEnvironment      : 'jsdom',
  testRegex            : '((\\.|/)(test|spec))\\.(j|t)s?$',
  verbose              : true,

  projects: [
    '<rootDir>/aemdesign-aem-compose/jest.config.js',
    '<rootDir>/aemdesign-testing/jest.config.js',
  ],
}

module.exports = config
