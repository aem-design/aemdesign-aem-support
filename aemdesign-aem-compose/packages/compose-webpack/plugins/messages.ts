import chalk from 'chalk'
import webpack from 'webpack'
import format from 'webpack-format-messages'

import { logger } from '@aem-design/compose-support'

// Internal
const pluginName = 'compose-messages'

/**
 * All credit and inspiration goes to https://github.com/lukeed/webpack-messages
 */
export default class ComposeMessages implements webpack.Plugin {
  private readonly name = chalk.bold('compose')

  private clear() {
    return process.stdout.write('\x1B[2J\x1B[3J\x1B[H\x1Bc')
  }

  private printError(message: string, errors: string[], warning: boolean = false) {
    const errorMessage = `${message}

    ${errors.join('')}`

    this.clear()

    if (warning) {
      logger.warning(errorMessage)
    } else {
      logger.error(errorMessage)
    }
  }

  apply(compiler: webpack.Compiler) {
    const onStart = () => {
      logger.info(`Building ${this.name}...`)
    }

    const onComplete = (stats: webpack.Stats) => {
      const messages = format(stats)

      if (messages.errors.length) {
        return this.printError(chalk.red(`Failed to compile ${this.name}!`), messages.errors)
      }

      if (messages.warnings.length) {
        return this.printError(chalk.yellow(`Compiled ${this.name} with warnings.`), messages.warnings, true)
      }

      const compileTime = (stats.endTime - stats.startTime) / 1e3

      logger.info(chalk.green(`Completed ${this.name} in ${compileTime}s!`))
    }

    if (compiler.hooks !== void 0) {
      compiler.hooks.compile.tap(pluginName, onStart)
      compiler.hooks.invalid.tap(pluginName, () => this.clear() && onStart())
      compiler.hooks.done.tap(pluginName, onComplete)
    } else {
      compiler.plugin('compile', onStart)
      compiler.plugin('invalid', () => this.clear() && onStart())
      compiler.plugin('done', onComplete)
    }
  }
}
