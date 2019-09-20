<template>
<div class="results-list" vue-component="base-results-list">
  <header class="results-list__header" v-if="heading"
    v-html="heading"
  />

  <slot name="list"></slot>

  <div class="results-list__footer" v-if="isPaged">
    <view-more v-bind="viewMore"
      @searching="onClickNextPage"
    />
  </div>
</div>
</template>

<script lang="ts"  src="./ResultsList.ts"></script>

<style lang="scss" src="./ResultsList.scss"></style>
