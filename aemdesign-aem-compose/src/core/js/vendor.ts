import $ from 'jquery'

function loadVendor(): void {
  console.log('vendor.js jQuery Version', $.fn.jquery)
}

if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', loadVendor)
} else {
  loadVendor()
}
