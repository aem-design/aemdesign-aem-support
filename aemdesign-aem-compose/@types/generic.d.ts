declare var require: {
  <T>(path: string): T;
  (paths: string[], callback: (...modules: any[]) => void): void;
  ensure: (
    paths: string[],
    callback: (require: <T>(path: string) => T) => void
  ) => void;
}

declare var __DEV__: boolean;
declare var __PROD__: boolean;

declare type FormInputCallback = (
  ($target: JQuery, $input: JQuery, event: JQuery.TriggeredEvent) => boolean
) | void;

declare type SubscriberTree = {
  default: SubscriberHandler,
}

declare type SubscriberHandler = (
  event: JQuery.Event, originalEvent: JQuery.TriggeredEvent, type: string
) => void;
