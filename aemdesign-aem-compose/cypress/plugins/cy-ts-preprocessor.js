const { relative, resolve } = require('path')

const TsconfigPathsPlugin = require('tsconfig-paths-webpack-plugin')
const VueLoaderPlugin     = require('vue-loader/lib/plugin')
const wp                  = require('@cypress/webpack-preprocessor')

const { CSSLoaders, JSLoaders } = require('../../config/webpack.loaders')

const webpackOptions = {
  mode: 'production',

  output: {
    path       : resolve(process.cwd(), 'cypress/public'),
    publicPath : '/',
  },

  module: {
    rules: [
      {
        test: /\.scss$/,

        use: [
          {
            loader: 'vue-style-loader',
            options: {
              sourceMap: true,
            },
          },
          ...CSSLoaders({ prod: true }, {
            sassOptions: {
              includePaths: [resolve(process.cwd(), 'src/core/scss')],
              data: `
                $asset-base-path: '${process.cwd()}/src/core/';
                $fa-font-path: '~@fortawesome/fontawesome-pro/webfonts';
                @import "@fortawesome/fontawesome-pro/scss/fontawesome.scss";
                @import '@fortawesome/fontawesome-pro/scss/light.scss';
                @import '@fortawesome/fontawesome-pro/scss/brands.scss';
                @import 'app';
              `,
            },
          }),
        ],
      },
      ...JSLoaders({ prod: true }, {
        configFile: resolve(__dirname, '../tsconfig.json'),
      }),
      {
        test: /\.(png|jpg|gif|eot|ttf|svg|woff|woff2)$/,

        use: [
          {
            loader: 'file-loader',
            options: {
              context  : resolve(process.cwd(), 'src/core'),
              emitFile : false,
              name     : '[path][name].[ext]',

              publicPath(_, resourcePath, context) {
                return `./${relative(context, resourcePath)}`
              },
            },
          },
        ],
      },
    ],
  },

  plugins: [
    new VueLoaderPlugin(),
  ],

  resolve: {
    extensions: ['.ts', '.js'],

    plugins: [
      new TsconfigPathsPlugin({
        configFile: resolve(__dirname, '../tsconfig.json'),
      }),
    ],
  },
}

module.exports = wp({ webpackOptions })
