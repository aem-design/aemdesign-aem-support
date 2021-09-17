import { defineComponent, ComponentPublicInstance } from '@vue/runtime-core'
import { ref } from '@vue/reactivity'

const err = ref<Error | false>(false)
const vinfo = ref('')
const vm = ref<ComponentPublicInstance | null>(null)

export default defineComponent({
  name: 'error-boundary',

  props: {
    heading: {
      default: 'Something went wrong',
      type: String,
    },

    stopPropagation: Boolean,
  },

  setup() {
    return { err, vinfo, vm }
  },

  errorCaptured(error, instance, info) {
    err.value = error as Error
    vinfo.value = info
    vm.value = instance

    return !this.stopPropagation
  },
})
