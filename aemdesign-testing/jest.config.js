/** @type {import('@jest/types').Config.InitialOptions} */
const config = {
  collectCoverageFrom: [],

  displayName: {
    color : 'yellow',
    name  : '@aem-design/testing',
  },

  roots: [
    '<rootDir>/src/test',
  ],
}

module.exports = config
