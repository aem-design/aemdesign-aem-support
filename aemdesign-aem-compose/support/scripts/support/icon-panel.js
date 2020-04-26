const { sortBy } = require('lodash')

/**
 * Generate the single icon.
 *
 * @param {{ class: string, name: string, prefix: string }} icon
 */
function generateIcon(icon) {
  return `
    <div class="icon-panel__item col-sm-12 col-md-4 col-xl-2">
        <div class="icon-panel__icon">
          <i class="${icon.prefix} fa-${icon.class}"></i>
        </div>

        <div class="icon-panel__body">
          <p>${icon.name}</p>
          <p class="small">${icon.category}</p>
        </div>
    </div>
  `
}

/**
 * Generate the icon panel.
 *
 * @param {Array<{ class: string, name: string, prefix: string, usable: boolean }>} icons
 */
function generateIconPanel(icons) {
  try {
    icons = JSON.parse(JSON.stringify(icons)).filter((icon) => icon.usable !== false)
    icons = sortBy(icons, ['name'])
  } catch (e) {
    console.error('Unable to parse icons for use:', e)
    icons = []
  }

  return `
    <div class="icon-panel row">
      ${icons.map(generateIcon).join('')}
    </div>
  `
}

module.exports = {
  generateIconPanel,
}
