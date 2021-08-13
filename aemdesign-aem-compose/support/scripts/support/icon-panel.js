const { sortBy } = require('lodash')

const customLabels = {
  'feed/rss': 'Feed / RSS',
}

/**
 * Generate the icon panel for each category.
 *
 * @param {Array<{ category: string, class: string, name: string, prefix: string, usable: boolean }>} icons
 */
function generateIconsByCategory(icons) {
  const iconsByCategory = {}

  for (const icon of icons) {
    iconsByCategory[icon.category] = iconsByCategory[icon.category] || []
    iconsByCategory[icon.category].push(icon)
  }

  return Object.keys(iconsByCategory).sort().map((category) => (`
    <div class="icon-panel row">
      <h3 class="icon-panel__heading h6">${getCategoryLabel(category)}</h3>
      ${iconsByCategory[category].map(generateIcon).join('')}
    </div>
  `)).join('')
}

/**
 * Generate the label for the given category.
 *
 * @param {string} category Original label
 */
function getCategoryLabel(category) {
  if (customLabels[category]) {
    return customLabels[category]
  }

  return category.charAt(0).toUpperCase() + category.substr(1)
}

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
        </div>
    </div>
  `
}

/**
 * Generate the icon panel.
 *
 * @param {Array<{ category: string, class: string, name: string, prefix: string, usable: boolean }>} icons
 */
function generateIconPanel(icons) {
  try {
    icons = JSON.parse(JSON.stringify(icons)).filter((icon) => icon.usable !== false)
    icons = sortBy(icons, ['name'])
  } catch (e) {
    console.error('Unable to parse icons for use:', e)
    icons = []
  }

  return generateIconsByCategory(icons)
}

module.exports = {
  generateIconPanel,
}
