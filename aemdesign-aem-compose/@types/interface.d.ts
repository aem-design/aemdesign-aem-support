interface Window {
  CQ: any;
  kWidget: KWidget;
  google: any;
  onYouTubeIframeAPIReady: () => void;

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

  kBind(eventName: string, callback: (state: string) => void): void;

  dummyElement: Element;
  kaltura: any | null;
  mediaProvider: string;
  youtube: YT.Player;
  waitForState: boolean;
}

interface HTMLSelectElement {
  isWrapped: boolean;
}

interface HTMLOptionElement {
  customItem: HTMLLIElement;
}

interface HTMLLIElement {
  optionTarget: HTMLOptionElement;
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

interface FieldElements {
  input: HTMLInputElement;
  label: HTMLLabelElement;
}

interface FormClickEvent {
  event: Event;
  input: HTMLInputElement;
  target: HTMLElement;
}

interface FormInputEvent {
  event: Event;
  input: HTMLInputElement;
  parent: HTMLElement;
  state: InputType | MouseAndTouchType;
}

interface FormInputOptions {
  watch?: boolean;
}

interface KWidget {
  embed(options: any): Element;
}

interface MediaTracking {
  lastWatchedSegment: number | null;
  timer: number | null;
}

interface MediaBrand {
  elements: Element[],
  needed: boolean,
}

interface SearchResultsResponseItem {
  title: string;
  summary: string;
  displayUrl: string;
  collection: string;
  metaData:any;
};

interface ISearchResultsItem {
  title: string;
  summary: string;
  url: string;
  collection: string;
}
