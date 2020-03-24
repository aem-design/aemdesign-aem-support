function navigateToPage(category: 'components' | 'guidelines', name: string, done: MochaDone) {
  let PACKAGE_CONTENTFOLDER = Cypress.env("PACKAGE_CONTENTFOLDER")
  const url = `/content/${PACKAGE_CONTENTFOLDER}-showcase/au/en/styleguide/${category}/${name}.html?wcmmode=disabled`

  cy.log('Navigating to', url)
  cy.visit(url)

  // Authenticate with AEM first
  if ((Cypress.env().auth || true)) {
    cy.authenticate(done)
  }
}

Cypress.Commands.add('authenticate', (done: MochaDone) => {
  // TODO: Load the username/password dynamically
  let USERNAME = Cypress.env("CRX_USERNAME")
  let PASSWORD = Cypress.env("CRX_PASSWORD")

  cy.get('#username').type(USERNAME)
  cy.get('#password').type(PASSWORD)

  cy.get('#login').submit()

  return cy.wait(200).then(() => done())
})

Cypress.Commands.add('visitGuideline', (guidelineName: string, done: MochaDone) => {
  navigateToPage('guidelines', guidelineName, done)
})

Cypress.Commands.add('visitComponent', (componentName: string, done: MochaDone) => {
  navigateToPage('components', componentName, done)
})

declare global {
  namespace Cypress {
    interface Chainable<Subject> {
      authenticate(callback: MochaDone): void;
      visitGuideline(guidelineName: string, done: MochaDone): void;
      visitComponent(componentName: string, done: MochaDone): void;
      tab(componentName: string, done: MochaDone): void;
    }
  }
}

export {}
