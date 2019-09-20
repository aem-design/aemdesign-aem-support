const breakpoints = [
  {
    height : 1280,
    key    : 'desktop-el',
    label  : 'Desktop - Extra Large',
    width  : 1366,
  },
  {
    height : 768,
    key    : 'desktop',
    label  : 'Desktop - Large',
    width  : 1024,
  },
  {
    height : 768,
    key    : 'tablet',
    label  : 'Tablet',
    width  : 640,
  },
  {
    height : 480,
    key    : 'mobile',
    label  : 'Mobile',
    width  : 320,
  },
]

export function getBreakpointForKey(key: string) {
  return breakpoints.filter((breakpoint) => breakpoint.key === key)[0]
}

export default breakpoints
