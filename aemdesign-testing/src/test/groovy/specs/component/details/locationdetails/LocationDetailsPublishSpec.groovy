package specs.component.details.locationdetails


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class LocationDetailsPublishSpec extends ComponentSpec {

    String pathPage = "component/details/location-details"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/locationdetails"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Location Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#location-details1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Should have sample heading"
        assert $("$selector h1").text().trim().toLowerCase().contains("Location Detail 1".toLowerCase())

        and: "Should have data attribute latitude"
        assert $(selector).attr("data-latitude") == "10.0"

        and: "Should have data attribute latitude"
        assert $(selector).attr("data-longitude") == "12.0"

        and: "Should have data attribute latitude"
        assert $(selector).attr("data-pages") != ""

        where:
        viewport << getViewPorts()
    }



    @Unroll("Functionality of Component Variant: Hidden in #viewport.label")
    def "Functionality of Component Variant: Hidden "() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Location Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#location-details2"
        def selectorContainer = "#contentblock2 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Component should be hidden"
        assert $("${selector}[hidden]").size() == 1

        where:
        viewport << getViewPorts()
    }
}
