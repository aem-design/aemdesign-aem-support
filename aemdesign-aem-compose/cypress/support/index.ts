import 'cypress-plugin-tab'

import './commands'

// Catch all app errors and hide them as they break load events from finishing
Cypress.on('uncaught:exception', () => false)
