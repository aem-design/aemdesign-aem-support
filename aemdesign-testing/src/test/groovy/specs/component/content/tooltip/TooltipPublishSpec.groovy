package specs.component.content.tooltip

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class TooltipPublishSpec extends ComponentSpec {

    String pathPage = "component/content/tooltip"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/tooltip"


    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Text"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#tooltip1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
//        takeScreenshot($(component).firstElement(), "The component should be on the page")

        and: "Has sample title"
        assert $("${selector}").attr("data-title").equals("Tool Tip Text")

        and: "Has sample description"
        assert $("${selector}").attr("data-description").equals("Tool Tip Description")

        where:
        viewport << viewPorts
    }



}
