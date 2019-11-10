import sassVars from '../../scss/settings/_common.scss'

export const breakpoints = {
  desktop      : parseInt(sassVars['breakpoint-lg'], 10),
  desktopLarge : parseInt(sassVars['breakpoint-xl'], 10),
  extraSmall   : parseInt(sassVars['breakpoint-xs'], 10),
  tablet       : parseInt(sassVars['breakpoint-md'], 10),
}
