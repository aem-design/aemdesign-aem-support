/// <reference types="cypress" />

describe('Site Search - AEM', () => {
  beforeEach((done) => cy.visitComponent('widgets', done))

  it('page has loaded', () => {
    cy.location('pathname').should('contain', 'widgets')
  })

  it('appears on the page', () => {
    cy.get('.site-search')
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
    cy.get('.site-search__open')
      .click({ force: true })

    cy.get('.site-search__modal')
      .wait(300)
      .should('be.visible')

    cy.get('.site-search__close')
      .should('be.focused')
  })

  it('modal closes', () => {
    cy.get('.site-search__open')
      .click({ force: true })

    cy.get('.site-search__modal')
      .wait(300)
      .should('be.visible')

    cy.get('.site-search__close')
      .should('be.focused')
      .click({ force: true })

    cy.get('.site-search__modal')
      .wait(300)
      .should('be.hidden')
  })

  it('modal open button has focus after close', () => {
    cy.get('.site-search__open')
      .click({ force: true })

    cy.get('.site-search__modal')
      .wait(300)
      .should('be.visible')

    cy.get('.site-search__close')
      .should('be.focused')
      .click({ force: true })

    cy.get('.site-search__modal')
      .wait(300)
      .should('be.hidden')

    cy.get('.site-search__open')
      .should('be.focused')
  })
})
