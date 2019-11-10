import { Component, Prop, Vue } from 'vue-property-decorator'

@Component({
  name: 'error-boundary',
})
export default class ErrorBoundary extends Vue {
  @Prop({ default: 'Something went wrong', type: String })
  public heading!: boolean

  @Prop({ type: Boolean })
  public stopPropagation!: boolean

  public err: Error | false = false
  public info: string = ''
  public vm: Vue | null = null

  errorCaptured(err: Error, vm: Vue, info: string) {
    this.err  = err
    this.vm   = vm
    this.info = info

    return !this.stopPropagation
  }
}
