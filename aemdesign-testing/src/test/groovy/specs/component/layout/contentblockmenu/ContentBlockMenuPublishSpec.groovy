package specs.component.layout.contentblockmenu


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ContentBlockMenuPublishSpec extends ComponentSpec {

    String pathPage = "component/layout/contentblockmenu"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblockmenu1"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Menu Source: Page in #viewport.label")
    def "Functionality of Component Menu Source: Page"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlockMenu"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblockmenu1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: 'Should have first link with Section Locked'
        assert $("$selector a")[0].getAttribute("innerText") == "Section Locked"

        and: 'Should have second link with Container Unlocked'
        assert $("$selector a")[1].getAttribute("innerText") == "Container Unlocked"

        where:
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Menu Source: Page in #viewport.label")
    def "Functionality of Component Menu Source: Page Path"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlockMenu"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblockmenu2"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: 'Should have first link with Section Locked'
        assert $("$selector a")[0].getAttribute("innerText") == "Section Locked Nested"

        and: 'Should have second link with Container Unlocked'
        assert $("$selector a")[1].getAttribute("innerText") == "Container Unlocked Nested"

        where:
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Content Block Menu in Content Block in #viewport.label")
    def "Functionality of Content Block Menu in Content Block"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlockMenu"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblockmenu13"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")


        and: 'Should have sample text from the first content block title'
        assert $("$selector a")[0].getAttribute("innerText") == "Content Block Test 1"

        and: 'Should have sample text from the second content block title'
        assert $("$selector a")[1].getAttribute("innerText") == "Content Block Test 2"

        where:
        viewport << getViewPorts()
    }


}
