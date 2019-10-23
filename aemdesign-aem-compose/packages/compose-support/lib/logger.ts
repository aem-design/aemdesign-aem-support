import chalk from 'chalk'

export enum LogType {
  ERROR = 'error',
  INFO = 'info',
  LOG = 'log',
  WARNING = 'warn',
}

// Internal
const LOG_TYPES_COLOURS = {
  ERROR   : chalk.bgRed(' ERROR '),
  INFO    : chalk.bgBlue(' INFO '),
  LOG     : 'LOG',
  WARNING : chalk.bgYellow(' WARN '),
}

function getLogLabel(value: string): string {
  const logTypeKeys = Object.keys(LogType)

  for (const logType of logTypeKeys) {
    if (LogType[logType] === value) {
      return chalk.bold(LOG_TYPES_COLOURS[logType])
    }
  }

  log(LogType.ERROR, 'Unknown log type given: %s. Valid log types are: %s', value, logTypeKeys)

  return chalk.bgWhiteBright('Unknown')
}

export function log(logType: LogType, ...args: any[]): void {
  if (process.env.COMPOSE_SIMPLE_LOG) {
    console[logType](...args)
  } else {
    console[logType](`${getLogLabel(logType)}`, ...args)
  }
}

export function error(...args: any[]): void {
  log(LogType.ERROR, ...args)
}

export function info(...args: any[]): void {
  log(LogType.INFO, ...args)
}

export function warning(...args: any[]): void {
  log(LogType.WARNING, ...args)
}
