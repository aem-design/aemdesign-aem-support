import { bold } from 'colors'
import { relative, resolve } from 'path'
import webpack from 'webpack'

import { getIfUtils } from 'webpack-config-utils'

import MiniCssExtractPlugin from 'mini-css-extract-plugin'
import OptimizeCSSAssetsPlugin from 'optimize-css-assets-webpack-plugin'
import TerserPlugin from 'terser-webpack-plugin'
import TsconfigPathsPlugin from 'tsconfig-paths-webpack-plugin'

console.log('________  _______   _____ ______       ________  _______   ________  ___  ________  ________      ')
console.log('|\\   __  \\|\\  ___ \\ |\\   _ \\  _   \\    |\\   ___ \\|\\  ___ \\ |\\   ____\\|\\  \\|\\   ____\\|\\   ___  \\    ')
console.log('\\ \\  \\|\\  \\ \\   __/|\\ \\  \\\\\\__\\ \\  \\   \\ \\  \\_|\\ \\ \\   __/|\\ \\  \\___|\\ \\  \\ \\  \\___|\\ \\  \\\\ \\  \\   ')
console.log(' \\ \\   __  \\ \\  \\_|/_\\ \\  \\\\|__| \\  \\   \\ \\  \\ \\\\ \\ \\  \\_|/_\\ \\_____  \\ \\  \\ \\  \\  __\\ \\  \\\\ \\  \\  ')
console.log('  \\ \\  \\ \\  \\ \\  \\_|\\ \\ \\  \\    \\ \\  \\ __\\ \\  \\_\\\\ \\ \\  \\_|\\ \\|____|\\  \\ \\  \\ \\  \\|\\  \\ \\  \\\\ \\  \\ ')
console.log('   \\ \\__\\ \\__\\ \\_______\\ \\__\\    \\ \\__\\\\__\\ \\_______\\ \\_______\\____\\_\\  \\ \\__\\ \\_______\\ \\__\\\\ \\__\\')
console.log('    \\|__|\\|__|\\|_______|\\|__|     \\|__\\|__|\\|_______|\\|_______|\\_________\\|__|\\|_______|\\|__| \\|__|')
console.log('                                                              \\|_________|                         ')
console.log('')

// Ensure 'tsconfig-paths-webpack-plugin' doesn't try to use the project file we supplied
// to 'ts-node' to parse this file.
delete process.env.TS_NODE_PROJECT

// Load our helper modules
import { logging } from '@aem-design/compose-support'
import { config, loaders } from '@aem-design/compose-webpack'

logging.info('Starting up the webpack bundler...')
logging.info('')

const { appsPath, authorPort, sharedAppsPath } = config.getMavenConfiguration()

// Ensure our Maven config values are valid before continuing on...
if (!(authorPort || appsPath || sharedAppsPath)) {
  logging.error('Unable to continue due to missing or invalid Maven configuration values!')
  process.exit(1)
}

logging.info(bold('Maven configuration'))
logging.info('-------------------')
logging.info(bold('Author Port         :'), authorPort)
logging.info(bold('Apps Path           :'), appsPath)
logging.info(bold('Shared Apps Path    :'), sharedAppsPath)
logging.info('')

// Set the public and source paths for the project
const PUBLIC_PATH = resolve(__dirname, 'public')
const SOURCE_PATH = resolve(__dirname, 'source')

export default (env: webpack.ParserOptions): webpack.Configuration => {
  if (!env.project) {
    logging.error('Specify a project when running webpack eg --env.project="core"')
    process.exit(1)
  }

  const mode    = env.dev === true ? 'development' : 'production'
  const project = config.projects[env.project]

  config.setEnvironment({
    ...env,
    mode,
  })

  const { ifDev, ifProd } = getIfUtils(env)

  logging.info(bold('Webpack Configuration'))
  logging.info('---------------------')
  logging.info(bold('Mode                :'), mode)
  logging.info(bold('Project             :'), env.project)
  logging.info(bold('Hot Reloading?      :'), env.hmr ? 'yes' : 'no')

  const clientLibsPath = `${sharedAppsPath}/${appsPath}/clientlibs/${env.project}/`
  logging.info(bold('Client Libary Path  :'), clientLibsPath)

  let entry = {}

  // Define the project path
  const PROJECT_PATH = resolve(SOURCE_PATH, env.project)

  // Define the public path to the assets
  let PUBLIC_PATH_AEM = `/etc.clientlibs/${appsPath}/clientlibs/${env.project}/`

  if (env.hmr === true) {

    PUBLIC_PATH_AEM = '/'

    const additionalEntries = {
      footer: [],
      header: [],
    }

    for (const key of Object.keys(additionalEntries)) {
      const additionalEntryKeys = Object.keys(project.additionalEntries)
        .filter((x) => project.hmr[key].mapToOutput.indexOf(x) !== -1)

      for (const entryKey of additionalEntryKeys) {
        additionalEntries[key] = [
          ...additionalEntries[key],
          ...project.additionalEntries[entryKey],
        ]
      }
    }

    entry = {
      [project.hmr.footer.outputName]: [
        `./${env.project}/js/${project.entryFile.js}`,
        `./${env.project}/scss/${project.entryFile.sass}`,
        ...additionalEntries.footer,
      ],

      [project.hmr.header.outputName]: [
        './hmr/empty.css',
        ...additionalEntries.header,
      ],
    }
  } else {
    entry = {
      [project.outputName]: [
        `./${env.project}/js/${project.entryFile.js}`,
        `./${env.project}/scss/${project.entryFile.sass}`,
      ],

      ...project.additionalEntries,
    }
  }

  logging.info(bold('Public Path         :'), PUBLIC_PATH)
  logging.info(bold('Public Path (AEM)   :'), PUBLIC_PATH_AEM)
  logging.info('')
  logging.info(bold('Entry Configuration'))
  logging.info('-------------------')
  logging.info(JSON.stringify(entry, null, 2))

  const outputFolder = env.hmr === true ? clientLibsPath : false

  return {
    context: SOURCE_PATH,
    devtool: ifDev(env.hmr === true ? 'cheap-module-source-map' : 'cheap-module-eval-source-map'),
    entry,
    mode,

    output: {
      chunkFilename : `${outputFolder || 'clientlibs-footer'}/resources/chunks/[name]${env.prod === true ? '.[contenthash:8]' : ''}.js`,
      filename      : `${outputFolder || 'clientlibs-footer/js'}/[name].js`,
      path          : resolve(PUBLIC_PATH, env.project),
      publicPath    : PUBLIC_PATH_AEM,
    },

    module: {
      rules: [
        {
          include : [resolve(PROJECT_PATH, 'scss')],
          test    : /\.scss$/,

          use: [
            env.hmr === true ? {
              loader: 'style-loader',
            } : { loader: MiniCssExtractPlugin.loader },
            ...loaders.css(env),
          ],
        },
        {
          include : [resolve(PROJECT_PATH, 'js/components')],
          test    : /\.scss$/,

          use: [
            {
              loader: 'vue-style-loader',

              options: {
                hmr       : env.hmr === true,
                sourceMap : true,
              },
            },
            ...loaders.css(env, {
              sassOptions: {
                data         : `@import 'setup';`,
                includePaths : [resolve(PROJECT_PATH, 'scss')],
              },
            }),
          ],
        },
        {
          test: /\.css$/,

          use: [
            {
              loader: MiniCssExtractPlugin.loader,
            },
            ...loaders.css(env),
          ],
        },
        {
          test: /\.(png|jpg|gif|eot|ttf|svg|woff|woff2)$/,

          use: [
            {
              loader: 'file-loader',
              options: {
                context  : `source/${env.project}`,
                emitFile : env.dev === true,
                name     : '[path][name].[ext]',

                publicPath(_, resourcePath, context) {
                  return `${env.hmr === true ? '/' : '../'}${relative(context, resourcePath)}`
                },
              },
            },
          ],
        },
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
        ...loaders.js(env),
      ],
    },

    optimization: {
      minimizer: [
        new TerserPlugin({
          cache     : true,
          sourceMap : false,

          extractComments: {
            condition: true,

            banner() {
              return `Copyright 2019-${(new Date()).getFullYear()} AEM.Design`
            },
          },

          terserOptions: {
            ecma     : 6,
            safari10 : true,
            warnings : false,

            compress: {
              drop_console: true,
            },

            output: {
              beautify: false,
              comments: false,
            },
          },
        }),

        new OptimizeCSSAssetsPlugin({
          canPrint     : true,
          cssProcessor : require('cssnano'),

          cssProcessorPluginOptions: {
            preset: ['default', {
              discardComments: {
                removeAll: true,
              },
            }],
          },
        }),
      ],

      splitChunks: {
        chunks: 'all',

        cacheGroups: {
          default: false,
          vendors: false,

          ['js/vendorlib/jquery.js']: env.hmr === true ? false : {
            name : 'jquery',
            test : /[\\/]node_modules[\\/](jquery)[\\/]/,
          },

          vue: {
            name: 'vue',
            test: /[\\/]node_modules[\\/](vue|vue-property-decorator)[\\/]/,
          },
        },
      },
    },

    plugins: [
      // ...
    ],

    resolve: {
      alias: {
        vue$: env.dev === true ? 'vue/dist/vue.esm.js' : 'vue/dist/vue.min.js',
      },

      extensions: ['.ts', '.tsx', '.js'],

      plugins: [
        new TsconfigPathsPlugin({
          configFile: resolve(__dirname, 'tsconfig.json'),
        }),
      ],
    },

    devServer: {
      contentBase : resolve(PUBLIC_PATH, env.project),
      host        : '0.0.0.0',
      open        : false,
      port        : 4504,

      proxy: [
        {
          context : () => true,
          target  : `http://${env.host || 'localhost'}:${authorPort}`,
        },
      ],
    },
  }
}
