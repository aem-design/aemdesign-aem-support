import * as path from 'path'
import * as webpack from 'webpack'

import { getIfUtils, removeEmpty } from 'webpack-config-utils'

import { CleanWebpackPlugin } from 'clean-webpack-plugin'
import CopyWebpackPlugin from 'copy-webpack-plugin'
import ImageminPlugin from 'imagemin-webpack-plugin'
import LodashPlugin from 'lodash-webpack-plugin'
import MiniCssExtractPlugin from 'mini-css-extract-plugin'
import OptimizeCSSAssetsPlugin from 'optimize-css-assets-webpack-plugin'
import StyleLintPlugin from 'stylelint-webpack-plugin'
import TerserPlugin from 'terser-webpack-plugin'
import TsconfigPathsPlugin from 'tsconfig-paths-webpack-plugin'
import VueLoaderPlugin from 'vue-loader/lib/plugin'
import BundleAnalyzerPlugin from 'webpack-bundle-analyzer'

// Load the projects configuration
import * as internalConfig from './webpack/config'

console.log(internalConfig)

// const { appsPath, authorPort, sharedAppsPath } = internalConfig.getMavenConfiguration()

process.exit(0)

// // Ensure our Maven config values are valid before continuing on...
// if (!(authorPort || appsPath || sharedAppsPath)) {
//   console.error('[ERROR] Unable to continue due to missing or invalid Maven configuration values!')
//   process.exit(1)
// }

// console.log('[INFO] Local configuration')
// console.log('[INFO] -------------------\n')
// console.log(`[INFO] Author Port      : ${authorPort}`)
// console.log(`[INFO] Apps Path        : ${appsPath}`)
// console.log(`[INFO] Shared Apps Path : ${sharedAppsPath}\n`)

// // Import the CSS and JavaScript loaders
// // import { CSSLoaders, JSLoaders } from './config/webpack.loaders'

// // Set the public and source paths for the project
// const PUBLIC_PATH = path.resolve(__dirname, 'public')
// const SOURCE_PATH = path.resolve(__dirname, 'source')

// //   if (!env.project) {
// //     console.log('Specify a project when running webpack eg --env.project="core"')
// //     return
// //   }

// export default (env: webpack.ParserOptions): webpack.Configuration => {
//   console.log(env)
//   process.exit(0)

//   const { ifDev, ifProd } = getIfUtils(env)

//   const mode    = env.dev === true ? 'development' : 'production'
//   const project = projects[env.project]

//   const clientLibsPath = `${sharedAppsPath}/${appsPath}/clientlibs/${env.project}/`
//   console.log(`[INFO] Client Libary Path: ${clientLibsPath}`)

//   return {}
// }

// module.exports = (env) => {

//   let entry = {}

//   // Define the project path
//   const PROJECT_PATH = resolve(SOURCE_PATH, env.project)

//   // Define the public path to the assets
//   let PUBLIC_PATH_AEM = `/etc.clientlibs/${appsPath}/clientlibs/${env.project}/`

// 	if (env.hmr === true) {
//     PUBLIC_PATH_AEM = '/'

//     const additionalEntries = {
//       footer: [],
//       header: [],
//     }

//     for (const key of Object.keys(additionalEntries)) {
//       const additionalEntryKeys = Object.keys(project.additionalEntries)
//         .filter((x) => project.hmr[key].mapToOutput.indexOf(x) !== -1)

//       for (const entry of additionalEntryKeys) {
//         additionalEntries[key] = [
//           ...additionalEntries[key],
//           ...project.additionalEntries[entry],
//         ]
//       }
//     }

//     entry = {
//       [project.hmr.footer.outputName]: [
//         `./${env.project}/js/${project.entryFile.js}`,
//         `./${env.project}/scss/${project.entryFile.sass}`,
//         ...additionalEntries.footer,
//       ],

//       [project.hmr.header.outputName]: [
//         './hmr/empty.css',
//         ...additionalEntries.header,
//       ],
//     }
//   } else {
//     entry = {
//       [project.outputName]: [
//         `./${env.project}/js/${project.entryFile.js}`,
//         `./${env.project}/scss/${project.entryFile.sass}`,
//       ],

//       ...project.additionalEntries,
//     }
//   }

//   const outputFolder = env.hmr === true ? clientLibsPath : false

//   return {
//     context: SOURCE_PATH,
//     devtool: ifDev(env.hmr === true ? 'cheap-module-source-map' : 'cheap-module-eval-source-map'),
//     entry,
//     mode,

//     output: {
//       filename      : `${outputFolder || 'clientlibs-footer/js'}/[name].js`,
//       chunkFilename : `${outputFolder || 'clientlibs-footer'}/resources/chunks/[name]${env.prod === true ? '.[contenthash:8]' : ''}.js`,
//       path          : resolve(PUBLIC_PATH, env.project),
//       publicPath    : PUBLIC_PATH_AEM,
//     },

//     module: {
//       rules: [
//         {
//           include : [resolve(PROJECT_PATH, 'scss')],
//           test    : /\.scss$/,

//           use: [
//             env.hmr === true ? {
//               loader: 'style-loader',

//               options: {
//                 hmr       : true,
//                 sourceMap : true,
//               },
//             } : { loader: MiniCssExtractPlugin.loader },
//             ...CSSLoaders(env),
//           ],
//         },
//         {
//           include : [resolve(PROJECT_PATH, 'js/components')],
//           test    : /\.scss$/,

//           use: [
//             {
//               loader: 'vue-style-loader',

//               options: {
//                 hmr       : env.hmr === true,
//                 sourceMap : true,
//               },
//             },
//             ...CSSLoaders(env, {
//               sassOptions: {
//                 data         : `@import 'setup';`,
//                 includePaths : [resolve(PROJECT_PATH, 'scss')],
//               },
//             }),
//           ],
//         },
//         {
//           test: /\.css$/,

//           use: [
//             {
//               loader: MiniCssExtractPlugin.loader,
//             },
//             ...CSSLoaders(env),
//           ],
//         },
//         {
//           test: /\.(png|jpg|gif|eot|ttf|svg|woff|woff2)$/,

//           use: [
//             {
//               loader: 'file-loader',
//               options: {
//                 context  : `source/${env.project}`,
//                 emitFile : env.dev === true,
//                 name     : '[path][name].[ext]',

//                 publicPath(_, resourcePath, context) {
//                   return `${env.hmr === true ? '/' : '../'}${relative(context, resourcePath)}`
//                 },
//               },
//             },
//           ],
//         },
//         {
//           test: require.resolve('jquery'),

//           use: [
//             {
//               loader  : 'expose-loader',
//               options : 'jQuery',
//             },
//             {
//               loader  : 'expose-loader',
//               options : '$',
//             },
//           ],
//         },
//         ...JSLoaders(env),
//       ],
//     },

//     optimization: {
//       minimizer: [
//         new TerserPlugin({
//           cache     : true,
//           sourceMap : false,

//           extractComments: {
//             condition: true,

//             banner() {
//               return `Copyright 2019-${(new Date).getFullYear()} AEM.Design`
//             },
//           },

//           terserOptions: {
//             ecma     : 6,
//             safari10 : true,
//             warnings : false,

//             compress: {
//               drop_console: true,
//             },

//             output: {
//               beautify: false,
//               comments: false,
//             },
//           },
//         }),

//         new OptimizeCSSAssetsPlugin({
//           canPrint     : true,
//           cssProcessor : require('cssnano'),

//           cssProcessorPluginOptions: {
//             preset: ['default', {
//               discardComments: {
//                 removeAll: true,
//               },
//             }],
//           },
//         }),
//       ],

//       splitChunks: {
//         chunks: 'all',

//         cacheGroups: {
//           default: false,
//           vendors: false,

//           jquery: env.hmr === true ? false : {
//             filename : 'js/vendorlib/jquery.js',
//             name     : 'jquery',
//             test     : /[\\/]node_modules[\\/](jquery)[\\/]/,
//           },

//           vue: {
//             name: 'vue',
//             test: /[\\/]node_modules[\\/](vue|vue-property-decorator)[\\/]/,
//           },
//         },
//       },
//     },

//     plugins: removeEmpty([
//       env.clean === true ? new CleanWebpackPlugin({
//         cleanOnceBeforeBuildPatterns: [resolve(PUBLIC_PATH, env.project, '**/*')],
//       }) : undefined,
//       new CopyWebpackPlugin([
//         {
//           context : PROJECT_PATH,
//           from    : './*.ico',
//           to      : resolve(PUBLIC_PATH, env.project),
//         },
//         {
//           context : resolve(PROJECT_PATH, 'clientlibs-header/resources'),
//           from    : './**/*.*',
//           to      : resolve(PUBLIC_PATH, env.project, 'clientlibs-header/resources'),
//         },
//         {
//           context : resolve(PROJECT_PATH, 'clientlibs-header/css'),
//           from    : './*.css',
//           to      : resolve(PUBLIC_PATH, env.project, 'clientlibs-header/css'),
//         },
//       ]),
//       new MiniCssExtractPlugin({
//         filename      : `${outputFolder || 'clientlibs-header/css'}/[name].css`,
//         chunkFilename : `${outputFolder || 'clientlibs-header/css'}/[id].css`,
//       }),
//       env.maven !== true ? new StyleLintPlugin({
//         context     : resolve(PROJECT_PATH, 'scss'),
//         emitErrors  : false,
//         failOnError : false,
//         files       : '**/*.scss',
//         quiet       : false,
//       }) : undefined,
//       ifProd(new ImageminPlugin({
//         test: /\.(jpe?g|png|gif|svg)$/i,
//       })),
//       new HashedModuleIdsPlugin(),
//       new LodashPlugin({
//         collections : true,
//         shorthands  : true,
//       }),
//       new VueLoaderPlugin(),
//       new ProvidePlugin({
//         // Expose the Bootstrap modules to the global namespace
//         // https://github.com/shakacode/bootstrap-loader#bootstrap-4-internal-dependency-solution
//         Popper    : ['popper.js', 'default'],
//         Alert     : 'exports-loader?Alert!bootstrap/js/dist/alert',
//         Button    : 'exports-loader?Button!bootstrap/js/dist/button',
//         Carousel  : 'exports-loader?Carousel!bootstrap/js/dist/carousel',
//         Collapse  : 'exports-loader?Collapse!bootstrap/js/dist/collapse',
//         Dropdown  : 'exports-loader?Dropdown!bootstrap/js/dist/dropdown',
//         Modal     : 'exports-loader?Modal!bootstrap/js/dist/modal',
//         Popover   : 'exports-loader?Popover!bootstrap/js/dist/popover',
//         Scrollspy : 'exports-loader?Scrollspy!bootstrap/js/dist/scrollspy',
//         Tab       : 'exports-loader?Tab!bootstrap/js/dist/tab',
//         Tooltip   : 'exports-loader?Tooltip!bootstrap/js/dist/tooltip',
//         Util      : 'exports-loader?Util!bootstrap/js/dist/util',
//       }),
//       new DefinePlugin({
//         'process.env': {
//           NODE_ENV: JSON.stringify(mode),
//         },

//         "__DEV__"  : env.dev === true,
//         "__PROD__" : env.prod === true,
//       }),
//       env.dev === true && env.maven !== true && env.deploy !== true ? new BundleAnalyzerPlugin({
//         openAnalyzer: false,
//       }) : undefined,
//       ifProd(new LoaderOptionsPlugin({
//         minimize: true,
//       })),
//     ]),

//     resolve: {
//       alias: {
//         vue$: env.dev === true ? 'vue/dist/vue.esm.js' : 'vue/dist/vue.min.js',
//       },

//       extensions: ['.webpack.js', '.web.js', '.ts', '.tsx', '.js'],

//       plugins: [
//         new TsconfigPathsPlugin(),
//       ],
//     },

//     devServer: {
//       contentBase : resolve(PUBLIC_PATH, env.project),
//       host        : '0.0.0.0',
//       open        : false,
//       port        : 4504,

//       proxy: {
//         context : () => true,
//         target  : `http://${env.host || 'localhost'}:${authorPort}`,
//       },
//     },
//   }
// }
