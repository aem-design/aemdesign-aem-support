console.log('DLS is here...')

// Change all links to have ?wcmmode=disabled to allow clean navigation experience
const navLinks = document.querySelectorAll('aside .nav-link')

if (navLinks.length) {
  for (const link of navLinks) {
    link.setAttribute('href', link.getAttribute('href') + '?wcmmode=disabled')
  }
}
