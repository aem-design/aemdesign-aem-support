import _throttle from 'lodash/throttle'

import { hasParent, matches, sanitizeHTML } from '@utility/dom'

// Internal
const CALLBACK_CLICK     = 'click'
const CALLBACK_KEYUP     = 'keyup'
const CALLBACK_RESIZE    = 'resize'
const QUICK_SEARCH_CLASS = '.brand-header__quick-search'

const callbacks: {
  click  : Array<(target: HTMLElement) => void>,
  keyup  : Array<(event: KeyboardEvent) => void>,
  resize : Array<() => void>,
} = {
  [CALLBACK_CLICK]  : [],
  [CALLBACK_KEYUP]  : [],
  [CALLBACK_RESIZE] : [],
}

let scrollbarWidth = 0

/**
 * Binds the submit button and form events that are used to toggle the visibility of the form
 * and submit the user input to the Funnelback hosted results page.
 *
 * @param {HTMLElement} quickSearch Quick search (AEM) component
 * @param {HTMLFormElement} searchForm Search `<form>` element
 * @param {HTMLInputElement} searchField Search `<input>` element
 * @param {HTMLElement} submitButton Search `<button>` element
 */
function bindSearchEvents({ quickSearch, searchField, searchForm, submitButton }: {
  quickSearch: HTMLElement;
  searchField: HTMLInputElement;
  searchForm: HTMLFormElement;
  submitButton: HTMLElement;
}) {
  submitButton.addEventListener('click', (event: Event) => {
    event.preventDefault()

    if (!quickSearch.classList.contains('show')) {
      setTrueWidthOfSearchField(searchField)

      quickSearch.classList.add('show')
      searchField.removeAttribute('tabindex')

      // Focus on the search input field, the timeout ensures that some mobile devices don't
      // miss this action.
      setTimeout(() => searchField.focus(), 1)
    } else {
      submitSearchForm({ searchForm, searchField })
    }
  })

  searchForm.addEventListener('submit', (event: Event) => {
    event.preventDefault()
    submitSearchForm({ searchForm, searchField })
  })
}

function setTrueWidthOfSearchField(searchField: HTMLElement) {
  searchField.style.width = ''

  const currentWidth = getComputedStyle(searchField).width

  if (currentWidth) {
    searchField.style.width = `${parseInt(currentWidth) - scrollbarWidth}px`
  }
}

/**
 * Appends the given `callback` to the `eventType` list defined.
 *
 * @template T
 * @param {(param: T) => void} callback
 * @param {string} [eventType=CALLBACK_CLICK]
 */
function setEventCallback<T>(callback: (param: T) => void, eventType: string = CALLBACK_CLICK) {
  callbacks[eventType].push(callback)
}

/**
 * Submits the user input to the defined action URL set in the backend.
 *
 * @param {HTMLInputElement} searchField Search `<input>` element
 * @param {HTMLFormElement} searchForm Search `<form>` element
 */
function submitSearchForm({ searchField, searchForm }: {
  searchField: HTMLInputElement;
  searchForm: HTMLFormElement;
}) {
  const userInput = encodeURIComponent(sanitizeHTML(searchField.value))

  if (userInput.length) {
    window.location.href = searchForm.action.replace('{{query}}', userInput)
  }
}

function getScrollbarWidth() {
  const outer = document.createElement('div')
  outer.style.visibility = 'hidden'
  outer.style.width = '100px'
  outer.style.msOverflowStyle = 'scrollbar' // needed for WinJS apps

  document.body.appendChild(outer)

  const widthNoScroll = outer.offsetWidth
  outer.style.overflow = 'scroll'

  const inner = document.createElement('div')
  inner.style.width = '100%'
  outer.appendChild(inner)

  const widthWithScroll = inner.offsetWidth

  if (outer.parentNode) {
    outer.parentNode.removeChild(outer)
  }

  return widthNoScroll - widthWithScroll
}

export default () => {
  const searchForms = document.querySelectorAll(QUICK_SEARCH_CLASS)

  if (!searchForms.length) {
    return
  }

  // Get the size of the scrollbar initially
  scrollbarWidth = getScrollbarWidth()

  for (const quickSearch of searchForms) {
    const searchField: HTMLInputElement = quickSearch.querySelector('input[type=search]') as HTMLInputElement
    const searchForm: HTMLFormElement = quickSearch.querySelector('form') as HTMLFormElement
    const submitButton: HTMLElement = quickSearch.querySelector('button[type=submit]') as HTMLElement

    if (searchField && searchForm && submitButton) {
      bindSearchEvents({ quickSearch: quickSearch as HTMLElement, searchField, searchForm, submitButton })

      // Define the event callback for when the user clicks on the page
      setEventCallback<HTMLElement>((target) => {
        if (!matches(target, QUICK_SEARCH_CLASS) && !hasParent(target.parentNode, QUICK_SEARCH_CLASS)) {
          quickSearch.classList.remove('show')
          searchField.setAttribute('tabindex', '-1')
        }
      })

      // Define the event callback for when the user releases any key on their keyboard
      setEventCallback<KeyboardEvent>(({ key }) => {
        if (/Esc(ape)?/.test(key) && quickSearch.classList.contains('show')) {
          quickSearch.classList.remove('show')
          searchField.setAttribute('tabindex', '-1')


          if (submitButton) {
            submitButton.focus()
          }
        }
      }, CALLBACK_KEYUP)

      // Define the resize event callback
      setEventCallback(() => setTrueWidthOfSearchField(searchField), CALLBACK_RESIZE)
    }
  }

  // Listen for when the user clicks around the window
  window.addEventListener('click', (event: Event) => {
    const target: HTMLElement = event.target as HTMLElement

    for (const callback of callbacks[CALLBACK_CLICK]) {
      callback(target)
    }
  })

  // Listen for when the user presses the escape key on their keyboard
  window.addEventListener('keyup', (event: KeyboardEvent) => {
    for (const callback of callbacks[CALLBACK_KEYUP]) {
      callback(event)
    }
  })

  window.addEventListener('resize', _throttle(() => {
    scrollbarWidth = getScrollbarWidth()

    for (const callback of callbacks[CALLBACK_RESIZE]) {
      callback()
    }
  }, 200))
}
