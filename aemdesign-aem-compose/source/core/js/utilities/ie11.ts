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

  // Apply a global class that we can use to target different parts of the page
  document.documentElement.classList.add('ie-11')
}
