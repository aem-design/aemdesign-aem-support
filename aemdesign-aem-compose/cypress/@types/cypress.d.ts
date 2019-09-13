import Vue from 'vue'

declare module 'vue/types/vue' {
  interface Vue {
    eventBus: Vue;
  }
}

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
