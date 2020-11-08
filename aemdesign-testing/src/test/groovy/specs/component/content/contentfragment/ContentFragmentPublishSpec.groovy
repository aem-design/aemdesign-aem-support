package specs.component.content.contentfragment

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ContentFragmentPublishSpec extends ComponentSpec {

    String pathPage = "component/content/contentfragment"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/contentfragment"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "EmbedSource"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock1 .cmp-contentfragment"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).getAttribute("data-path") == "/content/dam/aemdesign-showcase/en/content-fragments/all-field-types"
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: 'Custom embed style should apply to element'
        assert compareInnerTextIgnoreCase("${selector} .cmp-contentfragment__element.cmp-contentfragment__element--text .cmp-contentfragment__element-label", "Text")

        where:
        viewport << viewPorts
    }


}
