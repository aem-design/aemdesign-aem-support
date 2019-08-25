[![Apache V2 License](http://img.shields.io/badge/license-Apache%20V2-blue.svg)](https://github.com/Comcast/patternlab-edition-node-webpack/blob/master/LICENSE)

## AEM Design Prototype
The prototype project is designed as the base for all AEM Design components, functionality, and behaviour. It encapsulates static assets, JavaScript, CSS and a styleguide project within that controls the DLS (Design Language System).

## Installation
Getting going is pretty simple, you will, however, need to make sure you have the below installed first to ensure a smooth and consistent experience with other developers.

- **Node >= 8 <= 10** _(required)_
- **Yarn >= 1.10.0** _(version required for hash compatibility)_
- **TypeScript enabled IDE** _(VS Code or an IDE plugin)_
- **ESLint** _(required)_
- **Stylelint** _(required)_
- **EditorConfig** _(required)_
- _An AEM instance that has project installed_ **(required)**

### NPM Packages
Once you have met the requirements above, install the npm packages by simply running:

`yarn`

### Sync AEM with Your Local
Before you can start working on prototype, you need to sync the `clientibs` structure from AEM which we need to do VLT syncing. This syncing allows us to push smaller updates into AEM without requiring complete deployments which can take a while. This process only really needs to be done once but if you experience a `NULL` pointer error you will need to run the same command again.

Run the following to begin the sync: `yarn aem:checkout`

## Deploy Code into AEM
When you have VLT synced locally you can now run the command needed for the project you're working on.

>**NOTE:** Using any of the `yarn sync:*` commands results in a delay of ~6-8 seconds depending on the project.
#### Core
- Build directly into AEM: `yarn build:core`
- Watch for changes and sync into AEM: `yarn dev:core`

##### Core (HMR)
You may also use HMR via `webpack-dev-server` which proxies to your local AEM instance and provide real-time change support unlike the manual syncing approach. To start this process run `yarn serve:sut` and then visit http://localhost:4504 in your browser.

#### DLS (Styleguide)
- Build directly into AEM: `yarn build:sg`
- Watch for changes and sync into AEM: `yarn dev:sg`

The styleguide is its own project to remove any duplicate/irrelevant code from the final website builds. This allows us to have specific deployments for DLS while maintaining a clean codebase for other projects.

## Linting
All code in the project is linted both in your IDE (where supported) and during compilation. This is to ensure that bugs and issues can be fixed before going out and ensures consistency between developers. ESLint is used across all projects while TSLint is only used on TypeScript projects.

## Testing
Cypress will be used for all front end testing.
