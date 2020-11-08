[![Apache-2.0 License](https://img.shields.io/github/license/aem-design/aemdesign-aem-support)](https://github.com/aem-design/aemdesign-aem-support/tree/master/aemdesign-aem-compose)

# AEM.Design Compose

The compose project is designed as the base for all AEM Design components, functionality, and behaviour. It encapsulates static assets, JavaScript, CSS and a styleguide project within that controls the DLS (Design Language System).

## Table of Contents

- [Features](#features)
- [Installation](#installation)
  - [NPM Packages](#npm-packages)
- [Deploy Code into AEM](#deploy-code-into-aem)
  - [Hot Module Reloading](#hot-module-reloading)
    - [Core](#core)
    - [DLS (Styleguide)](#dls-styleguide)
- [Linting](#linting)
- [Testing](#testing)

## Features

- Content generator - _for generating content for AEM using YAML_
- ITCSS Sass structure
- PostCSS
- TypeScript driven JavaScript
- Vue.js components as widgets
- Tests using Jest

## Installation

Getting going is pretty simple, you will, however, need to make sure you have the below installed first to ensure a smooth and consistent experience with other developers.

- **Node >= 12** _(required)_
- **Yarn >= 1.10.0** _(version required for hash compatibility)_
- **TypeScript enabled IDE** _(VS Code or an IDE plugin)_
- **ESLint** _(required)_
- **Stylelint** _(required)_
- **EditorConfig** _(required)_
- **Prettier** _(required)_
- _An AEM instance that has project installed_ **(required)**

### NPM Packages

Once you have met the requirements above, install the npm packages by simply running:

`yarn`

## Deploy Code into AEM

Run `./deploy-local` and the Compose module will installed into your local AEM instance. If you like sourcemaps and debugging you can run `./deploy-local-dev` which is a non-production build.

### Hot Module Reloading

All local development uses `webpack-dev-server` which proxies to your local AEM instance and provides real time updates without needing to constantly deploy manually. The beneift to manual deployments is that we get:

- HMR support
- Real time change support
- Proper debugging via an IDE
- True sourcemaps

Visit http://localhost:4504 in your browser once `webpack-dev-server` has started.

**NOTE:** _You can only run one project at a time!_

#### Core

`yarn serve:core`

#### DLS

The DLS is its own project to remove any duplicate/irrelevant code from the final website builds. This allows us to have specific deployments for DLS while maintaining a clean codebase for other projects.

`yarn serve:dls`

## Linting

All code in the project is linted both in your IDE (where supported) and during compilation. This is to ensure that bugs and issues can be fixed before going out and ensures consistency between developers.

ESLint and Stylelint are used across all projects in conjuction with Prettier to ensure code is always written the same way by everyone. Both projects can be linted separately via the below commands.

```sh
yarn lint:core
yarn lint:dls
```

## Testing

All tests are conducted using Jest OOTB with the ability to use Playwright for e2e testing in the `aemdesign-testing` project. The aim of this structure is to ensure the codebase is concise and consistent and easy to follow.

Running the tests is as simple as: `yarn test`.
