<template>
  <div class="social-share position-relative">
    <div tabindex="0"
         @click="onOpenPopup"
         @keyup.enter="onOpenPopup">
      <span class="social-share__icon" ref="iconShare">
        <i class="icon fal fa-share-alt"></i>
      </span>
      <span class="social-share__label fw-700" v-if="label.length">{{ label }}</span>
    </div>

    <div class="social-share__popover position-fixed shadow-sm"
         :class="[{'arrow-left' : isIconOnLeft}, {'arrow-right': !isIconOnLeft}, {'arrow-bottom': isIconOnBottom}]"
         ref="popover"
         v-if="showing"
         :style="{'left': left, 'top': top}">
      <div class="align-items-center d-flex justify-content-between">
        <span class="text-charcoal">Share this with</span>
        <span class="social-share__close" tabindex="0"
              @click="showing = false"
              @keyup.enter="showing = false">
          <i class="icon fal fa-times"></i>
        </span>
      </div>

      <ul>
        <li v-for="(link, index) in links" :key="index">
            <i class="icon fab" :class="link.icon"></i>
            <a class="fw-700 ignore" :href="link.href" target="_blank"
               data-layer-track="true"
               :data-layer-location="analyticsLocation"
               :data-layer-label="link.name" >
              {{link.name}}
            </a>
        </li>
      </ul>

      <div class="social-share__copy fw-700" tabindex="0"
           @click="onCopyLink"
           @keyup.enter="onCopyLink"
           data-cy="link">
        <span class="text-charcoal">Copy this link</span>
        <div class="text-red text-truncate"
             data-cy="copy">
          {{ copied || currentUrl }}
        </div>
        <input type="hidden" id="url-content" :value="currentUrl">
      </div>
    </div>
  </div>
</template>

<script lang="ts"  src="./SocialShare.ts"></script>

<style lang="scss" src="./SocialShare.scss"></style>
