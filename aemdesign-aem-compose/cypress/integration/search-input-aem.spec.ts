/// <reference types="cypress" />

describe('Search Input - AEM', () => {
  beforeEach((done) => cy.visitComponent('widgets', done))

  it('page has loaded', () => {
    cy.location('pathname')
      .should('contain', 'widgets')
  })

  it('appears on the page', () => {
    cy.get('.search-input')
      .should('be.visible')
      .screenshot()
  })

  it('click on the search input', () => {
    cy.get('.search-input input')
      .click({ force: true })

    cy.get('.search-input .search-input__popover')
      .should('be.visible')

    cy.get('.search-input').screenshot()
  })
})
