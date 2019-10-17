interface Window {
  CQ: any;

  digitalData: {
    [key: string]: any;

    event: {
      push: (data: DigitalDataPush) => {},
    },
  };

  CustomEvent: (event: string, params: any) => CustomEvent<any>;
  MSInputMethodContext: any;
}

interface Document {
  documentMode: any;
}

interface Element {
  matchesSelector(selectors: string): boolean;
  msMatchesSelector(selectors: string): boolean;
  mozMatchesSelector(selectors: string): boolean;
  oMatchesSelector(selectors: string): boolean;
}

interface ComponentConfig {
  [key: string]: {
    icon: Element,
    selectors: string[],
  };
}

interface DigitalDataPush {
  eventAction: string;
}

interface BreakpointOptions {
  [key: number]: OwlCarousel.Options;
}

interface CarouselOptions {
  breakpoint?: BreakpointOptions;
  destroy?: boolean;
  needsCarousel: boolean;
  needsSplit: boolean;
  refreshOnly: boolean;
}
