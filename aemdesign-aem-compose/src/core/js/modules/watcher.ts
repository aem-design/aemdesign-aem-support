import { components as iconComponents } from '@module/icons'

import { matches } from '@utility/dom'

export default () => {
  console.info('[Watcher] Spinning up mutation observer for AEM author mode!')

  const mutationObserver = new MutationObserver((mutations: MutationRecord[]) => {
    console.info('[Watcher] Change detected! %d mutations in play', mutations.length)

    mutations.forEach(({ target }: MutationRecord) => {
      if (!matches(target as Element, '.aem-AuthorLayer-Edit')) {
        return
      }

      for (const component of Object.keys(iconComponents)) {
        const config   = iconComponents[component]
        const elements = document.querySelectorAll(config.selectors.join(','))

        if (elements.length) {
          console.info('[Watcher] Found %d elements for:', elements.length, config.selectors)

          for (const element of elements) {
            if (!element.querySelector('.icon')) {
              element.appendChild(config.icon.cloneNode())
            }
          }
        }
      }

      // Open all the 'collapse' elements on the page when in author
      $('.collapse[data-parent]').collapse('dispose')
    })

    console.info('[Watcher] Finished mutation run!')
  })

  mutationObserver.observe(document.documentElement, {
    childList : true,
    subtree   : true,
  })
}
