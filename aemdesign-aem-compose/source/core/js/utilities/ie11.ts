export default () => {
  // KeyboardEvent polyfill to map the key bindings correctly
  //
  // Our implementation here is slightly different to the documentation because the NPM code that
  // is deployed doesn't match the distribution code on GitHub which is annoying!!
  const kbp: {
    keys: {
      [key: number]: string | string[],
    },
  } = require('keyboardevent-key-polyfill')

  const keyboardProto: ThisType<KeyboardEvent> & { get: () => string } = {
    get() {
      let key = kbp.keys[this.which || this.keyCode];

      if (Array.isArray(key)) {
        key = key[+this.shiftKey]
      }

      return key
    },
  }

  Object.defineProperty(KeyboardEvent.prototype, 'key', keyboardProto)

  // Fixes an issue by which the logo in the header appears smaller than it should, this
  // code dynamically loads the original image source and applies a viewbox to it which
  // corrects the width and height
  $('.brand-header__container .theme--logo img[src$="svg"]').each((_, element) => {
      const $img = $(element)

      const imageSource = $img.prop('src')

      $.get(imageSource)
        .then((data) => {
          const svg    = data.querySelector('svg')
          const height = svg.getAttribute('height') || $img.height()
          const width  = svg.getAttribute('width')  || $img.width()

          svg.setAttribute('viewBox', (`0 0 ${width} ${height}`))

          const serializedString = new XMLSerializer().serializeToString(svg)

          $img.attr('src', `data:image/svg+xml;base64,${btoa(serializedString)}`)
        })
        .catch(() => console.warn('Failed to load the SVG image for:', imageSource))
  })

  // Apply a global class that we can use to target different parts of the page
  document.documentElement.classList.add('ie-11')
}
