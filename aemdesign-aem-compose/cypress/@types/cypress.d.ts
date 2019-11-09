import Vue from 'vue'

// tslint:disable-next-line:no-namespace
declare global {
  namespace Cypress {
    interface Cypress {
      vue: {
        [key: string]: any;
      };
    }

    interface ConfigOptions {
      projectName: string;
    }
  }
}
