package specs.component.content.text

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class TextPublishSpec extends ComponentSpec {

    String pathPage = "component/content/text"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/text"


    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Text"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#text1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample rich text"
        assert $(selector).children().size() == 28
        takeScreenshot($(selector).firstElement(), "Should have sample rich text")

        and: "Has sample table content"
        assert $("${selector} table").isEmpty() == false

        and: "Has sample link"
        assert $("${selector} a").isEmpty() == false

        and: "Should have simple BR element"
        assert $("$selector div br").size() > 0

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Simple in #viewport.label")
    def "Functionality of Component Variant: Simple"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Text"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#text2"
        def selectorContainer = "#contentblock2 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample rich text"
        assert $(selector).children().size() == 28
        takeScreenshot($(selector).firstElement(), "Should have sample rich text")

        and: "Has sample table content"
        assert $("${selector} table").isEmpty() == false

        and: "Has sample link"
        assert $("${selector} a").isEmpty() == false


        where:
        viewport << getViewPorts()
    }


}
