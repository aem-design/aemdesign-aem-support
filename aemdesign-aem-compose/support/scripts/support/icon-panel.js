const { sortBy } = require('lodash')

/**
 * Generate the single icon.
 *
 * @param {{ class: string, name: string, prefix: string }} icon
 */
function generateIcon(icon) {
  return `
    <div class="icon-panel__item col-sm-6 col-md-4 col-lg-3 col-xl-2">
      <div class="card">
        <div class="card-body">
          <i class="icon-panel__icon ${icon.prefix} fa-${icon.class}"></i>
          <p>${icon.name}</p>
          <p class="text-uppercase small">${icon.category}</p>
        </div>
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
