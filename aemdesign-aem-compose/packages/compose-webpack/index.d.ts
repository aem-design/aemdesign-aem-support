// Type definitions for AEM.Design Compose module
// Project: https://github.com/aem-design/aemdesign-aem-support, https://aem.design
// Definitions by: Chris Shaw <https://github.com/cshawaus>

import * as sass from 'sass'
import * as webpack from 'webpack'

declare namespace compose {
  type LogTypes = 'error' | 'info' | 'log' | 'warn'
  
  interface Configuration {
    getMavenConfiguration(paths: {
      child: string,
      parent: string,
    }): MavenConfigMap;
    
    projects: Projects;
  }
  
  interface Loaders {
    css(env: webpack.ParserOptions, options?: {
      sassOptions: sass.Options,
    }): webpack.RuleSetUseItem[];
    
    js(env: webpack.ParserOptions, options?: {
      configFile?: string,
    }): webpack.RuleSetRule[];
  }
  
  interface Logging {
    error(...args: any): void;
    info(...args: any): void;
    log(logType: LogTypes, ...args: any): void;
    warning(...args: any): void;
  }
  
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
  
  interface Projects {
    [project: string]: {
      [key: string]: any;
    }
  }
  
  interface SavedMavenConfig {
    [key: string]: string;
  }
}

declare module '@aem-design/compose-webpack' {
  const config: compose.Configuration;
  const loaders: compose.Loaders;
  const logging: compose.Logging;
}