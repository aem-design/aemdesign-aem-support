import { resolve } from 'path'
import sass from 'sass'
import webpack from 'webpack'

export default (env: webpack.ParserOptions, options: {
  sassLoader?: { [key: string]: any };
  sassOptions?: sass.Options,
} = {}): webpack.RuleSetUseItem[] => ([
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
