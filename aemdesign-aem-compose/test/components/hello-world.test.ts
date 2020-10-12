import { shallowMount } from '@vue/test-utils'

import HelloWorld from '@/core/components/hello-world/hello-world.vue'

describe('Render <HelloWorld />', () => {
  test('can render using defaults', () => {
    const wrapper = shallowMount(HelloWorld)

    expect(wrapper.text()).toStrictEqual('Hello, World!')

    expect(wrapper.html()).toBe('<p class="lead">Hello, World!</p>')
  })

  test('can render component using a custom name prop', () => {
    const wrapper = shallowMount(HelloWorld, {
      props: {
        name: 'Jest',
      },
    })

    expect(wrapper.text()).toStrictEqual('Hello, Jest!')

    expect(wrapper.html()).toBe('<p class="lead">Hello, Jest!</p>')
  })
})
