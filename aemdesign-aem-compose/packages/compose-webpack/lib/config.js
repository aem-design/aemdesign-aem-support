const { getMavenConfigurationValueByPath } = require('./helpers')

function getMavenConfiguration(paths) {
  return {
    appsPath: getMavenConfigurationValueByPath({
      parser : (value) => value[0],
      path   : 'package.appsPath',
      pom    : paths.child,
    }),
    authorPort: getMavenConfigurationValueByPath({
      parser : (value) => value[0],
      path   : 'crx.port',
      pom    : paths.parent,
    }),
    sharedAppsPath: getMavenConfigurationValueByPath({
      parser : (value) => value[0],
      path   : 'package.path.apps',
      pom    : paths.child,
    }),
  }
}

const projects = {
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

module.exports = {
  getMavenConfiguration,
  projects,
}
