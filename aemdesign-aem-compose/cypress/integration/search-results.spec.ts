/// <reference types="cypress" />

import mountVue from 'cypress-vue-unit-test'
import { eventBus } from '../support/vue'

import SearchResults from '@components/search-results/SearchResults.vue'

const components = {
  'search-results' : SearchResults
};

const template = `
  <div>
    <search-results v-bind="propsData" />
  </div>
`;

describe('Search Results - Declarative', function() {
  describe('Search Results - No Results', function() {
    const data = {
      propsData: {}
    }

    beforeEach(() => {
      mountVue({template, data, components})();
    });

    it('should not display anything before recieving an event', () => {
      cy.get('.search-results[vue-component="search-results"]').should('exist');
    });

    it('should display no results found when empty', () => {
      eventBus().$emit('search', 'no dice');

      cy.get('.search-results--empty').should('exist');
    });
  });


  describe('Search Results - Found Results', function() {
    const data = {
      propsData: {}
    }

    beforeEach(() => {
      mountVue({template, data, components})();

      eventBus().$emit('search', 'software');
    });


    it('should render the results list', () => {
      cy.get('.search-results__list').should('exist');
    });

    describe('Search Results - Result Item', function() {

      it('should link', () => {
        cy.get('.search-results__item').eq(0)
          .should('have.attr', 'href');
      });

      it('should have a title', () => {
        cy.get('.search-results__item').eq(0)
          .find('.search-results__item-title')
          .should('exist');
      });

      it('should have a description', () => {
        cy.get('.search-results__item').eq(0)
          .find('p.small')
          .should('exist');
      });

      it('should have an icon', () => {
        cy.get('.search-results__item').eq(0)
          .find('.icon')
          .should('exist');
      });
    });
  });

  describe('Search Results - Pagination', function() {
    const data = {
      propsData: {
        pagerMode:'infinite'
      }
    }

    beforeEach(() => {
      mountVue({template, data, components})();

      eventBus().$emit('search', 'Business');
    });

    it('should have a load more button', () => {
      cy.get('.results-list__footer .view-more').should('exist')

    });

    it('should update the title with paging', () => {
      cy.get('.results-list__header')
        .should('have.text', 'Showing 1-10 of 13 results for Business');

      cy.get('.results-list__footer .view-more').click();

      cy.get('.results-list__header')
        .should('have.text', 'Showing 1-13 of 13 results for Business');

    });

    // note: button spec is covered in SWU-941
    it('should add more to the list', () => {
      cy.get('.search-results__item')
        .should('have.length', 10);

      cy.get('.results-list__footer .view-more').click()

      // dont use alias, it is cached
      cy.get('.search-results__item')
        .should('have.length.greaterThan', 10);
    });
  });
});
