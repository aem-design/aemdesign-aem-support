interface Window {
  CQ: any

  digitalData: {
    [key: string]: unknown

    event: {
      push: (data: DigitalDataPush) => {}
    }
  }

  MSInputMethodContext: unknown
}

interface Document {
  documentMode: unknown
}

interface Element {
  matchesSelector(selectors: string): boolean
  msMatchesSelector(selectors: string): boolean
  mozMatchesSelector(selectors: string): boolean
  oMatchesSelector(selectors: string): boolean
}
