import sassVars from '../../scss/_common.scss'

export const breakpoints = {
  desktop      : parseInt(sassVars['breakpoint-lg'], 10),
  desktopLarge : parseInt(sassVars['breakpoint-xl'], 10),
  extraSmall   : parseInt(sassVars['breakpoint-xs'], 10),
  tablet       : parseInt(sassVars['breakpoint-md'], 10),
}

export const margins = {
  default : parseInt(sassVars['list-item-margin'], 10),
  desktop : parseInt(sassVars['list-item-lg-margin'], 10),
  tablet  : parseInt(sassVars['list-item-md-margin'], 10),
}
