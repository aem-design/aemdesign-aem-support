/**
 * Generates the dynamic import logic needed for each async Vue component.
 *
 * @param {string} folder Name of the folder containing the Vue component
 * @param {string} view Name of the Vue component
 * @return {() => Promise<any>}
 */
function loadView(folder: string, view: string): () => Promise<any> {
  return () => import(
    /* webpackChunkName: "components/[request]" */
    `./${folder}/${view}.vue`)
}

export default async (references: NodeListOf<Element>) => {
  const { default: Vue } = await import('vue')

  Vue.config.devtools      = true
  Vue.config.performance   = __DEV__
  Vue.config.productionTip = __PROD__
  Vue.config.silent        = __PROD__

  // Define the components we can use
  const components = {
    'social-share'    : loadView('social-share', 'SocialShare'),
    'site-search'     : loadView('site-search', 'SiteSearch'),
    'search-input'    : loadView('search-input', 'SearchInput'),
    'search-results'  : loadView('search-results', 'SearchResults'),
    'view-more'       : loadView('view-more', 'ViewMore'),
  }

  const validComponents = Object.keys(components)

  // Register the components
  console.info('[Vue] %d ready to be created!', validComponents.length)

  for (const component of validComponents) {
    console.info('[Vue] Registering...', component)
    Vue.component(component, components[component])
  }

  // Find any and all Vue component references in the DOM
  console.info('[Vue] Found %d reference elements!', references.length)

  if (references.length) {
    for (const reference of references) {
      const componentName = reference.getAttribute('vue-component')

      if (!componentName || validComponents.indexOf(componentName) === -1) {
        console.warn("[Vue] Component '%s' is invalid, valid components are:", validComponents.join(', '))
        continue
      }

      console.info('[Vue] Reference:', reference, componentName)

      const instance = new Vue({ el: reference })
      console.info('[Vue] Component initialized and ready to go!', instance)
    }
  }
}
