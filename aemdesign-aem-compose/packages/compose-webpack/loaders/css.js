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
        path: resolve(process.cwd(), 'postcss.config.js'),

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
      outputStyle    : env.dev === true ? 'expanded' : 'compressed',
      precision      : 5,
      sourceMap      : env.dev === true,
      ...(options.sassOptions || {}),
    },
  },
])