const { resolve } = require('path')
const { configuration, registerHook } = require('@aem-design/compose-webpack')
const { DefinePlugin } = require('webpack')

registerHook('init:post', {
  before(env, project) {
    // Do we need to generate any colours or icons for this project?
    if (project.generateColours === true) {
      const {
        buildAndSaveColours,
      } = require('./support/scripts/stub-generators/colours')

      buildAndSaveColours(
        resolve(env.paths.project.src, project.coloursFilePath),
        project.coloursConfigFilename,
      )
    }

    if (project.generateIcons === true) {
      const {
        buildAndSaveIcons,
      } = require('./support/scripts/stub-generators/icons')

      buildAndSaveIcons(
        resolve(env.paths.project.src, project.iconsFilePath),
        project.iconsConfigFilename,
      )
    }
  },
})

module.exports = configuration({
  features: {
    bootstrap: true,
    typescript: true,

    vue: {
      version: 3,
    },
  },

  standard: {
    banner: {
      font: 'ANSI Shadow',
    },

    mergeProjects: false,

    projects: {
      core: {
        entryFile: 'app.ts',
        outputName: 'app',

        fileMap: {
          header: [
            'vendorlib/common',
          ],
        },

        additionalEntries: {
          'vendorlib/common': [
            './core/js/vendor.ts',
            'es6-promise/auto',
            'classlist.js',
            'picturefill',
          ],
        },

        coloursConfigFilename: 'colours',
        coloursFilePath: 'scss/settings/_colours.scss',
        generateColours: true,
        generateIcons: true,
        iconsConfigFilename: 'icons',
        iconsFilePath: 'js/modules/fontawesome.ts',
      },
      styleguide: {
        entryFile: 'styleguide.ts',
        outputName: 'styleguide',
      },
    },
  },

  webpack: (env) => ({
    module: {
      rules: [
        {
          loader: 'expose-loader',
          test: require.resolve('jquery'),

          options: {
            exposes: ['$', 'jQuery'],
          },
        },
      ],
    },

    optimization: {
      splitChunks: {
        cacheGroups: {
          jquery:
            env.hmr === true
              ? false
              : {
                  filename: 'clientlibs-footer/js/vendorlib/jquery.js',
                  name: 'jquery',
                  test: /[\\/]node_modules[\\/](jquery)[\\/]/,
                },
        },
      },
    },

    plugins: [
      new DefinePlugin({
        __TESTING__: false,
      }),
    ],
  }),
})
