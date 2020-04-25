import { Component, Prop, Vue } from 'vue-property-decorator'

import { LogLevel } from '@type/enum'

@Component
export default class BaseComponent extends Vue {
  @Prop({ default: '', type: String })
  public readonly analyticsLocation!: string
  @Prop({ default: '', type: String })
  public readonly analyticsName!: string
  @Prop({ default: null, type: String })
  public readonly target!: string

  protected _name!: string

  protected documentTarget: Document = document
  protected windowTarget: Window = window

  beforeMount(): void {
    if (__TESTING__) {
      this.detectDocumentTargetFromId()
      this.detectWindowTargetFromId()
    }
  }

  protected log(type: LogLevel, ...args: any[]) {
    console[type.toString().toLowerCase()](`[${this._name || 'Unknown'}]`, ...args)
  }

  private getAppFrame() {
    if (this.target) {
      const appIframe = window.parent.document.getElementById(this.target) as HTMLIFrameElement

      if (appIframe) {
        return appIframe
      }
    } else {
      const iframe = window.top.frames.document.querySelector('[id^="Your App: "]');

      if (iframe) {
        return iframe as HTMLIFrameElement
      } else if (window.frameElement) {
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
