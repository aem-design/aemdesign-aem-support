import { MouseAndTouchType } from '@type/enum'

import { typeGuard } from '@utility/generic'

// Internal
let isTouchDevice: boolean = false

/**
 * Sets the touch detection state depending on the type of event that was detected.
 *
 * @export
 */
export function setTouchState() {
  isTouchDevice = true
}

/**
 * Returns the current touch device state.
 *
 * @export
 * @return {boolean}
 */
export function hasTouch(): boolean {
  return isTouchDevice
}

/**
 * Attempts to determine the input field and label elements of the `target` given.
 *
 * @export
 * @param {(Element | EventTarget | null)} [target=null] Element or event target
 * @return {Partial<FieldElements>}
 */
export function getFormFieldElements(target: Element | EventTarget | null = null): Partial<FieldElements> {
  if (!target) {
    console.error('[Forms] Unable to handle target given as it is null!')
    return {}
  }

  const input = target as HTMLInputElement

  if (!input.id) {
    console.error("[Forms] Unable to handle input given as it doesn't appear to have a valid ID!")
    return {}
  }

  const owner = (target as HTMLElement).ownerDocument as Document

  return {
    input,
    label: owner.querySelector(`label[for=${input.id}]`) as HTMLLabelElement,
  }
}

/**
 * Retrieve some information about the event `target` and `type` or return `false` when
 * we are unable to determine any part of the event.
 *
 * @export
 * @template T
 * @param {(EventTarget | null)} target Event target
 * @param {typeof Event.prototype.type} type Event type
 * @param {T} eventEnum Enum to match with the `type`
 * @return {(FieldElements & { nodeName: string, state: string } | false)}
 */
export function getElementsForInputEvent<T>(
  target: EventTarget | null,
  type: typeof Event.prototype.type,
  eventEnum: any,
): FieldElements & { nodeName: string, state: string | T } | false {
  const { input, label } = getFormFieldElements(target)
  const state: string | T | false = (/^form\./.test(type) && type) || eventEnum[type.toUpperCase()] as T || false

  if (state === false || !input || !label) {
    console.error('[Forms] Unable to determine the state or input and/or label element for the target!', event)
    return false
  }

  return {
    input,
    label,
    nodeName: input.nodeName.toLowerCase(),
    state,
  }
}

/**
 * Retrieve the input value dynamically for the input field type.
 *
 * @export
 * @param {HTMLInputElement} input Input field to get value
 * @return {string}
 */
export function getInputValue(input: HTMLInputElement): string {
  return (input as HTMLInputElement).value
}

/**
 * Determine if the label requires its active state based on the type of `input` given.
 *
 * @export
 * @param {HTMLElement} input Input field to check against
 * @return {boolean}
 */
export function labelRequiresActiveState(input: HTMLInputElement): boolean {
  const inputType = input.type
  const nodeName  = input.nodeName.toLowerCase()
  const value     = getInputValue(input)

  //      Text
  return (nodeName === 'input' && inputType === 'text' && value.length > 0)
  //      Select
      || (nodeName === 'select' && value.length > 0)
}

/**
 * Dispatch an event to all those listening in on the input field.
 *
 * @param input Input field to dispatch event for
 * @param eventName Name of native event to create
 */
export function dispatchInputEvent(input: HTMLElement, eventName: string, detail = {}) {
  try {
    input.dispatchEvent(new CustomEvent(eventName, {
      detail: detail || {},
    }))
  } catch (ex) {
    console.error('[Forms] Unable to dispatch event!!', ex)
  }
}

/**
 * Set a custom selection value for the input field.
 *
 * @export
 * @param {HTMLInputElement} input Input field to retrieve value from
 * @param {(HTMLElement | null)} selectionTarget Target element to set the selection string
 */
export function setCustomSelectionFromInput<T>(input: T, selectionTarget: HTMLElement | null) {
  if (!selectionTarget) {
    console.error("[Forms] Unable to set custom selection because the target element couldn't be found!", input, selectionTarget)
    return
  }

  let selectionText = ''

  if (typeGuard(input, HTMLSelectElement)) {
    selectionText = input.options[input.selectedIndex].innerText
  }

  selectionTarget.innerText = selectionText
}
