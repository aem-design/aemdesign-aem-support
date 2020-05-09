import type { ResponsiveOptions, TinySliderInstance } from 'tiny-slider'

export interface CarouselElement extends HTMLElement {
  tinyslider?: TinySliderInstance;
}

export interface CarouselOptions {
  autoWidth?: boolean;
  breakpoints?: ResponsiveOptions;
  destroy?: boolean;
  items?: number;
  needsCarousel: boolean;
  needsSplit: boolean;
  refreshOnly: boolean;
  responsive: boolean;
  targets: NodeListOf<HTMLElement> | null;
}
