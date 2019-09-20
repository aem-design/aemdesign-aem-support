/// <reference types="cypress" />

import mountVue from 'cypress-vue-unit-test'

import SocialShare from '@components/social-share/SocialShare.vue'

describe('Social Share at bottom - Declarative', () => {
  const components = {
    'social-share': SocialShare,
  }

  let template = `
    <div>
      <social-share style="right:200px; left:auto; bottom:0; top:auto" class="position-absolute" />
    </div>
  `

  beforeEach(mountVue({ components, template }))

  it('appears on the page', () => {
    cy.get('.social-share')
      .wait(300)
      .should('be.visible')
      .screenshot()
  })

  it('popover visible after click', () => {
    cy.get('.social-share')
      .click()

    cy.get('.social-share__popover')
      .should('be.visible')

    cy.screenshot()
  })
})
