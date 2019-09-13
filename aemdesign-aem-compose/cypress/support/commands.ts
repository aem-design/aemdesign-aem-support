function navigateToPage(category: 'components' | 'guidelines', name: string, done: MochaDone) {
  const url = `/content/aemdesign-showcase/au/en/styleguide/${category}/${name}.html?wcmmode=disabled`

  cy.log('Navigating to', url)
  cy.visit(url)

  // Authenticate with AEM first
  if ((Cypress.env().auth || true)) {
    cy.authenticate(done)
  }
}

Cypress.Commands.add('authenticate', (done: MochaDone) => {
  // TODO: Load the username/password dynamically
  cy.get('#username').type('admin')
  cy.get('#password').type('admin')

  cy.get('#login').submit()

  return cy.wait(200).then(() => done())
})

Cypress.Commands.add('visitGuideline', (guidelineName: string, done: MochaDone) => {
  navigateToPage('guidelines', guidelineName, done)
})

Cypress.Commands.add('visitComponent', (componentName: string, done: MochaDone) => {
  navigateToPage('components', componentName, done)
})

// tslint:disable-next-line:no-namespace
declare global {
  namespace Cypress {
    // tslint:disable-next-line:interface-name
    interface Chainable<Subject> {
      authenticate(callback: MochaDone): void;
      visitGuideline(guidelineName: string, done: MochaDone): void;
      visitComponent(componentName: string, done: MochaDone): void;
      tab(componentName: string, done: MochaDone): void;
    }
  }
}

export {}
