/// <reference types="cypress" />

import mountVue from 'cypress-vue-unit-test'

import SocialShare from '@components/social-share/SocialShare.vue'

describe('Social Share (with label) - Declarative', () => {
  const components = {
    'social-share': SocialShare,
  }

  let template = `
    <div>
      <icon-helper />
      <social-share label="Share Video" target="${`Your App: '${Cypress.config('projectName')}'`}" />
    </div>
  `

  beforeEach(mountVue({ components, template }))

  it('appears on the page', () => {
    cy.get('.social-share')
      .wait(300)
      .should('be.visible')
      .screenshot()
  })

  it('Show the label Share Video', () => {
    cy.get('.social-share__label')
      .should('exist')

    cy.contains('Share Video')

    cy.get('.social-share')
      .should('be.visible')
      .screenshot()
  })
})
