package specs.component.layout.aside

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class AsidePublishSpec extends ComponentSpec {

    String pathPage = "component/layout/aside"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/aside"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component in #viewport.label")
    def "Functionality of Component"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "Aside"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#plainaside"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").text().trim() == "Aside Content"
        takeScreenshot($(selector).firstElement(), "Should have sample rich text")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component with Background in #viewport.label")
    def "Functionality of Component with Background"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "Aside"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#asidewithbackground"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        takeScreenshot($(selector).firstElement(), "I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").text().trim() == "Aside Content with Background"

        and: 'Section should have a background image'
        assert $(selector).css("background-image").contains(".png")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


}
