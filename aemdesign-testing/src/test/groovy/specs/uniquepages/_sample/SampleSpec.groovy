package specs.uniquepages._sample

import geb.spock.GebReportingSpec
import spock.lang.IgnoreIf
import spock.lang.Stepwise

@Stepwise
@IgnoreIf({ System.properties.getProperty("geb.env") != "sample" })
class SampleSpec extends GebReportingSpec {

    static url = "https://google.com"

    static at = {
        waitFor(30) { $("body") }
    }



    def "Google is online"() {
        given: "we are on google page"
        def selector = "body"

        expect: "body element should not be empty"
        assert !$(selector).isEmpty()
    }

}
