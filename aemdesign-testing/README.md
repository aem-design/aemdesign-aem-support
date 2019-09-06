#AEM Design AEM Testing Framework
This is a AEM Functional Testing Framework used to test AEM Sites.
It is setup to use Firefox driver.

##Getting Started
- Install Maven [https://maven.apache.org/run-maven/index.html]
- Test if Maven installed correctly
```bash
mvn --version
```
- Install ImageMagick [http://www.imagemagick.org/script/download.php]
- Test if ImageMagick installed correctly
```bash
compare --version
```
- Install webdrivers
```bash
mvn verify -DskipTests=true
```
- Deploy related content projects from parent
```bash
mvn -PautoInstallPackage clean install -DskipTests=true
```
- Run sample test from parent
```bash
mvn -pl aemdesign-testing clean test -Dgeb.env=dev -Dtest=Text*
```
- Run full suit of test
```bash
mvn -pl aemdesign-testing clean test -Dgeb.env=dev -Dtest=Text*
```
- Pass parameters to test on other sites
From Root:
```bash
mvn -pl aemdesign-testing clean test -Dgeb.env=dev -Dcrx.host=localhost -Dcrx.port=4502 -Dcrx.user=admin -Dcrx.password=admin -Dtest=Text*
```
In Project Folder
```bash
mvn clean test -Dgeb.env=dev -Dcrx.host=localhost -Dcrx.port=4502 -Dcrx.user=admin -Dcrx.password=admin -Dtest=Text*
```
- Quick test on clean instance
Test Login Page from Root Project

```bash
mvn -pl aemdesign-testing clean test -Dgeb.env=dev -Dtest=LoginSpec
```

Test Login Page from Testing Project

```bash
mvn clean test -Dgeb.env=dev -Dtest=LoginSpec
```

Test Login Page

```bash
./testspec LoginSpec
```

##Screenshots

- Base screenshots
These screenshots will be used as a base when comparing the screen from specs
```
src/test/screenshots
```
When running a test first time a new base image will be generated, when you are happy with test output commit this image to repository.
This image then will be used by other build test processes.
All test should be done either on Showcase or respect unknown nature of target host being tested.


##Technical Info
- Clone Repo
SSH Clone
```bash
git clone git@gitlab.com:aem.design/aemdesign-testing.git
```
- Test if project is runnable:
```bas
mvn clean
```
- Run Tests:
```bash
mvn test
```

## Usage

Just check it out and run:
```bash
mvn site
```
To run all tests:
```bash
mvn test
```
To run specific tests:
```bash
mvn test -Dtest=Link* -Dtest=Text*
```
To test screenshot failure run:
```bash
mvn test -Dtest=Text* -Dfailtest=true
```
To test against and environment:
```bash
mvn -Dgeb.env=dev test
```
Sample tests:
```bash
mvn -Dgeb.env=dev clean test -Dtest=Text*
mvn -Dgeb.env=dev clean test -Dtest=Link*
```
Run All Functional Tests against a local environment
```bash
mvn clean test -Dgeb.env=dev -P functional-test
```
Run All Screenshot Tests against a local environment
```bash
mvn clean test -Dgeb.env=dev -P screenshot-test
```

## Questions and issues

Please ask questions and raise issues at [Testing Frameworks Issues](https://gitlab.com/aem.design/aemdesign-testing/issues).


## Showcase Site Information

Publish:
```text
http://localhost:4503/aemdesign-showcase/au/en.html
```

Author:
```text
http://localhost:4502/content/aemdesign-showcase/au/en.html
```
Accounts
```text
ro: showcase:showcase
rw: showcaseadmin:showcaseadmin
```

## Component Documentation

### AEM Authoring Guides

- [Authoring Pages](https://docs.adobe.com/docs/en/aem/6-0/author/page-authoring.html)
- [Authoring - the Environment and Tools](https://docs.adobe.com/docs/en/aem/6-0/author/page-authoring/author-environment-tools.html)
- [Components for Page Authoring](https://docs.adobe.com/docs/en/aem/6-0/author/page-authoring/default-components/editmode.html)

### GEB Info

- [Geb Github Projects](https://github.com/geb)
- [Geb Maven Sample](https://github.com/geb/geb-example-maven)
- [Spock and Geb in Action](http://www.slideshare.net/InfoQ/taming-functional-web-testing-with-spock-and-geb)
- [Taming Functional Web Testing with Spock and Geb](http://www.slideshare.net/InfoQ/taming-functional-web-testing-with-spock-and-geb)

# Maven Reports

- [Spock Reports Extension](https://github.com/renatoathaydes/spock-reports)
- [Surefire Reports](http://maven.apache.org/surefire/maven-surefire-report-plugin/usage.html)


# ASCII Doc Reports
Adding screenshots to Test Steps is done by detecting a text string in code block and then adding picking a screenshot to show.
Update the following line in ```spect-template.ad```:

```
    def codeCheckFileGen = /[\t ]*report|designRef|takeScreenshot.*/
```

For multi-screenshot print check following block to ensure columns are printed.

```
    if (filePathFull.endsWith(pathCheckEndImage)) {
        if (filePathFull.startsWith(pathCheckStartSource)) {
            blockScreenshots = blockScreenshots << """ .Source\n"""
        } else if (filePathFull.endsWith(pathCheckEndDiff)) {
            blockScreenshots = blockScreenshots << """ a| .Diff\n"""
        } else if (!filePathFull.startsWith(pathCheckStartSpec)) {
            if (it.files.size() > 1) {
                blockScreenshots = blockScreenshots << """ a| """
            }
            blockScreenshots = blockScreenshots << """ .Current\n"""
        }
```

If HTML report are not showing expected result check base templates in ```spock-reports/.ad``` these are converted to html.

# Driver Specific Parameters

## BrowserStack Variables

You can pass following variable via command line to specify browserstack parameters.

For more information please see following reference site [https://www.browserstack.com/automate/capabilities](https://www.browserstack.com/automate/capabilities)

| Internal Name                      | Command Line Param                 | Browserstack Variable        |
|------------------------------------|------------------------------------|------------------------------|
| GEB_BROWSERSTACK_USERNAME          | geb.browserstack.username          |                              |
| GEB_BROWSERSTACK_AUTHKEY           | geb.browserstack.authkey           |                              |
| GEB_BROWSERSTACK_SCHEMA            | geb.browserstack.schema            |                              |
| GEB_BROWSERSTACK_HOST              | geb.browserstack.host              |                              |
| GEB_BROWSERSTACK_URL               | "SCHEMA://USERNAME:AUTHKEY@HOST"   |                              |
| GEB_BROWSERSTACK_BROWSER           | geb.browserstack.browser           | browser                      |
| GEB_BROWSERSTACK_BROSWER_VERSION   | geb.browserstack.browserversion    | browser_version              |
| GEB_BROWSERSTACK_BUILD             | geb.browserstack.build             | build                        |
| GEB_BROWSERSTACK_OS                | geb.browserstack.os                | os                           |
| GEB_BROWSERSTACK_OS_VERSION        | geb.browserstack.osversion         | os_version                   |
| GEB_BROWSERSTACK_DEBUG             | geb.browserstack.debug             | browserstack.debug           |
| GEB_BROWSERSTACK_DEVICE            | geb.browserstack.device            | device                       |
| GEB_BROWSERSTACK_REALMOBILE        | geb.browserstack.device            | realMobile                   |
| GEB_BROWSERSTACK_RESOLUTION        | geb.browserstack.resolution        | resolution                   |
| GEB_BROWSERSTACK_LOCAL             | geb.browserstack.local             | browserstack.local           |
| GEB_BROWSERSTACK_LOCALID           | geb.browserstack.localIdentifier   | browserstack.localIdentifier |
| GEB_BROWSERSTACK_PROJECT           | geb.browserstack.project           | project                      |
| GEB_BROWSERSTACK_ACCESPTSSL        | geb.browserstack.acceptSslCerts    | acceptSslCerts               |
| GEB_BROWSERSTACK_PLATFORM          | geb.browserstack.platform          | platform                     |
| GEB_BROWSERSTACK_SELENIUMVERSION   | geb.browserstack.selenium_version  | selenium_version             |
| GEB_BROWSERSTACK_NAME              | geb.browserstack.name              | name                         |
| GEB_BROWSERSTACK_CONSOLE           | geb.browserstack.console           | browserstack.console         |
| GEB_BROWSERSTACK_VIDEO             | geb.browserstack.video             | browserstack.video           |
| GEB_BROWSERSTACK_NETWORKLOGS       | geb.browserstack.networkLogs       | browserstack.networkLogs     |
| GEB_BROWSERSTACK_TIMEZONE          | geb.browserstack.timezone          | browserstack.timezone        |
| GEB_BROWSERSTACK_GEOLOCATION       | geb.browserstack.geoLocation       | browserstack.geoLocation     |
| GEB_BROWSERSTACK_NETWORKSPEED      | geb.browserstack.customNetwork     | browserstack.customNetwork   |
| GEB_BROWSERSTACK_DEVICEORIENTATION | geb.browserstack.deviceOrientation | deviceOrientation            |
