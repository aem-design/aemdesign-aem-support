import type {
  CommonOptions,
  TinySliderInstance,
  TinySliderSettings,
} from 'tiny-slider'

import { CarouselType } from './enums'

export interface CarouselElement extends HTMLElement {
  tinyslider?: TinySliderInstance;
}

interface CarouselBreakpoints {
  [breakpoint: number]: CommonOptions & CarouselIntermediateSettings;
}

export interface CarouselOptions extends CommonOptions {
  breakpoints: CarouselBreakpoints;
  responsive: boolean;
}

export interface CarouselConfiguration {
  carouselOptions: CarouselOptions;
  destroy?: boolean;
  needsCarousel: boolean;
  needsSplit: boolean;
  refreshOnly: boolean;
  type: CarouselType | null;
}

export interface CarouselIntermediateSettings {
  /**
   * Center the active slide in the viewport
   * @defaultValue false
   */
  center?: boolean;
}

export interface CarouselSettings extends CarouselIntermediateSettings, TinySliderSettings {
  /**
   * Breakpoint: Integer.
   * Defines options for different viewport widths
   * @defaultValue false
   */
  responsive?: CarouselBreakpoints | false;
}
