package specs.component.forms.options

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class OptionsPublishSpec extends ComponentSpec {

    String pathPage = "component/forms/options"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/options"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Options"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock1 .form-options-checkbox"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} #options1").size() == 2
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Radio Buttons in #viewport.label")
    def "Functionality of Component Variant: Radio Buttons"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Options"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock2 .form-options-radio"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} #options2").size() == 2
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Variant: Dropdown in #viewport.label")
    def "Functionality of Component Variant: Dropdown"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Options"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock3 .form-options-drop-down"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} [name=options3] option").size() == 2
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Variant: Multi Dropdown in #viewport.label")
    def "Functionality of Component Variant: Multi Dropdown"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Options"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock4 .form-options-multi-drop-down"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} [name=options4] option").size() == 2
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << getViewPorts()
    }


}
