/**
 * Retrieves the current width of the window.
 *
 * @return {Number}
 */
export function getWindowWidth(): number {
  return $(window).width() || window.innerWidth || 0
}

/**
 * Sanitize and encode all HTML in a user-submitted string.
 *
 * @copyright Chris Ferdinandi
 * @license MIT
 * @external https://gomakethings.com
 * @param {string} input The user-submitted string
 * @return {string} The sanitized string
 */
export function sanitizeHTML(input: string): string {
  const temp = document.createElement('div')
  temp.textContent = input

  return temp.innerHTML
}

/**
 * Determine if the given `selector` matches the given `el` element.
 *
 * @param {Element} el Target element to test
 * @param {string} selector String to test against the element
 * @returns {boolean}
 */
export function matches(el: Element, selector: string): boolean {
  return (
    el.matches ||
    el.matchesSelector ||
    el.msMatchesSelector ||
    el.mozMatchesSelector ||
    el.webkitMatchesSelector ||
    el.oMatchesSelector
  ).call(el, selector)
}

/**
 * Determine if the given parent `selector` matches any parent elements.
 *
 * @param {ParentNode} parent Target element to test
 * @param {string} selector String to test against the element
 * @returns {boolean}
 */
export function hasParent(parent: ParentNode | null, selector: string): boolean {
  let pn = parent

  while (pn && pn !== document) {
    if (matches(pn as Element, selector)) {
      pn = null
      return true
    } else {
      pn = (pn as Element).parentNode
    }
  }

  return false
}
