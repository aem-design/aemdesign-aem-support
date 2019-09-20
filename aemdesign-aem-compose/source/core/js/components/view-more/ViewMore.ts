import { Component, Mixins, Prop, Watch } from 'vue-property-decorator'

import BaseComponent from '@components/BaseComponent'

@Component({
  name: 'ViewMore'
})
export default class ViewMore extends Mixins(BaseComponent) {

  @Prop({ default: false, type: Boolean })
  public searching!: boolean
  @Prop({ default: false, type: Boolean })
  public noMoreResults!: boolean

  public disabled: boolean = false
  public hidden: boolean = false

  viewMore() {
    this.disabled = true;
    this.$emit('searching', true)
  }

  @Watch('searching')
  onSearching(value: boolean) {
    this.disabled = value
  }

  @Watch('noMoreResults')
  onNoMoreResults(value: boolean) {
    this.hidden = value
  }
}
