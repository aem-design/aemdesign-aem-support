import '../scss/app.scss'
// TODO: Find out why we need to load '_common.scss' here too!!
import '../scss/settings/_common.scss'

import AEMFixes from '@/core/module/aem'
import { bindVueComponents } from '@/core/module/binder'
import Icons from '@/core/module/icons'

import { isAuthorMode } from '@/core/utility/aem'

async function loadApp(): Promise<any> {
  console.log('app.js jQuery Version', $.fn.jquery)

  /**
   * Vue.js components
   *
   * We typically want to load these as soon as possible to ensure our more feature-rich experiences
   * are available to the end-user as soon-as-possible.
   */
  await bindVueComponents()

  /**
   * Bind a carousel to every instance found on the page.
   */
  const carouselTargets = document.querySelectorAll<HTMLElement>(
    '[data-modules*="carousel"]',
  )

  if (carouselTargets.length && !isAuthorMode()) {
    const carousel = (
      await import(
        /* webpackChunkName: "md/a/carousel" */ '@/core/module/adaptive/carousel'
      )
    ).default

    carousel(carouselTargets)
  }

  /**
   * Append icons elements to any elements that need them. This is done via JavaScript because
   * we don't want to waste paint performance using CSS which won't yield as good of a result.
   */
  Icons()

  /**
   * AEM comnponent level fixes.
   */
  AEMFixes()

  /**
   * Apply some fixes when we are in the AEM author 'edit' mode.
   */
  if (isAuthorMode()) {
    // Open all the 'collapse' elements on the page, by default they are closed and can't be updated
    // by an author which isn't what we want.
    $('.collapse[data-parent]').collapse('dispose')

    // DOM watch mode!
    const watcher = (
      await import(/* webpackChunkName: "md/watcher" */ '@/core/module/watcher')
    ).default

    watcher()
  }

  /**
   * Load the Font Awesome icons now as they are the heaviest payload overall.
   */
  const fontAwesome = (
    await import(
      /* webpackChunkName: "md/fontawesome" */ '@/core/module/fontawesome'
    )
  ).default

  fontAwesome()
}

if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', loadApp)
} else {
  loadApp()
}

// Hot module reloading support
if (module.hot) {
  module.hot.accept()
}
