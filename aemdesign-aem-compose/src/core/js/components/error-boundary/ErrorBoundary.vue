<template>
  <div class="error-boundary">
    <slot
      v-if="err"
      name="error"
      v-bind:err="err"
      v-bind:vm="vm"
      v-bind:info="info"
    >
      <h5 class="error-boundary__heading">{{ heading }}</h5>
      <p>{{ err }}</p>
    </slot>
    <slot v-else></slot>
  </div>
</template>

<script lang="ts" src="./ErrorBoundary.ts"></script>

<style lang="scss" src="./ErrorBoundary.scss"></style>
