<template>
  <button v-if="!hidden"
          :disabled="disabled"
          class="view-more btn btn-secondary-outline"
          @click="viewMore()">
    View more
  </button>
</template>


<script lang="ts"  src="./ViewMore.ts"></script>

<style lang="scss" src="./ViewMore.scss"></style>
