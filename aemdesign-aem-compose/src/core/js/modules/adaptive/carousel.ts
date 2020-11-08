import _get from 'lodash/get'
import _throttle from 'lodash/throttle'

import { tns } from 'tiny-slider/src/tiny-slider'

import { getWindowWidth } from '@/core/utility/dom'
import { breakpoints, margins } from '@/core/utility/config'

import type {
  CarouselElement,
  CarouselConfiguration,
  CarouselOptions,
  CarouselSettings,
} from '@/typings/carousel'

import { CarouselType } from '@/typings/enums'

// Internal
const DEFAULT_OPTIONS: CarouselConfiguration = {
  needsCarousel: true,
  needsSplit: false,
  refreshOnly: false,
  type: null,

  carouselOptions: {
    breakpoints: {
      [breakpoints.extraSmall]: {},
      [breakpoints.tablet]: {},
      [breakpoints.desktop]: {},
    },

    responsive: false,
  },
}

let lastWindowWidth = 0

/**
 * Try and determine what type of carousel the given `target` is based on the `expected` classes.
 *
 * @param {CarouselElement} target Target element to bind as a carousel
 * @param {string[]} expected Expected target types
 * @return {boolean}
 */
function determineTargetType(
  target: CarouselElement,
  expected: string[],
): boolean {
  return (
    expected.filter((expectation) => target.classList.contains(expectation))
      .length > 0
  )
}

/**
 * Adjust how the carousel behaves based on some core business rules.
 *
 * @param {HTMLElement} target Target element
 * @param {CarouselConfiguration} options Carousel options
 * @return {CarouselConfiguration}
 */
function getListConfiguration(
  target: HTMLElement,
  options: CarouselConfiguration,
): CarouselConfiguration {
  const carouselTarget = getCarouselTargetByType(target, CarouselType.LIST)

  if (!carouselTarget) {
    throw new Error(`Unable to find carousel target for: ${target.id}`)
  }

  const isReady = target.classList.contains('tns-ready')
  const itemsTotal = carouselTarget.children.length
  const orientation =
    (screen.orientation && Math.abs(screen.orientation.angle)) || 0
  const splitEnabled = target.dataset.listSplitEnabled === 'true'

  let resolutionRequired = 768
  let slideItemsBy = 1
  let totalItems: number

  switch (true) {
    // Quarter scenario (4 items)
    case target.classList.contains('theme--lists-quarter'):
      slideItemsBy = 4
      totalItems = 4
      break
    // Equal scenario (2 items)
    case target.classList.contains('theme--lists-equal'):
      slideItemsBy = 2
      totalItems = 2
      break
    // Full scenario (1 item)
    case target.classList.contains('theme--lists-fill'):
      resolutionRequired = breakpoints.tablet
      totalItems = 1
      break
    // Default (3 items)
    default:
      totalItems = 3
  }

  const windowWidth = getWindowWidth()

  options.destroy =
    !splitEnabled &&
    windowWidth >= breakpoints.tablet &&
    itemsTotal <= totalItems
  options.needsSplit = splitEnabled
  options.refreshOnly = isReady

  options.carouselOptions.responsive = !target.classList.contains(
    'theme--lists-fill',
  )

  options.carouselOptions.breakpoints[breakpoints.tablet].items = totalItems
  options.carouselOptions.breakpoints[breakpoints.tablet].slideBy = slideItemsBy

  options.carouselOptions.breakpoints[breakpoints.desktop].items = totalItems
  options.carouselOptions.breakpoints[
    breakpoints.desktop
  ].slideBy = slideItemsBy

  options.needsCarousel =
    (splitEnabled && itemsTotal >= totalItems) ||
    (!isReady &&
      ((windowWidth <= breakpoints.tablet &&
        itemsTotal >= 2 &&
        orientation !== 90) ||
        ((windowWidth < resolutionRequired ||
          windowWidth >= resolutionRequired) &&
          itemsTotal > totalItems)))

  return options
}

/**
 * Constructs the configuration for the current `target`.
 *
 * @param {CarouselElement} target Target element
 * @param {CarouselType} type The type of list the carousel applies to
 * @return {CarouselConfiguration}
 */
function getConfiguration(
  target: CarouselElement,
  type: CarouselType,
): CarouselConfiguration {
  let options: CarouselConfiguration = JSON.parse(
    JSON.stringify(DEFAULT_OPTIONS),
  )

  options.type = type

  if (type === CarouselType.LIST) {
    options = getListConfiguration(target, options)
  }

  return options
}

/**
 * Remove the carousel from the given `target` element.
 *
 * @param {CarouselElement} target Target element to destroy
 */
function removeCarouselFromTarget(target: CarouselElement) {
  if (target.tinyslider) {
    target.classList.remove('tns-ready')

    target.tinyslider.destroy()
    target.tinyslider = undefined
  }
}

/**
 * Refresh the carousel for the given `target` element.
 *
 * @param {CarouselElement} target Target carousel to refresh
 */
function refreshCarouselByTarget(target: CarouselElement) {
  if (target.tinyslider) {
    target.tinyslider.refresh()
  }
}

/**
 * Retrieve the target element for the carousel based on the carousel `type`.
 *
 * @param {HTMLElement} target Element to detect target from
 * @param {CarouselType} carouselType The type of carousel this should be
 * @return {HTMLElement}
 */
function getCarouselTargetByType(
  target: HTMLElement,
  carouselType: CarouselType | null,
): HTMLElement {
  if (carouselType === CarouselType.LIST) {
    return target.querySelector('ul.list') as HTMLElement
  }

  return target
}

/**
 * Address custom behaviours required by a list carousel.
 *
 * @param {HTMLElement} target Target element
 * @param {HTMLElement} carouselTarget Target containing the carousel items
 */
function handleCustomListCarouselBehaviours(
  target: HTMLElement,
  carouselTarget: HTMLElement,
) {
  let carouselElement = target.querySelector('.carousel-hook')

  // Stop here when a hook already exists to prevent duplication
  if (carouselElement) {
    return
  }

  carouselElement = document.createElement('div')
  carouselElement.classList.add('carousel-hook')

  const listItems = target.querySelectorAll('ul.list > li')

  if (listItems.length) {
    for (const item of listItems) {
      const itemElement = document.createElement('div')
      itemElement.classList.add('item')

      item.childNodes.forEach((child) =>
        itemElement.appendChild(child.cloneNode(true)),
      )

      carouselElement.appendChild(itemElement)
    }
  }

  // Wrap the carousel element in another element so we can target it correctly in CSS
  const carouselWrapperElement = document.createElement('div')
  carouselWrapperElement.classList.add('carousel-wrapper')

  carouselWrapperElement.appendChild(carouselElement)

  carouselTarget.insertAdjacentElement('beforebegin', carouselWrapperElement)
}

/**
 * Generate the configuration needed for `tiny-slider` to function correctly with our component.
 *
 * @param {CarouselOptions} carouselOptions Options that define the carousels behaviour
 * @return {CarouselSettings}
 */
function getCarouselSettingsByConfig(
  carouselOptions: CarouselOptions,
): CarouselSettings {
  return {
    center: _get(carouselOptions, 'center', false),
    controlsText: _get(carouselOptions, 'controlsText', ['Previous', 'Next']),
    edgePadding: _get(carouselOptions, 'edgePadding', 0),
    gutter: _get(carouselOptions, 'gutter', margins.mobile),
    items: _get(carouselOptions, 'items', 1),
    loop: _get(carouselOptions, 'loop', false),
    mouseDrag: _get(carouselOptions, 'mouseDrag', false),
    nav: _get(carouselOptions, 'nav', false),
    slideBy: _get(carouselOptions, 'slideBy', 1),

    // Responsive overrides for each breakpoint
    // NOTE: Breakpoints + margins are controlled via scss/settings/_common.scss
    responsive: carouselOptions.responsive
      ? {
          // Mobile
          [breakpoints.extraSmall]: {
            ...carouselOptions.breakpoints[breakpoints.tablet],
          },

          // Large mobiles (landscape) and tablets in portrait
          [breakpoints.tablet]: {
            ...carouselOptions.breakpoints[breakpoints.tablet],

            center: _get(
              carouselOptions,
              `breakpoints.${breakpoints.tablet}.center`,
              false,
            ),
            items: _get(
              carouselOptions,
              `breakpoints.${breakpoints.tablet}.items`,
              3,
            ),
            gutter: _get(
              carouselOptions,
              `breakpoints.${breakpoints.tablet}.gutter`,
              margins.tablet,
            ),
          },

          // Tablets (landscape) and desktop browsers
          [breakpoints.desktop]: {
            ...carouselOptions.breakpoints[breakpoints.desktop],

            center: _get(
              carouselOptions,
              `breakpoints.${breakpoints.desktop}.center`,
              false,
            ),
            items: _get(
              carouselOptions,
              `breakpoints.${breakpoints.desktop}.items`,
              3,
            ),
            gutter: _get(
              carouselOptions,
              `breakpoints.${breakpoints.desktop}.gutter`,
              margins.desktop,
            ),
          },
        }
      : false,

    // TODO: Find 'tiny-slider' replacements for these options
    // dots              : _get(carouselOptions, 'dots', false),
    // nav               : _get(carouselOptions, 'nav', true),
    // navClass          : _get(carouselOptions, 'navClass', ['owl-prev btn btn', 'owl-next btn']),
    // navContainerClass : _get(carouselOptions, 'navContainerClass', 'owl-nav btn-group'),
    // stageElement      : _get(carouselOptions, 'stageElement', null),
  }
}

/**
 * Attachs a `tiny-slider` instance to the given `target` element.
 *
 * @param {CarouselElement} target Target element to attach to
 * @param {CarouselConfiguration} config Configuration for the carousel
 */
function attachCarouselToTarget(
  target: CarouselElement,
  config: CarouselConfiguration,
) {
  const carouselTarget = getCarouselTargetByType(target, config.type)

  if (config.type === CarouselType.LIST) {
    handleCustomListCarouselBehaviours(target, carouselTarget)
  } else {
    carouselTarget.classList.add('carousel-hook')
  }

  // Let the CSS know that the carousel is ready to be attached (but not need initialised)
  target.classList.add('tns-ready')

  // Finally.. attach 'tiny-slider' to our hook element
  const carouselHookElement = target.querySelector('.carousel-hook')

  if (carouselHookElement) {
    target.tinyslider = tns({
      ...getCarouselSettingsByConfig(config.carouselOptions),

      container: carouselHookElement,

      onInit() {
        if (config.type === CarouselType.LIST) {
          const carouselWrapperElement = target.querySelector(
            '.carousel-wrapper',
          )

          if (carouselWrapperElement) {
            // Check to see if the carousel is based on a split list and whether we need to handle
            // some additional functional behaviours.
            if (config.needsSplit) {
              carouselWrapperElement.classList.add('carousel-wrapper--mobile')
            }
          }
        }

        // Make the navigation controls Bootstrap friendly
        const controlsElement = target.querySelector('.tns-controls')

        if (controlsElement) {
          const controlElements = controlsElement.querySelectorAll(
            'button[data-controls]',
          )

          for (const controlElement of controlElements) {
            controlElement.classList.add('btn')
            controlElement.classList.add('btn-primary')
          }
        }
      },
    })
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

    if (determineTargetType(target, ['pagelist'])) {
      carouselType = CarouselType.LIST
    }

    if (carouselType !== false) {
      console.log(
        '[Carousel] Looks like everything is valid for the target, the carousel type is:',
        carouselType,
        target,
      )

      try {
        const config = getConfiguration(target, carouselType)
        console.log('[Carousel] Configuration:', config)

        // Destroy the carousel
        if (config.destroy) {
          removeCarouselFromTarget(target)
        }

        // Attach the carousel
        if (!config.destroy && config.needsCarousel && !config.refreshOnly) {
          attachCarouselToTarget(target, config)
        }

        // Refresh the carousel
        if (!config.destroy && config.refreshOnly) {
          refreshCarouselByTarget(target)
        }
      } catch (e) {
        console.error('[Carousel] Unexpected error!', e)
      }
    } else {
      console.warn('[Carousel] It appears the target is invalid!', target)
    }
  }
}

export default async (targets: NodeListOf<CarouselElement>): Promise<void> => {
  console.log(
    '[Carousel] Ready to adapt the following targets as carousels:',
    targets,
  )

  lastWindowWidth = getWindowWidth()

  // Create the carousels for the given targets
  generateCarouselsFromTargets(targets)

  // Watch for resize events, this will enable us to automatically regenerate the carousels when
  // the size of the window changes.
  window.addEventListener(
    'resize',
    _throttle(() => {
      if (getWindowWidth() !== lastWindowWidth) {
        generateCarouselsFromTargets(targets)

        lastWindowWidth = getWindowWidth()
      }
    }, 200),
  )
}
