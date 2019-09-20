import { Component, Mixins, Prop } from 'vue-property-decorator'

import BaseComponent from '@components/BaseComponent'

const iconConfigLookup:{[key: string]:string} = {
  'news': 'fa-newspaper',
};

@Component({
  name: 'search-results-item'
})
export default class SearchResultsItem extends Mixins(BaseComponent) {
  @Prop()
  public item!:ISearchResultsItem;

  public iconCssClass:string = this.getIconForCollection();

  private getIconForCollection() {
    const matchingIcon:string = iconConfigLookup[this.item.collection];

    if (!matchingIcon) {
      return '';
    }

    return `fal ${matchingIcon}`;
  }

  get hasIcon() {
    return !!this.iconCssClass;
  }
}
