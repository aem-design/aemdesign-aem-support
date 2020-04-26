/**
 * Gemerate a colour panel.
 *
 * @param {{ [key: string]: { colours: Array<{ background: boolean, class: string, deprecated: boolean, hex: string, label: string }>, label: string, summary?: string } }} categories List of colour categories
 * @param {boolean} [forBackground=false] Filter out the background when `false`
 * @return {string}
 */
function generateColourPanel(categories, forBackground = false) {
  categories = JSON.parse(JSON.stringify(categories))

  let deprecatedColours = []

  for (const category in categories) {
    // Filter in the background specific colours (if 'forBackground' is 'true')
    categories[category].colours = categories[category].colours.filter((colour) => (
      (!colour.background || (forBackground && colour.background === true)) && colour.usable !== false
    ))

    // Filter out deprecated colours into their own category
    deprecatedColours = [
      ...deprecatedColours,
      ...categories[category].colours.filter((colour) => colour.deprecated === true),
    ]

    // Remove the deprecated colours from the category
    categories[category].colours = categories[category].colours.filter((colour) => !colour.deprecated)
  }

  categories.deprecated = {
    colours : deprecatedColours,
    label   : 'Deprecated',
    summary : 'These colours are considered deprecated and should <strong>not</strong> be used in new or existing content.',
  }

  return `
    <div class="colour-panel">
      ${Object.keys(categories).map((category) => {
        const { colours, label, summary } = categories[category]

        return generateColourSection({ category, colours, label, summary })
      }).join('')}
    </div>
  `
}

/**
 * Generates a colour section for a given category.
 *
 * @param {string} category Category key/name
 * @param {string} label Human readable category label
 * @param {Array<{ background: boolean, class: string, deprecated: boolean, hex: string, label: string }>} colours List of colours
 * @return {string}
 */
function generateColourSection({ category, colours, label, summary }) {
  return `
    <section class="colour-panel__section" id="${category}">
      <div class="row">
        <div class="colour-panel__header col-12">
          <h2 class="colour-panel__title h4">${label}</h2>
          ${summary ? `<p>${summary}</p>` : ''}
        </div>

        ${generateColourItems(colours, category === 'deprecated')}
      </div>
    </section>
  `
}

/**
 * Generates a single colour item.
 *
 * @param {Array<{ background: boolean, class: string, deprecated: boolean, hex: string, label: string }>} colours List of colours
 * @return {string}
 */
function generateColourItems(colours, deprecated = false) {
  if (!colours.length) {
    return `
      <div class="col-12">
        <p>${deprecated ? 'No colours are deprecated' : 'No colours currently available for this category'}!</p>
      </div>
    `
  }

  return colours.map((colour) => `
    <div class="col-sm-12 col-md-4 col-lg-3 col-xl-2">
      <div class="colour-panel__item bg-${colour.class} ${colour.deprecated ? 'colour-panel__item--deprecated' : ''}">
        <div class="colour-panel__details">
          <h3 class="h6">${colour.label}</h3>

          <code>
            <span class="icon-hex"><i class="fas fa-tint"></i></span>
            #${colour.hex}
          </code>
          ${!colour.background ? `<code>
            <span class="icon-sass"><i class="fab fa-sass"></i></span>
            color('${colour.class}')
          </code>` : ''}
        </div>
      </div>
    </div>
  `).join('')
}

module.exports = {
  generateColourPanel,
}
