import { Component, Mixins, Prop } from 'vue-property-decorator'

import BaseComponent from '@components/BaseComponent'

// i take a list make it 'paged' based on the mode
// i do not handle displaying said list, only methods
@Component({
  name: 'base-paged-list'
})
export default class PagedList extends Mixins(BaseComponent) {

  @Prop({type: Number, default: 10})
  public pagerSize!: number

  // pagination: prev/next buttons. list items replaced (warn: not supported yet)
  // infinite: load more button. list items append
  @Prop({type: String, default: 'pagination'})
  public pagerMode!:string;

  public pagerItems = new Array<{}>();

  public isPagerLoadingResults:boolean = false;

  public pagerCurrentStart:number = 1;
  public pagerCurrentEnd:number=0;
  public pagerTotalResults:number=0;
  public pagerNextStart:number=0;

  /**
   * fetch items over async
   * @todo abstract to service?
   */
  fetchAsyncPagerItems(endpoint:string): Promise<{} | Error > {
    this.isPagerLoadingResults = true;

    return new Promise((resolve, reject) => {
      const request = new XMLHttpRequest()

      request.open('GET', endpoint);

      request.onload = () => {
        if (request.status === 200) {
          resolve(JSON.parse(request.response))

        } else {
          reject(Error(`An error occurred while loading url. error code: ${request.statusText}`))
        }
      };

      request.onloadend = () => {
        this.isPagerLoadingResults = false;
      };

      request.onerror = () => {
        this.isPagerLoadingResults = false;
        reject(Error('xhr error'))
      }

      request.send();
    });
  }

  /**
   * adds items into the existing pager items.
   * for infinite pagination mode
   *
   * @param items to add to list
   */
  pagerAddResults(items:Array<{}>) {
    this.pagerItems = [...this.pagerItems, ...items]
  }

  get pagerViewingLabel() {
    const start = this.pagerMode.toLowerCase() === 'pagination' ? this.pagerCurrentStart : 1;

    return `${start}-${this.pagerCurrentEnd} of ${this.pagerTotalResults}`;
  }
}

