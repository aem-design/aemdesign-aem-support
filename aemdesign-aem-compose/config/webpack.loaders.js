const { resolve } = require('path')

/**
 * CSS/Sass loaders
 */
const CSSLoaders = (env, options = {}) => ([
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

/**
 * JavaScript loaders
 */
const JSLoaders = (env) => ([
  {
    exclude : [resolve('node_modules')],
    loader  : 'vue-loader',
    test    : /\.vue$/,
  },
  {
    exclude : [resolve('node_modules')],
    test    : /\.[jt]sx?$/,

    use: [
      {
        loader: 'babel-loader',
      },
      {
        loader: 'ts-loader',
      },
    ],
  },
  {
    enforce : 'pre',
    exclude : [resolve('node_modules')],
    test    : /\.js$/,
    use     : ['eslint-loader'],
  },
  {
    enforce : 'pre',
    test    : /\.js$/,
    use     : ['source-map-loader'],
  },
])

module.exports = {
  CSSLoaders,
  JSLoaders,
}
