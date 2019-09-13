import {
  bindMediaScrollCallback,
  MediaElement,
  mediaIsInView,
  activatePlayButton,
  toggleOverlay,
} from '@module/media'

import { MediaHandler, MediaState } from '@type/enum'

// Internal
let elements: Element[]
let iframeAPIReady = false

function onYouTubeIframeAPIReady() {
  console.info('YouTube iframe API is ready!')
  iframeAPIReady = true
}

function onReady(event: YT.PlayerEvent) {
  console.info('YouTube player is ready!', event.target.getIframe())
}

function onStateChange(state: YT.PlayerState, element: MediaElement) {
  if (state === YT.PlayerState.PLAYING) {
    element.mediaInstance.startRecording(() => ({
      currentTime : element.youtube.getCurrentTime(),
      duration    : element.youtube.getDuration(),
    }))
  }

  if (state === YT.PlayerState.ENDED || state === YT.PlayerState.PAUSED) {
    element.mediaInstance.clearRecording(state === YT.PlayerState.ENDED)
  }

  if (state === YT.PlayerState.ENDED) {
    toggleOverlay(element)
  }
}

function embedMediaElementFrames() {
  if (!iframeAPIReady) {
    console.warn("YouTube iframe isn't quite ready yet!")
    return
  }

  for (const mediaElement of elements) {
    const element = mediaElement as HTMLElement

    if (!mediaElement.youtube && mediaIsInView(element)) {
      mediaElement.youtube = new YT.Player(element.dummyElement as HTMLElement, {
        events: {
          onReady,

          onStateChange(event: YT.OnStateChangeEvent) {
            onStateChange(event.data, mediaElement as MediaElement)
          },
        },

        height  : 768,
        videoId : element.dataset.sourceId,
        width   : 1024,
      })

      activatePlayButton(element)
    }
  }
}

function waitForYouTubeInstanceToLoad(element: MediaElement) {
  const waitInterval = setInterval(() => {
    if (element.youtube) {
      clearInterval(waitInterval)

      element.mediaInstance.triggerHandler(MediaHandler.STATE_PLAY)
    }
  }, 50)
}

function youTubeVideoAction(element: MediaElement, action: 'play' | 'pause' | 'stop') {
  if (element.youtube) {
    console.log('Media action for YouTube triggered, action type:', action)
    element.youtube[`${action}Video`]()
    
    return MediaState.IS_READY
  }
  
  waitForYouTubeInstanceToLoad(element as MediaElement)
  
  return MediaState.NOT_READY
}

export default (mediaElements: Element[]) => {
  elements = mediaElements

  for (const element of elements as MediaElement[]) {
    element.mediaInstance.registerHandler(MediaHandler.ON_PLAY, (element) => {
      return youTubeVideoAction(element as MediaElement, 'play')
    })

    element.mediaInstance.registerHandler(MediaHandler.ON_PAUSE, (element) => {
      return youTubeVideoAction(element as MediaElement, 'pause')
    })

    element.mediaInstance.registerHandler(MediaHandler.ON_STOP, (element) => {
      return youTubeVideoAction(element as MediaElement, 'stop')
    })
  }

  const firstScriptInHead = document.querySelector('script')
  const youTubeIframeAPI  = document.createElement('script')

  youTubeIframeAPI.src = 'https://www.youtube.com/iframe_api'

  if (firstScriptInHead && firstScriptInHead.parentNode) {
    firstScriptInHead.parentNode.insertBefore(youTubeIframeAPI, firstScriptInHead)

    window.onYouTubeIframeAPIReady = onYouTubeIframeAPIReady

    bindMediaScrollCallback(embedMediaElementFrames)

    const onLoadInterval = setInterval(function() {
      if(iframeAPIReady){
        console.log("OnLoad - YouTube API is now ready.")
        embedMediaElementFrames()
        clearInterval(onLoadInterval)
      }
    }, 500)
  }
}
