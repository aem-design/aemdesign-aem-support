const { resolve } = require('path')

/**
 * CSS/Sass loaders
 */
const CSSLoaders = (env, options = {
  sass: {},
}) => ([
  {
    loader: 'css-loader',

    options: {
      esModule      : false,
      importLoaders : 2,
      sourceMap     : env.dev === true,
      url           : false,

      modules: {
        compileType: 'icss',
      },
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

        ...(options.sass.options || {}),
      },

      ...(options.sass.loader || {}),
    },
  },
])

/**
 * JavaScript loaders
 */
const JSLoaders = (env, options = {}) => ([
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

        options: {
          configFile: options.configFile ? options.configFile : resolve(process.cwd(), 'tsconfig.json'),
        },
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
