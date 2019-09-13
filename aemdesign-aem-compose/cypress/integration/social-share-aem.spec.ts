/// <reference types="cypress" />

describe('Social Share - AEM', () => {
  beforeEach((done) => cy.visitComponent('widgets', done))

  it('page has loaded', () => {
    cy.location('pathname').should('contain', 'widgets')
  })

  it('appears on the page', () => {
    cy.get('.social-share').wait(300).should('be.visible')
      .screenshot()
  })

  it('popup appears on the page', () => {
    cy.get('.social-share__icon').click({ force: true })

    cy.get('.social-share__popover').should('be.visible')
      .screenshot()
  })
})
