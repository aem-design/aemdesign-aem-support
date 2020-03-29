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
