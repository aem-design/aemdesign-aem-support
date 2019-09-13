import _debounce from 'lodash/debounce'
import _get from 'lodash/get'
import _isNil from 'lodash/isNil'
import _omitBy from 'lodash/omitBy'
import _throttle from 'lodash/throttle'

import { CarouselType } from '@type/enum'
import { isAuthorEditMode } from '@utility/aem'
import { breakpoints, margins } from '@utility/config'
import { getWindowWidth } from '@utility/dom'

// Internal
let carousels: NodeListOf<Element>
let lastWindowWidth: number = 0

/**
 * Retrieves the configuration for the current `target`.
 *
 * @param {HTMLElement} target Target element
 * @param {CarouselType} type The type of list the carousel applies to
 * @return {CarouselOptions}
 */
function getConfiguration(target: HTMLElement, type: CarouselType): CarouselOptions {
  let options = {
    needsCarousel : true,
    needsSplit    : false,
    refreshOnly   : false,
  }

  // Determine if the carousel is needed
  if (type === CarouselType.LIST) {
    options = determineListNeeds(target, options)
  }

  return options
}

/**
 * Determines the needs of the list is needed based on a few business rules.
 *
 * @param {HTMLElement} list List element
 * @param {CarouselOptions} options Carousel options
 * @returns {CarouselOptions}
 */
function determineListNeeds(list: HTMLElement, options: CarouselOptions): CarouselOptions {
  const target      = list.querySelector('ul.list')
  const windowWidth = getWindowWidth()

  if (!target) {
    console.warn('Unable to determine the target list!')
    return options
  }

  const isReady      = list.classList.contains('owl-ready')
  const itemsTotal   = target.children.length
  const orientation  = (screen.orientation && Math.abs(screen.orientation.angle)) || 0
  const splitEnabled = list.dataset.listSplitEnabled === 'true'

  let itemsRequired: number
  let resolutionRequired = 768

  // Quarter scenario (4 items)
  if (list.classList.contains('theme--lists-quarter')) {
    itemsRequired = 4

  // Equal scenario (2 items)
  } else if (list.classList.contains('theme--lists-equal')) {
    itemsRequired = 2

  // Full scenario (1 item)
  } else if (list.classList.contains('theme--lists-fill')) {
    itemsRequired      = 1
    resolutionRequired = breakpoints.tablet

  // Default scenario (3 items)
  } else {
    itemsRequired = 3
  }

  options.destroy     = !splitEnabled && windowWidth >= breakpoints.tablet && itemsTotal <= itemsRequired
  options.needsSplit  = splitEnabled
  options.refreshOnly = isReady

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
 * Builds a basic list of elements for the previous/next nav buttons.
 *
 * @return {string[]}
 */
function getNavTextElements(): string[] {
  return [
    '<i class="fal fa-long-arrow-left"></i>',
    '<i class="fal fa-long-arrow-right"></i>',
  ]
}

/**
 * Binds `OwlCarousel` to the given target element.
 *
 * @param {HTMLElement} element An element to bind OwlCarousel to
 * @param {HTMLElement} parent The parent element of the carousel
 * @param {CarouselOptions} options Custom options for this carousel
 */
function bindCarouselToElement(
  element: HTMLElement,
  parent: HTMLElement,
  options: CarouselOptions,
) {
  let $list = $(element)

  const totalItems = $list.find('li').length

  // When a split is active we need to clone the list items into a mobile only version to ensure
  // the visual aspects of the list remain intact.
  if (options.needsSplit) {
    const listParent = element.parentNode as Element

    if (listParent) {
      let mobileList = listParent.querySelector('ul.mobile-carousel')

      if (!mobileList) {
        mobileList = document.createElement('ul')
        mobileList.classList.add('mobile-carousel')
        mobileList.classList.add('owl-carousel')

        $list = $(mobileList as HTMLElement)

        // Migrate the existing lists items into the mobile clone
        const existingLists = listParent.querySelectorAll('ul')

        if (existingLists.length) {
          for (const list of existingLists) {
            $(list).children().each((_, item) => {
              $(item)
                .children()
                .clone(false)
                .appendTo($list)
                .wrap('<div class="item"></div>')
            })
          }
        }

        listParent.insertAdjacentElement('afterbegin', mobileList)
      } else {
        $list = $(mobileList as HTMLElement)
      }
    }
  } else {
    // Create a new host element for the carousel
    const $carousel = $('<div />', { class: 'owl-carousel' }).insertBefore($list)

    if (totalItems > 0) {
      $list.children().each((_, item) => {
        $(item)
          .children()
          .clone(false)
          .appendTo($carousel)
          .wrap('<div class="item"></div>')
      })
    }

    $list = $carousel
  }

  // Setup the configuration
  const carouselConfig = _omitBy({
    autoWidth         : _get(options, 'autoWidth', true),
    center            : _get(options, 'center', false),
    dots              : _get(options, 'dots', false),
    items             : _get(options, 'items', 1),
    loop              : _get(options, 'loop', false),
    margin            : _get(options, 'margin', margins.default),
    mouseDrag         : _get(options, 'mouseDrag', false),
    nav               : _get(options, 'nav', true),
    navClass          : _get(options, 'navClass', ['owl-prev btn', 'owl-next btn']),
    navContainerClass : _get(options, 'navContainerClass', 'owl-nav btn-group btn-group-sm'),
    navText           : _get(options, 'navText', getNavTextElements()),
    slideBy           : _get(options, 'slideBy', 1),
    stageElement      : _get(options, 'stageElement', null),
    stagePadding      : _get(options, 'stagePadding', 0),

    responsive: {
      // Mobile
      [breakpoints.extraSmall]: _omitBy({}, _isNil),

      // Large mobile (landscape) and tablets
      [breakpoints.tablet]: _omitBy({
        center : _get(options, `breakpoint.${breakpoints.tablet}.center`, false),
        items  : _get(options, `breakpoint.${breakpoints.tablet}.items`, 3),
        margin : _get(options, 'breakpoint.${breakpoints.tablet}.margin', margins.tablet),
      }, _isNil),

      // Tablets (landscape) and small desktop browsers
      [breakpoints.desktop]: _omitBy({
        center : _get(options, `breakpoint.${breakpoints.desktop}.center`, false),
        items  : _get(options, `breakpoint.${breakpoints.desktop}.items`, 3),
        margin : _get(options, 'breakpoint.${breakpoints.tablet}.margin', margins.desktop),
      }, _isNil),
    },
  }, _isNil)

  // Set the parent list as 'ready'
  parent.classList.add('owl-ready')

  // Create the carousel instance
  $list.on('initialized.owl.carousel', _debounce((event: JQuery.TriggeredEvent) => {
    const $target  = $(event.target as HTMLElement)
    const $nav     = $target.find('.owl-nav')
    const $navPrev = $nav.find('.owl-prev')
    const $navNext = $nav.find('.owl-next')

    $nav.attr('aria-label', 'Carousel navigation')
    $nav.attr('role', 'group')

    $navPrev.attr('data-layer-track', 'true')
      .attr('data-layer-location', 'content block carousel')
      .attr('data-layer-label', 'carousel left')

    $navNext.attr('data-layer-track', 'true')
      .attr('data-layer-location', 'content block carousel')
      .attr('data-layer-label', 'carousel right')
  }, 500))

  // Fixes a strange issue that began from nowhere which causes the width of the carousel
  // to be calculated incorrectly.
  setTimeout(() => $list.owlCarousel(carouselConfig), 50)
}

/**
 * Attempts to detect the give `type` using the `list` element given.
 *
 * @param {HTMLElement} list Parent element containing the list
 * @param {string} type Type of list to detect
 * @return {boolean}
 */
function detectListType(list: HTMLElement, type: string): boolean {
  return list.classList.contains(type)
}

/**
 * Detects and creates a carousel instance for the target elements.
 */
function loopAndGenerateCarousels() {
  for (const carousel of carousels) {
    const list: HTMLElement = carousel as HTMLElement

    let config: CarouselOptions = { needsCarousel: true, needsSplit: false, refreshOnly: false }
    let target: HTMLElement | null = null

    switch (true) {
      case detectListType(list, 'eventlist'):
      case detectListType(list, 'newslist'):
      case detectListType(list, 'pagelist'):
        config = getConfiguration(list, CarouselType.LIST)
        target = list.querySelector('ul.list')
        break

      default:
        console.warn('Carousel definition not defined for:', carousel)
    }

    if (target) {
      if (config.destroy === true) {
        $(list).removeClass('owl-ready').find('.owl-carousel').remove()
      } else if (config.needsCarousel && config.refreshOnly === false) {
        bindCarouselToElement(target, list, config)
      } else {
        $(list).find('.owl-carousel').trigger('refresh.owl.carousel')
      }
    }
  }
}

export default async () => {
  carousels       = document.querySelectorAll('[data-modules*="carousel"]')
  lastWindowWidth = getWindowWidth()

  if (carousels.length && !isAuthorEditMode()) {
    await import(/* webpackChunkName: "owl.carousel" */ 'owl.carousel')

    console.info('Owl Carousel loaded and ready!')

    loopAndGenerateCarousels()

    window.addEventListener('resize', _throttle(() => {
      // If the width of the window matches the last known width, do nothing!
      if (getWindowWidth() === lastWindowWidth) {
        return
      }

      loopAndGenerateCarousels()

      lastWindowWidth = getWindowWidth()
    }, 200))
  }
}
