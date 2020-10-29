export enum CarouselType {
  LIST,
}

export enum MediaHandler {
  ON_PAUSE,
  ON_PLAY,
  ON_STOP,

  STATE_PLAY,
  STATE_PAUSE,
  STATE_ENDED,
}

export enum MediaState {
  INVALID,
  IS_READY,
  NOT_READY,
}

export enum MediaProvider {
  YOUTUBE = 'youtube',
}

export enum LogType {
  LOG = 'log',
  INFO = 'info',
  WARNING = 'warn',
  ERROR = 'error',
}

export enum InputType {
  BLUR = 'blur',
  CHANGE = 'change',
  FOCUS = 'focus',
  INPUT = 'input',
}

export enum MouseAndTouchType {
  MOUSEDOWN = 'mousedown',
  MOUSEMOVE = 'mousemove',
  MOUSEUP = 'mouseup',
  TOUCHEND = 'touchend',
  TOUCHSTART = 'touchstart',
}
