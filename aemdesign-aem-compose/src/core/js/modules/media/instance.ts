import {
  MediaHandler,
  MediaProvider,
  MediaState,
} from '@/core/types/enum'

import { trackAction } from '@/core/utilities/analytics'

type MediaHandlerCallback = (element: Element) => MediaState

export default class MediaInstance {
  private readonly element!: Element;
  private readonly provider!: MediaProvider;

  private handlers: {
    [key: string]: MediaHandlerCallback[]
  } = {}

  private tracking: MediaTracking = {
    lastWatchedSegment: null,
    timer: null,
  }

  private segments: number[] = []

  constructor(target: Element, provider: MediaProvider) {
    this.element  = target
    this.provider = provider
  }

  registerHandler(handler: MediaHandler, callback: MediaHandlerCallback) {
    const handlerName = MediaHandler[handler]

    this.handlers[handlerName] = this.handlers[handlerName] || []
    this.handlers[handlerName].push(callback)
  }

  triggerHandler(handler: MediaHandler) {
    const handlerName = MediaHandler[handler]
    const callbacks   = this.handlers[handlerName]

    if (!callbacks) {
      console.warn('No callbacks have been registered yet for:', handlerName)
      return MediaState.INVALID
    }

    for (const callback of callbacks) {
      if (callback(this.element) === MediaState.NOT_READY) {
        return MediaState.NOT_READY
      }
    }

    return MediaState.IS_READY
  }

  startRecording(callback: () => { currentTime: number, duration: number }) {
    this.tracking.timer = window.setInterval(() => {
      const { currentTime, duration } = callback()

      if (isNaN(currentTime) || isNaN(duration)) {
        console.warn('Unable to run segment tracking as the current time or duration is invalid!', currentTime, duration)
        return
      }

      const currentSegment = Math.floor(currentTime / duration * 10)

      if (currentSegment !== this.tracking.lastWatchedSegment) {
        this.tracking.lastWatchedSegment = currentSegment
        this.segments.push(currentSegment)

        this.trackSegment()
      }
    }, 100)
  }

  clearRecording(runMissingSegment = false) {
    if (this.tracking.timer) {
      clearInterval(this.tracking.timer)

      if (runMissingSegment) {
        this.trackSegment(10)
      }

      this.segments = []
    }
  }

  trackSegment(customSegment?: number) {
    if (customSegment && this.segments.indexOf(customSegment) !== -1) {
      console.warn('Segment %d0 has already been executed!', customSegment)
      return
    }

    trackAction('video', {
      progress : `${customSegment || this.tracking.lastWatchedSegment}0`,
      provider : this.provider,
      title    : this.element.getAttribute('title') || '',
    }, 'video-interact')
  }
}
