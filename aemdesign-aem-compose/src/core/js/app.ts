import '../scss/app.scss'

import $ from 'jquery'

import AEMFixes from '@module/aem'
import Icons from '@module/icons'

import { isAuthorMode } from '@utility/aem'

// Internal
const authorMode = isAuthorMode()

async function loadApp() {
  console.log('app.js jQuery Version', $.fn.jquery)

  /**
   * Vue.js components
   *
   * We typically want to load these as soon as possible to ensure our more feature-rich experiences
   * are available to the end-user as soon-as-possible.
   */
  const vueComponents = document.querySelectorAll('[vue-component]')

  if (!authorMode && vueComponents.length) {
    const composeVue = (await import(/* webpackChunkName: "vue/compose" */ '@components/compose-vue')).default

    composeVue(vueComponents)
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
  if (authorMode) {
    // Open all the 'collapse' elements on the page, by default they are closed and can't be updated
    // by an author which isn't what we want.
    $('.collapse[data-parent]').collapse('dispose')

    // DOM watch mode!
    const watcher = (await import(/* webpackChunkName: "md/watcher" */ '@module/watcher')).default

    watcher()
  }

  /**
   * Load the Font Awesome icons now as they are the heaviest payload overall.
   *
   * TODO: Add the ability to define only a subset of icons instead of everything.
   */
  await import(/* webpackChunkName: "vd/fontawesome-brands" */ '@fortawesome/fontawesome-free/js/brands')
  await import(/* webpackChunkName: "vd/fontawesome" */ '@fortawesome/fontawesome-free/js/fontawesome')

  /**
   * IE11 fixes... ಥ﹏ಥ
   */
  if ((!!window.MSInputMethodContext && !!document.documentMode)) {
    const makeIE11Work = (await import(/* webpackChunkName: "ut/ie11-fixes" */ '@utility/ie11')).default

    makeIE11Work()
  }
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
