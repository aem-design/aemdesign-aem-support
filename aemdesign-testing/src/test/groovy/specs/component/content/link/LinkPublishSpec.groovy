package specs.component.content.link

import spock.lang.IgnoreRest
import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class LinkPublishSpec extends ComponentSpec {

    String pathPage = "component/content/link"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/link"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: 'The page hierarchy is created as "Components" > "Content" > "Link"'
        def selector = "#link1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample component text"
        assert $(selector).text().trim() == "Link: Default"
        report("Should have sample component text")

        and: "Should have video-play module tag"
        assert $(selector).attr("data-modules") == "play-video"
        report("Should have sample component text")

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Button in #viewport.label")
    def "Functionality of Component Variant: Button"() {

        given: 'The page hierarchy is created as "Components" > "Content" > "Link"'
        def selector = "#link2"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample component text"
        assert $(selector).text().trim() == "Link: Button"
        report("Should have sample component text")

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Default no Label in #viewport.label")
    def "Functionality of Component Variant: Default no Label"() {

        given: 'The page hierarchy is created as "Components" > "Content" > "Link"'
        def selector = "#link3"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample component text"
        assert $(selector).text().trim() == "Link"
        report("Should have sample component text")

        where:
        viewport << getViewPorts()
    }

    @IgnoreRest
    @Unroll("Functionality of Component Variant: Default with Analytics in #viewport.label")
    def "Functionality of Component Variant: Default with Analytics"() {

        given: 'The page hierarchy is created as "Components" > "Content" > "Link"'
        def selector = "#link6"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample component text"
        assert $(selector).text().trim() == "Link"
        report("Should have sample component text")

        and: "Should have analytics attribute: data-layer-track"
        assert $(selector).attr("data-layer-track") == "true"

        and: "Should have analytics attribute: data-layer-label"
        assert $(selector).attr("data-layer-label") == "link description"

        and: "Should have analytics attribute: data-layer-location"
        assert $(selector).attr("data-layer-location") == "link page"

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Button with Analytics in #viewport.label")
    def "Functionality of Component Variant: Button with Analytics"() {

        given: 'The page hierarchy is created as "Components" > "Content" > "Link"'
        def selector = "#link7"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample component text"
        assert $(selector).text().trim() == "Link"
        report("Should have sample component text")

        and: "Should have analytics attribute: data-layer-track"
        assert $(selector).attr("data-layer-track") == "true"

        and: "Should have analytics attribute: data-layer-label"
        assert $(selector).attr("data-layer-label") == "link description"

        and: "Should have analytics attribute: data-layer-location"
        assert $(selector).attr("data-layer-location")  == "link page"

        where:
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Variant: Default with Analytics Track Only in #viewport.label")
    def "Functionality of Component Variant: Default with Analytics Track Only"() {

        given: 'The page hierarchy is created as "Components" > "Content" > "Link"'
        def selector = "#link12"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample component text"
        assert $(selector).text().trim() == "Link"
        report("Should have sample component text")

        and: "Should have analytics attribute: data-layer-track"
        assert $(selector).attr("data-layer-track") == "true"

        and: "Should have analytics attribute: data-layer-label"
        assert $(selector).attr("data-layer-label") == "Link"

        and: "Should have analytics attribute: data-layer-location"
        assert $(selector).attr("data-layer-location") == ""

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Button with Analytics Track Only in #viewport.label")
    def "Functionality of Component Variant: Button with Analytics Track Only"() {

        given: 'The page hierarchy is created as "Components" > "Content" > "Link"'
        def selector = "#link13"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample component text"
        assert $(selector).text().trim() == "Link"
        report("Should have sample component text")

        and: "Should have analytics attribute: data-layer-track"
        assert $(selector).attr("data-layer-track") == "true"

        and: "Should have analytics attribute: data-layer-label"
        assert $(selector).attr("data-layer-label") == "Link"

        and: "Should have analytics attribute: data-layer-location"
        assert $(selector).attr("data-layer-location") == ""

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Button Button with Icon Left in #viewport.label")
    def "Functionality of Component Variant: Button Button with Icon Left"() {

        given: 'The page hierarchy is created as "Components" > "Content" > "Link"'
        def selector = "#link14"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample component text"
        assert $(selector).text().trim() == "Link"
        takeScreenshot($(selector).firstElement(), "Should have sample component text")

        and: "Should have left icon first"
        assert $("$selector > *").firstElement().getAttribute("class").contains("icon")

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Button Button with Icon Right in #viewport.label")
    def "Functionality of Component Variant: Button Button with Icon Right"() {

        given: 'The page hierarchy is created as "Components" > "Content" > "Link"'
        def selector = "#link15"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample component text"
        assert $(selector).text().trim() == "Link"
        takeScreenshot($(selector).firstElement(), "Should have sample component text")

        and: "Should have right icon last"
        assert $("$selector > *").lastElement().getAttribute("class").contains("icon")

        where:
        viewport << getViewPorts()
    }


}
