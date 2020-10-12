import { mount } from '@vue/test-utils'

import HelloWorld from '@/core/components/hello-world/hello-world.vue'

describe('hello world', () => {
  test('can render component', () => {
    // TODO: Raise bug with vue test devs
    // @ts-expect-error it doesn't appear that the types match up yet
    const wrapper = mount(HelloWorld, {
      props: {
        name: 'Jest',
      },
    })

    expect(wrapper.text()).toEqual('Hello, Jest!')
  })

  test('has correct lead class', () => {
    const wrapper = mount(HelloWorld)

    expect(wrapper.element.classList.contains('lead')).toBeTruthy()
  })
})
