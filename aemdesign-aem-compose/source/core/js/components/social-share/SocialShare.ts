import { Component, Mixins, Prop } from 'vue-property-decorator'

import BaseComponent from '@components/BaseComponent'

@Component
export default class SocialSharing extends Mixins(BaseComponent) {
  @Prop({ default: '', type: String })
  public label!: string

  public showing: boolean = false
  public copied: string = ''
  public left: string = 'auto'
  public top: string = 'auto'
  public isIconOnLeft: boolean = true
  public isIconOnBottom: boolean = true

  public links: any = [{
    name: 'Facebook',
    icon: 'fa-facebook-square',
    href: `https://www.facebook.com/sharer/sharer.php?u=${this.currentUrl}`,
  },{
    name: 'Twitter',
    icon: 'fa-twitter-square',
    href: `https://twitter.com/home?status=${this.currentUrl}`,
  },{
    name: 'Linkedin',
    icon: 'fa-linkedin',
    href: `https://www.linkedin.com/shareArticle?mini=true&url=${this.currentUrl}&title=&summary=&source=`,
  }]

  mounted() {
    window.addEventListener("resize", this.onPositionPopup)
    window.addEventListener('scroll', this.onPositionPopup)
  }

  destroyed() {
    window.removeEventListener("resize", this.onPositionPopup)
    window.removeEventListener('scroll', this.onPositionPopup)
  }

  get currentUrl() {
    return window.location.href
  }

  elementPosition(refName: string): DOMRect {
    const icon = this.$refs[refName] as Element
    return icon.getBoundingClientRect() as DOMRect
  }

  onPositionPopup() {
    if (this.showing) {
      const positionOfIcon    = this.elementPosition('iconShare')
      const positionOfpopover = this.elementPosition('popover')
      this.isIconOnLeft = this.windowTarget.innerWidth - positionOfIcon.left > this.windowTarget.innerWidth / 2
      this.isIconOnBottom = this.windowTarget.innerHeight - positionOfIcon.top < this.windowTarget.innerHeight / 2
      this.left = this.isIconOnLeft ? (positionOfIcon.left-10)+'px' : (positionOfIcon.left-positionOfpopover.width+20)+'px'
      this.top = !this.isIconOnBottom ? (positionOfIcon.top + 15)+'px' :  (positionOfIcon.top - positionOfpopover.height-26)+'px'
    }
  }

  onOpenPopup() {
    this.showing = !this.showing
    this.$nextTick(() => {
      this.onPositionPopup()
    })
  }

  onCopyLink () {
    const urlToCopy = this.windowTarget.document.querySelector('#url-content') as HTMLInputElement
    if (urlToCopy) {
      urlToCopy.setAttribute('type', 'text')
      urlToCopy.select()

      try {
        this.windowTarget.document.execCommand('copy')
        this.copied = 'Copied!'
      } catch (err) {
        this.copied = 'Unable to copy...'
      } finally {
        setTimeout(() => {
          this.copied = ''
        }, 2000);
      }

      urlToCopy.setAttribute('type', 'hidden')
    }
  }
}
