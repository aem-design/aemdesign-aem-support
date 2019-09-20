/// <reference types="cypress" />

import mountVue from 'cypress-vue-unit-test'

import SocialShare from '@components/social-share/SocialShare.vue'

describe('Social Share - Declarative', function() {
  const components = {
    'social-share' : SocialShare,
  }

  let template = `
    <div>
      <social-share target="${`Your App: '${Cypress.config('projectName')}'`}" />
    </div>
  `

  beforeEach(mountVue({ components, template }))

  it('share icon visible', () => {
    cy.get('.social-share')
      .wait(300)
      .should('be.visible')

    cy.get('.social-share__icon')
      .should('be.visible')
      .screenshot()
  })

  it('popover visible after click', () => {
    cy.get('.social-share')
      .click()

    cy.get('.social-share__popover')
      .should('be.visible')
      .screenshot()
  })

  it('popover visible after click - portrait', () => {
    cy.get('.social-share')
      .click()

    cy.viewport('macbook-11', 'portrait')

    cy.get('.social-share__popover')
      .should('be.visible')
      .screenshot()
  })

  it('popover visible after click - landscape', () => {
    cy.get('.social-share')
      .click()

    cy.viewport('macbook-11', 'landscape')

    cy.get('.social-share__popover')
      .should('be.visible')
      .screenshot()
  })

  it('tabs on popover - 1 tab = close', () => {
    cy.get('.social-share')
      .click()

    cy.get('.social-share__popover')
      .focused().tab()
      .screenshot()
  })

  it('tabs on popover - 2 tab = facebook', () => {
    cy.get('.social-share')
      .click()

    cy.get('.social-share__popover')
      .focused().tab().tab()
      .screenshot()
  })

  it('tabs on popover - 3 tab = twitter', () => {
    cy.get('.social-share')
      .click()

    cy.get('.social-share__popover')
      .focused().tab().tab().tab()
      .screenshot()
  })

  it('tabs on popover - 4 tab = linkedin', () => {
    cy.get('.social-share')
      .click()

    cy.get('.social-share__popover')
      .focused().tab().tab().tab().tab()
      .screenshot()
  })

  it('tabs on popover - 5 tab = copy', () => {
    cy.get('.social-share')
      .click()

    cy.get('.social-share__popover')
      .focused().tab().tab().tab().tab().tab()
      .screenshot()
  })

  it('tabs on popover - Click on copy link', () => {
    cy.get('.social-share')
      .click()

    cy.get('.social-share__popover')
       .should('exist')

    cy.get('[data-cy=link]')
      .click()

    cy.get('[data-cy=copy]')
      .contains('Copied!')

    cy.get('.social-share__popover')
      .should('be.visible')
      .screenshot()
  })

  it('Click on the icon, Click on cross on popup', () => {
    cy.get('.social-share')
      .click()

    cy.get('.social-share__popover')
      .should('exist')

    cy.get('.social-share__close').click()
    cy.get('.social-share__popover').should('not.exist')
  })

  it('Label should be empty', () => {
    cy.get('.social-share__label').should('not.exist')
  })

  it('Click on the icon 2 times', () => {
    cy.get('.social-share')
      .click()

    cy.get('.social-share__popover')
      .should('exist')

    cy.get('.social-share')
      .click()

    cy.get('.social-share__popover')
      .should('not.exist')

    cy.get('.social-share')
      .click()

    cy.get('.social-share__popover')
      .should('exist')

    cy.get('.social-share__close')
      .click()

    cy.get('.social-share__popover')
      .should('not.exist')
  })
})
