import {
  EVENT_TYPE_CLICK,
  EVENT_TYPE_KEYDOWN,
  EVENT_TYPE_KEYUP,
} from '../utilities/constants'

/**
 * Handles the behaviour of the input field when a user is typing.
 *
 * @param  {JQuery} $target The target element object
 * @param  {JQuery} $input The input element object
 * @param  {JQuery.TriggeredEvent} event The event object
 * @return {boolean}
 */
function handleInputFieldUserInput($target: JQuery, $input: JQuery, event: JQuery.TriggeredEvent): boolean {
  const value = $input.val() as string

  // Handle the submission of the input when the enter key is pressed
  if (event.keyCode === 13) {
    console.info('[Form Input] User pressed the enter key on the input field')
    return true
  }

  // Toggle the clear button depending on whether the input has a value
  if (value.length) {
    $input.next().addClass('active')
  } else {
    $input.next().removeClass('active')
  }

  return false
}

/**
 * Handles the behaviour of when the clear button is clicked.
 *
 * @param  {JQuery} $target The target element object
 * @param  {JQuery} $input The input element object
 * @param  {JQuery.TriggeredEvent} event The event object
 * @return {boolean}
 */
function handleClearInputButton($target: JQuery, $input: JQuery, event: JQuery.TriggeredEvent): boolean {
  // Clear the input field
  $input.val('')

  // Hide the clear button
  $target.removeClass('active')

  // Focus on the input field again
  $input.get(0).focus()

  return true
}

export default (event: JQuery.Event, originalEvent: JQuery.TriggeredEvent, type: string) => {
  const $target: JQuery = $(originalEvent.target)

  let $input         = $target
  let preventDefault = false

  let callbackFunction: FormInputCallback

  // Is the target an input field? If so detect key events and toggle the clear button
  if ($input.is(':text') && (type === EVENT_TYPE_KEYDOWN || type === EVENT_TYPE_KEYUP)) {
    console.info('[Form Input] Text input found', $input)
    callbackFunction = handleInputFieldUserInput

  // Basic clear which searches for a previous sibling that is the input type. Also checks to ensure
  // the triggered event was for a click.
  } else if (
    ($input = $target.prev('input[type=text]')).length === 1 &&
    type === EVENT_TYPE_CLICK
  ) {
    console.log(originalEvent)
    console.info('[Form Input] Input field found next to the target!', $input)
    callbackFunction = handleClearInputButton
  }

  // Was an action defined?
  if (typeof callbackFunction === 'function') {
    preventDefault = callbackFunction($target, $input, originalEvent)
  } else {
    console.warn('[Form Input] Unable to determine the context for the trigger:', type)
  }

  // Prevent the default submit action
  if (preventDefault) {
    // originalEvent.stopImmediatePropagation()
    originalEvent.preventDefault()
  }
}
