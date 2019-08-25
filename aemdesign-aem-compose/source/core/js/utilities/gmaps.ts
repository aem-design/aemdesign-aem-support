const CALLBACK_NAME = 'gmapsCallback'

let initialized = !!window.google
let resolveInitPromise
let rejectInitPromise

const initPromise = new Promise((resolve, reject) => {
  resolveInitPromise = resolve
  rejectInitPromise = reject
})

export default function init(apiKey: string) {
  if (initialized) {
    return initPromise
  }

  initialized = true

  // The callback function is called by the Google Maps script if it is successfully loaded
  window[CALLBACK_NAME] = () => resolveInitPromise()

  // We inject a new script tag into the `<head>` of our HTML to load the Google Maps script
  const script = document.createElement('script')

  script.async   = true
  script.defer   = true
  script.src     = `https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=${CALLBACK_NAME}`
  script.onerror = rejectInitPromise

  const head = document.querySelector('head')

  if (head) {
    head.appendChild(script)
  }

  return initPromise
}
