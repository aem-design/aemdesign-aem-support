import { defineComponent } from 'vue'

export default defineComponent({
  name: 'hello-world',

  props: {
    name: {
      default: 'World',
      type: String,
    },
  },
})
