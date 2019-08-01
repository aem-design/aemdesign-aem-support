import support.annotation.DomainNameTest
import support.annotation.ProductionDataTest
import support.annotation.NotLocal

runner {
    def includes = [], excludes = []

    // Exclude Specs that cannot be run locally
    def env = System.properties.getProperty("geb.env")
    if (env == null || env == "local") {
        excludes.push(NotLocal)
    }

    // If specified, excludes specs that uses domain name for websites.
    // Use this if the stack does not have dedicated domain name for testing.
    if (System.properties.containsKey("skipDomainNameTests")) {
        excludes.push(DomainNameTest)
    }

    // If specified, run ProductionDataTest specs which test production data export/import
    if (System.properties.containsKey("productionDataTests")) {
        includes.push(ProductionDataTest)
    } else {
        excludes.push(ProductionDataTest)
    }

    if (includes) {
        include((Class<?>[])includes.toArray())
    }
    if (excludes) {
        exclude((Class<?>[])excludes.toArray())
    }
}
