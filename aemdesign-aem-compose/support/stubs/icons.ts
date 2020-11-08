import { config, dom, library } from '@fortawesome/fontawesome-svg-core'

import { %%brands-import%% } from '@fortawesome/free-brands-svg-icons'
import { %%solid-import%% } from '@fortawesome/free-solid-svg-icons'

config.autoA11y             = true
config.autoReplaceSvg       = true
config.keepOriginalSource   = __DEV__
config.measurePerformance   = __DEV__
config.observeMutations     = true
config.searchPseudoElements = false
config.showMissingIcons     = true

const icons = [%%all-icons%%]

export default (): void => {
  console.log('[Font Awesome] Loading up our badass icons!')
  console.log('[Font Awesome] Icons to add: %d', icons.length)
  library.add(...icons)

  console.log('[Font Awesome] Now watching the DOM for icons, what a beautiful sight it will be...')
  dom.watch()
}
