/// <reference types="cypress" />

import mountVue from 'cypress-vue-unit-test'

import SocialShare from '@components/social-share/SocialShare.vue'

describe('Social Share (without label) - Declarative', () => {
  const components = {
    'social-share': SocialShare,
  }

  let template = `
    <div>
      <social-share />
    </div>
  `

  beforeEach(mountVue({ components, template }))

  it('Appears on the page', () => {
    cy.get('.social-share')
      .wait(300)
      .should('be.visible')
      .screenshot()
  })

  it('Label should be empty', () => {
    cy.get('.social-share__label')
      .should('not.exist')
  })
})
