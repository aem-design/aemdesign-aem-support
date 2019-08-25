import { Component, Mixins, Prop, Watch } from 'vue-property-decorator'

import BaseComponent, { LogType } from '@components/BaseComponent'

import { trackAction } from '@utility/analytics'
import GoogleMapsInit from '@utility/gmaps'

@Component
export default class JourneyPlanner extends Mixins(BaseComponent) {
  @Prop({ default: 'campus locator', type: String })
  public analyticsName!: string
  @Prop({ required: true, type: String })
  public campus!: string
  @Prop({ required: true, type: String })
  public googleMapsKey!: string
  @Prop({ required: true, type: String })
  public icon!: string
  @Prop({ required: true, type: String })
  public title!: string

  public transportMode: string = 'DRIVING'
  public userAddress: string = ''

  private directionsService!: google.maps.DirectionsService
  private directionsDisplay!: google.maps.DirectionsRenderer
  private directionsZoom!: google.maps.DirectionsRenderer
  private inputTimeout: number = 0

  async mounted() {
    try {
      await GoogleMapsInit(this.googleMapsKey)

      const geocoder = new google.maps.Geocoder()

      const map: google.maps.Map = new google.maps.Map(this.$refs.map as Element, {
        backgroundColor   : '#e5e5e5',
        fullscreenControl : false,
        mapTypeControl    : false,
        streetViewControl : false,

        zoomControlOptions: {
          position: google.maps.ControlPosition.RIGHT_TOP,
        },
      })

      const campusMarker = new google.maps.Marker({
        animation : google.maps.Animation.DROP,
        map,
        zIndex    : 1,
      })

      this.directionsService = new google.maps.DirectionsService()

      this.directionsZoom = new google.maps.DirectionsRenderer({
        draggable              : false,
        suppressBicyclingLayer : true,
        suppressInfoWindows    : true,
        suppressMarkers        : true,
        suppressPolylines      : true,
      })

      this.directionsDisplay = new google.maps.DirectionsRenderer({
        draggable              : false,
        preserveViewport       : true,
        suppressBicyclingLayer : true,
        suppressMarkers        : false,

        markerOptions: {
          crossOnDrag : false,
          zIndex      : 0,

          icon: {
            fillColor    : '#ffffff',
            fillOpacity  : 1,
            path         : google.maps.SymbolPath.CIRCLE,
            scale        : 6,
            strokeColor  : '#000000',
            strokeWeight : 3,
          },
        },
      })

      geocoder.geocode({ address: this.campus }, (results, status) => {
        if (status !== google.maps.GeocoderStatus.OK || !results[0]) {
          throw new Error(status.toString())
        }

        map.setCenter(results[0].geometry.location)
        map.fitBounds(results[0].geometry.viewport)

        campusMarker.setPosition(results[0].geometry.location)
      })

      this.log(LogType.INFO, 'marker', campusMarker)
      this.log(LogType.INFO, 'marker constructor', google.maps.Marker)

      this.directionsZoom.setMap(map)
      this.directionsDisplay.setMap(map)
    } catch (error) {
      this.log(LogType.ERROR, 'An error has occurred!', error)
    }
  }

  calcRoute() {
    const request: google.maps.DirectionsRequest = {
      destination              : this.campus,
      origin                   : this.userAddress,
      provideRouteAlternatives : false,
      travelMode               : google.maps.TravelMode[this.transportMode],
    }

    trackAction('locator', {
      location : this.campus,
      name     : this.analyticsName,
      type     : this.transportMode,
    }, 'location-interact')

    this.directionsService.route(request, (response, status) => {
      this.log(LogType.LOG, 'Directions Response:', status, response)

      if (status === google.maps.DirectionsStatus.OK) {
        this.directionsZoom.setDirections(response)
        this.directionsDisplay.setDirections(response)
      }
    })
  }

  @Watch('transportMode')
  @Watch('userAddress')
  reloadMap() {
    if (this.inputTimeout !== 0) {
      window.clearTimeout(this.inputTimeout)
    }

    this.inputTimeout = window.setTimeout(this.calcRoute, 200)
  }

  onSubmit() {
    this.log(LogType.INFO, 'Form submitted!')
  }
}
