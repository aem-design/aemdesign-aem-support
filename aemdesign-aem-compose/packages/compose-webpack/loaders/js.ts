import { resolve } from 'path'
import webpack = require('webpack')

const nodeModulesPath = resolve(process.cwd(), 'node_modules')

export default (env: webpack.ParserOptions, options: {
  configFile?: string,
} = {}): webpack.RuleSetRule[] => ([
  {
    exclude : [nodeModulesPath],
    loader  : 'vue-loader',
    test    : /\.vue$/,
  },
  {
    exclude : [nodeModulesPath],
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
    exclude : [nodeModulesPath],
    test    : /\.js$/,
    use     : ['eslint-loader'],
  },
  {
    enforce : 'pre',
    test    : /\.js$/,
    use     : ['source-map-loader'],
  },
])
