import ErrorBoundary from '@/core/components/error-boundary/error-boundary.vue'

// Internal
let hasInitialised = false

/**
 * Generates the dynamic import logic needed for each async Vue component.
 *
 * @param {string} component Name of the Vue component
 * @return {() => Promise<any>}
 */
function loadView(component: string): () => Promise<any> {
  return () => import(
    /* webpackChunkName: "vue/c/[request]" */
    `./${component}/${component}.vue`)
}

// Define the components we can use
const components = [
  'hello-world',
]

/**
 * Sets up and binds the components needed for our Vue experiences.
 *
 * @param {VueConstructor} vue Vue.js constructor class
 */
function initialiseVue(vue: Vue.VueConstructor) {
  // TODO: Find a nice way to make devtools CI/CD configurable
  vue.config.devtools      = true
  vue.config.performance   = __DEV__
  vue.config.productionTip = __PROD__
  vue.config.silent        = __PROD__

  // Register the components
  console.info('[Vue] %d components ready to be registered!', components.length)

  for (const component of components) {
    console.info('[Vue] Registering...', component)
    vue.component(component, loadView(component))
  }

  vue.component('error-boundary', ErrorBoundary)
}

export default async (references: NodeListOf<Element>) => {
  const { default: Vue } = await import('vue')

  if (!hasInitialised) {
    hasInitialised = true

    initialiseVue(Vue)
  }

  // Find any and all Vue component references in the DOM
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

      console.info('[Vue] Reference:', reference, componentName)

      const instance = new Vue({ el: reference })
      console.info('[Vue] Component initialized and ready to go!', instance)

      // Ensure the component cannot be re-initalised
      reference.setAttribute('js-set', 'true')
    }
  }
}
