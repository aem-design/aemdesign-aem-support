import '../scss/app.scss'

import { bindVueComponents } from '@/core/modules/binder'

import AEMFixes from '@/core/modules/aem'
import Icons from '@/core/modules/icons'

import { isAuthorMode } from '@/core/utilities/aem'

async function run() {
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
  const carouselTargets = document.querySelectorAll<HTMLElement>('[data-modules*="carousel"]')

  if (carouselTargets.length && !isAuthorMode()) {
    ;(await import(/* webpackChunkName: "md/a/carousel" */ '@/core/modules/adaptive/carousel')).default(carouselTargets)
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

  // Apply some fixes when we aren't in the AEM author 'edit' mode
  if (isAuthorMode()) {
    // Open all the 'collapse' elements on the page when in author
    $('.collapse[data-parent]').collapse('dispose')

    ;(await import(/* webpackChunkName: "md/watcher" */ '@/core/modules/watcher')).default()
  }

  // Look for any media elements on the page
  const mediaElements = document.querySelectorAll('[data-media]')

  if (mediaElements.length && !isAuthorMode()) {
    ;(await import(/* webpackChunkName: "md/media" */ '@/core/modules/media')).default(mediaElements)
  }

  // Load the Font Awesome icons last as they are the heaviest payload
  await import(/* webpackChunkName: "vd/fontawesome-free-brands" */ '@fortawesome/fontawesome-free/js/brands')
  await import(/* webpackChunkName: "vd/fontawesome-free-solid" */ '@fortawesome/fontawesome-free/js/solid')
  await import(/* webpackChunkName: "vd/fontawesome-free" */ '@fortawesome/fontawesome-free/js/fontawesome')

  // IE11 hacky fixes ಥ﹏ಥ
  if ((!!window.MSInputMethodContext && !!document.documentMode)) {
    ;(await import(/* webpackChunkName: "ut/ie11" */ '@/core/utilities/ie11')).default()
  }
}

run()

if (module.hot) {
  module.hot.accept()
}
