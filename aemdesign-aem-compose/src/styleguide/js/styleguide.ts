import '../scss/styleguide.scss'

// Internal
let scrollOffset: number

function resetScrollOffset(): void {
  scrollOffset = parseInt(getComputedStyle(document.body).top, 10)

  document.body.classList.remove('no-scroll')
  document.body.style.top = 'auto'

  window.scrollTo(0, -scrollOffset)
}

function fixLinksForEnvironment(): void {
  const navLinks = document.querySelectorAll(
    'aside .nav-link, #dls_home_button_link',
  )

  if (navLinks.length) {
    for (const link of navLinks) {
      const href = link.getAttribute('href')

      if (href && href !== '#' && href.indexOf('wcmmode=disabled') === -1) {
        link.setAttribute(
          'href',
          `${link.getAttribute('href')}?wcmmode=disabled`,
        )
      }
    }
  }
}

function headerMenu(): void {
  const header = document.querySelector('div.header')

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

      const menuElements = document.querySelectorAll('div.aside, .menu-overlay')

      if (menuElements.length) {
        menuElements.forEach((element) => element.classList.toggle('visible'))

        if (document.body.classList.contains('no-scroll')) {
          resetScrollOffset()
        } else {
          scrollOffset = window.scrollY

          // Prevent the scroll on the body of the page and set the top offset to the current
          // scroll position so the user remains at the same offset.
          document.body.classList.add('no-scroll')
          document.body.style.top = `${-scrollOffset}px`
        }
      }
    })

    menuControl.appendChild(icon)

    header.appendChild(menuControl)
  }

  // Menu overlay
  let menuOverlay = document.querySelector('.menu-overlay')

  if (menuOverlay) {
    menuOverlay.parentNode?.removeChild(menuOverlay)
  }

  menuOverlay = document.createElement('div')
  menuOverlay.setAttribute('class', 'menu-overlay')

  document.body.appendChild(menuOverlay)
}

async function loadApp(): Promise<any> {
  console.log('DLS is rocking...')

  if (window.location.search.indexOf('wcmmode') !== -1) {
    // Change all links to have ?wcmmode=disabled to allow clean navigation experience
    fixLinksForEnvironment()
  }

  // Force open any active drop down menu
  const activeDropDown = document.querySelector(
    '.dropdown-submenu.depth-2.active > .nav-link',
  )

  if (activeDropDown) {
    $(activeDropDown).dropdown('show')

    activeDropDown.parentElement?.classList.add('keep-open')
  }

  // Inject the collapse functionality to toggle the menu
  headerMenu()
}

if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', loadApp)
} else {
  loadApp()
}

if (module.hot) {
  module.hot.accept()
}
