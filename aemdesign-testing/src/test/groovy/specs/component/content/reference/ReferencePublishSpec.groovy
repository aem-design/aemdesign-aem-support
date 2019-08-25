package specs.component.content.reference

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ReferencePublishSpec extends ComponentSpec {

    String pathPage = "component/content/reference"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/reference"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Reference"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#page1-page-details"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        $("${selector}").text().contains("Content Reference")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << getViewPorts()
    }


}
