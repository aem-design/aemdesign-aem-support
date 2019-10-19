const { resolve } = require('path')

module.exports = (env, options = {}) => ([
  {
    loader: 'css-loader',

    options: {
      importLoaders : 1,
      sourceMap     : env.dev === true,
    },
  },
  {
    loader: 'postcss-loader',

    options: {
      ident     : 'postcss',
      sourceMap : env.dev === true,

      config: {
        path: resolve(__dirname, '../postcss.config.js'),

        ctx: {
          prod: env.prod === true,
        },
      },
    },
  },
  {
    loader: 'sass-loader',

    options: {
      implementation : require('sass'),
      sourceMap      : env.dev === true,

      sassOptions: {
        outputStyle : env.dev === true ? 'expanded' : 'compressed',
        precision   : 5,
        ...(options.sassOptions || {}),
      },

      ...(options.sassLoader || {}),
    },
  },
])