package specs.component.common.timing

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class TimingPublishSpec extends ComponentSpec {

    String pathPage = "component/common/timing"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/timing"
    String pageSelectors = ""

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Common" > "Timing"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#timing"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The page should have a Component Timing link"
        assert $("$selector a").text().trim().equals("Component Timing")
        takeScreenshot($(selector).firstElement(), "The page should have a Component Timing link")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

}
