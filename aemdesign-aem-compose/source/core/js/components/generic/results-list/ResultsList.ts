import { Component, Mixins, Prop, Watch } from 'vue-property-decorator'

import BaseComponent from '@components/BaseComponent'

import ViewMore from '@components/view-more/ViewMore.vue'

// i dont do much but dispatch a click
// and handle a slotted template
@Component({
  name: 'base-results-list',
  components: {
    ViewMore
  }
})
export default class BaseResultsList extends Mixins(BaseComponent) {
  @Prop({type: Boolean})
  public isPaged!: boolean

  @Prop({type: String, default: ''})
  public heading!: string

  // settings to pass on to view-more
  @Prop()
  public viewMore!:Object;

  /**
   * dispatch clicked footer next
   * @emits 'next-page'
   */
  onClickNextPage() {
    this.$emit('next-page');
  }

  /**
   * dispatch clicked footer prev
   * @emits 'previous-page'
   */
  onClickPreviousPage() {
    this.$emit('previous-page');
  }
}
