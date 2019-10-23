import { resolve } from 'path'
import webpack from 'webpack'

import { getIfUtils } from 'webpack-config-utils'

import { logger } from '@aem-design/compose-support'

import { getMavenConfigurationValueByPath } from './helpers'

export enum ConfigurationType {
  MAVEN_PARENT = 'maven.parent',
  MAVEN_PROJECT = 'maven.project',
  PATH_CLIENTLIBS = 'paths.clientlibs',
  PATH_PUBLIC = 'paths.public',
  PATH_PUBLIC_AEM = 'paths.public.aem',
  PATH_SOURCE = 'paths.source',
}

export interface Configuration {
  [ConfigurationType.MAVEN_PARENT]: string;
  [ConfigurationType.MAVEN_PROJECT]: string;
  [ConfigurationType.PATH_CLIENTLIBS]: string | false;
  [ConfigurationType.PATH_PUBLIC]: string;
  [ConfigurationType.PATH_PUBLIC_AEM]: string;
  [ConfigurationType.PATH_SOURCE]: string;
}

export interface Environment extends webpack.ParserOptions {
  mode: 'development' | 'production';
  project: string;
}

export interface MavenConfigMap {
  authorPort: number;
  appsPath: string;
  sharedAppsPath: string;
}

export interface ProjectMap {
  [project: string]: Project;
}

export interface Project {
  [key: string]: any;
}

// Internal
const workingDirectory = process.cwd()

const configurationDefaults: Configuration = {
  [ConfigurationType.MAVEN_PARENT]    : resolve(workingDirectory, '../pom.xml'),
  [ConfigurationType.MAVEN_PROJECT]   : resolve(workingDirectory, './pom.xml'),
  [ConfigurationType.PATH_CLIENTLIBS] : false,
  [ConfigurationType.PATH_PUBLIC]     : resolve(workingDirectory, 'public'),
  [ConfigurationType.PATH_PUBLIC_AEM] : '/',
  [ConfigurationType.PATH_SOURCE]     : resolve(workingDirectory, 'src'),
}

const configuration: Configuration = {
  ...configurationDefaults,
}

const configKeys = Object.values(ConfigurationType)

export let environment: Environment = {
  mode    : 'development',
  project : null,
}

export function getConfiguration<T extends ConfigurationType, R extends Configuration[T]>(key: T): R {
  if (!configKeys.includes(key)) {
    throw new ReferenceError(`Unable to get configuration for ${key} as it isn't a valid configuration key. Avaliable configuration keys to use are:\n${configKeys.join(', ')}\n`)
  }

  return configuration[key] as R
}

export function setConfiguration<T extends ConfigurationType, V extends Configuration[T]>(key: T, value: V): void {
  if (!configKeys.includes(key)) {
    throw new ReferenceError(`Unable to set configuration for ${key} as it isn't a valid configuration key. Avaliable configuration keys to use are:\n${configKeys.join(', ')}\n`)
  }

  configuration[key] = value
}

/**
 * Stores our current Webpack configuration and environment variables.
 *
 * @param {webpack.ParserOptions} env Webpack environment configuration
 */
export function setupEnvironment(env: webpack.ParserOptions): void {
  environment = {
    ...env,

    mode    : env.dev === true ? 'development' : 'production',
    project : env.project,
  }

  // Ensure the project is valid
  if (!environment.project) {
    logger.error('Specify a project when running webpack eg --env.project="core"')
    process.exit(1)
  }
}

export function ifDev(obj: any) {
  return getIfUtils(environment).ifDevelopment(obj)
}

export function ifProd(obj: any) {
  return getIfUtils(environment).ifProduction(obj)
}

export function getProjectConfiguration(): Project {
  return projects[environment.project]
}

export function getProjectPath<T extends ConfigurationType>(path: T): string {
  return resolve(getConfiguration(path), environment.project)
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

export const projects: ProjectMap = {
  core: {
    outputName: 'app',

    hmr: {
      footer: {
        mapToOutput : [],
        outputName  : 'clientlibs-footer',
      },

      header: {
        mapToOutput : ['../../clientlibs-header/js/vendorlib/common'],
        outputName  : 'clientlibs-header',
      },
    },

    entryFile: {
      js   : 'app.ts',
      sass : 'app.scss',
    },

    additionalEntries: {
      '../../clientlibs-header/js/vendorlib/common': [
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
