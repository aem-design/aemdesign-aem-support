import { resolve } from 'path'
import webpack from 'webpack'

import { getIfUtils } from 'webpack-config-utils'

import { getMavenConfigurationValueByPath } from './helpers'

export enum ConfigurationType {
  MAVEN_PARENT = 'maven.parent',
  MAVEN_PROJECT = 'maven.project',
  PATH_PUBLIC = 'paths.public',
  PATH_SOURCE = 'paths.source',
  PROJECT_NAME = 'project',
}

interface Configuration {
  [ConfigurationType.MAVEN_PARENT]: string;
  [ConfigurationType.MAVEN_PROJECT]: string;
  [ConfigurationType.PATH_PUBLIC]: string;
  [ConfigurationType.PATH_SOURCE]: string;
  [ConfigurationType.PROJECT_NAME]: string;
}

interface MavenConfigMap {
  authorPort: number;
  appsPath: string;
  sharedAppsPath: string;
}

interface Projects {
  [project: string]: {
    [key: string]: any;
  }
}

// Internal
const workingDirectory = process.cwd()

const configurationDefaults: Configuration = {
  [ConfigurationType.MAVEN_PARENT]  : resolve(workingDirectory, '../pom.xml'),
  [ConfigurationType.MAVEN_PROJECT] : resolve(workingDirectory, './pom.xml'),
  [ConfigurationType.PATH_PUBLIC]   : resolve(workingDirectory, 'public'),
  [ConfigurationType.PATH_SOURCE]   : resolve(workingDirectory, 'source'),
  [ConfigurationType.PROJECT_NAME]  : null,
}

const configuration: Configuration = {
  ...configurationDefaults,
}

const configKeys = Object.keys(ConfigurationType)

export let environment: webpack.ParserOptions = {}

export function getConfiguration<T extends ConfigurationType, R extends Configuration[T]>(key: T): R {
  if (configKeys.indexOf(key) === -1) {
    throw new ReferenceError(`Unable to get configuration for ${key} as it isn't a valid configuration key. Avaliable configuration keys to use are:\n${configKeys.join(', ')}`)
  }

  return configuration[key] as R
}

export function setConfiguration<T extends ConfigurationType, V extends Configuration[T]>(key: T, value: V): void {
  if (configKeys.indexOf(key) === -1) {
    throw new ReferenceError(`Unable to set configuration for ${key} as it isn't a valid configuration key. Avaliable configuration keys to use are:\n${configKeys.join(', ')}`)
  }

  configuration[key] = value
}

export function getProjectName(): string {
  return configuration[ConfigurationType.PROJECT_NAME]
}

/**
 * Stores our current Webpack configuration and environment variables.
 *
 * @param {webpack.ParserOptions} env Webpack environment configuration
 */
export function setEnvironment(env: webpack.ParserOptions): void {
  environment = env
}

export function ifDev(obj: any) {
  return getIfUtils(environment).ifDevelopment(obj)
}

export function ifProd(obj: any) {
  return getIfUtils(environment).ifProduction(obj)
}

export function getProjectPath(
  path: ConfigurationType.PATH_PUBLIC | ConfigurationType.PATH_SOURCE = ConfigurationType.PATH_SOURCE,
): string {
  return resolve(configuration[path], getProjectName())
}

export function getMavenConfiguration(): MavenConfigMap {
  return {
    appsPath: getMavenConfigurationValueByPath<string>({
      parser : (value) => value[0],
      path   : 'package.appsPath',
      pom    : configuration[ConfigurationType.MAVEN_PROJECT],
    }),

    authorPort: getMavenConfigurationValueByPath<number>({
      parser : (value) => value[0],
      path   : 'crx.port',
      pom    : configuration[ConfigurationType.MAVEN_PARENT],
    }),

    sharedAppsPath: getMavenConfigurationValueByPath<string>({
      parser : (value) => value[0],
      path   : 'package.path.apps',
      pom    : configuration[ConfigurationType.MAVEN_PROJECT],
    }),
  }
}

export const projects: Projects = {
  core: {
    outputName: 'app',

    hmr: {
      footer: {
        mapToOutput : [],
        outputName  : 'clientlibs-footer',
      },

      header: {
        mapToOutput : ['vendorlib/common'],
        outputName  : 'clientlibs-header',
      },
    },

    entryFile: {
      js   : 'app.ts',
      sass : 'app.scss',
    },

    additionalEntries: {
      'vendorlib/common': [
        './core/js/vendor.ts',
        'es6-promise/auto',
        'classlist.js',
        'picturefill',
        'bootstrap/js/dist/util',
        'bootstrap/js/dist/collapse',
        'bootstrap/js/dist/dropdown',
      ],
    },
  },

  styleguide: {
    outputName: 'styleguide',

    entryFile: {
      js   : 'styleguide.ts',
      sass : 'styleguide.scss',
    },

    additionalEntries: {},
  },
}
