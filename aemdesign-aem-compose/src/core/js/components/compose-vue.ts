import ErrorBoundary from '@components/error-boundary/ErrorBoundary.vue'

// Internal
let hasInitialised = false

// Define the components we can use
const components = {
  // <Component (kebab-case)>: loadView('component', 'Entry')
  'hello-world': loadView('hello-world', 'HelloWorld'),
}

const validComponents = Object.keys(components)

/**
 * Generates the dynamic import logic needed for each async Vue component.
 *
 * @param {string} folder Name of the folder containing the Vue component
 * @param {string} view Name of the Vue component
 * @return {() => Promise<any>}
 */
function loadView(folder: string, view: string): () => Promise<any> {
  return () => import(
    /* webpackChunkName: "vue/c/[request]" */
    `./${folder}/${view}.vue`)
}

/**
 * Sets up and binds the components needed for our Vue experiences.
 *
 * @param {VueConstructor} vue Vue.js constructor class
 */
function initialiseVue(vue: Vue.VueConstructor) {
  vue.config.devtools      = true
  vue.config.performance   = __DEV__
  vue.config.productionTip = __PROD__
  vue.config.silent        = __PROD__

  // Register the components
  console.info('[Vue] %d components ready to be registered!', validComponents.length)

  for (const component of validComponents) {
    console.info('[Vue] Registering...', component)
    vue.component(component, components[component])
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

      if (!componentName || validComponents.indexOf(componentName) === -1) {
        console.warn("[Vue] Component '%s' is invalid, valid components are:", componentName, validComponents.join(', '))
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
