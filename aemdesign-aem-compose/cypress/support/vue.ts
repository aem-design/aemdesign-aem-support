export const eventBus = () => {
  return Cypress.vue.$children[0].eventBus
}
