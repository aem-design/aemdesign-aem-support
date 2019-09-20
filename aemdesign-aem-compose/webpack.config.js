const { readFileSync }      = require('fs')
const { relative, resolve } = require('path')

const { getIfUtils, removeEmpty } = require('webpack-config-utils')

const {
  DefinePlugin,
  HashedModuleIdsPlugin,
  LoaderOptionsPlugin,
  ProvidePlugin,
} = require('webpack')

const BundleAnalyzerPlugin    = require('webpack-bundle-analyzer').BundleAnalyzerPlugin
const { CleanWebpackPlugin }  = require('clean-webpack-plugin')
const CopyWebpackPlugin       = require('copy-webpack-plugin')
const ImageminPlugin          = require('imagemin-webpack-plugin').default
const LodashPlugin            = require('lodash-webpack-plugin')
const MiniCssExtractPlugin    = require('mini-css-extract-plugin')
const OptimizeCSSAssetsPlugin = require('optimize-css-assets-webpack-plugin')
const StyleLintPlugin         = require('stylelint-webpack-plugin')
const TerserPlugin            = require('terser-webpack-plugin')
const TsconfigPathsPlugin     = require('tsconfig-paths-webpack-plugin')
const VueLoaderPlugin         = require('vue-loader/lib/plugin')
const xml2js                  = require('xml2js')

// Load the parent maven configuration
const pomConfig = readFileSync(resolve(__dirname, './pom.xml'), 'utf-8')
const parentPomConfig = readFileSync(resolve(__dirname, '../pom.xml'), 'utf-8')
const xmlParser = new xml2js.Parser()


let authorPort
let appsPath
let sharedAppsPath

//get config from local pom
xmlParser.parseString(pomConfig, (_, { project }) => {
  const properties = project.properties[0]

  appsPath            = properties['package.appsPath'][0]
  sharedAppsPath      = properties['package.path.apps'][0]
})

//get config from parent pom
xmlParser.parseString(parentPomConfig, (_, { project }) => {
  const properties = project.properties[0]

  authorPort          = properties['crx.port'][0]
})

console.log(`authorPort:  ${authorPort}`)
console.log(`appsPath:  ${appsPath}`)
console.log(`sharedAppsPath:  ${sharedAppsPath}`)

if (!(authorPort || appsFolder)) {
  console.error('Unable to parse the parent maven configuration!')
  process.exit(0)
}

// Load the CSS and JavaScript loaders
const { CSSLoaders, JSLoaders } = require('./config/webpack.loaders')

// Load the projects configuration
const projects = require('./config/projects.json')

// Set the public and source paths for the project
const PUBLIC_PATH = resolve(__dirname, 'public')
const SOURCE_PATH = resolve(__dirname, 'source')

module.exports = env => {
  const { ifDev, ifProd } = getIfUtils(env)

  if (!env.project) {
    console.log('Specify a project when running webpack eg --env.project="core"')
    return
  }

  const mode    = env.dev === true ? 'development' : 'production'
  const project = projects[env.project]

  let clientLibsPath = `${sharedAppsPath}/${appsPath}/clientlibs/${env.project}/`
  console.log(`clientLibsPath:  ${clientLibsPath}`)


  let entry = {}

  // Define the project path
  const PROJECT_PATH = resolve(SOURCE_PATH, env.project)

  // Define the public path to the assets
  let PUBLIC_PATH_AEM = `/etc.clientlibs/${appsPath}/clientlibs/${env.project}/`
	console.log(`PUBLIC_PATH_AEM:  ${PUBLIC_PATH_AEM}`)

  if (env.hmr === true) {
    PUBLIC_PATH_AEM = '/'

    const additionalEntries = {
      footer: [],
      header: [],
    }

    for (const key of Object.keys(additionalEntries)) {
      const additionalEntryKeys = Object.keys(project.additionalEntries)
        .filter(x => project.hmr[key].mapToOutput.indexOf(x) !== -1)

      for (const entry of additionalEntryKeys) {
        additionalEntries[key] = [
          ...additionalEntries[key],
          ...project.additionalEntries[entry],
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

  const outputFolder = env.hmr === true ? clientLibsPath : false

  return {
    context: SOURCE_PATH,
    devtool: ifDev(env.hmr === true ? 'cheap-module-source-map' : 'cheap-module-eval-source-map'),
    entry,
    mode,

    output: {
      filename      : `${outputFolder || 'clientlibs-footer/js'}/[name].js`,
      chunkFilename : `${outputFolder || 'clientlibs-footer'}/resources/chunks/[name]${env.prod === true ? '.[contenthash:8]' : ''}.js`,
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

              options: {
                hmr       : true,
                sourceMap : true,
              },
            } : { loader: MiniCssExtractPlugin.loader },
            ...CSSLoaders(env),
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
            ...CSSLoaders(env, {
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
            ...CSSLoaders(env),
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
            }
          ],
        },
        ...JSLoaders(env),
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
              return `Copyright 2019-${(new Date).getFullYear()} AEM.Design`
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

          jquery: env.hmr === true ? false : {
            filename : 'js/vendorlib/jquery.js',
            name     : 'jquery',
            test     : /[\\/]node_modules[\\/](jquery)[\\/]/,
          },

          vue: {
            name: 'vue',
            test: /[\\/]node_modules[\\/](vue|vue-property-decorator)[\\/]/,
          },
        },
      },
    },

    plugins: removeEmpty([
      env.clean === true ? new CleanWebpackPlugin({
        cleanOnceBeforeBuildPatterns: [resolve(PUBLIC_PATH, env.project, '**/*')],
      }) : undefined,
      new CopyWebpackPlugin([
        {
          context : PROJECT_PATH,
          from    : './*.ico',
          to      : resolve(PUBLIC_PATH, env.project),
        },
        {
          context : resolve(PROJECT_PATH, 'clientlibs-header/resources'),
          from    : './**/*.*',
          to      : resolve(PUBLIC_PATH, env.project, 'clientlibs-header/resources'),
        },
        {
          context : resolve(PROJECT_PATH, 'clientlibs-header/css'),
          from    : './*.css',
          to      : resolve(PUBLIC_PATH, env.project, 'clientlibs-header/css'),
        },
      ]),
      new MiniCssExtractPlugin({
        filename      : `${outputFolder || 'clientlibs-header/css'}/[name].css`,
        chunkFilename : `${outputFolder || 'clientlibs-header/css'}/[id].css`,
      }),
      env.maven !== true ? new StyleLintPlugin({
        context     : resolve(PROJECT_PATH, 'scss'),
        emitErrors  : false,
        failOnError : false,
        files       : '**/*.scss',
        quiet       : false,
      }) : undefined,
      ifProd(new ImageminPlugin({
        test: /\.(jpe?g|png|gif|svg)$/i,
      })),
      new HashedModuleIdsPlugin(),
      new LodashPlugin({
        collections : true,
        shorthands  : true,
      }),
      new VueLoaderPlugin(),
      new ProvidePlugin({
        PubSub: 'pubsub-js',

        // Expose the Bootstrap modules to the global namespace
        // https://github.com/shakacode/bootstrap-loader#bootstrap-4-internal-dependency-solution
        Popper    : ['popper.js', 'default'],
        Alert     : 'exports-loader?Alert!bootstrap/js/dist/alert',
        Button    : 'exports-loader?Button!bootstrap/js/dist/button',
        Carousel  : 'exports-loader?Carousel!bootstrap/js/dist/carousel',
        Collapse  : 'exports-loader?Collapse!bootstrap/js/dist/collapse',
        Dropdown  : 'exports-loader?Dropdown!bootstrap/js/dist/dropdown',
        Modal     : 'exports-loader?Modal!bootstrap/js/dist/modal',
        Popover   : 'exports-loader?Popover!bootstrap/js/dist/popover',
        Scrollspy : 'exports-loader?Scrollspy!bootstrap/js/dist/scrollspy',
        Tab       : 'exports-loader?Tab!bootstrap/js/dist/tab',
        Tooltip   : 'exports-loader?Tooltip!bootstrap/js/dist/tooltip',
        Util      : 'exports-loader?Util!bootstrap/js/dist/util',
      }),
      new DefinePlugin({
        'process.env': {
          NODE_ENV: JSON.stringify(mode),
        },

        __DEV__  : env.dev === true,
        __PROD__ : env.prod === true,
      }),
      env.dev === true && env.maven !== true && env.deploy !== true ? new BundleAnalyzerPlugin({
        openAnalyzer: false,
      }) : undefined,
      ifProd(new LoaderOptionsPlugin({
        minimize: true,
      })),
    ]),

    resolve: {
      alias: {
        vue$: env.dev === true ? 'vue/dist/vue.esm.js' : 'vue/dist/vue.min.js',
      },

      extensions: ['.webpack.js', '.web.js', '.ts', '.tsx', '.js'],

      plugins: [
        new TsconfigPathsPlugin(),
      ],
    },

    devServer: {
      contentBase : resolve(PUBLIC_PATH, env.project),
      host        : '0.0.0.0',
      open        : false,
      port        : 4504,

      proxy: {
        context : () => true,
        target  : `http://${env.host || 'localhost'}:${authorPort}`,
      },
    },
  }
}
