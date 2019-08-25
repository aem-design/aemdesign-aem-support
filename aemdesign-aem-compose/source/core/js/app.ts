import AEMFixes from '@module/aem'
import Carousels from '@module/carousel'
import Header from '@module/header'
import Icons from '@module/icons'
import Subscribers from '@module/subscribers'

import { isAuthorEditMode } from '@utility/aem'

// Begin the app...
$(async () => {

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

  // 'object-fit' polyfill for unsupported browsers
  if ('objectFit' in document.documentElement.style === false) {
    const {
      default: objectFitImages,
    } = await import(/* webpackChunkName: "object-fit-images" */ 'object-fit-images')

    objectFitImages()
  }

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

  // Load the Font Awesome icons last as they are the heaviest payload
  await import(/* webpackChunkName: "fontawesome-pro-brands" */ '@fortawesome/fontawesome-pro/js/brands')
  await import(/* webpackChunkName: "fontawesome-pro-light" */ '@fortawesome/fontawesome-pro/js/light')
  await import(/* webpackChunkName: "fontawesome-pro" */ '@fortawesome/fontawesome-pro/js/fontawesome')

})

if (module.hot) {
  module.hot.accept()
}
