<template>
<div class="search-results" vue-component="search-results">
  <div v-if="searchTerm">
    <div v-if="pagerItems.length" class="search-results__list">
      <results-list
        :heading="headingListLabel"
        :is-paged="true"
        :view-more="viewMoreProps"

        @next-page="buildSearchResults"
      >
        <div slot="list">
          <search-results-item v-for="(item, index) in pagerItems"
            :key="index"
            :item="item"
          />
        </div>
      </results-list>
    </div>

    <div v-if="!isPagerLoadingResults && !pagerItems.length" class="search-results--empty">
      <p>No results found.</p>
    </div>
  </div>
</div>
</template>

<script lang="ts"  src="./SearchResults.ts"></script>
