/// <reference types="cypress" />

import breakpoints, { getBreakpointForKey } from '../support/breakpoints'

describe('Forms - Text Field', () => {
  before((done) => cy.visitGuideline('forms', done))

  beforeEach(() => {
    const breakpoint = getBreakpointForKey('desktop')
    cy.viewport(breakpoint.width, breakpoint.height)
  })

  it('page has loaded', () => {
    cy.location('pathname').should('contain', 'forms')
  })

  it('we can click on the text field and it is active', () => {
    cy.get('#contentblock_text_field_input').within(() => {
      cy.get('input').click().screenshot()
      cy.get('input').should('have.class', 'focused')

      cy.get('label').should('have.class', 'active')
    })
  })

  it("type the text 'Hello World!'", () => {
    const input = cy.get('#contentblock_text_field_input input')

    input.type('Hello World!')
    input.should('have.value', 'Hello World!')
    input.screenshot()
  })

  it('clear the initial value and lose focus', () => {
    const input = cy.get('#contentblock_text_field_with_value input')

    input.clear().blur()
    input.should('not.have.class', 'focused').and('have.value', '')
    input.screenshot()
  })
})

breakpoints.forEach(({ height, label, width }) => {
  describe(`Forms - Text Field at ${label}`, () => {
    beforeEach(() => cy.viewport(width, height))

    it('text field appears on the page', () => {
      const input = cy.get('#contentblock_text_field_input')

      input.should('be.visible')
      input.screenshot()
    })
  })
})
