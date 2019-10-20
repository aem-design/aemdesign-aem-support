import AEMFixes from '@module/aem'
import Carousels from '@module/carousel'
import Forms from '@module/forms'
import Header from '@module/header'
import Icons from '@module/icons'
import Subscribers from '@module/subscribers'

import { isAuthorEditMode } from '@utility/aem'

// Begin the app...
$(async () => {

  console.log('app.js jQuery Version', $.fn.jquery)

  // Modify any form elements on the page first as they are the most important components
  // that require custom modifications in order to work correctly.
  const formElements = document.querySelectorAll('input:not([type=button]), select')

  if (!isAuthorEditMode()) {
    Forms(formElements)
  }

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

  // Carousel functionality for anything!
  Carousels()

  // Bind the pub/sub event subscribers
  Subscribers()

  // Header controls
  Header()

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

  // Look for any media elements on the page
  const mediaElements = document.querySelectorAll('[data-media]')

  if (mediaElements.length && !isAuthorEditMode()) {
    const {
      default: media,
    } = await import(/* webpackChunkName: "media" */ '@module/media')

    media(mediaElements)
  }

  // Load the Font Awesome icons last as they are the heaviest payload
  await import(/* webpackChunkName: "fontawesome-free-brands" */ '@fortawesome/fontawesome-free/js/brands')
  await import(/* webpackChunkName: "fontawesome-free-solid" */ '@fortawesome/fontawesome-free/js/solid')
  await import(/* webpackChunkName: "fontawesome-free" */ '@fortawesome/fontawesome-free/js/fontawesome')

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
