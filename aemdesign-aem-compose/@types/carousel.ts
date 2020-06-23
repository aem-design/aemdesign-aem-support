import type {
  ResponsiveOptions,
  TinySliderInstance,
  CommonOptions,
  TinySliderSettings,
} from 'tiny-slider'

import { CarouselType } from './enums'

export interface CarouselElement extends HTMLElement {
  tinyslider?: TinySliderInstance;
}

export interface CarouselOptions extends CommonOptions {
  breakpoints?: ResponsiveOptions;
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
