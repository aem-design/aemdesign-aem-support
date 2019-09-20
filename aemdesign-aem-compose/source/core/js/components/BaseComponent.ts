import { Component, Prop, Vue } from 'vue-property-decorator'

// Internal
const EventBus = new Vue()

import { LogType } from '@type/enum'

@Component
export default class BaseComponent extends Vue {
  @Prop({ default: '', type: String })
  public analyticsLocation!: string
  @Prop({ default: '', type: String })
  public analyticsName!: string
  @Prop({ default: null, type: String })
  public target!: string

  public eventBus!: Vue

  // tslint:disable-next-line:variable-name
  protected _name!: string
  protected documentTarget: Document = document
  protected windowTarget: Window = window

  constructor() {
    super()

    this.eventBus = EventBus
  }

  beforeMount() {
    this.detectDocumentTargetFromId()
    this.detectWindowTargetFromId()
  }

  protected log(type: LogType, ...args) {
    console[type.toString().toLowerCase()](`[${this._name}]`, ...args)
  }

  private getAppFrame() {
    if (this.target) {
      const appIframe = window.parent.document.getElementById(this.target) as HTMLIFrameElement

      if (appIframe) {
        return appIframe
      }
    } else {
      if (window.frameElement) {
        return window.frameElement as HTMLIFrameElement
      }
    }

    return null
  }

  protected detectDocumentTargetFromId() {
    const frame = this.getAppFrame()

    if (frame) {
      this.documentTarget = frame.contentDocument as Document
    }
  }

  protected detectWindowTargetFromId() {
    const frame = this.getAppFrame()

    if (frame) {
      this.windowTarget = frame.contentWindow as Window
    }
  }
}
