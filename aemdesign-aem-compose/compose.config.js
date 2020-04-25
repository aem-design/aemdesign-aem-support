const { resolve } = require('path')
const { configuration, registerHook } = require('@aem-design/compose-webpack')
const { DefinePlugin } = require('webpack')

// TODO: Create a way to extend default projects
// "lazysizes",
// "lazysizes/plugins/object-fit/ls.object-fit",
// "lazysizes/plugins/parent-fit/ls.parent-fit",

const projectExtras = {
  core: {
    generateColours : true,
    generateIcons   : true,

    coloursConfigFilename : 'colours',
    coloursFilePath       : 'scss/settings/_colours.scss',
    iconsConfigFilename   : 'icons',
    iconsFilePath         : 'js/modules/fontawesome.ts',
  },
}

registerHook('init:post', {
  before(env) {
    const extras = projectExtras[env.project] || {}

    // Do we need to generate any colours or icons for this project?
    if (extras.generateColours === true) {
      const { buildAndSaveColours } = require('./support/scripts/generate-colours')

      buildAndSaveColours(resolve(env.paths.project.src, extras.coloursFilePath), extras.coloursConfigFilename)
    }

    if (extras.generateIcons === true) {
      const { buildAndSaveIcons } = require('./support/scripts/generate-icons')

      buildAndSaveIcons(resolve(env.paths.project.src, extras.iconsFilePath), extras.iconsConfigFilename)
    }
  },
})

module.exports = configuration({
  features: ['typescript', 'vue'],

  standard: {
    banner: {
      font: 'ANSI Shadow',
    },
  },

  webpack: (env) => ({
    module: {
      rules: [
        {
          test: require.resolve('jquery'),

          use: [
            {
              loader  : 'expose-loader',
              options : 'jQuery',
            },
            {
              loader  : 'expose-loader',
              options : '$',
            },
          ],
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
      }),
    ],
  }),
})
