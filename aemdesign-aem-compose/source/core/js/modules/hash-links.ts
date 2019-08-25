export default () => {
  const hashLinks = document.querySelectorAll('[href*="#"]')

  if (hashLinks.length) {
    for (const item of hashLinks) {
      const hashLink = item as HTMLAnchorElement

      // Only take control of links that have a valid hash value
      if (hashLink.hash && hashLink.hash.length) {
        hashLink.addEventListener('click', (e: Event) => {
          e.preventDefault()

          if (e.target !== null) {
            const target = document.querySelector((e.target as HTMLAnchorElement).hash)

            if (target !== null) {
              const offset = target.getBoundingClientRect()

              // Scroll to the position on the page
              window.scrollTo(0, offset.top)
            }
          }
        })
      }
    }
  }
}
