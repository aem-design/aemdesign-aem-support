import $ from 'jquery'

function loadVendor() {
  console.log('vendor.js jQuery Version', $.fn.jquery)
}

if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', loadVendor)
} else {
  loadVendor()
}
