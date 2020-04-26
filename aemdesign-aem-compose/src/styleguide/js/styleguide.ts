import '../scss/styleguide.scss'

async function loadApp() {
  console.log('DLS is rocking...')

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

  // Inject the collapse functionality to toggle the menu
  const header = document.querySelector('#dls-header')

  if (header) {
    let menuControl = header.querySelector('.header-menu-control')

    if (menuControl) {
      menuControl.parentNode?.removeChild(menuControl)
    }

    menuControl = document.createElement('button')
    menuControl.classList.add('header-menu-control')

    const icon = document.createElement('i')
    icon.classList.add('fas')
    icon.classList.add('fa-bars')

    menuControl.addEventListener('click', (event) => {
      event.preventDefault()

      const menu = document.querySelector('#dls-menu-controls')

      if (menu) {
        menu.classList.toggle('visible')
      }
    })

    menuControl.appendChild(icon)

    header.appendChild(menuControl)
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
