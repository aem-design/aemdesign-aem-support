package specs.component.content.external


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ExternalPublishSpec extends ComponentSpec {

    String pathPage = "component/content/external"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/external"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: iFrame with Parameters in #viewport.label")
    def "Functionality of Component Variant: iFrame with Parameters"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "External"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        and: '>"Width" is set to 600'
        and: '>"Height" is set to 900'
        and: '>"No" is selected in "Show Scrollbar" selection'
        def selector = "#external1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Its external content is loaded"
        waitFor { withFrame("external1") {  $("#external-page1-text") } }

        and: "Should have sample content in iFrame 600x900"
        withFrame("external1") { browser.page.find("#external-page1-text").text().trim().toLowerCase() == "External Page Content 1".toLowerCase() }
        takeScreenshot($(selector).firstElement(), "Should have sample content in iFrame 600x900")

        and: 'I should be able to see the width of the iframe is set to 600'
        assert $(selector).attr("width") == "600"

        and: 'I should be able to see the height of the iframe is set to 900'
        assert $(selector).attr("height") == "900"

        and: 'I should not see the horizontal and vertical scroll bar'
        assert $(selector).attr("scrolling") == "false"

        where:
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Variant: iFrame without Parameters in #viewport.label")
    def "Functionality of Component Variant: iFrame without Parameters"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "External"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        and: '>"Width" is leave blank'
        and: '>"Height" is leave blank'
        and: '>"Show Scrollbar" selection leave as default (default = "Yes")'
        def selector = "#external2"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Its external content is loaded"
        waitFor { withFrame("external2") { "#external-page1-text" } }

        and: "Should have sample content in iFrame with scrollbar"
        withFrame("external2") { browser.page.find("#external-page1-text").text().trim().toLowerCase() == "External Page Content 1".toLowerCase() }
        takeScreenshot($(selector).firstElement(), "Should have sample content in iFrame with scrollbar")

        and: 'I should see the horizontal and vertical scrollbar'
        $(selector).attr("scrolling") == "yes"

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: SSI Include in #viewport.label")
    def "Functionality of Component Variant: SSI Include"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "External"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#external3"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have SSI Include tag"
        assert $(selector).getAttribute("innerHTML").contains("#include")

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Server Import in #viewport.label")
    def "Functionality of Component Variant: Server Import"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "External"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#external4"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have imported content"
        assert $(selector).text().trim().contains("Adobe")
        takeScreenshot($(selector).firstElement(), "Should have imported content")

        where:
        viewport << getViewPorts()
    }


}
