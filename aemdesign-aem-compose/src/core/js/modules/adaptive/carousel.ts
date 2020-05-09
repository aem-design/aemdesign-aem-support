// import _debounce from 'lodash/debounce'
// import _get from 'lodash/get'
// import _isNil from 'lodash/isNil'
// import _omitBy from 'lodash/omitBy'
import _throttle from 'lodash/throttle'
// import { tns } from 'tiny-slider'

import { getWindowWidth } from '@/core/utility/dom'
import { breakpoints } from '@/core/utility/config'

import type { CarouselElement, CarouselOptions } from '@/typings/carousel'
import { CarouselType } from '@/typings/enums'

// console.log('[Carousel] ')

// Internal
const DEFAULT_OPTIONS: CarouselOptions = {
  needsCarousel : true,
  needsSplit    : false,
  refreshOnly   : false,
  responsive    : false,
  targets       : null,
}

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
 * Adjust how the carousel behaves based on some core business rules.
 *
 * @param {HTMLElement} list List element
 * @param {CarouselOptions} options Carousel options
 * @return {CarouselOptions}
 */
function determineListNeeds(list: HTMLElement, options: CarouselOptions): CarouselOptions {
  const target = list.querySelector('ul.list')

  if (!target) {
    console.warn('Unable to determine the target list!')
    return options
  }

  const isReady      = list.classList.contains('tns-ready')
  const itemsTotal   = target.children.length
  const orientation  = (screen.orientation && Math.abs(screen.orientation.angle)) || 0
  const splitEnabled = list.dataset.listSplitEnabled === 'true'

  let autoWidth = true
  let itemsRequired: number
  let resolutionRequired = 768

  switch (true) {
    // Quarter scenario (4 items)
    case list.classList.contains('theme--lists-quarter'):
      itemsRequired = 4
      break
    // Equal scenario (2 items)
    case list.classList.contains('theme--lists-equal'):
      itemsRequired = 2
      break
    // Full scenario (1 item)
    case list.classList.contains('theme--lists-fill'):
      itemsRequired      = 1
      resolutionRequired = breakpoints.tablet
      autoWidth          = false
      break
    default:
      itemsRequired = 3
  }

  const windowWidth  = getWindowWidth()

  options.destroy     = !splitEnabled && windowWidth >= breakpoints.tablet && itemsTotal <= itemsRequired
  options.needsSplit  = splitEnabled
  options.refreshOnly = isReady
  options.autoWidth   = autoWidth

  options.needsCarousel = (splitEnabled && itemsTotal >= itemsRequired) ||
    (
      !isReady &&
      (
        (windowWidth <= breakpoints.tablet && itemsTotal >= 2 && orientation !== 90) ||
        (
          (windowWidth < resolutionRequired || windowWidth >= resolutionRequired) &&
          itemsTotal > itemsRequired
        )
      )
    )

  return options
}

/**
 * Constructs the configuration for the current `target`.
 *
 * @param {CarouselElement} target Target element
 * @param {CarouselType} type The type of list the carousel applies to
 * @return {CarouselOptions}
 */
function getConfiguration(target: CarouselElement, type: CarouselType): CarouselOptions {
  let options: CarouselOptions = JSON.parse(JSON.stringify(DEFAULT_OPTIONS))

  options.responsive = !target.classList.contains('theme--lists-fill')

  if (type === CarouselType.LIST) {
    options = determineListNeeds(target, options)
  }

  return options
}

/**
 * Remove the carousel from the given `target` element.
 *
 * @param target Target element to destroy
 */
function removeCarouselFromTarget(target: CarouselElement) {
  if (target.tinyslider) {
    target.classList.remove('tns-ready')
    target.tinyslider.destroy()
  }
}

/**
 * Detects the type of carousel each target is and generates the correct configuration for each.
 *
 * @param {NodeListOf<CarouselElement>} targets List of target elements
 */
function generateCarouselsFromTargets(targets: NodeListOf<CarouselElement>) {
  for (const target of targets) {
    let carouselType: CarouselType | false = false

    switch (true) {
      case determineTargetType(target, ['pagelist']):
        carouselType = CarouselType.LIST
        break

      default:
        console.warn('[Carousel] Definition not defined for:', target)
    }

    if (carouselType !== false) {
      console.log('[Carousel] Looks like every is valid for the target, the type is:', carouselType, target)

      const config = getConfiguration(target, carouselType)
      console.log('[Carousel] Configuration:', config)

      if (config.destroy === true) {
        removeCarouselFromTarget(target)
      }
    } else {
      console.warn('[Carousel] It appears the target is invalid!', target)
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
