<template>
  <div class="journey-planner">
    <div class="container bg-charcoal">
      <div class="row">
        <div class="journey-planner__container col-xs-12 col-lg-6">
          <div class="row">
            <div class="col-sm-9 col-lg-10">
              <h3><i class="icon" :class="icon"></i>{{ title }}</h3>
            </div>
          </div>

          <form @submit.prevent="onSubmit">
            <div class="form-row">
              <div class="col col-md-5 col-lg-6 col-xl-5">
                <div class="form-group">
                  <input type="text" id="address" name="address" v-model="userAddress" autocomplete="off" required />
                  <label for="address">Enter your address</label>
                </div>
              </div>

              <div class="col col-md-3 col-lg-4 col-xl-4">
                <div class="form-group">
                  <select id="transport" name="transport" v-model="transportMode">
                    <option value="DRIVING">Driving</option>
                    <option value="BICYCLING">Bicycling</option>
                    <option value="TRANSIT">Transit</option>
                    <option value="WALKING">Walking</option>
                  </select>

                  <label for="transport">Mode of Transport</label>
                </div>
              </div>
            </div>
          </form>

          <p>
            <small>
              <slot name="description"></slot>
            </small>
          </p>
        </div>

        <div class="journey-planner__map" ref="map"></div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./JourneyPlanner.ts"></script>

<style lang="scss" src="./JourneyPlanner.scss"></style>
