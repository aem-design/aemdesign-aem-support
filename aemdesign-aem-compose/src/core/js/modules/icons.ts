import { IconPrefix } from '@type/enum'

const icons: { [key: string]: Element } = {
  longArrowRight: buildIcon('long-arrow-alt-right', IconPrefix.SOLID),
}

export const components: ComponentConfig = {
  link: {
    icon      : icons.longArrowRight,
    selectors : ['.link.btn'],
  },
}

/**
 * Generates an HTML element for the given Font Awesome `iconClass`.
 *
 * @param {string} iconClass CSS class needed for Font Awesome
 * @return {Element}
 */
function buildIcon(iconClass: string, prefix: IconPrefix): Element {
  const icon = document.createElement('i')
  icon.setAttribute('class', `icon ${prefix} fa-${iconClass}`)

  return icon
}

export default async () => {
  console.info('[Icons] Booting up...')

  // Apply icons to all components on the page by default
  for (const component of Object.keys(components)) {
    const config   = components[component]
    const elements = document.querySelectorAll(config.selectors.join(','))

    if (elements.length) {
      console.info('[Icons] Found %d elements for:', elements.length, config.selectors)

      for (const element of elements) {
        if (!element.querySelector('.icon')) {
          element.appendChild(config.icon.cloneNode())
        }
      }
    }
  }

  console.info('[Icons] All done!')
}
