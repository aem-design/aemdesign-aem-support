# Content Generator
Run `node generator.js` to parse the  `config.yml` and output AEM tags to `content/`

## Run via maven
This can also be run directly via mavens `frontend-maven-plugin` execution id. This will provide consistency by using the maven defined versions for node and yarn.

From the root of dsj-prototype module:

`mvn clean frontend:yarn@exec-aem-generate-tags`
