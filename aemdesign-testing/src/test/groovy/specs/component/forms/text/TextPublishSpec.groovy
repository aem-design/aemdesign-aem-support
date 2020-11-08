package specs.component.forms.text

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class TextPublishSpec extends ComponentSpec {

    String pathPage = "component/forms/text"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/text"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Text"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock1 .content"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert compareInnerTextIgnoreCase("${selector} label","Text")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Textarea in #viewport.label")
    def "Functionality of Component Variant: Textarea"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Text"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock2 .content"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert compareInnerTextIgnoreCase("${selector} label","Textarea")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Email in #viewport.label")
    def "Functionality of Component Variant: Email"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Text"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock3 .content"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert compareInnerTextIgnoreCase("${selector} label","Email")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Telephone in #viewport.label")
    def "Functionality of Component Variant: Telephone"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Text"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock4 .content"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert compareInnerTextIgnoreCase("${selector} label","Telephone")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << viewPorts
    }


    @Unroll("Functionality of Component Variant: Date in #viewport.label")
    def "Functionality of Component Variant: Date"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Text"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock5 .content"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert compareInnerTextIgnoreCase("${selector} label","Date")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << viewPorts
    }



    @Unroll("Functionality of Component Variant: Number in #viewport.label")
    def "Functionality of Component Variant: Number"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Text"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock6 .content"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert compareInnerTextIgnoreCase("${selector} label","Number")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << viewPorts
    }


    @Unroll("Functionality of Component Variant: Password in #viewport.label")
    def "Functionality of Component Variant: Password"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Text"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock7 .content"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert compareInnerTextIgnoreCase("${selector} label","Password")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where:
        viewport << viewPorts
    }


}
