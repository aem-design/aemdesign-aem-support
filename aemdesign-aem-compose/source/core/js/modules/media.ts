import _random from 'lodash/random'
import _throttle from 'lodash/throttle'

import MediaInstance from '@module/media/instance'

import { MediaHandler, MediaProvider, MediaState } from '@type/enum'

import { hasParent } from '@utility/dom'

export interface MediaElement extends HTMLElement {
  mediaInstance: MediaInstance
}

// Internal
const mediaBrands: {
  [key: string]: MediaBrand,
} = {
  [MediaProvider.Kaltura]: {
    elements : [],
    needed   : false,
  },

  [MediaProvider.YouTube]: {
    elements : [],
    needed   : false,
  },
}

const scrollCallbacks: Array<() => void> = []

function mediaWatchScroll() {
  for (const callback of scrollCallbacks) {
    callback()
  }
}

// Check that media is in a valid state
function callStateHandler(mediaElement: MediaElement, handler: MediaHandler, notReady: () => void, ready: () => void) {
  const state = mediaElement.mediaInstance.triggerHandler(handler)

  console.log(mediaElement, MediaHandler[handler], MediaState[state])

  if (state === MediaState.NOT_READY) {
    notReady()
  } else if (state === MediaState.INVALID) {
    console.warn('No valid states could be detected, unable to continue for:', MediaHandler[handler])
  } else {
    ready()
  }
}

export function toggleOverlay(mediaElement: Element) {
  const closeButton      = mediaElement.querySelector('.close-video') as MediaElement
  const mediaContainer   = mediaElement.parentNode as HTMLElement
  const titleOverlay     = mediaContainer.querySelector('.onlinemedia-title') as HTMLElement
  const mediaInfoOverlay = mediaElement.querySelector('.onlinemedia-info-container') as HTMLElement
  const overlayIsHidden  = mediaInfoOverlay.classList.contains('hidden')
  const parentElement    = mediaElement.parentNode as HTMLElement

  if (!(closeButton || mediaInfoOverlay || parentElement)) {
    console.error(
      '[Online Media] Unable to toggle overlay state as a key element is missing in the DOM!\n',
      'Close Button:', closeButton,
      'Info Overlay:', mediaInfoOverlay,
      'Parent Element:', parentElement,
    )

    return
  }

  if (overlayIsHidden) {
    if (titleOverlay) {
      titleOverlay.classList.remove('hidden')
    }

    mediaInfoOverlay.classList.remove('hidden')
    closeButton.classList.add('hidden')

    parentElement.classList.remove('onlinemedia--playing')
  } else {
    if (titleOverlay) {
      titleOverlay.classList.add('hidden')
    }

    mediaInfoOverlay.classList.add('hidden')
    closeButton.classList.remove('hidden')

    parentElement.classList.add('onlinemedia--playing')
  }
}

// Bind the media controls to the overlay image
function bindStandardMediaControls(mediaElement: Element) {
  const instance = (mediaElement as MediaElement).mediaInstance = new MediaInstance(
    mediaElement,
    (mediaElement.mediaProvider as MediaProvider)
  )

  // Media container and overlays
  const mediaContainer   = mediaElement.parentNode as HTMLElement
  const mediaInfoOverlay = mediaElement.querySelector('.onlinemedia-info-container') as HTMLElement

  // Play and close buttons
  const playButton  = mediaElement.querySelector('.onlinemedia-info-container') as MediaElement
  const closeButton = mediaElement.querySelector('.close-video') as MediaElement

  // Create a dummy element for the iframe
  const dummyElement = document.createElement('div')
  dummyElement.setAttribute('id', `dummy-player-${_random(100, 1000)}`)
  dummyElement.setAttribute('class', 'embed-responsive-item')
  
  const dummyElementContainer = mediaElement.querySelector('.onlinemedia-player-container') as HTMLElement

  // Only make the player container 16:9 when it is not present within a card
  if (!hasParent(mediaElement.parentNode, '.card')) {
    dummyElementContainer.classList.add('embed-responsive')
    dummyElementContainer.classList.add('embed-responsive-16by9')
  } else {
    (mediaElement.parentNode as HTMLElement).classList.add('onlinemedia--card')
  }

  // Attach the dummy element to the media element
  dummyElementContainer.appendChild(dummyElement)
  mediaElement.dummyElement = dummyElement

  // Bind the play button click event (also binds to overlay image)
  if (playButton) {
    playButton.addEventListener('click', (event: Event) => {
      event.preventDefault()

      console.log(playButton.waitForState)
      if (playButton.waitForState === true) {
        return
      }

      callStateHandler(
        mediaElement as MediaElement,
        MediaHandler.ON_PLAY,
        () => {
          console.info('Not quite ready to play the video!')
          playButton.waitForState = true
        },
        () => {
          console.log('Ready to play video =)')
          playButton.waitForState = false
        })

      toggleOverlay(mediaElement)
    })
  }

  // Bind the close button click event
  if (closeButton) {
    closeButton.addEventListener('click', (event: Event) => {
      event.preventDefault()

      if (closeButton.waitForState === true) {
        return
      }

      callStateHandler(
        mediaElement as MediaElement,
        MediaHandler.ON_PAUSE,
        () => closeButton.waitForState = true,
        () => {
          console.log('OK to stop video')
          closeButton.waitForState = false
        })

      toggleOverlay(mediaElement)
    })

    // Close button fade in on mouseover
    mediaContainer.addEventListener('mouseover', (event: Event) => {
      if (closeButton.waitForState === true) {
        return
      }

      if (closeButton.classList.contains('hidden') && mediaInfoOverlay.classList.contains('hidden')) {
        closeButton.classList.remove('hidden')
      }
      
      event.stopPropagation()
    })

    // Close button fade out on mouseleave
    mediaContainer.addEventListener('mouseleave', (event: Event) => {
      if (closeButton.waitForState === true) {
        return
      }

      if (!closeButton.classList.contains('hidden') && mediaInfoOverlay.classList.contains('hidden')) {
        closeButton.classList.add('hidden')
      }
      event.stopPropagation()
    })
  }

  instance.registerHandler(MediaHandler.STATE_PLAY, (element) => {
    element.waitForState = false

    const e = (element.querySelector('.play-video') as HTMLElement)
    e.click()

    return MediaState.IS_READY
  })

  instance.registerHandler(MediaHandler.STATE_PAUSE, (element) => {
    element.waitForState = false

    const e = (element.querySelector('.close-video') as HTMLElement)
    e.click()

    return MediaState.IS_READY
  })
}

// Enable play button when online media source API is ready
export function activatePlayButton(mediaElement: Element) {
  const playButton = mediaElement.querySelector('.play-video') as MediaElement
  playButton.classList.remove('disabled')

  playButton.innerHTML = '<i class="fal fa-play"></i>'
}

export function bindMediaScrollCallback(fn: () => void) {
  scrollCallbacks.push(fn)
}

// Detect if a given media element is visible in the active viewport
export function mediaIsInView(mediaElement: HTMLElement): boolean {
  const scrollOffset = (window.scrollY || window.pageYOffset) + window.innerHeight

  const { top: bodyTop }    = document.body.getBoundingClientRect()
  const { top: elementTop } = mediaElement.getBoundingClientRect()

  return scrollOffset >= (elementTop - bodyTop)
}

export default async (mediaElements: NodeListOf<Element>) => {
  // For each media element, detect the media provider and bind the appropriate controls
  for (const mediaElement of mediaElements) {
    const provider = (mediaElement as HTMLElement).dataset.media

    // YouTube
    if (provider === MediaProvider.YouTube) {
      mediaElement.mediaProvider = MediaProvider.YouTube

      mediaBrands[MediaProvider.YouTube].needed = true
      mediaBrands[MediaProvider.YouTube].elements.push(mediaElement)
    }

    // Kaltura
    if (provider === MediaProvider.Kaltura) {
      mediaElement.mediaProvider = MediaProvider.Kaltura

      mediaBrands[MediaProvider.Kaltura].needed = true
      mediaBrands[MediaProvider.Kaltura].elements.push(mediaElement)
    }

    // Bind the standard control events
    bindStandardMediaControls(mediaElement)
  }

  // Do any of the media elements require the YouTube iframe API?
  if (mediaBrands[MediaProvider.YouTube].needed) {
    const {
      default: YouTubeHandler,
    } = await import(/* webpackChunkName: "media/youtube" */ '@module/media/youtube')

    YouTubeHandler(mediaBrands[MediaProvider.YouTube].elements)
  }

  // Maybe Kaltura?
  if (mediaBrands[MediaProvider.Kaltura].needed) {
    const {
      default: KalturaHandler,
    } = await import(/* webpackChunkName: "media/kaltura" */ '@module/media/kaltura')

    KalturaHandler(mediaBrands[MediaProvider.Kaltura].elements)
  }

  window.addEventListener('scroll', _throttle(mediaWatchScroll, 300))
}
