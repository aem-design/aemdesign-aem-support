package specs.component.layout.contentblock


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ContentBlockComponentConfigPublishSpec extends ComponentSpec {

    String pathPage = "component/layout/contentblock/contentblock-showconfig"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1"
    String pageSelectors = ""

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Component Config showing config for first element in Container in #viewport.label")
    def "Functionality of Component Variant: Component Config showing config for first element in Container"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlock"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock13"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have a sample list component"
        assert $("${selector} #pagelist13").size() == 1

        and: "Should have a Component Config table"
        assert $("${selector} .firstComponentConfig table").size() == 1
        takeScreenshot($("${selector} .firstComponentConfig").firstElement(), "I am on the component showcase page")

        where:
        viewport << getViewPorts()
    }

}
