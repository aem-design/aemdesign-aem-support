# Content Generator
Run `node generator.js` to parse the  `config.yml` and output AEM tags to `content/`

## Run via maven
This can also be run directly via mavens `frontend-maven-plugin` execution id. This will provide consistency by using the maven defined versions for node and yarn.

From the root of module:

`mvn clean frontend:yarn@exec-aem-generate-tags`

## Debug

To run debug generate for all content run 

```bash
 DEBUG=1 ./generate
```

## Testing

To run small subset of tests run 

```bash
 ./generate-test
```