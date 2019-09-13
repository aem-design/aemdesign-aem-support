const cypress     = require('cypress')
const fg          = require('fast-glob')
const fse         = require('fs-extra')
const { merge }   = require('mochawesome-merge')
const generator   = require('mochawesome-report-generator')
const { resolve } = require('path')

const addContext = require('./addContext')

const cypressBasePath = 'cypress/integration'
const reportDir       = resolve(__dirname, 'reports')

const specPaths = process.argv[2] ?
  process.argv.slice(2).map((name) => `${cypressBasePath}/${name.trim()}.spec.ts`):
  [`${cypressBasePath}/**/*.spec.ts`]

function parseSuites(result, suites, screenshot) {
  for (const suite of suites) {
    if (!screenshot.path.includes(suite.title)) {
      continue
    }

    for (const test of suite.tests) {
      test.screenshot = test.code.includes('.screenshot()')
    }

    addContext(result, [screenshot.path])

    // Keep going down...
    if (suite.suites) {
      parseSuites(result, suite.suites, screenshot)
    }
  }
}

async function runTests() {
  const spec = await fg(specPaths, { absolute: true })

  // Remove the report folder and recreate the whole path
  await fse.remove(reportDir)
  await fse.mkdirs(resolve(reportDir, 'screenshots'))

  // Run Cypress against any test specs supplied
  const { runs, totalFailed, totalTests } = await cypress.run({
    browser: 'chrome',
    spec,
  })

  // No results were run, this is probably due to an error
  if (totalTests === undefined) {
    throw new Error('Something went wrong while generating the results!')
  }

  // Generate JSON report and HTML report
  const jsonReport = await merge({
    reportDir,
  })

  for (const { screenshots } of runs) {
    for (const screenshot of screenshots) {
      for (const result of jsonReport.results) {
        parseSuites(result, result.suites, screenshot)
      }
    }
  }

  await generator.create(jsonReport, {
    inline          : true,
    reportDir,
    reportFilename  : 'summary',
    reportPageTitle : 'UI Results',
    reportTitle     : 'UI Results',
    saveJson        : true,
  })

  process.exit(totalFailed)
}

runTests()
