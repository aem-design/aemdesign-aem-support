package specs.component.layout.header

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class HeaderPublishSpec extends ComponentSpec {

    String pathPage = "component/layout/header"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/header"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component in #viewport.label")
    def "Functionality of Component"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "Header"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#plainheader"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").text().trim() == "Header Content"
        report("Should have sample rich text")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component with Background in #viewport.label")
    def "Functionality of Component with Background"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "Header"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#headerwithbackground"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        report("I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").text().trim() == "Header Content with Background"

        and: 'Section should have a background image'
        assert $(selector).css("background-image").contains(".png")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


}
