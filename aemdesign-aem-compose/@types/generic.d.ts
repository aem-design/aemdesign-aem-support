declare let require: {
  <T>(path: string): T;
  (paths: string[], callback: (...modules: any[]) => void): void;
  ensure: (
    paths: string[],
    callback: (require: <T>(path: string) => T) => void
  ) => void;
}

declare let __DEV__: boolean;
declare let __PROD__: boolean;
declare let __VERSION__: number;
