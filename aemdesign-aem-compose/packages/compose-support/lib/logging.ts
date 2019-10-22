import colors from 'colors'

export enum LogTypes {
  ERROR = 'error',
  INFO = 'info',
  LOG = 'log',
  WARNING = 'warn',
}

// Internal
const LOG_TYPES_COLOURS = {
  ERROR   : colors.red('ERROR'),
  INFO    : colors.blue('INFO'),
  LOG     : 'LOG',
  WARNING : colors.yellow('WARN'),
}

function getLogLabel(value: string): string {
  const logTypeKeys = Object.keys(LogTypes)

  for (const logType of logTypeKeys) {
    if (LogTypes[logType] === value) {
      return colors.bold(LOG_TYPES_COLOURS[logType])
    }
  }

  log(LogTypes.ERROR, 'Unknown log type given: %s. Valid log types are: %s', value, logTypeKeys)

  return colors.gray('Unknown')
}

export function log(logType: LogTypes, ...args: any[]) {
  if (process.env.COMPOSE_SIMPLE_LOG) {
    console[logType](...args)
  } else {
    console[logType](`[${getLogLabel(logType)}]`, ...args)
  }
}

export function error(...args: any[]) {
  log(LogTypes.ERROR, ...args)
}

export function info(...args: any[]) {
  log(LogTypes.INFO, ...args)
}

export function warning(...args: any[]) {
  log(LogTypes.WARNING, ...args)
}
