/** @typedef {import('ts-jest')} */
/** @type {import('@jest/types').Config.InitialOptions} */
const config = {
  collectCoverage      : process.env.NODE_ENV === 'test',
  preset               : 'ts-jest/presets/js-with-ts',
  moduleFileExtensions : ['ts', 'js', 'json', 'node'],
  testEnvironment      : 'node',
  testRegex            : '((\\.|/)(test|spec))\\.ts?$',
  verbose              : true,

  projects: [
    '<rootDir>/aemdesign-aem-compose/jest.config.js',
    '<rootDir>/aemdesign-testing/jest.config.js',
  ],
}

module.exports = config
