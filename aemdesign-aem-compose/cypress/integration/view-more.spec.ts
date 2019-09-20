/// <reference types="cypress" />

import mountVue from 'cypress-vue-unit-test'

import ViewMore from '@components/view-more/ViewMore.vue'

describe('View More - Declarative', function() {

  beforeEach(mountVue(ViewMore))

  it('view more visible', () => {
    const viewMore = cy.get('.view-more')
    viewMore.wait(300).should('be.visible').screenshot()
  })

  it('view more - click = disabled', () => {
    const viewMore = cy.get('.view-more')
    viewMore.click().should('be.disabled').screenshot()
  })

  it('view more - no more results = hidden', () => {
    const viewMore = cy.get('.view-more')
    Cypress.vue.noMoreResults = true
    viewMore.should('be.not.visible')
  })

  it('view more - click - click = searching called twice', () => {
    const viewMore = cy.get('.view-more')
    const spy = cy.spy().as('searching')

    Cypress.vue.$on('searching', spy)

    viewMore.click().then(() => { Cypress.vue.disabled = false })
            .click().then(() => {
              expect(spy).to.have.been.calledWith(true);
              expect(spy).to.have.been.calledTwice;
            })
  })
})
