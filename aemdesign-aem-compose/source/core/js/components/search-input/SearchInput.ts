import { Component, Mixins, Prop, Watch } from 'vue-property-decorator'

import BaseComponent from '@components/BaseComponent'

import Forms from '@module/forms'

@Component
export default class SearchInput extends Mixins(BaseComponent) {
  @Prop({ default: '', type: String })
  public destination!: string
  @Prop({ default: 'Start your search here...', type: String })
  public label!: string
  @Prop({ default: true, type: Boolean })
  public shouldEmit!: boolean
  @Prop({ default: true, type: Boolean })
  public activePopover!: boolean
  @Prop({ default: null, type: String })
  public target!: string
  @Prop({ default: 'search-input', type: String })
  public identifier!: string

  public search: string = ''
  public filter: string = ''
  public inputFocused: boolean = false
  public wasValidated: boolean = false
  public isValid: boolean = false
  public componentHaveFocus: boolean = false

  public buttons: Array<{label: string, value: string }> = [{
    label: 'All',
    value: '',
  },{
    label: 'Courses',
    value: 'courses',
  },{
    label: 'News',
    value: 'news',
  },{
    label: 'Events',
    value: 'events',
  }]

  mounted() {
    Forms([
      this.$refs.search as HTMLInputElement,
    ])
  }

  focusChanged (element?: Element) {
    this.componentHaveFocus = (this.$refs.form as HTMLElement).contains(element || this.windowTarget.document.activeElement);

    if(!this.componentHaveFocus) {
      this.wasValidated = false;
      this.isValid = false;
      this.inputFocused = false
    }
  }

  inputOrButtonBlur($event: FocusEvent) {
    this.focusChanged($event.relatedTarget as Element)
  }

  searchQuery() {
    console.log('searchQuery')
    this.wasValidated = true;

    if (this.isValid) {
      const query = (this.search || '').trim()

      if (this.shouldEmit) {
        this.eventBus.$emit('search', query)
      } else {
        window.location.href = `${this.destination}?query=${query}`
      }
    }
  }

  selectedButton($event: Event, filter: string) {
    $event.preventDefault()
    $event.stopPropagation()
    this.filter = filter
    this.eventBus.$emit('filter', filter)
  }

  classToggleButton(filter: string) {
    return this.filter == filter ? 'btn-secondary-charcoal' : 'btn-secondary-outline-charcoal'
  }

  get isPopoverOpen() {
    return this.activePopover && this.componentHaveFocus && !this.isShowingError
  }

  get isShowingError() {
    return this.inputFocused && this.wasValidated && !this.isValid
  }

  @Watch('search')
  checkValidity() {
    this.isValid = (this.$refs.search as HTMLInputElement).checkValidity()

    if(this.isValid)
      this.wasValidated = false
  }
}
