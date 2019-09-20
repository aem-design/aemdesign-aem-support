import media, {
  bindMediaScrollCallback,
  MediaElement,
  mediaIsInView,
  activatePlayButton,
  toggleOverlay,
} from '@module/media'

import { KalturaState, MediaHandler, MediaState } from '@type/enum'

// Internal
const partnerId = '691292'
const uiConfId  = '20499062'

let elements: Element[]
let iframeAPIReady = false

function playerReadyCallback(playerId: string) {
  console.info('Kaltura player is ready! Player ID: ', playerId)
  
  const playerElement = document.querySelector(`#${playerId}`)

  if (playerElement) {
    const playerContainer = playerElement.parentNode as Element
    const mediaElement = playerContainer.parentNode as Element

    if (mediaElement.kaltura) {
      mediaElement.kaltura.kBind('playerStateChange', (state) => {
        onPlayerStateChange(state, mediaElement as MediaElement)
      })

      mediaElement.kaltura.kBind('playerPlayEnd', () => {
        onPlayerPlayEnd(mediaElement as MediaElement)
      })

      activatePlayButton(mediaElement)
    }
  }
}

function onPlayerStateChange(state: string, element: MediaElement) {
  if (state === KalturaState.PLAYING) {
    element.mediaInstance.startRecording(() => ({
      currentTime : element.kaltura.evaluate('{video.player.currentTime}'),
      duration    : element.kaltura.evaluate('{duration}'),
    }))
  }

  if (state === KalturaState.PAUSED) {
    element.mediaInstance.clearRecording()
  }
}

function onPlayerPlayEnd(element: MediaElement) {
  element.mediaInstance.clearRecording(true)
  toggleOverlay(element)
}

function embedMediaElementFrames() {
  if (!iframeAPIReady) {
    console.warn("Kaltura iframe isn't quite ready yet!")
    return
  }

  for (const mediaElement of elements) {
    const element = mediaElement as HTMLElement

    if (!mediaElement.kaltura && mediaIsInView(element)) {
      window.kWidget.embed({
        entry_id      : `${element.dataset.sourceId}`,
        readyCallback : playerReadyCallback,
        targetId      : mediaElement.dummyElement.id,
        uiconf_id     : `${uiConfId}`,
        wid           : `_${partnerId}`,

        flashvars: {
          autoPlay: false,
        },

        params: {
          wmode: 'transparent',
        },
      })

      mediaElement.kaltura = document.getElementById(mediaElement.dummyElement.id) as Element
    }
  }
}

function generateKalturaScriptUrl() {
  return `https://cdnapisec.kaltura.com/p/${partnerId}/sp/${partnerId}00/embedIframeJs/uiconf_id/${uiConfId}/partner_id/${partnerId}`
}

function waitForKalturaInstanceToLoad(element: MediaElement) {
  const waitInterval = setInterval(() => {
    if (element.kaltura) {
      clearInterval(waitInterval)

      element.mediaInstance.triggerHandler(MediaHandler.STATE_PLAY)
    }
  }, 50)
}

function kalturaVideoAction(element: MediaElement, action: 'play' | 'pause' | 'stop') {
  if (element.kaltura) {
    console.log('Media action for Kaltura triggered, action type:', action)
    element.kaltura.sendNotification(`do${action.charAt(0).toUpperCase() + action.substr(1)}`)
    
    return MediaState.IS_READY
  }
  
  waitForKalturaInstanceToLoad(element as MediaElement)
  
  return MediaState.NOT_READY
}

export default (mediaElements: Element[]) => {
  elements = mediaElements

  for (const element of elements as MediaElement[]) {    
    element.mediaInstance.registerHandler(MediaHandler.ON_PLAY, (element) => {
      return kalturaVideoAction(element as MediaElement, 'play')
    })

    element.mediaInstance.registerHandler(MediaHandler.ON_PAUSE, (element) => {
      return kalturaVideoAction(element as MediaElement, 'pause')
    })

    element.mediaInstance.registerHandler(MediaHandler.ON_STOP, (element) => {
      return kalturaVideoAction(element as MediaElement, 'stop')
    })
  }

  const firstScriptInHead   = document.querySelector('script')
  const kalturaIframeScript = document.createElement('script')

  kalturaIframeScript.onload = () => {
    iframeAPIReady = true
    embedMediaElementFrames()
  }

  kalturaIframeScript.src = generateKalturaScriptUrl()

  if (firstScriptInHead && firstScriptInHead.parentNode) {
    firstScriptInHead.parentNode.insertBefore(kalturaIframeScript, firstScriptInHead)

    bindMediaScrollCallback(embedMediaElementFrames)
  }
}
