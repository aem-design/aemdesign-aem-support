import AEMFixes from '@module/aem'
import Icons from '@module/icons'

import { isAuthorEditMode } from '@utility/aem'

// Begin the app...
$(async () => {

  console.log('app.js jQuery Version', $.fn.jquery)

  // Vue.js components, we load them before anything else as they are non-blocking
  const vueReferences = document.querySelectorAll('[vue-component]')

  if (!isAuthorEditMode() && vueReferences.length) {
    const {
      default: BindVueToPage,
    } = await import(/* webpackChunkName: "components/vue-bind" */ '@components/vue-bind')

    BindVueToPage(vueReferences)
  }

  // AEM comnponent level fixes
  AEMFixes()

  // Apply some fixes when we aren't in the AEM author 'edit' mode
  if (isAuthorEditMode()) {
    // Open all the 'collapse' elements on the page when in author
    $('.collapse[data-parent]').collapse('dispose')

    const {
      default: authorWatch,
    } = await import(/* webpackChunkName: "author-watch" */ '@module/author-watch')

    authorWatch()
  }

  // Append Font Awesome icons to any/all elements that need them
  Icons()

  // Load the Font Awesome icons last as they are the heaviest payload
  await import(/* webpackChunkName: "fontawesome-brands" */ '@fortawesome/fontawesome-free/js/brands')
  await import(/* webpackChunkName: "fontawesome" */ '@fortawesome/fontawesome-free/js/fontawesome')

  // IE11 hacky fixes ಥ﹏ಥ
  if ((!!window.MSInputMethodContext && !!document.documentMode)) {
    const {
      default: IE11Fixes,
    } = await import(/* webpackChunkName: "ie11-fixes" */ '@utility/ie11')

    IE11Fixes()
  }

})

if (module.hot) {
  module.hot.accept()
}
