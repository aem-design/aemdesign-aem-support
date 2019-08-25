import {
  EVENT_TYPE_CLICK,
  TOPIC_VIDEO_GLOBAL,
} from '../utilities/constants'

export default (event: JQuery.Event, originalEvent: JQuery.TriggeredEvent, type: string) => {
  const $target = $(originalEvent.target)

  // Try and work out the context of how the event was triggered
  let $video: JQuery = $()

  // Trigger a global video popup that automatically plays the video if it is supported
  // by the <VideoPlayer /> component.
  if (($target.prop('href') || '').length > 1 && type === EVENT_TYPE_CLICK) {
    originalEvent.preventDefault()

    PubSub.publish(TOPIC_VIDEO_GLOBAL, {
      url: $target.prop('href'),
    })

  // Play a video over top of a content block when the background video theme has been
  // applied to it.
  } else if (
    $target.parents('.contentblock.theme--video-background').length === 1 &&
    type === EVENT_TYPE_CLICK
  ) {
    console.info('[Play Video] Content block context was discovered!')

    // Get the parent element
    const $contentblock = $target.parents('.contentblock')

    // Try and find a `.video` element within the `.contentblock` parent element
    $video = $contentblock.find('.video')

  } else {
    console.warn('[Play Video] Unable to determine the context for the trigger:', type)
  }

  // If a video element was found, play the video instantly!
  if ($video !== null) {
    const $videoPlayer = $video.find('video')
    const player       = $videoPlayer.get(0) as HTMLVideoElement
    const promise      = player.play()

    if (promise instanceof Promise) {
      promise.then(() => {
        $video.addClass('playing')
        console.info('[Play Video] Video is playing!')
      })
    }
  }
}
