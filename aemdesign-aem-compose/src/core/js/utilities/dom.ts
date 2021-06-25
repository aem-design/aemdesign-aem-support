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
 * @param {string} selector String to test elements against
 * @param {boolean} [returnOnFound=false] Return the matched selected when found?
 * @returns {ParentNode | boolean}
 */
export function hasParent(
  parent: ParentNode | null,
  selector: string,
  returnOnFound = false,
): ParentNode | boolean {
  let pn = parent

  while (pn && pn !== document) {
    if (matches(pn as Element, selector)) {
      return returnOnFound ? pn : true
    } else {
      pn = (pn as Element).parentNode
    }
  }

  return false
}

/**
 * Attempt to find a parent element using the given `selector`.
 *
 * @param {ParentNode} currentNode Current element in context
 * @param {string} selector String to test elements against
 * @return {Element | null}
 */
export function getParent(currentNode: Element, selector: string): Element | null {
  const parent = hasParent(currentNode.parentNode, selector, true)

  if (parent !== false) {
    return parent as Element
  }

  return null
}
