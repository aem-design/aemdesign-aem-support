const { configuration } = require('@aem-design/compose-webpack')

module.exports = configuration({
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

    devServer: {
      host: 'localhost',
    },
  }),
})
