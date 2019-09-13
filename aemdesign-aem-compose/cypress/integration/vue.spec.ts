/// <reference types="cypress" />

import mountVue from 'cypress-vue-unit-test'

describe('Vue - Declarative', () => {
  const template = `
    <div id="app">
      {{ message }}
    </div>
  `

  const data = {
    message: 'Hello Vue!',
  }

  beforeEach(mountVue({ template, data }))

  it('shows hello', () => {
    cy.contains('Hello Vue!')
  })

  it('changes message if data changes', () => {
    Cypress.vue.message = 'Vue rocks!'
    cy.contains('Vue rocks!')
  })
})
