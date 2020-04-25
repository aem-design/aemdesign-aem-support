import { Component, Mixins, Prop } from 'vue-property-decorator'

import BaseComponent from '@components/base-component'

@Component({
  name: 'hello-world',
})
export default class HelloWorld extends Mixins(BaseComponent) {
  @Prop({ default: 'World', type: String })
  public readonly name!: string
}
