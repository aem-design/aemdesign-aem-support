import { Component, Mixins} from 'vue-property-decorator'


import PagedList from '@components/generic/paged-list/PagedList'
import ResultsList from '@components/generic/results-list/ResultsList.vue'
import SearchResultsItem from './SearchResultsItem.vue'

@Component({
  name: 'search-results-container',
  components: {
    SearchResultsItem,
    ResultsList
  }
})
export default class SearchResults extends Mixins(PagedList) {
  // set by input event
  public searchTerm:string = '';

  created() {
    this.eventBus.$on('search', term => {
      if (!term || term === this.searchTerm) {
        return
      }

      // especially for search as we only append more results to list
      if (term != this.searchTerm) {
        this.resetSearchResults()
      }

      this.searchTerm = term;
      this.buildSearchResults();
    });
  }

  destroyed() {
    this.resetSearchResults()
    this.eventBus.$off('search');
  }

  get viewMoreProps() {
    return {
      noMoreResults: !this.pagerNextStart,
      searching: this.isPagerLoadingResults,
    }
  }


  // do we need to sanitize searchTerm is that already handled?
  get searchEndpoint() {
    const url = `https://searchserver/s/search.json?collection=collection&query=${this.searchTerm}`;

    if (this.pagerNextStart) {
      return `${url}&start_rank=${this.pagerNextStart}`;
    }

    return url;
  }

  resetSearchResults() {
    this.pagerItems = [];
    this.updatePagingValues({});
  }

  /**
   * update pager and parse funnelback items
  */
  handleSearchResponse(data) {
    this.pagerAddResults(this.parseSearchResults(data.response.resultPacket.results));
    this.updatePagingValues(data.response.resultPacket.resultsSummary)
  }

  fetchSearchResults(endpoint) {
    return this.fetchAsyncPagerItems(endpoint)
  }

  /**
   * fetch and handle search request
  */
  private async buildSearchResults() {
    try {
      const response = await this.fetchSearchResults(this.searchEndpoint)

      this.handleSearchResponse(response)

    } catch(error) {
      console.log('search results failed:', error)
    }
  }

  /**
   * update pager mixin with resultsSummary data for pagination
   * @param summary
   */
  private updatePagingValues(summary) {
    this.pagerCurrentStart = summary.currStart;
    this.pagerCurrentEnd = summary.currEnd;
    this.pagerTotalResults = summary.totalMatching;
    this.pagerNextStart = summary.nextStart;
  }

  private parseSearchResults(items:SearchResultsResponseItem[]) {
    return items.map(item => ({
      collection : item.collection,
      summary    : item.metaData.x,
      title      : item.title,
      url        : item.displayUrl,
    }))
  }

  get headingListLabel() {
    return `Showing ${this.pagerViewingLabel} results for ${this.searchTerm}`
  }
}
