import { Component, Mixins, Prop, Vue } from 'vue-property-decorator'

// Currently broken in ie11 — https://github.com/Pixeldenker/vue-body-scroll-lock/issues/5
// import VBodyScrollLock from 'v-body-scroll-lock'

import { disableBodyScroll, enableBodyScroll, clearAllBodyScrollLocks } from 'body-scroll-lock';

import dialogPolyfill from 'dialog-polyfill'

import BaseComponent from '@components/BaseComponent'

// Currently broken in ie11
// Vue.use(VBodyScrollLock)

@Component
export default class SiteSearch extends Mixins(BaseComponent) {
  @Prop({ required: true, type: String })
  public logo!: string

  private siteSearchDialog!: HTMLDialogElement
  private siteSearchLogo!: HTMLElement
  private openDialogButton!: HTMLElement
  private closeDialogButton!: HTMLElement

  private isDialogOpen: boolean = false

  mounted() {
    console.log('Site Search mounted');
    this.siteSearchDialog = this.$refs.siteSearch as HTMLDialogElement
    this.siteSearchLogo = this.$refs.siteSearchLogo as HTMLElement
    this.openDialogButton = this.$refs.openDialog as HTMLElement
    this.closeDialogButton = this.$refs.closeDialog as HTMLElement

    // Fix for an IE11 issue where the svg logo content is clipped.
    // This code dynamically loads the logo image source and if it's
    // an SVG it adds a viewbox attribute using the width and height
    if ((!!window.MSInputMethodContext && !!document.documentMode)) {
      this.fetchURL(this.logo).then(this.svgLogoAddViewBox)
    }

    this.siteSearchDialog.addEventListener('close', () => {
      this.isDialogOpen = false
      enableBodyScroll(this.siteSearchDialog)
      this.openDialogButton.focus()
    })

    dialogPolyfill.registerDialog(this.siteSearchDialog);
    console.log('site search', this.siteSearchDialog);
  }

  openDialog() {
    this.siteSearchDialog.showModal()
    this.isDialogOpen = true
    disableBodyScroll(this.siteSearchDialog)
    this.closeDialogButton.focus()
  }

  closeDialog() {
    this.siteSearchDialog.close()
  }

  fetchURL(url) {
    return new Promise((resolve, reject) => {
      const request = new XMLHttpRequest()
      request.open('GET', url)

      request.onload = () => {
        if (request.status === 200) {
          resolve(request.response)
        } else {
          reject(Error(`An error occurred while loading url. error code: ${request.statusText}`))
        }
      }

      request.send()
    })
  }

  svgLogoAddViewBox(response) {
    console.log('response', response);

    const logoSVG = document.createRange().createContextualFragment(response as string).querySelector('svg')
    console.log('logo svg', logoSVG)

    if (logoSVG) {
      console.log('process site search logo svg')
      const height = logoSVG.getAttribute('height') || this.siteSearchLogo.clientHeight
      const width  = logoSVG.getAttribute('width')  || this.siteSearchLogo.clientWidth

      logoSVG.setAttribute('viewBox', `0 0 ${width} ${height}`)

      const serializedString = new XMLSerializer().serializeToString(logoSVG)

      this.siteSearchLogo.setAttribute('src', `data:image/svg+xml;base64,${btoa(serializedString)}`)
    }
  }
}
