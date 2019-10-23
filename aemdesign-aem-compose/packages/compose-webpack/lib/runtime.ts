import chalk from 'chalk'
import dotenv from 'dotenv'
import figlet from 'figlet'

import { getMavenConfiguration } from './config'

import { logger } from '@aem-design/compose-support'

const {
  appsPath,
  authorPort,
  sharedAppsPath,
} = getMavenConfiguration()

export default () => {
  dotenv.config()

  /**
   * Support banner
   */
  console.log(figlet.textSync(process.env.BANNER_TEXT, {
    font: process.env.BANNER_FONT as figlet.Fonts,
  }))

  /**
   * General output
   */
  logger.info('Starting up the webpack bundler...')
  logger.info('')

  /**
   * Ensure our Maven config values are valid before continuing on...
   */
  if (!(authorPort || appsPath || sharedAppsPath)) {
    logger.error('Unable to continue due to missing or invalid Maven configuration values!')
    process.exit(1)
  }

  logger.info(chalk.bold('Maven configuration'))
  logger.info('-------------------')
  logger.info(chalk.bold('Author Port         :'), authorPort)
  logger.info(chalk.bold('Apps Path           :'), appsPath)
  logger.info(chalk.bold('Shared Apps Path    :'), sharedAppsPath)
  logger.info('')
}
