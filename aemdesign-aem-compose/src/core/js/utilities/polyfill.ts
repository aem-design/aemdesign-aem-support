export function customEventPolyfill() {
  if (typeof window.CustomEvent === 'function') {
    return false
  }

  function CustomEvent(event: string, params: any) {
    params = params || {
      bubbles    : false,
      cancelable : false,
      detail     : null,
    }

    const evt = document.createEvent('CustomEvent')
    evt.initCustomEvent(event, params.bubbles, params.cancelable, params.detail)

    return evt
  }

  // @ts-expect-error
  window.CustomEvent = CustomEvent;
}
