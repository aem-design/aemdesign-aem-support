import { isAuthorMode } from '@utility/aem'

/**
 * Vue.js components!
 *
 * @param {boolean} [force=false] Should the component be forced to load?
 * @return {Promoise<number>} Number of Vue components that exist
 */
export async function bindVueComponents(force = false): Promise<number> {
  const vueElements = document.querySelectorAll('[vue-component]:not([js-set])')

  if ((force || !(force && isAuthorMode())) && vueElements.length) {
    const { default: ComposeVue } = await import(/* webpackChunkName: "vue/compose" */ '@components/compose-vue')

    ComposeVue(vueElements)
  }

  return Promise.resolve(vueElements.length)
}
