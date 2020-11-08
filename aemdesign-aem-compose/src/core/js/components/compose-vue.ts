import {
  createApp,
  defineAsyncComponent,
} from 'vue'

import type {
  Component,
} from '@vue/runtime-core'

import ErrorBoundary from '@/core/components/error-boundary/error-boundary.vue'

/**
 * Generates the dynamic import logic needed for each async Vue component.
 */
function loadView(component: string): () => Promise<Component> {
  return defineAsyncComponent(() => import(
    /* webpackChunkName: "vue/c/[request]" */
    `./${component}/${component}.vue`))
}

// Define the components we can use
const components = [
  'hello-world',
]

/**
 * Sets up and binds the components needed for our Vue experiences.
 */
function initialiseVue(options: Component = {}) {
  const app = createApp(options)

  app.config.performance = __DEV__

  for (const component of components) {
    app.component(component, loadView(component))
  }

  app.component('error-boundary', ErrorBoundary)

  return app
}

export default async (references: NodeListOf<Element>) => {
  console.info('[Vue] Found %d reference elements!', references.length)

  if (references.length) {
    for (const reference of references) {
      const componentName = reference.getAttribute('vue-component')

      if (!componentName || components.indexOf(componentName) === -1) {
        console.warn(
          "[Vue] Component '%s' is invalid, valid components are:",
          componentName,
          components.join(', ')
        )

        continue
      }

      const instance = initialiseVue().mount(reference)

      console.info(
        '[Vue] Component initialized and ready to go!',
        instance,
        componentName,
        reference,
      )
    }
  }
}
