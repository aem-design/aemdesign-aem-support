import { isAuthorMode } from '@/core/utilities/aem'

/**
 * Vue.js components!
 *
 * @param {boolean} [force=false] Should the component be forced to load?
 * @return {Promoise<number>} Number of Vue components that exist
 */
export async function bindVueComponents(force = false): Promise<number> {
  const vueElements = document.querySelectorAll('[vue-component]:not([js-set])')

  if ((force || !(force && isAuthorMode())) && vueElements.length) {
    (await import(/* webpackChunkName: "cmp/vue/compose" */ '@/core/components/compose-vue')).default(vueElements)
  }

  return Promise.resolve(vueElements.length)
}
