/// <reference types="cypress" />

import mountVue from 'cypress-vue-unit-test'

import SiteSearch from '@components/site-search/SiteSearch.vue'

describe('Site Search - Declarative', function() {
  const components = {
    'site-search' : SiteSearch,
  }

  let template = `<site-search logo="https://placehold.it/50" target="${`Your App: '${Cypress.config('projectName')}'`}" />`

  before(mountVue({ components, template }))

  it('site-search button visible', () => {
    cy.get('.site-search')
      .should('be.visible')

    cy.get('.site-search__open')
      .wait(300)
      .should('be.visible')
      .screenshot()
  })

  it('modal appears on the page', () => {
    cy.get('.site-search__open')
      .click({ force: true })

    cy.get('.site-search__modal')
      .wait(300)
      .should('be.visible')
      .screenshot()
  })

  it('modal close button has focus', () => {
    cy.get('.site-search__close')
      .should('be.focused')
  })

  it('modal closes', () => {
    cy.get('.site-search__close')
      .click({ force: true })

    cy.get('.site-search__modal')
      .wait(300)
      .should('be.hidden')
  })

  it('modal open button has focus after close', () => {
    cy.get('.site-search__open')
      .should('be.focused')
  })
})
