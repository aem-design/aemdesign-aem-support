import * as helpers from './helpers'

export function getMavenConfiguration(): MavenConfigMap {
  return {
    appsPath: helpers.getMavenConfigurationValueByPath<string>({
      path : 'package.appsPath[0]',
      pom  : './pom.xml',
    }),
    authorPort: helpers.getMavenConfigurationValueByPath<number>({
      path : 'crx.port[0]',
      pom  : '../pom.xml',
    }),
    sharedAppsPath: helpers.getMavenConfigurationValueByPath<string>({
      path : 'package.path.apps[0]',
      pom  : './pom.xml',
    }),
  }
}

export const projects = {
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
