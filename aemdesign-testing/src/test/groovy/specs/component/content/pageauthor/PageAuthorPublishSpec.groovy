package specs.component.content.pageauthor

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class PageAuthorPublishSpec extends ComponentSpec {

    String pathPage = "component/content/pageauthor"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/pageauthor"


    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Page Author"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pageauthor1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample text"
        assert $(selector).text().trim().startsWith("Administrator")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Simple in #viewport.label")
    def "Functionality of Component Variant: Simple"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Page Author"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pageauthor2"
        def selectorContainer = "#contentblock2 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample text"
        assert $(selector).text().trim().startsWith("Administrator")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Default with Override in #viewport.label")
    def "Functionality of Component Variant: Default with Override"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Page Author"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pageauthor3"
        def selectorContainer = "#contentblock3 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample text"
        assert $(selector).text().trim().startsWith("Author Full Name")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Variant: Simple with Override in #viewport.label")
    def "Functionality of Component Variant: Simple with Override"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Page Author"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pageauthor4"
        def selectorContainer = "#contentblock4 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample text"
        assert $(selector).text().trim().startsWith("Author Full Name")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << getViewPorts()
    }


}
