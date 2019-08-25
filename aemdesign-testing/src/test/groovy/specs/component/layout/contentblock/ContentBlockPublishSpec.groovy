package specs.component.layout.contentblock


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ContentBlockPublishSpec extends ComponentSpec {

    String pathPage = "component/layout/contentblock"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlock"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        takeScreenshot($(selector).firstElement(), "I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").text().trim() == "Variant: Default"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Variant: Default Container in #viewport.label")
    def "Functionality of Component Variant: Default Container"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlock"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock2"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        report("I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").text().trim() == "Variant: Default Container"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Float Section in #viewport.label")
    def "Functionality of Component Variant: Float Section"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlock"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock3"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        report("I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").text().trim() == "Variant: Float Section"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Description List Section in #viewport.label")
    def "Functionality of Component Variant: Description List Section"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlock"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock4"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        report("I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").text().trim() == "Variant: Description List Section"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Field Set Section in #viewport.label")
    def "Functionality of Component Variant: Field Set Section"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlock"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock5"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        report("I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample title text'
        assert $(selector + " .legend").text().trim() == "Variant: Field Set Section"

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").text().trim() == "Variant: Field Set Section"

        where:
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Variant: Advanced Plain Section in #viewport.label")
    def "Functionality of Component Variant: Advanced Plain Section"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlock"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock6"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        report("I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").text().trim() == "Variant: Advanced Plain Section"

        where:
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Variant: Advanced Section with Links in #viewport.label")
    def "Functionality of Component Variant: Advanced Section with Links"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlock"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock7"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        report("I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").text().trim() == "Variant: Advanced Section with Links"

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Section with Background in #viewport.label")
    def "Functionality of Component Variant: Section with Background"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlock"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock8"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        report("I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").text().trim() == "Variant: Section with Background"

        and: 'Section should have a background image'
        assert $(selector).css("background-image").contains(".png")

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Section with Background Video in #viewport.label")
    def "Functionality of Component Variant: Section with Background Video"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlock"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock9"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        takeScreenshot($(selector).firstElement(), "I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").size() > 0

        and: 'Section should have a background image'
        assert $(selector).css("background-image").contains(".png")

        and: 'Section should have video tag with video rendition'
        assert $("${selector} source").firstElement().getAttribute("src").contains(".flv")

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Parsys in #viewport.label")
    def "Functionality of Component Variant: Parsys"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlock"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock10"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        takeScreenshot($(selector).firstElement(), "I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample rich text'
        assert $(selector + " .text[component]").text().trim() == "Variant: Parsys"

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Child Section in #viewport.label")
    def "Functionality of Component Variant: Child Section"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlock"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock11"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        takeScreenshot($(selector).firstElement(), "I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Should have sample child sections'
        assert $(selector + " .childsection").size() == 3

        when: 'I click first child section button'
        scrollIntoView($("#contentblock11a-heading button").firstElement())
        clickOnElement($("#contentblock11a-heading button").firstElement())
        takeScreenshot($(selector).firstElement(), "I click first child section button")

        then: 'Fist child section content should be expanded'
        waitFor (3, 0.1) { $("#contentblock11a-content [component].text").isDisplayed() }
        takeScreenshot($(selector).firstElement(), "Fist child section content should be expanded")

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Section with Background using Image Option using Manual MediaQuery with Rendition in #viewport.label")
    def "Functionality of Component Variant: Section with Background using Image Option using Manual MediaQuery with Rendition"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "ContentBlock"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblock12"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        takeScreenshot($(selector).firstElement(), "I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: 'Style element should use custom rendition'
        assert $("#background-contentblock12").getAttribute("innerHTML").indexOf("aem-design-logo.png") > 0

        where:
        viewport << getViewPorts()
    }


}
