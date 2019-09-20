import {
  getElementsForInputEvent,
  getFormFieldElements,
  hasTouch,
  labelRequiresActiveState,
  setTouchState,
} from '@module/forms/common'

import dropDown from '@module/forms/drop-down'

import { InputType, MouseAndTouchType } from '@type/enum'

import {
  FORM_EVENT_GLOBAL_CLICK,
  FORM_EVENT_INPUT_CHANGE,
  FORM_EVENT_INPUT_FOCUS,
  FORM_EVENT_MOBILE_CHANGE,
  FORM_EVENT_MOUSE_TOUCH,
} from '@utility/constants'

import { customEventPolyfill } from '@utility/polyfill'

// Internal
const globalInputsToWatch: HTMLInputElement[] = []

let previousInput: HTMLInputElement

/**
 * Triggered when a mouse event occurs on the input.
 *
 * @param {MouseEvent | TouchEvent} event Mouse event for the input
 */
function inputMouseAndTouch(event: MouseEvent | TouchEvent) {
  const elementsValue = getElementsForInputEvent<MouseAndTouchType>(event.target, event.type, MouseAndTouchType)

  if (elementsValue === false) {
    return
  }

  const { input, label, nodeName, state } = elementsValue
  console.info("[Forms] '%s' occurred on an '%s' element", state, nodeName, input, label)

  const parent = input.parentNode as HTMLElement

  // Set the previous input field
  if (previousInput !== input) {
    previousInput = input
  }

  // Do we need to trigger 'blur' on another input?
  const activeElement = document.activeElement

  if (activeElement && activeElement !== input) {
    console.info("[Forms] active element '%s' is not the input", activeElement)
  }

  // Alert the input to do something with this event
  input.dispatchEvent(new CustomEvent<FormInputEvent>(FORM_EVENT_MOUSE_TOUCH, {
    detail: { event, input, parent, state },
  }))
}

/**
 * Detects the input state change and executes any behaviour required for the desired state.
 *
 * @param {Event} event Input event for the desired state
 */
function inputStateChange(event: Event) {
  const elementsValue = getElementsForInputEvent<InputType>(event.target, event.type, InputType)

  if (elementsValue === false) {
    return
  }

  const { input, label, nodeName, state } = elementsValue
  console.info("[Forms] '%s' occurred on an '%s' element", state, nodeName, input, label)

  const parent = input.parentNode as HTMLElement

  // Set the focus state
  if (state === InputType.FOCUS) {
    if (hasTouch() && input instanceof HTMLSelectElement) {
      input.dispatchEvent(new CustomEvent<FormInputEvent>(FORM_EVENT_INPUT_FOCUS, {
        detail: { event, input, parent, state },
      }))

      return
    }

    input.classList.add('focused')
  }

  // Set the blur state
  if (state === InputType.BLUR) {
    input.classList.remove('focused')
  }

  // Set the label state depending on the type of event
  const inputOrChangeWithoutFocus = (
    state === InputType.INPUT || state === InputType.CHANGE || state === FORM_EVENT_MOBILE_CHANGE
  ) && !input.classList.contains('focused')

  const labelNeedsActiveState = labelRequiresActiveState(input)

  if (state === InputType.FOCUS || (inputOrChangeWithoutFocus && labelNeedsActiveState)) {
    label.classList.add('active')
  }

  if ((state === InputType.BLUR || inputOrChangeWithoutFocus) && !labelNeedsActiveState) {
    label.classList.remove('active')
  }

  // Send out an event for any custom inputs, this can result in a circular loop so it is important
  // all liste(ners check the input state before committing to an action.
  if (hasTouch() && state !== FORM_EVENT_MOBILE_CHANGE) {
    input.dispatchEvent(new CustomEvent<HTMLInputElement>(FORM_EVENT_INPUT_CHANGE, {
      detail: input,
    }))
  }

  // Set the previous input field
  if (previousInput !== input) {
    previousInput = input
  }
}

/**
 * Binds a `change` event to the given `input` field.
 *
 * @param {HTMLInputElement} input Field to listen on
 */
function bindChangeEventToInput(input: HTMLInputElement) {
  input.removeEventListener('change', inputStateChange, false)
  input.addEventListener('change', inputStateChange, false)
}

/**
 * Sets the default state of the label based on the way the input has been configured.
 *
 * @param {HTMLInputElement} input Input field to check against
 * @param {HTMLLabelElement} label Label for the input field
 */
function setDefaultLabelState(input: HTMLInputElement, label: HTMLLabelElement) {
  label.classList.add('show')

  if (labelRequiresActiveState(input)) {
    label.classList.add('no-transition')
    label.classList.add('active')

    // Remove 'no-transition' once the active state is set
    setTimeout(() => label.classList.remove('no-transition'), 200)
  }
}

/**
 * Watches for click events on the page awaiting when input fields need to change their
 * state when the user is no longer focused on the input anymore.
 *
 * @param {Event} event Click event
 */
function windowClickWatcher(event: Event) {
  const target = event.target as HTMLElement

  for (const input of globalInputsToWatch) {
    input.dispatchEvent(new CustomEvent<FormClickEvent>(FORM_EVENT_GLOBAL_CLICK, {
      detail: { event, input, target },
    }))
  }
}

export default (elements: NodeListOf<Element> | Element[]) => {
  console.log('[Forms] Getting ready...')

  customEventPolyfill()

  // Touch tracking/detection
  const touchTrackingEventNames = [
    MouseAndTouchType.TOUCHSTART,
    MouseAndTouchType.TOUCHEND,
  ]

  for (const eventName of touchTrackingEventNames) {
    window.addEventListener(eventName, setTouchState, false)
  }

  for (const element of elements) {
    const { input, label } = getFormFieldElements(element)

    if (!input || !label) {
      console.error('[Forms] Unable to determine the input or label for the target element!\n', input, label)
      continue
    }

    // Determine the default state of the label
    setDefaultLabelState(input, label)

    // Ensure the label has a valid identifier
    if (!label.id) {
      label.setAttribute('id', `${input.id}-label`)
    }

    // Bind various events to the input
    input.addEventListener('touchstart', inputMouseAndTouch, false)
    input.addEventListener('touchend', inputMouseAndTouch, false)
    input.addEventListener('mousedown', inputMouseAndTouch, false)
    input.addEventListener('mouseup', inputMouseAndTouch, false)

    input.addEventListener('focus', inputStateChange, false)
    input.addEventListener('blur', inputStateChange, false)
    input.addEventListener('input', inputStateChange, false)

    input.addEventListener(FORM_EVENT_MOBILE_CHANGE, inputStateChange, false)

    // Now for the custom work...
    const inputOptions: FormInputOptions = {}

    if (input instanceof HTMLSelectElement) {
      dropDown(input, label, inputOptions)
      bindChangeEventToInput(input)
    }

    if (inputOptions.watch === true) {
      globalInputsToWatch.push(input)
    }
  }

  // Global click event for tracking focus/blur states outside the inputs
  window.addEventListener('click', windowClickWatcher, false)
}
