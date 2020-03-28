/* eslint-disable */

const argv   = require('yargs').argv
const colors = require('colors')
const mkdirp = require('mkdirp')

const {
  readFileSync,
  existsSync,
  lstatSync,
  readdirSync,
  writeFile,
} = require('fs')

const { join, resolve } = require('path')

const isDebug = process.argv.find(function(value) { return value === "--debug"}) ? true : false

const {
  each,
  escape,
  isArray,
  isString,
  size,
} = require('lodash')

const rootPath = '../target/classes'

const breakpoints = {
  sm: null,
  md: 'Tablet',
  lg: 'Desktop',
  xl: 'Desktop (Large)',
}

const prefixes = {
  // Font weights
  '300': 'Light',
  '400': 'Normal',
  '600': 'SemiBold',
  '700': 'Bold',

  // Screen Reader
  'sr-only'           : 'Only (visually hidden)',
  'sr-only-focusable' : 'Show when focused',

  // Hidden components (invisiblility)
  'invisible'         : 'Always',
  'invisible-lg-down' : 'Desktop - Down',
  'invisible-lg-up'   : 'Desktop - Up',
  'invisible-md-down' : 'Tablet - Down',
  'invisible-md-up'   : 'Tablet - Up',
  'invisible-sm-down' : 'Mobile (landscape) - Down',
  'invisible-sm-up'   : 'Mobile (landscape) - Up',
  'invisible-xl-down' : 'Desktop (large) - Down',
  'invisible-xl-up'   : 'Desktop (large) - Up',
  'invisible-xs-down' : 'Mobile - Down',
  'invisible-xs-up'   : 'Mobile - Up',

  // Some colours are vauge, define specifc labels here
  'dark'         : 'Charcoal',
  'blue-50'      : 'Blue (50% opacity)',
  'green-50'     : 'Green (50% opacity)',
  'purple-50'    : 'Purple (50% opacity)',
  'red-50'       : 'Red (50% opacity)',
  'turquoise-50' : 'Turquoise (50% opacity)',
  'yellow-50'    : 'Yellow (50% opacity)',

  // Add the word 'Square' to these icon
  'facebook': 'Facebook - Square',
  'linkedin': 'LinkedIn - Square',

  // Misc styles
  'col'    : 'Columns',
  'nowrap' : 'No Wrap',
}

let templateCache = {}

function isDirectory(source) {
  return lstatSync(source).isDirectory()
}

function getDirectories (source) {
  return readdirSync(source).map(name => join(source, name)).filter(isDirectory)
}

function currentPath(path) {
  return resolve(__dirname, path)
}

function getBreakpointInfix(breakpoint) {
  return breakpoint === 'sm' ? '' : breakpoint
}

function mapBreakpointToName(breakpoint) {
  return breakpoints[breakpoint]
}

function loadTemplateForCategory(category = null) {
  if (!category) {
    throw new Error('Invalid category name supplied!')
  }

  if (templateCache[category]) {
    return templateCache[category]
  }

  templateCache[category] = readFileSync(currentPath(`./templates/${category}.xml`), 'utf-8').toString()
  return templateCache[category]
}

function normalisePrefix(prefix, emptyTitle = false) {
  const newPrefix = prefixes[prefix]

  if (!newPrefix) {
    return !emptyTitle ? titleCase(fixTitle(prefix)) : ''
  }

  return titleCase(fixTitle(newPrefix))
}

function fixTitle(title) {
  if (title.length > 0) {
    return title
      .replace(/-/g, ' ')
      .replace(/\$/g, '')
      .replace(/{/g, '')
      .replace(/}/g, '')
      .replace(/\s\s/g, ' ')
      .replace(/\s\s/g, ' ')
      .trim()
  }

  return title
}

function escapeXml(unsafe) {
  return unsafe.replace(/[<>&'"]/g, function (c) {
    switch (c) {
      case '<': return '&lt;';
      case '>': return '&gt;';
      case '&': return '&amp;';
      case '\'': return '&apos;';
      case '"': return '&quot;';
    }
  });
}

function titleCase(str) {
  try {
    return str.toLowerCase().replace('-', ' ').split(' ').map(word => (
      word.replace(word[0], word[0].toUpperCase())
    )).join(' ')
  } catch (ex) {
    logToConsole(colors.red(`Error: ${str}`), ex)
  }

  return str
}

function generateContent(categoryTemplate, categoryTemplateDefault, category, path, contentData) {
  let folderName = ''
  let template   = categoryTemplate

  if (contentData.template !== '') {
    template = loadTemplateForCategory(contentData.template)
  }

  // Make a copy of the template
  let templatePatch = template

  if (contentData.type === 'tag') {
    debug({type: "tag", contentData: contentData})
    const value = contentData.valueFormatted !== '' ?
      escape(contentData.valueFormatted):
      escape(contentData.prefixValue + contentData.value)

    // Replace the template vars
    templatePatch = templatePatch.replace('%%title%%', contentData.title)
    templatePatch = templatePatch.replace('%%description%%', contentData.description)
    templatePatch = templatePatch.replace('%%value%%', value)

    // Strip the folder name if the category is 'component-style-module'
    if (!contentData.flat) {
      folderName = contentData.value

      const prefixToFolders = contentData.prefixToFolders
      if (size(prefixToFolders)) {
        each(prefixToFolders, (replacement, prefix) => {
          if (new RegExp(prefix).test(folderName)) {
            folderName = `${replacement}/${folderName}`
          }
        })
      }
    }
  } else {
    if (contentData.content !== undefined) {
        debug({type: "content", contentData: contentData})
        const templateHasAttributes = templatePatch.indexOf('%%attributes%%')>-1

        //check if json being specified as contents
        if (contentData.json) {
            const content = contentData.content

            templatePatch = templatePatch.replace('%%title%%', titleCase(contentData.jsonKey))
            templatePatch = templatePatch.replace('%%value%%', contentData.jsonKey)
            //remove attributes field is exist in template
            templatePatch = templatePatch.replace('%%attributes%%', "")

            if (isArray(content) || isString(content)) {
                let localPatch = template

                localPatch = localPatch.replace('%%title%%', titleCase(contentData.key))
                localPatch = localPatch.replace('%%value%%', Buffer.from(JSON.stringify(content || [])).toString('base64'))
                //remove attributes field is exist in template
                localPatch = localPatch.replace('%%attributes%%', "")

                writeTemplate(`${rootPath}/${category}/${path}/${contentData.key}`, categoryTemplateDefault, localPatch)
            } else {
                each(content, (field, key) => {
                    let localPatch = template

                    localPatch = localPatch.replace('%%title%%', field.title)
                    localPatch = localPatch.replace('%%value%%', Buffer.from(JSON.stringify(field || [])).toString('base64'))
                    //remove attributes field is exist in template
                    localPatch = localPatch.replace('%%attributes%%', "")

                    writeTemplate(`${rootPath}/${category}/${path}/${contentData.key}/${key}`, categoryTemplateDefault, localPatch)
                })
            }

        } else {
          debug({type: "other", contentData: contentData})
          for (const field of Object.keys(contentData.content)) {
            folderName = field
            templatePatch = templatePatch.replace('%%node%%', field)

            const fieldValues = contentData.content[field]
            let fieldAttributes = ""

            //for all fields apart from json flag
            for (const fieldName of Object.keys(fieldValues).filter(x => x !== 'json')) {
              const value = fieldValues[fieldName]
              //check if the field is explicitly mentioned in template
              if (templatePatch.indexOf('%%' + fieldName + '%%')>-1) {
                //replace field value
                templatePatch = templatePatch.replace(
                    '%%' + fieldName + '%%',
                    fieldValues.json === true && fieldName === 'value' ? Buffer.from(JSON.stringify(value)).toString('base64') : value
                )
              } else if (templateHasAttributes) {
                //if not array add to attributes collection
                if (!Array.isArray(value)) {
                  fieldAttributes += fieldName + '="' + value + '" '
                } else {
                  //if array join and add as array string
                  fieldAttributes += fieldName + '="[' + Object.values(value).join(",") + ']" '
                }
              }
            }
            //replace attributes placeholder with collected fields
            if (templateHasAttributes) {
              templatePatch = templatePatch.replace('%%attributes%%', fieldAttributes)
            }
          }

        }


    } else {
      logToConsole('Nothing to do with:', path)
      return
    }
  }
  //remove attributes filed from template
  templatePatch = templatePatch.replace('%%attributes%%', '')

  writeTemplate(`${rootPath}/${category}/${path}/${folderName}`, categoryTemplateDefault, templatePatch)
}

function verifyTemplate(path,content) {
  try {
    libxmljs.parseXml(content)
  } catch (ex) {
    logToConsole('Invalid Template for ' + path)
    return false
  }

  return true
}

function writeTemplate(outputPath, templateDefault, template) {
  createTemplate(outputPath, template).then(res => {
    consoleResult(...res)

    // Walk up the path and ensure parents have content.xml
    let paths = outputPath.split('/')
    let promises = []

    //remove file from path array
    paths.pop()

    if (paths.length > 0) {
      while (paths.length !== 0) {

        let node  = paths[paths.length - 1] || ''
        let title = paths[paths.length - 1] || ''

        //skip generation of content for ../target
        if ( title === ".." || ( paths.length === 2 && (paths[paths.length - 2] === ".." && title === "target")) ) {
          paths.pop()
          continue
        }

        title = normalisePrefix(title)

        let templateDefaultPatch = templateDefault
          .replace('%%title%%', title)
          .replace('%%node%%', node)
          .replace('%%value%%', title.toLowerCase().replace(/\s/g, '-'))

        const currentDirectory = paths.join('/')
        const workingDirectory = currentPath(currentDirectory)

        // Get all the child directories and list them in the parent template
        const directories = getDirectories(workingDirectory).map(directory => {
          let nodename = directory.replace(workingDirectory, '').substr(1);
          //convert name of node to HEX if its not a-zA-Z
          if (nodename.match(/^[^a-zA-Z]/)) {
            let firstCharHex = nodename.substr(1, 1).charCodeAt(0).toString(16);
            firstCharHex = ("000"+firstCharHex).slice(-4);
            nodename = nodename.replace(/^[^a-zA-Z]/, "_x" + firstCharHex + "_");
          }
          return `<${nodename}/>`
        })


        templateDefaultPatch = templateDefaultPatch.replace('%%children%%', directories.join('\n    '))

        promises.push(createTemplate(currentDirectory, templateDefaultPatch))
        paths.pop()
      }
    }

    Promise.all(promises).then(walks => {
      walks.forEach(walk => consoleResult(...walk))
    })
  })
}

function createTemplate(outputPath, template) {
  mkdirp.sync(currentPath(outputPath))

  return new Promise(res => {
    if (!existsSync(currentPath(`${outputPath}/.content.xml`))) {
      writeFile(currentPath(`${outputPath}/.content.xml`), template, err => {
        if (err) {
          res([false, outputPath, err])
        } else {
          res([true, outputPath])
        }
      })
    }
  })
}

function consoleResult(success, outputPath, err = null) {
  if (!success) {
    logToConsole(colors.red('Unable to save content for:'), outputPath, '\n', err)
  } else {
    logToConsole('Saved content for:', outputPath)
  }
}

function logToConsole(...args) {
  if (argv.console !== false) {
    console.log(...args)
  }
}

function formatSizeWithSuffix(input) {
  let suffix = ''
  if (input > 3 && input < 21) {
    suffix = 'th'
  } else {
    switch (input % 10) {
      case 1:
        suffix = 'st'
        break
      case 2:
        suffix = 'nd'
        break
      case 3:
        suffix = 'rd'
        break
      default:
        suffix = 'th'
    }
  }

  return input + suffix
}

function parseTitle(title, prefix, options = {}) {
  if (title === undefined) {
    return ''
  }

  let breakpointName
  if (options.bp) {
    breakpointName = mapBreakpointToName(options.bp)
  }

  const prefixNormalised = normalisePrefix(prefix, options.emptyTitle)

  title = title
    .replace('%%breakpoint%%', breakpointName ? `- ${breakpointName} Up` : '')
    .replace('%%prefix_normalised%%', prefixNormalised)
    .replace('%%size%%', (options.size !== undefined && options.size.toString()) || '')
    .replace('%%sizeWithSuffix%%', options.size ? formatSizeWithSuffix(options.size) : '')

  if (/^\s+?-/.test(title)) {
    title = title.replace(/^\s+?-/, '')
  }

  return title.trim()
}


function debug(text) {
  if (isDebug) {
    console.log(colors.red('DEBUG:'), text)
  }
}


module.exports = {
  currentPath,
  formatSizeWithSuffix,
  generateContent,
  getBreakpointInfix,
  loadTemplateForCategory,
  mapBreakpointToName,
  normalisePrefix,
  parseTitle,
  getDirectories,
  isDirectory,
  debug
}

/* eslint-enable */
