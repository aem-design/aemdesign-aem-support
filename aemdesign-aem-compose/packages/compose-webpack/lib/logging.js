const colors = require('colors')

// Internal
const LOG_TYPES = {
  ERROR   : 'error',
  INFO    : 'info',
  LOG     : 'log',
  WARNING : 'warn',
}

const LOG_TYPES_COLOURS = {
  ERROR   : colors.red('ERROR'),
  INFO    : colors.blue('INFO'),
  LOG     : 'LOG',
  WARNING : colors.yellow('WARN'),
}

function getLogLabel(value) {
  const logTypeKeys = Object.keys(LOG_TYPES)
  
  for (const logType of logTypeKeys) {
    if (LOG_TYPES[logType] === value) {
      return LOG_TYPES_COLOURS[logType]
    }
  }

  log(LOG_TYPES.ERROR, 'Unknown log type given: %s. Valid log types are: %s', value, logTypeKeys)

  return colors.gray('Unknown')
}

function error(...args) {
  log(LOG_TYPES.ERROR, ...args)
}

function info(...args) {
  log(LOG_TYPES.INFO, ...args)
}

function log(logType, ...args) {
  console[logType](`[${getLogLabel(logType)}]`, ...args)
}

function warning(...args) {
  log(LOG_TYPES.WARNING, ...args)
}

module.exports = {
  error,
  info,
  log,
  warning,
}
