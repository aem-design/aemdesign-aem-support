<template>
<a :href="item.url" class="search-results__item">
  <div class="search-results__item-icon">
    <i class="icon" v-if="hasIcon"
      :class="iconCssClass"
    />
  </div>

  <div class="search-results__item-content">
    <p class="search-results__item-title strong" v-html="item.title" />
    <p class="small" v-html="item.summary" />
  </div>
</a>
</template>

<script lang="ts"  src="./SearchResultsItem.ts"></script>

<style lang="scss" src="./SearchResultsItem.scss"></style>
