<template>
  <div id="site-search" class="site-search dialog">
    <button role="button" class="site-search__open" aria-label="Open modal" @click="openDialog" ref="openDialog">
      <i class="icon fal fa-search"></i>
      <span class="lg-sr-only">Search</span>
    </button>

    <!--
    v-bsl is currently broken in ie11
    <dialog id="site-search-modal" class="site-search__modal dialog__overlay" ref="siteSearch" v-bsl="isDialogOpen" role="dialog" aria-modal="true" aria-labelledby="site-search__title">
    -->
    <dialog id="site-search-modal" class="site-search__modal dialog__overlay" ref="siteSearch" role="dialog" aria-modal="true" aria-labelledby="site-search__title">
      <div class="site-search__wrapper dialog__container">
        <header class="site-search__header">
          <div class="container">
            <div class="row no-gutters">
              <h2 id="site-search__title" class="sr-only">Search</h2>
              <img v-if="logo" class="site-search__logo" :src="logo" alt="" ref="siteSearchLogo">
              <button role="button" class="site-search__close" aria-label="Close modal" @click="closeDialog" ref="closeDialog"><i class="icon fal fa-times"></i>Close<span class="sm-sr-only"> window</span></button>
            </div>
          </div>
        </header>
        <div class="site-search__body">
          <div class="container">
            <div class="row">
              <div class="col-lg-8">
              <h3>Search</h3>
              </div>
              <div class="col-lg-4 site-search__sidebar">
                <div class="row">
                  <div class="col-md-6 col-lg-12">
                    <h4>Your search history</h4>
                    <ul>
                    </ul>
                  </div>
                  <div class="col-md-6 col-lg-12">
                    <h4>People also searched for</h4>
                    <ul>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </dialog>
  </div>
</template>

<script lang="ts" src="./SiteSearch.ts"></script>

<style lang="scss" src="./SiteSearch.scss"></style>
