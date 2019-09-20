function getPercentClass(pct) {
  if (pct <= 50) {
    return 'danger'
  } else if (pct > 50 && pct < 80) {
    return 'warning'
  } else {
    return 'success'
  }
}

const min = (a, b) => (a < b ? a : b)
const max = (a, b) => (a > b ? a : b)

function mergedReports(reports) {
  const baseStats = {
    suites: 0,
    tests: 0,
    passes: 0,
    pending: 0,
    failures: 0,
    start: '2050-12-31T00:00:00.000Z',
    end: '1940-01-01T00:00:00.000Z',
    duration: 0,
    testsRegistered: 0,
    passPercent: 0,
    pendingPercent: 0,
    other: 0,
    hasOther: false,
    skipped: 0,
    hasSkipped: false,
    passPercentClass: 'success',
    pendingPercentClass: 'success',
  }

  const mergeStats = (result, stat) => ({
    suites: result.suites + stat.suites,
    tests: result.tests + stat.tests,
    passes: result.passes + stat.passes,
    pending: result.pending + stat.pending,
    failures: result.failures + stat.failures,
    start: min(result.start, stat.start),
    end: max(result.end, stat.end),
    duration: result.duration + stat.duration,
    testsRegistered: result.testsRegistered + stat.testsRegistered,
    other: result.other + stat.other,
    hasOther: result.hasOther || stat.hasOther,
    skipped: result.skipped + stat.skipped,
    hasSkipped: result.hasSkipped || stat.hasSkipped,
  })

  const stats = reports.map(f => f.stats).reduce(mergeStats, baseStats)

  // calculated stats
  stats.passPercent = Math.round(stats.passes / stats.tests * 100)
  stats.pendingPercent = Math.round(stats.pending / stats.tests * 100)
  stats.passPercentClass = getPercentClass(stats.passPercent)
  stats.pendingPercentClass = getPercentClass(stats.pendingPercent)

  /** Combine fields by merging the arrays  */
  const concatFields = field => (result, item) => result.concat(item.results[0][field])

  const suites = reports.reduce(concatFields('suites'), [])

  const mergedReport = {
    stats,
    results: suites,
    copyrightYear: new Date().getFullYear(),
  }

  return mergedReport
}

module.exports = mergedReports
