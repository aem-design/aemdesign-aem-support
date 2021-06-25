/* eslint-disable */
const colors = require('colors')
const fs     = require('fs')
const mkdirp = require('mkdirp')
const rimraf = require('rimraf')
const yaml   = require('js-yaml')
const yimp   = require('yaml-import')

// CLI arguments
const args = require('yargs')
  .option('clean', {
    alias   : 'c',
    type: 'boolean', /* array | boolean | string */
    default : false,
    description: 'clean after'
  })
  .option('console', {
    alias   : 'cs',
    default : true,
    type    : 'boolean',
  })
  .option('debug', {
    alias   : 'd',
    type: 'boolean', /* array | boolean | string */
    default : false,
    description: 'debug mode'
  })
  .option('config', {
    default : false,
    description: 'specify config YAML configuration file',
    nargs: 1,
    demand: true,
    demand: 'config file is required',
    default: 'core.yml'
  })
  .example('node generator.js --debug' , 'run debug mode')
  .example('node generator.js --config=<filename>.yml' , 'run conversion of config')
  .example('node generator.js --config=<filename>.yml --debug' , 'run with debug enabled')
  .example('node generator.js --config=<filename>.yml --no-clean', 'run without tree cleaning')
  .example('node generator.js --config=<filename>.yml --no-console', 'run without realtime logging')
  .demandOption(['config'], 'Please provide a config file to run')
  .version(false)
  .wrap(130)
  .argv

const {
  each,
  isArray,
} = require('lodash')

const rootPath       = '../target/classes'
const tmpPath        = 'output'
const pathPrefixTags = 'content/_cq_tags'
const pathPrefixApps = 'apps/'

const {
  currentPath,
  generateContent,
  getBreakpointInfix,
  generateColoursFromConfig,
  generateIconsFromConfig,
  loadTemplateForCategory,
  parseTitle,
  debug
} = require('./functions')

console.clear()

try {
  const baseConfigFilePath = currentPath(`config/${args.config}`)
  const configFilePath     = currentPath(`${tmpPath}/config.${(Date.now() / 1000 | 0)}.yml`)

  debug(configFilePath)

  if (!fs.existsSync(currentPath(tmpPath))) {
    mkdirp.sync(currentPath(tmpPath))
  }

  // Generate some additional files for core using our support configuration
  if (args.config.indexOf('core') !== -1) {
    generateColoursFromConfig()
    generateIconsFromConfig()
  }

  // Merge the YAML configurations together into a single readable file
  yimp.write(baseConfigFilePath, configFilePath)

  const config = yaml.safeLoad(fs.readFileSync(configFilePath, 'utf8'))

  function generator() {
    const categories = {}

    for (const category of Object.keys(config)) {
      const children = config[category]

      if (!children) {
        continue
      }

      // Create the object for the category
      categories[category] = categories[category] || {}

      for (const subcategory of Object.keys(children)) {
        const data = children[subcategory]

        let prefixes = data.prefixes

        if (prefixes === undefined && data.prefix !== undefined) {
          prefixes = [data.prefix]
        }

        const contents    = data.contents
        const prefixValue = data.prefixValue || ''

        // Create the object for the sub-category
        categories[category][subcategory] = categories[category][subcategory] || {
          content: [],
        }

        if (contents !== undefined) {
          each(contents, (content, key) => {
            if (key !== 'json' && key !== 'jsonKey') {
              categories[category][subcategory].content.push({
                content,
                key,
                json     : contents.json,
                jsonKey  : contents.jsonKey || '',
                template : data.template || '',
                type     : 'content',
              })
            }
          })
        }

        if (prefixes !== undefined) {
          for (const index in prefixes) {

            if (!prefixes.hasOwnProperty(index)) {
              return
            }


            let prefix           = prefixes[index]
            let prefixIsAdvanced = false
            let prefixTitle      = ''
            let prefixValue      = ''
            let prefixConfig     = {}
            let value            = ''


            // Is the prefix simple?
            if (typeof prefix === 'object') {
              prefixIsAdvanced = true
              //simple name
              prefix = Object.keys(prefix)[0]
              //get prefix value as config
              prefixConfig = prefixes[index][prefix]
              //get title
              prefixTitle = prefixConfig['title'] ? prefixConfig['title'] : prefix
              prefixValue = prefixConfig['value'] ? prefixConfig['value'] : prefix
              value = ""
            } else {
              prefixTitle = data['title'] ? data['title'] : prefix
              value = prefix
              prefixValue = data['prefixValue'] ? data['prefixValue'] : ''
            }

            let emptyTitle      = data.emptyTitle || false
            let prefixToFolders = prefixIsAdvanced ? {name: prefix} : data.prefixToFolders || {}
            let valueFormat     = data.valueFormat || ''


            if (data.startWith) {
              value = data.startWith + value
            }


            debug({
              "PROCESSING PREFIX": prefix,
              isObject: typeof prefix === 'object',
              prefixIsAdvanced: prefixIsAdvanced,
              prefix: prefix,
              prefixConfig: prefixConfig,
              prefixTitle: prefixTitle,
              prefixValue: prefixValue,
              category: category,
              subcategory: subcategory,
              emptyTitle: emptyTitle,
              prefixToFolders: prefixToFolders,
              valueFormat: valueFormat,
              value: value,
              data: data
            })



            if (isArray(data.breakpoints)) {
              debug({PROCESSING: "breakpoints",breakpoints: data.breakpoints})
              for (const bp of data.breakpoints) {
                const infix = getBreakpointInfix(bp)

                // Does the data tree have sizes?
                if (isArray(data.sizes)) {
                  for (const size of data.sizes) {
                    const skipSize = (data.skipSizes || []).indexOf(size) !== -1


                    let valueFormatted
                    if (valueFormat) {
                        valueFormatted = valueFormat
                            .replace(new RegExp('%%prefix%%', 'g'), prefix)
                            .replace(new RegExp('%%infix%%', 'g'), infix ? `-${infix}` : '')
                    } else {
                        valueFormatted = prefixValue ? prefixValue.concat(prefix) : value
                    }

                    if (!skipSize) {
                      valueFormatted = valueFormatted.replace(new RegExp('%%size%%', 'g'), size)
                    }

                    categories[category][subcategory].content.push({
                      prefixToFolders,
                      flat           : data.flat || false,
                      prefixValue    : prefixValue,
                      template       : data.template || '',
                      title          : parseTitle(data.title, prefix.toString(), { bp, emptyTitle, size }),
                      type           : 'tag',
                      value          : value + (infix ? `-${infix}` : '') + (!skipSize ? `-${size}` : ''),
                      valueFormatted : valueFormatted
                    })
                  }

                  // Nup, no sizing here
                } else {
                    let valueFormatted
                    if (valueFormat) {
                        valueFormatted = valueFormat
                            .replace(new RegExp('%%prefix%%', 'g'), prefix)
                            .replace(new RegExp('%%infix%%', 'g'), infix ? `-${infix}` : '')
                    } else {
                        valueFormatted = prefixValue ? prefixValue.concat(prefix) : value
                    }

                  categories[category][subcategory].content.push({
                    prefixToFolders,
                    flat           : data.flat || false,
                    prefixValue    : prefixValue,
                    template       : data.template || '',
                    title          : parseTitle(data.title, prefix.toString(), { bp, emptyTitle }),
                    type           : 'tag',
                    value          : value + (infix ? `-${infix}` : ''),
                    valueFormatted : valueFormatted
                  })
                }
              }
            } else if (isArray(data.sizes)) {
              debug({PROCESSING: "sizes",breakpoints: data.sizes})
              // Does the data tree have sizes?

              for (const size of data.sizes) {
                const skipSize = (data.skipSizes || []).indexOf(size) !== -1
                  let valueFormatted
                  if (valueFormat) {
                      valueFormatted = valueFormat
                          .replace(new RegExp('%%prefix%%', 'g'), prefix)
                  } else {
                      valueFormatted = prefixValue ? prefixValue.concat(prefix) : value
                  }

                if (!skipSize) {
                  valueFormatted = valueFormatted.replace(new RegExp('%%size%%', 'g'), size)
                }

                categories[category][subcategory].content.push({
                  prefixToFolders,
                  flat           : data.flat || false,
                  prefixValue    : prefixValue,
                  template       : data.template || '',
                  title          : parseTitle(data.title, prefix.toString(), { emptyTitle, size }),
                  type           : 'tag',
                  value          : value + (!skipSize ? `-${size}` : ''),
                  valueFormatted : valueFormatted
                })
              }

            } else if (prefixIsAdvanced) {
              debug({PROCESSING: "advanced"})
              categories[category][subcategory].content.push({
                prefixToFolders,
                flat           : data.flat || false,
                prefixValue    : '',
                template       : data.template || '',
                title          : prefixTitle,
                type           : 'tag',
                value          : prefix,
                valueFormatted : prefixValue
              })
            } else {
                debug({PROCESSING: "normal", prefixValue: prefixValue})
                let valueFormatted
                if (valueFormat) {
                    valueFormatted = valueFormat
                        .replace(new RegExp('%%prefix%%', 'g'), prefixIsAdvanced ? prefixValue : value)
                } else {
                    valueFormatted = prefixValue ? prefixValue.concat(prefix) : value
                }
                debug({valueFormatted: valueFormatted})

              categories[category][subcategory].content.push({
                prefixToFolders,
                flat           : data.flat || false,
                prefixValue    : prefixValue,
                template       : data.template || '',
                title          : prefixIsAdvanced ? prefixTitle : parseTitle(data.title, prefix.toString(), { emptyTitle }),
                type           : 'tag',
                value          : value,
                valueFormatted : valueFormatted
              })
            }
          }
        }
      }
    }

    let groupedItems = {}
    let contentTemplate
    let tagTemplate
    let tagParentTemplate

    Object.keys(categories).forEach(category => {
      let categoryTemplate
      let categoryTemplateDefault

      // Load the template for the current category
      if (category.startsWith(pathPrefixTags)) {
        categoryTemplate = tagTemplate || loadTemplateForCategory('tag')
        categoryTemplateDefault = tagParentTemplate || loadTemplateForCategory('tag-parent')
      } else if (category.startsWith(pathPrefixApps)) {
        categoryTemplate = contentTemplate || loadTemplateForCategory('content')
        categoryTemplateDefault = contentTemplate || loadTemplateForCategory('content-parent')
      } else {
        return
      }

      if (!groupedItems[category]) {
        groupedItems[category] = {
          category,
          categoryTemplate,
          categoryTemplateDefault,
          items: [],
        }
      }

      Object.keys(categories[category]).forEach(subcategory => {
        const subcategoryContent = categories[category][subcategory].content

        groupedItems[category].items = [
          ...groupedItems[category].items,
          ...subcategoryContent.map(content => ({
            subcategory,
            content
          }))
        ]
      })
    })

    let totalNumberOfTags = 0
    each(groupedItems, category => {
      category.items.forEach(item => {
        generateContent(
          category.categoryTemplate,
          category.categoryTemplateDefault,
          category.category,
          item.subcategory,
          item.content
        )

        totalNumberOfTags++
      })
    })

    console.log(colors.blue('Total number of files generated:'), totalNumberOfTags)
  }

  // If the clean flag is set and its value is `false`, we retain the current content folder
  if (args.clean === false) {
    generator()
  } else {
    const tagsPath = currentPath(`${rootPath}/${pathPrefixTags}`)

    rimraf(tagsPath, () => {
      mkdirp.sync(tagsPath)
      generator()
    })
  }

  // Remove the temporary YAML merge file
  fs.unlinkSync(configFilePath)
} catch (e) {
  console.log(e)
}
