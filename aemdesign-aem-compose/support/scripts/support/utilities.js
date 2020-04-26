const { execSync }                    = require('child_process')
const { readFileSync, writeFileSync } = require('fs')
const { resolve }                     = require('path')
const { JSDOM }                       = require('jsdom')
const minify                          = require('html-minifier').minify

const PATHS_BASE     = '..'
const PATHS_CONTENT  = 'content/aemdesign-showcase/au/en'
const PATHS_SHOWCASE = `aemdesign-aem-showcase/src/main/content/jcr_root/${PATHS_CONTENT}`

/**
 * Escapes the input HTML to ensure it works correct in AEM.
 *
 * @param {string} unsafe String to escape
 * @return {string}
 */
function escapeHtml(unsafe) {
  return unsafe
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#039;')
}

/**
 * Replaces the contents of the given `path` with the `replacement` HTML.
 *
 * @param {string} path Full path to the content file
 * @param {string} componentName Identifier of the component
 * @param {string} replacement New HTML to insert
 */
function replaceHtmlContent(path, componentName, replacement) {
  const contentFilePath = resolvePath(path)

  const input      = readFileSync(contentFilePath).toString('utf8')
  const dom        = new JSDOM(input, { contentType: 'text/xml' })
  const foundInput = escapeHtml(dom.window.document.querySelector(componentName).getAttribute('html'))

  let newHTML = `${replacement}`
  newHTML = minify(newHTML, { collapseWhitespace: true })
  newHTML = escapeHtml(newHTML)

  writeFileSync(contentFilePath, input.replace(
    new RegExp(`html="${foundInput.substr(0, 20)}.*${foundInput.substr(-20)}"`, 'g'),
    `html="${newHTML}"`
  ))
}

/**
 * Sync the given `path` with AEM.
 * TODO: Work out why VLT is throwing itself around like a baby!
 *
 * @param {string} path Something to sync to AEM
 */
function syncFileToAEM(path) {
  const stdout = execSync(`./aemdesign-aem-compose/tools/vault-cli/bin/vlt --credentials admin:admin import http://localhost:4502/crx ${resolvePath(path, true)} /${PATHS_CONTENT}/${stripPathEnd(path)}`, {
    cwd      : resolve(process.cwd(), PATHS_BASE),
    encoding : 'utf8',
  })

  console.log(stdout)
}

/**
 * Resolve the given path to the showcase content package.
 *
 * @param {string} path Right-side of the showcase path
 * @return {string}
 */
function resolvePath(path) {
  return resolve(process.cwd(), `${PATHS_BASE}/${PATHS_SHOWCASE}/${path}`)
}

/**
 * Strips the end of the path off.
 *
 * @param {string} path Right-side of the showcase path
 * @return {string}
 */
function stripPathEnd(path) {
  return path.substr(0, path.lastIndexOf('/'))
}

module.exports = {
  replaceHtmlContent,
  syncFileToAEM,
}
