<template>
  <form class="search-input position-relative needs-validation"
        :class="{'was-validated': wasValidated}"
        @submit.prevent.stop="searchQuery"
        @keydown.enter.exact.prevent="searchQuery"
        ref="form"
        novalidate>
    <div class="input-group">
      <div class="input-group-prepend">
        <div class="input-group-text"
             :class="[{'focused' : inputFocused},
                      {'invalid' : isShowingError}]">
          <i class="icon fal fa-search"></i>
        </div>
      </div>
      <label :for="identifier">{{ label }}</label>
      <input @focus="inputFocused = true, focusChanged()"
             @blur="inputFocused = false, inputOrButtonBlur($event)"
             required
             type="text" class="form-control" :id="identifier" :name="identifier" v-model="search" ref="search" autocomplete="off">
      <div class="input-group-append"
           v-if="isShowingError">
        <div class="input-group-text" :class="{'invalid': !isValid}">
          <i class="icon fal fa-exclamation-circle"></i>
        </div>
      </div>
      <div class="invalid-feedback"
           v-if="isShowingError">Your search query  should not be empty</div>
    </div>
    <div class="search-input__popover position-absolute"
         @mousedown.stop.prevent
         v-if="isPopoverOpen">
      <button v-for="(button, index) in buttons"
           :key="index"
           :class="classToggleButton(button.value)"
           @blur="inputOrButtonBlur($event)"
           @focus="focusChanged()"
           class="link btn btn-sm"
           @click="selectedButton($event, button.value)"
           @keyup.enter.exact="selectedButton($event, button.value)">
        <span class="link-text">{{ button.label }}</span>
      </button>
    </div>
  </form>
</template>

<script lang="ts"  src="./SearchInput.ts"></script>

<style lang="scss" src="./SearchInput.scss"></style>
