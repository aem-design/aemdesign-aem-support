/// <reference types="cypress" />

import breakpoints, { getBreakpointForKey } from '../support/breakpoints'

describe('Forms - Drop-down', () => {
  before((done) => cy.visitGuideline('forms', done))

  beforeEach(() => {
    const breakpoint = getBreakpointForKey('desktop')
    cy.viewport(breakpoint.width, breakpoint.height)

    cy.get('#contentblock_dropdown_field_input').as('dropDown')
    cy.get('@dropDown').find('.drop-down__wrapper').as('dropDownWrapper')
    cy.get('@dropDown').find('.drop-down__options').as('dropDownOptions')

    cy.get('#contentblock_dropdown_field_with_selection_input').as('dropDownWithSelection')
    cy.get('@dropDownWithSelection').find('.drop-down__wrapper').as('dropDownWithSelectionWrapper')
  })

  afterEach(() => {
    cy.get('body').click()
    cy.get('@dropDown').click()
    cy.get('@dropDownOptions').find('li').eq(0).click()

    cy.get('@dropDownWithSelection').click()
    cy.get('@dropDownWithSelectionWrapper').find('li').eq(1).click();

    cy.get('body').click()
  });

  it('page has loaded', () => {
    cy.location('pathname').should('contain', 'forms')
  })

  it('we can click on the drop-down and it is active', () => {
    cy.get('@dropDown').click()

    cy.get('@dropDownWrapper').should('have.class', 'drop-down__wrapper--open')
    cy.get('@dropDownOptions').should('have.class', 'drop-down__options--open')

    cy.get('@dropDown').screenshot()
    cy.get('@dropDownOptions').screenshot()
  })

  it('we can click on the drop-down and it is not active', () => {
    cy.get('@dropDown').click()
    // must be open before we can close
    cy.get('@dropDown').click()

    cy.get('@dropDownWrapper').should('not.have.class', 'drop-down__wrapper--open')
    cy.get('@dropDownOptions').should('not.have.class', 'drop-down__options--open')

    cy.get('@dropDown').screenshot()
  })

  it('select the first option', () => {
    cy.get('@dropDown').click()

    cy.get('@dropDownOptions').find('li').eq(1)
      .click()
      .should('have.attr', 'aria-selected', 'true')

    cy.get('@dropDown').screenshot()
  })

  it('has screen-reader text for the active item', () => {
    const selectedIndex = 2;

    cy.get('@dropDown').click()

    cy.get('@dropDownOptions').find('li').eq(selectedIndex)
      .click();

    cy.get('@dropDownOptions').find('.js-drop-down__aria--selected')
      .should('have.length', 1)

    // active should have a11y text
    cy.get('@dropDownOptions').find('li').eq(selectedIndex)
      .find('.js-drop-down__aria--selected')
      .should('exist')
  });

  it('has screen-reader text for the active item when preselected', () => {
    cy.get('@dropDownWithSelection').click()

    cy.get('@dropDownWithSelection').find('.js-drop-down__aria--selected')
      .should('have.length', 1)

    cy.get('@dropDownWithSelection').find('li[aria-selected="true"]')
      .find('.js-drop-down__aria--selected')
      .should('exist')
  });

  it('select the second option using the arrow down key', () => {
    cy.get('@dropDownWithSelectionWrapper').focus()

    // down on focused input to activate dropdown
    cy.focused().type('{downarrow}')

    // already focused on selected, move down once to select next
    cy.focused().type('{downarrow}')
    cy.focused().type('{enter}')

    cy.get('@dropDownWithSelectionWrapper').find('li').eq(2)
      .should('have.attr', 'aria-selected', 'true')

    cy.get('@dropDownWithSelection').screenshot()
  })

  it('select the fourth second option using the arrow down key', () => {
    cy.get('@dropDownWithSelectionWrapper').focus()

    // down on focused input to activate dropdown
    cy.focused().type('{downarrow}')

    cy.focused().type('{downarrow}')
    cy.focused().type('{downarrow}')
    cy.focused().type('{downarrow}')
    cy.focused().type('{enter}')

    cy.get('@dropDownWithSelectionWrapper').find('li').eq(4)
      .should('have.attr', 'aria-selected', 'true')

    cy.get('@dropDownWithSelection').screenshot()
  })

  it('select the third option using the arrow down and up keys', () => {
    cy.get('@dropDownWithSelectionWrapper').focus()

    // down on focused input to activate dropdown
    cy.focused().type('{downarrow}')

    cy.focused().type('{downarrow}')
    cy.focused().type('{downarrow}')
    // go passed to prove 'up' works
    cy.focused().type('{downarrow}')
    cy.focused().type('{uparrow}')
    cy.focused().type('{enter}')

    cy.get('@dropDownWithSelectionWrapper').find('li').eq(3)
      .should('have.attr', 'aria-selected', 'true')

    cy.get('@dropDownWithSelection').screenshot()
  })

  it('maintains active state when closing', () => {
    const selectionIndex = 5;

    cy.get('@dropDown').click()

    cy.get('@dropDownOptions').find('li').eq(selectionIndex)
      .click()
      .should('have.attr', 'aria-selected', 'true')

    cy.get('@dropDown').click()

    // active state from above should be maintained
    cy.get('@dropDownOptions').find('li').eq(selectionIndex)
      .should('have.attr', 'aria-selected', 'true')

    cy.get('@dropDown').screenshot()
    cy.get('@dropDownOptions').screenshot()
  });
})

breakpoints.forEach((size) => {
  describe(`Forms - Drop-down at ${size.label}`, () => {
    beforeEach(() => {
      cy.viewport(size.width, size.height)

      cy.get('#contentblock_dropdown_field_input').as('dropDown')
    })

    it('drop-down appears on the page', () => {
      cy.get('@dropDown').should('be.visible').screenshot()
    })
  })
})
