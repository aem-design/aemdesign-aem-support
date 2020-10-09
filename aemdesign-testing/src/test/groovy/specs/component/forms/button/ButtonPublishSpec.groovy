package specs.component.forms.button

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ButtonPublishSpec extends ComponentSpec {

    String pathPage = "component/forms/button"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/button"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Button"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#button1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert compareInnerTextIgnoreCase("${selector}","Submit")
        takeScreenshot($(selector).firstElement(), "Should have sample content Submit")

        where:
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Button in #viewport.label")
    def "Functionality of Component Variant: Button"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Button"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#button2"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert compareInnerTextIgnoreCase("${selector}","Button")
        takeScreenshot($(selector).firstElement(), "Should have sample content Button")

        where:
        viewport << viewPorts
    }


}
