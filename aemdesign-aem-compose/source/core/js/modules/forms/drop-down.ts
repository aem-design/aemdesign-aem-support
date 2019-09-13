import { Key } from 'w3c-keys'

import {
  dispatchInputEvent,
  hasTouch,
  setCustomSelectionFromInput,
} from '@module/forms/common'

import { MouseAndTouchType, InputType } from '@type/enum'

import {
  FORM_CLASSES_DROPDOWN,
  FORM_EVENT_GLOBAL_CLICK,
  FORM_EVENT_INPUT_CHANGE,
  FORM_EVENT_MOBILE_CHANGE,
  FORM_EVENT_MOUSE_TOUCH,
  FORM_EVENT_INPUT_FOCUS,
} from '@utility/constants'

import { hasParent, matches } from '@utility/dom'

type ToggleState = 'on' | 'off'

/**
 * Handle all input for the drop-down whether it is mouse, touch or the focus state.
 *
 * @param {Event} event Event for the input
 * @param {HTMLInputElement} input Drop-down field
 * @param {InputType | MouseAndTouchType} state Enum value for the current event state
 */
function handleMouseTouchAndFocusForDropDown(
  event: Event,
  input: HTMLInputElement,
  state: InputType | MouseAndTouchType,
) {
  const openClass = FORM_CLASSES_DROPDOWN.open
  const wrapper   = input.nextElementSibling as HTMLElement

  let openCustomSelectOptions = true

  if (state === MouseAndTouchType.MOUSEDOWN || state === InputType.FOCUS) {
    if (wrapper.classList.contains(openClass) && !hasTouch()) {
      toggleCustomDropDownOptions(wrapper, 'off')

      wrapper.classList.remove(openClass)

      openCustomSelectOptions = false
    } else {
      wrapper.classList.add(openClass)
    }
  }

  if (!hasTouch()) {
    event.preventDefault()

    if (state === MouseAndTouchType.MOUSEUP && openCustomSelectOptions) {
      toggleCustomDropDownOptions(wrapper, 'on')
    }
  }
}

/**
 * replicates native <select> behavour of focusing on current option
 * @param {HTMLUListElement} options
 */
function focusOnActiveOption(options: HTMLUListElement) {
  for (const child of options.childNodes) {
    const option = child as HTMLLIElement

    if (option.getAttribute('aria-selected') === 'true') {
      option.focus()
      break
    }
  }
}

/**
 * Toggles the custom select options element based on the given `state`.
 *
 * @param {HTMLElement} wrapper Wrapper element that contains the options
 * @param {ToggleState} state Shown or hidden?
 */
function toggleCustomDropDownOptions(wrapper: HTMLElement, state: ToggleState): ToggleState | null {
  const input   = wrapper.previousElementSibling as HTMLSelectElement
  const options = wrapper.querySelector<HTMLUListElement>(`.${FORM_CLASSES_DROPDOWN.options}`)

  if (!input || !options ||  (!wrapper.classList.contains(FORM_CLASSES_DROPDOWN.open) && state === 'on')) {
    console.warn('[Forms] Invalid state for the custom options detected!', wrapper)
    return null
  }

  if (state === 'on') {
    options.classList.add(`${FORM_CLASSES_DROPDOWN.options}--visible`)

    wrapper.setAttribute('aria-expanded', 'true')

    setTimeout(() => {
      options.classList.add(`${FORM_CLASSES_DROPDOWN.options}--open`)
      options.scrollTop = 0

      focusOnActiveOption(options);
    }, 50)
  } else {
    options.classList.remove(`${FORM_CLASSES_DROPDOWN.options}--open`)
    options.classList.remove(`${FORM_CLASSES_DROPDOWN.options}--visible`)

    wrapper.setAttribute('aria-expanded', 'false')
  }

  // Set the active descendant
  const newSelection = input.options[input.selectedIndex].customItem.id

  if (options.getAttribute('aria-activedescendant') !== newSelection) {
    options.setAttribute('aria-activedescendant', newSelection)
  }

  return state
}

/**
 * adds hidden 'seclect' text to HTML as aria isn't reading the active state
 *
 * @param {HTMLLIElement} item
 *  dropdown node to add accessible text to
 */
function addSelectedScreenReaderStateToElement(
  item: HTMLLIElement
) {
  // warn: must be declared in scope otherwise it removes existing ones
  const activeScreenReaderState = document.createElement('span')
  activeScreenReaderState.classList.add(FORM_CLASSES_DROPDOWN.selectedAria)
  activeScreenReaderState.classList.add('sr-only')

  activeScreenReaderState.innerText = 'selected'

  item.insertAdjacentElement('afterbegin', activeScreenReaderState)
}

/**
 * Create a custom option element based on the given `option`.
 *
 * @param {HTMLOptionElement} option Original `<option>` element
 * @param {HTMLUListElement} options Custom list to store the option
 * @param {() => void} selectionCallback Callback to run when an option is selected
 * @returns {HTMLLIElement}
 */
function createCustomOptionsItem(
  option: HTMLOptionElement,
  options: HTMLUListElement,
  selectionCallback: () => void,
  wrapper: HTMLDivElement,
): HTMLLIElement {
  const item = document.createElement('li')
  item.classList.add(FORM_CLASSES_DROPDOWN.option)

  item.setAttribute('aria-selected', option.selected ? 'true' : 'false')
  item.setAttribute('id', `${options.id}-${option.index}`)
  item.setAttribute('role', 'option')
  item.setAttribute('tabindex', '0')

  item.innerText    = option.innerText
  item.optionTarget = option

  if (option.selected) {
    addSelectedScreenReaderStateToElement(item);
  }

  item.addEventListener('click', () => {
    const previous = options.querySelector('li[aria-selected=true]') as HTMLLIElement;

    if (previous) {
      const previousAriaText = previous.querySelector(`.${FORM_CLASSES_DROPDOWN.selectedAria}`);

      previous.setAttribute('aria-selected', 'false')
      previous.optionTarget.selected = false

      if (previousAriaText) {
        previousAriaText.remove();
      }
    }

    item.setAttribute('aria-selected', 'true')
    item.optionTarget.selected = true

    addSelectedScreenReaderStateToElement(item);

    hideCustomDropDownFromView(wrapper);

    selectionCallback()
  })

  option.customItem = item

  // Run the selection callback now if the current option is selected already
  if (option.selected) {
    selectionCallback()
  }

  return item
}

/**
 * Generates a callback that is used to toggle the state of the drop-down when an option has
 * been selected by the user.
 *
 * @param {HTMLSelectElement} input Drop-down field
 * @param {HTMLParagraphElement} selectionElement Element to set the selected option in
 * @param {HTMLDivElement} wrapper Custom wrapper element for the drop-down
 */
function setSelectionCallback(
  input: HTMLSelectElement,
  selectionElement: HTMLParagraphElement,
  wrapper: HTMLDivElement,
) {
  return () => {
    setCustomSelectionFromInput(input, selectionElement)
    // toggleCustomDropDownOptions(wrapper, 'off')

    // wrapper.classList.remove(FORM_CLASSES_DROPDOWN.open)

    // Trigger the change
    dispatchInputEvent(input, 'input')
  }
}

/**
 * Attach a `keydown` event to the drop-down wrapper so keyboard users are able to navigate
 * the custom version of the drop-down.
 *
 * @param {HTMLElement} wrapper Custom wrapper element
 * @param {HTMLUListElement} options Custom options list
 */
function bindKeyboardEventsToDropDown(wrapper: HTMLElement, options: HTMLUListElement) {
  wrapper.addEventListener('keydown', (event: KeyboardEvent) => {
    const expanded = wrapper.getAttribute('aria-expanded') === 'true'
    const key      = event.key

    if (
      key === Key.Escape ||
      (!expanded && (key === Key.Space || key === Key.ArrowDown || key === Key.ArrowUp))
    ) {
      event.preventDefault()

      console.info("[Forms] '%s' occurred on a drop-down", key, wrapper)

      if (!expanded) {
        wrapper.classList.add(FORM_CLASSES_DROPDOWN.open)
      }

      const state = toggleCustomDropDownOptions(wrapper, key === Key.Escape || expanded ? 'off' : 'on')

      if (state === 'off') {
        hideCustomDropDownFromView(wrapper)
      }
    }
  }, true)

  // Bind various events to each custom list option
  for (const option of options.children as HTMLCollectionOf<HTMLLIElement>) {
    bindKeyboardEventsToDropDownOption(option, wrapper)
  }
}

/**
 * Attach a `keydown` event to the drop-down option so keyboard users are able to navigate
 * and select the original drop-down option.
 *
 * @param {HTMLLIElement} option Custom list option
 * @param {HTMLElement} wrapper Custom wrapper element
 */
function bindKeyboardEventsToDropDownOption(option: HTMLLIElement, wrapper: HTMLElement) {
  option.addEventListener('keydown', (event: KeyboardEvent) => {
    const key = event.key

    console.info("[Forms] '%s' occurred on a drop-down custom list option", key, option)

    if (key === Key.Enter || key === Key.Space) {
      dispatchInputEvent(option, 'click')
      hideCustomDropDownFromView(wrapper)

      return
    }

    const isTabKey = key === Key.Tab

    const nextOption = ((isTabKey && event.shiftKey) || key === Key.ArrowUp ?
      option.previousSibling : option.nextSibling) as HTMLLIElement

    if ((key === Key.ArrowDown || key === Key.ArrowUp) && nextOption) {
      event.preventDefault()
      nextOption.focus()

      return
    }

    if (isTabKey && !nextOption) {
      event.preventDefault()
      hideCustomDropDownFromView(wrapper)
    }
  })
}

/**
 * Hide the custom drop-down options and focus on the `wrapper` element.
 *
 * @param {HTMLElement} wrapper Custom wrapper element
 */
function hideCustomDropDownFromView(wrapper: HTMLElement) {
  toggleCustomDropDownOptions(wrapper, 'off')
  wrapper.classList.remove(FORM_CLASSES_DROPDOWN.open)

  // Put the focus back onto the wrapper
  wrapper.focus()
}

/**
 * Wrap the given drop-down input with a custom wrapper so we can show a custom UI
 * around it on any device.
 *
 * @param {HTMLElement} input Drop-down field
 * @param {HTMLLabelElement} label Drop-down label
 */
function bindCustomWrapperToDropdown(input: HTMLSelectElement, label: HTMLLabelElement) {
  const parent = input.parentNode as HTMLElement

  if (!parent || !parent.parentNode) {
    console.warn('[Forms] Unable to wrap drop-down as the parent element/node is invalid!', parent, parent.parentNode)
    return
  }

  parent.classList.add('drop-down')

  // <div class="drop-down__wrapper"></div>
  const wrapper = document.createElement('div')
  wrapper.classList.add(FORM_CLASSES_DROPDOWN.wrapper)

  wrapper.setAttribute('aria-expanded', 'false')
  wrapper.setAttribute('aria-labelledby', `${label.id} ${input.id}-selection`)
  wrapper.setAttribute('tabindex', '0')

  // <p class="drop-down__selection"></p>
  const selection = document.createElement('p')
  selection.classList.add(FORM_CLASSES_DROPDOWN.selection)

  selection.setAttribute('id', `${input.id}-selection`)

  // <ul class="drop-down__options"></ul>
  const customOptions = document.createElement('ul')
  customOptions.classList.add(FORM_CLASSES_DROPDOWN.options)

  customOptions.setAttribute('aria-haspopup', 'listbox')
  customOptions.setAttribute('aria-labelledby', label.id)
  customOptions.setAttribute('id', `${input.id}-options`)
  customOptions.setAttribute('role', 'listbox')

  const selectionCallback = setSelectionCallback(input, selection, wrapper)

  for (const originalOption of input.options) {
    customOptions.appendChild(createCustomOptionsItem(
      originalOption,
      customOptions,
      selectionCallback,
      wrapper
    ))
  }

  // <i class="drop-down__chevron"></i>
  const chevron = document.createElement('i')
  chevron.setAttribute('class', 'drop-down__chevron fal fa-chevron-down')

  wrapper.appendChild(selection)
  wrapper.appendChild(chevron)
  wrapper.appendChild(customOptions)

  input.insertAdjacentElement('afterend', wrapper)

  // Set the keyboard events
  bindKeyboardEventsToDropDown(wrapper, customOptions)
}

/**
 * Determines if any of the drop-downs on the page need to be hidden based on the target of
 * the users click on the page.
 *
 * @param {Event} event Click event
 * @param {HTMLElement} target Target element that was clicked
 * @param {HTMLInputElement} input Drop-down field to test
 */
function handleGlobalClickEventForDropdown(event: Event, target: HTMLElement, input: HTMLInputElement) {
  const wrapper = input.nextElementSibling as HTMLElement

  if (
    target !== input &&
    wrapper.classList.contains(FORM_CLASSES_DROPDOWN.open) &&
    !matches(target, `.${FORM_CLASSES_DROPDOWN.options}`) &&
    !hasParent(target.parentNode, `.${FORM_CLASSES_DROPDOWN.options}`)
  ) {
    wrapper.classList.remove(FORM_CLASSES_DROPDOWN.open)
    toggleCustomDropDownOptions(wrapper, 'off')
  }
}

/**
 * Handles any native mobile input and updates the drop-down `input` wrapper.
 *
 * @param {HTMLSelectElement} input Drop-down field
 */
function handleNativeInputForDropDown(input: HTMLSelectElement) {
  const wrapper = input.nextElementSibling as HTMLElement

  if (!hasTouch() || !wrapper) {
    return
  }

  const selectionElement = wrapper.querySelector(`.${FORM_CLASSES_DROPDOWN.selection}`)

  if (selectionElement) {
    setCustomSelectionFromInput(input, selectionElement as HTMLElement)
    input.dispatchEvent(new CustomEvent(FORM_EVENT_MOBILE_CHANGE))

    wrapper.classList.remove(FORM_CLASSES_DROPDOWN.open)
  }
}

export default (input: HTMLSelectElement, label: HTMLLabelElement, options: FormInputOptions) => {
  options.watch = true

  // Listen for any custom events
  //
  // The function typecast is required to overcome strict type checks in TypeScript 2.6+ which
  // forbids us from defining an `CustomEvent` as `Event` even though it extends it.
  // https://github.com/Microsoft/TypeScript/issues/28357#issuecomment-436484705
  input.addEventListener(FORM_EVENT_GLOBAL_CLICK, (({ detail }: CustomEvent<FormClickEvent>) => {
    handleGlobalClickEventForDropdown(detail.event, detail.target, detail.input)
  }) as EventListener)

  input.addEventListener(FORM_EVENT_INPUT_CHANGE, (({ detail }: CustomEvent<HTMLSelectElement>) => {
    handleNativeInputForDropDown(detail)
  }) as EventListener)

  input.addEventListener(FORM_EVENT_INPUT_FOCUS, (({ detail }: CustomEvent<FormInputEvent>) => {
    handleMouseTouchAndFocusForDropDown(detail.event, detail.input, detail.state)
  }) as EventListener)

  input.addEventListener(FORM_EVENT_MOUSE_TOUCH, (({ detail }: CustomEvent<FormInputEvent>) => {
    handleMouseTouchAndFocusForDropDown(detail.event, detail.input, detail.state)
  }) as EventListener)

  // Generate the custom wrapper around the drop-down
  if (!input.isWrapped) {
    bindCustomWrapperToDropdown(input, label)
    input.isWrapped = true
  }
}
