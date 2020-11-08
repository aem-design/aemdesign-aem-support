package specs.component.analytics.datalayer

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class DataLayerPublishSpec extends ComponentSpec {

    String pathPage = "component/analytics/datalayer"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/page-details"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Data Layer in #viewport.label")
    def "Functionality of Data Layer"() {

        given: '>the page hierarchy is created as "Components" > "Analytics" > "Data Layer"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagedetails1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The page should have digitalData object defined'
        assert js.exec("return window.digitalData != undefined;")

        then: 'The digitalData.page.pageInfo.pageType should equals "common"'
        assert js.exec("return window.digitalData.page.pageInfo.pageType == 'common';")

        then: 'The digitalData.page.pageInfo.contentLanguage should equals "Australia"'
        assert js.exec("return window.digitalData.page.pageInfo.contentLanguage == 'english';")

        then: 'The digitalData.page.pageInfo.contentCountry should equals "english"'
        assert js.exec("return window.digitalData.page.pageInfo.contentCountry == 'Australia';")

        then: 'The digitalData.page.pageInfo.pageName should equals "aemdesign-showcase:en:component:analytics:datalayer"'
        assert js.exec("return window.digitalData.page.pageInfo.pageName == 'aemdesign-showcase:en:component:analytics:datalayer';")

        then: 'The digitalData.page.pageInfo.effectiveDate should equals ""'
        assert js.exec("return window.digitalData.page.pageInfo.effectiveDate == '';")


        then: 'The digitalData.page.pageInfo.referringURL should equal ""'
        assert js.exec("return window.digitalData.page.pageInfo.referringURL == '';")

        then: 'The digitalData.page.pageInfo.destinationUrl should end with "datalayer.html"'
        assert js.exec("return window.digitalData.page.pageInfo.destinationUrl.endsWith(\"datalayer.html\");")

        then: 'The digitalData.page.attributes.abort should equals "false"'
        assert js.exec("return window.digitalData.page.pageInfo.abort == 'false';")

        then: 'The digitalData.page.attributes.platform should equals "aem"'
        assert js.exec("return window.digitalData.page.pageInfo.platform == 'aem';")

//         then: 'The digitalData.page.attributes.breakPoint should equals "#viewport.label"'
//         assert js.exec("return window.digitalData.page.attributes.breakPoint;").toString() == viewport.label

        then: 'The digitalData.page.pageInfo.sections should be set'
        assert js.exec("return window.digitalData.page.pageInfo.sections !== \"\";")

        where:
        viewport << viewPorts
    }

}
