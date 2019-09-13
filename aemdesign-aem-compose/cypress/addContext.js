const addContext = (result, screenshots) => {
  const getTests = t => t.tests
  const getSuites = t => t.suites

  const addSuiteContext = (suite, previousTitles = []) => {
    const titles = suite.title ? previousTitles.concat(suite.title) : previousTitles

    suite.tests = getTests(suite).map(test => {
      if (!test.screenshot) {
        return test
      }

      test.timedOut = false // for some reason this is dropped
      const context = JSON.parse(test.context || '[]')

      const testFileName = titles
        .concat(test.title)
        .join(' -- ')
        .replace(/,/g, '')

      const testScreenshots = screenshots.filter((s) => {
        const testName = s.split('\\').pop().split('/').pop().replace(/\.[^/.]+$/, '')

        return new RegExp(`^${testFileName}`).test(testName)
      })

      testScreenshots.forEach(screenshot => {
        const screenshotRelativePath = screenshot.substr(screenshot.lastIndexOf('screenshots/'), screenshot.length)

        if (screenshot.includes('(failed)')) {
          context.push({
            title: 'Failure Screenshot',
            value: screenshotRelativePath,
          })
        } else {
          const screenshotTitle = screenshot.substr(screenshot.lastIndexOf('/') + 1, screenshot.length)

          context.push({
            title: screenshotTitle.replace(/\s--?/g, '').replace(/\s\(\d{1,3}\)$/, '.png'),
            value: screenshotRelativePath,
          })
        }
      })

      test.context = JSON.stringify(context)

      return test
    })

    suite.suites = getSuites(suite).map(s => addSuiteContext(s, titles))
  }

  for (const suite of result.suites) {
    addSuiteContext(suite)
  }
}

module.exports = addContext
