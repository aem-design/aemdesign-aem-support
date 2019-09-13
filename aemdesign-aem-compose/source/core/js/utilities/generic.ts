type Constructor<T> = new (...args: any[]) => T;

export function typeGuard<T>(o: any, className: Constructor<T>): o is T {
  return o instanceof className
}
