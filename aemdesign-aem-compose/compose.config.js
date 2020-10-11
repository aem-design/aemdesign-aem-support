const { resolve }                     = require('path')
const { configuration, registerHook } = require('@aem-design/compose-webpack')
const { DefinePlugin }                = require('webpack')

registerHook('init:post', {
  before(env, project) {
    // Do we need to generate any colours or icons for this project?
    if (project.generateColours === true) {
      const { buildAndSaveColours } = require('./support/scripts/stub-generators/colours')

      buildAndSaveColours(
        resolve(env.paths.project.src, project.coloursFilePath),
        project.coloursConfigFilename
      )
    }

    if (project.generateIcons === true) {
      const { buildAndSaveIcons } = require('./support/scripts/stub-generators/icons')

      buildAndSaveIcons(
        resolve(env.paths.project.src, project.iconsFilePath),
        project.iconsConfigFilename
      )
    }
  },
})

module.exports = configuration({
  features: ['bootstrap', 'typescript', 'vue'],

  standard: {
    banner: {
      font: 'ANSI Shadow',
    },

    mergeProjects: true,

    projects: {
      core: {
        additionalEntries: {
          'vendorlib/common': [
            'lazysizes',
            'lazysizes/plugins/object-fit/ls.object-fit',
            'lazysizes/plugins/parent-fit/ls.parent-fit',
          ],
        },

        coloursConfigFilename : 'colours',
        coloursFilePath       : 'scss/settings/_colours.scss',
        generateColours       : true,
        generateIcons         : true,
        iconsConfigFilename   : 'icons',
        iconsFilePath         : 'js/modules/fontawesome.ts',
      },
    },
  },

  webpack: (env) => ({
    module: {
      rules: [
        {
          loader : 'expose-loader',
          test   : require.resolve('jquery'),

          options: {
            exposes: ['$', 'jQuery'],
          },
        },
      ],
    },

    optimization: {
      splitChunks: {
        cacheGroups: {
          jquery: env.hmr === true ? false : {
            filename : 'clientlibs-footer/js/vendorlib/jquery.js',
            name     : 'jquery',
            test     : /[\\/]node_modules[\\/](jquery)[\\/]/,
          },
        },
      },
    },

    plugins: [
      new DefinePlugin({
        __TESTING__: false,

        __VUE_OPTIONS_API__   : JSON.stringify(true),
        __VUE_PROD_DEVTOOLS__ : JSON.stringify(env.prod !== false),
      }),
    ],

    resolve: {
      alias: {
        'vue': env.prod === true ? 'vue/dist/vue.runtime.esm-browser.prod.js' : 'vue/dist/vue.esm-bundler.js',
      },
    },
  }),
})
