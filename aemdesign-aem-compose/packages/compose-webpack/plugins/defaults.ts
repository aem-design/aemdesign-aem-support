import { resolve } from 'path'
import webpack from 'webpack'

import { removeEmpty } from 'webpack-config-utils'

import { CleanWebpackPlugin } from 'clean-webpack-plugin'
import CopyWebpackPlugin from 'copy-webpack-plugin'
import ImageminPlugin from 'imagemin-webpack-plugin'
import LodashPlugin from 'lodash-webpack-plugin'
import MiniCssExtractPlugin from 'mini-css-extract-plugin'
import StyleLintPlugin from 'stylelint-webpack-plugin'
import { VueLoaderPlugin } from 'vue-loader'
import { BundleAnalyzerPlugin } from 'webpack-bundle-analyzer'

import {
  ConfigurationType,
  environment,
  getConfiguration,
  getProjectPath,
  ifProd,
} from '../lib/config'

import ComposeMessages from './messages'

export {
  ComposeMessages,
}

export default (): webpack.Plugin[] => {
  const outputFolder = ''
  const publicPath   = getConfiguration(ConfigurationType.PATH_PUBLIC)
  const projectName  = environment.project
  const projectPath  = getProjectPath(ConfigurationType.PATH_SOURCE)

  return removeEmpty<webpack.Plugin>([

    /**
     * When enabled, we clean up our public directory for the current project so we are using old
     * assets when sending out files for our builds and pipelines.
     *
     * @see https://github.com/johnagan/clean-webpack-plugin
     */
    environment.clean === true ? new CleanWebpackPlugin({
      cleanOnceBeforeBuildPatterns: [resolve(publicPath, projectName, '**/*')],
    }) : undefined,

    /**
     * Copies static assets from our source folder into the public structure for AEM.
     *
     * @see https://webpack.js.org/plugins/copy-webpack-plugin
     */
    new CopyWebpackPlugin([
      {
        context : resolve(projectPath, 'clientlibs-header/resources'),
        from    : './**/*.*',
        to      : resolve(publicPath, projectName, 'clientlibs-header/resources'),
      },
      {
        context : resolve(projectPath, 'clientlibs-header/css'),
        from    : './*.css',
        to      : resolve(publicPath, projectName, 'clientlibs-header/css'),
      },
    ]),

    /**
     * CSS extraction.
     * Pulls out our CSS from the defined entry path(s) and puts it into our AEM structure.
     *
     * @see https://webpack.js.org/plugins/mini-css-extract-plugin
     */
    new MiniCssExtractPlugin({
      chunkFilename : `${outputFolder || 'clientlibs-header/css'}/[id].css`,
      filename      : `${outputFolder || 'clientlibs-header/css'}/[name].css`,
    }),

    /**
     * Validate our Sass code using Stylelint to ensure we are following our own good practices.
     *
     * @see https://webpack.js.org/plugins/stylelint-webpack-plugin
     */
    environment.maven !== true ? new StyleLintPlugin({
      context     : resolve(projectPath, 'scss'),
      emitErrors  : false,
      failOnError : false,
      files       : ['**/*.scss'],
      quiet       : false,
    }) : undefined,

    /**
     * Compress any static assets in our build.
     *
     * @see https://www.npmjs.com/package/imagemin-webpack-plugin
     */
    ifProd(new ImageminPlugin({
      test: /\.(jpe?g|png|gif|svg)$/i,
    })),

    /**
     * Ensure all chunks that are generated have a unique ID assigned to them instead of pseudo-random
     * ones which are good but don't provide enough uniqueness.
     *
     * @see https://webpack.js.org/plugins/hashed-module-ids-plugin
     */
    new webpack.HashedModuleIdsPlugin(),

    /**
     * Lodash tree-shaking helper!
     *
     * Make sure we aren't including the entire Lodash library but instead just a small subset of the
     * library to keep our vendor weight down.
     *
     * @see https://www.npmjs.com/package/lodash-webpack-plugin
     */
    new LodashPlugin({
      collections : true,
      shorthands  : true,
    }),

    /**
     * Vue compilation configuration.
     *
     * @see https://vue-loader.vuejs.org/guide/
     */
    new VueLoaderPlugin(),

    /**
     * Expose´ for 3rd-party vendors & libraries.
     *
     * @see https://webpack.js.org/plugins/provide-plugin
     */
    new webpack.ProvidePlugin({
      // Expose the Bootstrap modules to the global namespace
      // https://github.com/shakacode/bootstrap-loader#bootstrap-4-internal-dependency-solution
      Alert     : 'exports-loader?Alert!bootstrap/js/dist/alert',
      Button    : 'exports-loader?Button!bootstrap/js/dist/button',
      Carousel  : 'exports-loader?Carousel!bootstrap/js/dist/carousel',
      Collapse  : 'exports-loader?Collapse!bootstrap/js/dist/collapse',
      Dropdown  : 'exports-loader?Dropdown!bootstrap/js/dist/dropdown',
      Modal     : 'exports-loader?Modal!bootstrap/js/dist/modal',
      Popover   : 'exports-loader?Popover!bootstrap/js/dist/popover',
      Popper    : ['popper.js', 'default'],
      Scrollspy : 'exports-loader?Scrollspy!bootstrap/js/dist/scrollspy',
      Tab       : 'exports-loader?Tab!bootstrap/js/dist/tab',
      Tooltip   : 'exports-loader?Tooltip!bootstrap/js/dist/tooltip',
      Util      : 'exports-loader?Util!bootstrap/js/dist/util',
    }),

    /**
     * Define custom environment variables that can be exposed within the code base.
     *
     * @see https://webpack.js.org/plugins/define-plugin
     */
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: JSON.stringify(environment.mode),
      },

      '__DEV__'  : environment.dev === true,
      '__PROD__' : environment.prod === true,
    }),

    /**
     * Bundle analyzer is used to showcase our overall bundle weight which we can use to find
     * heavy files and optimise the result for production.
     *
     * @see https://www.npmjs.com/package/webpack-bundle-analyzer
     */
    environment.dev === true && environment.maven !== true && environment.deploy !== true ? new BundleAnalyzerPlugin({
      openAnalyzer: false,
    }) : undefined,

    /**
     * @see https://webpack.js.org/plugins/loader-options-plugin
     */
    ifProd(new webpack.LoaderOptionsPlugin({
      minimize: true,
    })),

  ])
}
