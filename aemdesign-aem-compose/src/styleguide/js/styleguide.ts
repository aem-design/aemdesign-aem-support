import '../scss/styleguide.scss'

async function loadApp() {
  console.log('Styleguide is here...')

  const queryParam = new URLSearchParams(window.location.search)

  if (queryParam.has('wcmmode')) {
    // Change all links to have ?wcmmode=disabled to allow clean navigation experience
    const navLinks = document.querySelectorAll('aside .nav-link, #dls_home_button_link')

    if (navLinks.length) {
      for (const link of navLinks) {
        const href = link.getAttribute('href')

        if (href && href.indexOf('wcmmode=disabled') === -1) {
          link.setAttribute('href', `${link.getAttribute('href')}?wcmmode=disabled`)
        }
      }
    }
  }
}

if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', loadApp)
} else {
  loadApp()
}

if (module.hot) {
  module.hot.accept()
}
