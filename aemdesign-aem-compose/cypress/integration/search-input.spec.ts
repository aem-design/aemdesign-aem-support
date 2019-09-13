/// <reference types="cypress" />

import mountVue from 'cypress-vue-unit-test'

import SearchInput from '@components/search-input/SearchInput.vue'

import { eventBus } from '../support/vue'

describe('Search Input - Declarative', function() {
  const components = {
    'search-input' : SearchInput,
  }

  let template = `
    <div>
      <search-input target="${`Your App: '${Cypress.config('projectName')}'`}" />
    </div>
  `

  beforeEach(mountVue({ components, template }))

  it('search input visible', () => {
    const searchInput = cy.get('.search-input')

    searchInput.wait(300).should('be.visible').screenshot()
  })

  it("type the text 'Hello World!'", () => {
    const input = cy.get('.search-input input')
    const spy = cy.spy()

    eventBus().$on('search', spy)

    input.click().type('Hello World!')

    const searchInput = cy.get('.search-input')

    searchInput.submit().submit().then(() => {
      expect(spy).to.be.calledTwice
    })

    input.screenshot()
  })

  it('focus on input - tab - click', () => {
    const input = cy.get('.search-input input')

    input.click().tab().click()

    const body = cy.get('body')

    body.screenshot()
  })

  it('focus on input - tab - tab - click', () => {
    const input = cy.get('.search-input input')

    input.click()
         .tab()
         .tab()
         .click()

    const body = cy.get('body')

    body.screenshot()
  })

  it('focus on input - enter', () => {
    const input = cy.get('.search-input input')
    input.click().type('{enter}')

    const error = cy.get('.search-input .input-group-append')
    error.should('be.visible')

    const body = cy.get('body')
    body.screenshot()
  })

  it('focus on input - click outside of the component', () => {
    const input = cy.get('.search-input input')
    input.click()

    const body = cy.get('body')
    body.click()
        .screenshot()
  })
})
