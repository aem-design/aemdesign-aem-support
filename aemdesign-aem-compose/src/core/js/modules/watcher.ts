import _throttle from 'lodash/throttle'

import {
  bindVueComponents,
} from '@/core/modules/binder'

import { components as iconComponents } from '@/core/modules/icons'

function handleIcons() {
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
}

function handleExperienceFragments(target: Element) {
  const fragments = target.querySelectorAll('.experiencefragment')

  if (fragments.length) {
    // Create any new Vue component instances
    bindVueComponents(true)
  }
}

function handleRougeContent(target: Element | null = null) {
  console.info('[Rouge Content] Getting ready to smash some elements, 𝗛𝗨𝗟𝗞 style!')

  const paragraphs = (target || document).querySelectorAll('p')

  let totalSmashed = 0

  if (paragraphs.length) {
    for (const paragraph of paragraphs) {
      if (paragraph.innerHTML === '&nbsp;') {
        paragraph.classList.add('is-empty')
        totalSmashed++
      }
    }
  }

  console.info('[Rouge Content] Smashed %d paragraph elements!', totalSmashed)
}

export default () => {
  console.info('[Watcher] Spinning up mutation observer for AEM author mode!')

  const mutationObserver = new MutationObserver(_throttle((mutations: MutationRecord[]) => {
    console.info('[Watcher] Change detected! %d mutations in play', mutations.length)

    for (const { target } of mutations) {
      console.info('[Watcher] Target:', target)

      // Rouge content
      handleRougeContent(target as Element)

      // Experience fragments
      handleExperienceFragments(target as Element)

      // Icons
      handleIcons()
    }

    // Ensure all the 'collapse' elements on the page when in author
    $('.collapse[data-parent]').collapse('dispose')

    console.info('[Watcher] Finished mutation run!')
  }, 500)) // -> Wait 500 milliseconds between executions so we don't freeze the authoring UI

  mutationObserver.observe(document.querySelector('body > .container') as HTMLElement, {
    childList : true,
    subtree   : true,
  })

  // Rouge content
  handleRougeContent()
}
