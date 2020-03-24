import Vue from 'vue'

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
