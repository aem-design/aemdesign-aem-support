export default async (references: NodeListOf<Element>) => {
  const { default: Vue } = await import('vue')

  Vue.config.devtools      = true
  Vue.config.performance   = __DEV__
  Vue.config.productionTip = __PROD__
  Vue.config.silent        = __PROD__

  // Define the components we can use
  const components = {
    'journey-planner': () => import(/* webpackChunkName: "components/journey-planner" */ './journey-planner/JourneyPlanner.vue'),
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
