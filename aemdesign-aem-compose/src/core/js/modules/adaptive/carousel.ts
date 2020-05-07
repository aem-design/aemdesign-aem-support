// import _debounce from 'lodash/debounce'
// import _get from 'lodash/get'
// import _isNil from 'lodash/isNil'
// import _omitBy from 'lodash/omitBy'
import _throttle from 'lodash/throttle'
// import { tns } from 'tiny-slider'

import { getWindowWidth } from '@/core/utility/dom'

import type { CarouselElement } from '@/typings/carousel'
import { CarouselType } from '@/typings/enums'

// Internal
let lastWindowWidth = 0

/**
 * Try and determine what type of carousel the given `target` is based on the `expected` classes.
 *
 * @param {CarouselElement} target Target element to bind as a carousel
 * @param {string[]} expected Expected target types
 * @return {boolean}
 */
function determineTargetType(target: CarouselElement, expected: string[]): boolean {
  return expected.filter((expectation) => target.classList.contains(expectation)).length > 0
}

/**
 * Detects the type of carousel each target is and generates the correct configuration for each.
 *
 * @param {NodeListOf<CarouselElement>} targets List of target elements
 */
function generateCarouselsFromTargets(targets: NodeListOf<CarouselElement>) {
  for (const target of targets) {
    let carouselTargets: NodeListOf<CarouselElement> | null = null
    let carouselType: CarouselType | false = false

    switch (true) {
      case determineTargetType(target, ['pagelist']):
        carouselTargets = target.querySelectorAll('ul.list')
        carouselType    = CarouselType.LIST
        break

      default:
        console.warn('[Carousel] Definition not defined for:', target)
    }

    if (carouselType !== false && carouselTargets && carouselTargets.length) {
      console.log('[Carousel] Looks like every is valid for the target, the type is:', carouselType, target)
      // console.log('[Carousel] ')
    } else {
      console.warn('[Carousel] It appears the target is invalid as no valid assignable targets were found!', target)
    }
  }
}

export default async (targets: NodeListOf<CarouselElement>) => {
  console.log('[Carousel] Ready to adapt the following targets as carousels:', targets)

  lastWindowWidth = getWindowWidth()

  // Create the carousels for the given targets
  generateCarouselsFromTargets(targets)

  // Watch for resize events, this will enable us to automatically regenerate the carousels when
  // the size of the window changes.
  window.addEventListener('resize', _throttle(() => {
    if (getWindowWidth() !== lastWindowWidth) {
      generateCarouselsFromTargets(targets)

      lastWindowWidth = getWindowWidth()
    }
  }, 200))
}
