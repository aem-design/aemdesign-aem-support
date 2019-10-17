 interface MavenConfig<T> {
  fallback?: T;
  parser?: (value: any) => T;
  path: string;
  pom: string;
}

interface MavenConfigMap {
  authorPort: number;
  appsPath: string;
  sharedAppsPath: string;
 }
 
interface SavedMavenConfig {
  [key: string]: string;
}