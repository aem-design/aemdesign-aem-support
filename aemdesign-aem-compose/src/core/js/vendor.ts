import $ from 'jquery'

function load() {
  console.log('vendor.js jQuery Version', $.fn.jquery)
}

if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', load)
} else {
  load()
}
