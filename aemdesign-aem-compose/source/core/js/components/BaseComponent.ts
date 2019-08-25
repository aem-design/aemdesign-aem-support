import { Component, Vue } from 'vue-property-decorator'

export enum LogType {
  LOG     = 'log',
  INFO    = 'info',
  WARNING = 'warn',
  ERROR   = 'error',
}

@Component
export default class BaseComponent extends Vue {
  // tslint:disable-next-line:variable-name
  protected _name!: string

  protected log(type: LogType, ...args) {
    console[type.toString().toLowerCase()](`[${this._name}]`, ...args)
  }
}
