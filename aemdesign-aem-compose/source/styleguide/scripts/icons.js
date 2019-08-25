/* eslint-disable */

const fs = require('fs')

const icons = [
  { class: 'fal fa-home', 'name': 'Home' },
  { class: 'fal fa-chevron-right', 'name': 'Chevron Right' },
  { class: 'fal fa-chevron-left', 'name': 'Chevron left' },
  { class: 'fal fa-calculator', 'name': 'Calculator' },
  { class: 'fal fa-briefcase', 'name': 'Briefcase' },
  { class: 'fal fa-fast-forward', 'name': 'Fast Forward' },
  { class: 'fal fa-comments', 'name': 'Comments' },
  { class: 'fal fa-search', 'name': 'Search' },
  { class: 'fal fa-calendar', 'name': 'Calendar' },
  { class: 'fal fa-calendar-alt', 'name': 'Calendar Alt' },
  { class: 'fal fa-edit', 'name': 'Edit' },
  { class: 'fal fa-phone', 'name': 'Phone' },
  { class: 'fal fa-pencil-alt', 'name': 'Pencil Alt' },
  { class: 'fal fa-book', 'name': 'Book' },
  { class: 'fal fa-reply-all', 'name': 'Reply All' },
  { class: 'fal fa-life-ring', 'name': 'Life Ring' },
  { class: 'fal fa-envelope', 'name': 'Envelope' },
  { class: 'fal fa-star', 'name': 'Star' },
  { class: 'fal fa-car', 'name': 'Car' },
  { class: 'fal fa-street-view', 'name': 'Street View' },
  { class: 'fal fa-globe', 'name': 'Globe' },
  { class: 'fal fa-train', 'name': 'Train' },
  { class: 'fal fa-play-circle', 'name': 'Play Circle' },
  { class: 'fal fa-user', 'name': 'User' },
  { class: 'fal fa-users', 'name': 'Users' },
  { class: 'fal fa-users-class', 'name': 'Users Class' },
  { class: 'fal fa-user-graduate', 'name': 'User Graduate' },
  { class: 'fal fa-balance-scale', 'name': 'Balance Scale' },
  { class: 'fal fa-building', 'name': 'Building' },
  { class: 'fal fa-chalkboard-teacher', 'name': 'Chalkboard Teacher' },
  { class: 'fal fa-child', 'name': 'Child' },
  { class: 'fal fa-comment-alt-exclamation', 'name': 'Comment Al Exclamation' },
  { class: 'fal fa-comment-alt-smile', 'name': 'Comment Alt Smile' },
  { class: 'fal fa-comment-alt-check', 'name': 'Comment Alt Check' },
  { class: 'fal fa-comment-alt-dots', 'name': 'Comment Alt Dots' },
  { class: 'fal fa-comments-alt', 'name': 'Comment Alt' },
  { class: 'fal fa-info-circle', 'name': 'Info Circle' },
  { class: 'fal fa-laptop', 'name': 'Laptop' },
  { class: 'fal fa-location-circle', 'name': 'Location Circle' },
  { class: 'fal fa-map-pin', 'name': 'Map pin' },
  { class: 'fal fa-plane-arrival', 'name': 'Plane Arrival' },
  { class: 'fal fa-plane-departure', 'name': 'Plane Departure' },
  { class: 'fal fa-thumbs-up', 'name': 'Thumbs Up' },
  { class: 'fal fa-thumbs-down', 'name': 'Thumbs Down' },
  { class: 'fal fa-trophy', 'name': 'Trophy' },
  { class: 'fal fa-handshake-alt', 'name': 'Handshake Alt' },
  { class: 'fal fa-wheelchair', 'name': 'Wheelchair' },
  { class: 'fal fa-star', 'name': 'Star' },
  { class: 'fal fa-school', 'name': 'School' },
  { class: 'fal fa-paper-plane', 'name': 'Paper Plane' },
  { class: 'fal fa-newspaper', 'name': 'Newspaper' },
  { class: 'fal fa-chart-bar', 'name': 'Chart Bar' },
  { class: 'fal fa-university', 'name': 'University' },
  { class: 'fal fa-video', 'name': 'Video' },
  { class: 'fal fa-graduation-cap', 'name': 'Graduation Cap' },
  { class: 'fal fa-cogs', 'name': 'Cogs' },
  { class: 'fal fa-gamepad', 'name': 'Gamepad' },
  { class: 'fal fa-code', 'name': 'Code' },
  { class: 'fal fa-gavel', 'name': 'Gavel' },
  { class: 'fal fa-stethoscope', 'name': 'Stethoscope' },
  { class: 'fal fa-flask', 'name': 'Flask' },
  { class: 'fal fa-wrench', 'name': 'Wrench' },
  { class: 'fal fa-heartbeat', 'name': 'Heartbeat' },
  { class: 'fal fa-link', 'name': 'Link' },
  { class: 'fal fa-bars', 'name': 'Bars' },
  { class: 'fal fa-times', 'name': 'Times' },
  { class: 'fab fa-connectdevelop', 'name': 'Connectdevelop' },
  { class: 'fab fa-facebook-f', 'name': 'Facebook F' },
  { class: 'fab fa-facebook', 'name': 'Facebook' },
  { class: 'fab fa-instagram', 'name': 'Instagram' },
  { class: 'fab fa-twitter', 'name': 'Twitter' },
  { class: 'fab fa-pinterest-p', 'name': 'Pinterest P' },
  { class: 'fab fa-linkedin-in', 'name': 'Linkedin In' },
  { class: 'fab fa-linkedin', 'name': 'Linkedin' },
  { class: 'fab fa-youtube', 'name': 'Youtube' },
  { class: 'fab fa-tumblr-square', 'name': 'Tumblr Square' },
  { class: 'fab fa-tumblr', 'name': 'Tumblr' },
]

let template = '<div class="icon-panel row">'

icons.forEach(icon => {
  template += `<div class="col-xl-2 col-lg-3 col-md-4 col-sm-6 icon-panel__item">
    <i class="icon-panel__icon ${icon.class}"></i>
    <p>${icon.name}</p>
  </div>`
})

template += '</div>'

fs.writeFile('../html/icons.html', template, (err) => {
  if (err) {
    throw err

  }

  console.log('Icons template generated successfully!')
})

/* eslint-enable */
