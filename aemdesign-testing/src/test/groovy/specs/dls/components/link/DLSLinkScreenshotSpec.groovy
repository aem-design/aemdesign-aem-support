package specs.dls.components.link

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class DLSLinkScreenshotSpec extends ComponentSpec {
    String pathPage = "dls/components/link"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of default link on #viewport.label")
    def "Appearance of default link"() {
        given: '>I am on the components DLS page'
        and: '>the component content is on the DLS page'
        def selector = "#link_default_component"

        when: 'I am on the component DLS page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component content should appear on the page'
        waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of button link on #viewport.label")
    def "Appearance of button link"() {
        given: '>I am on the components DLS page'
        and: '>the component content is on the DLS page'
        def selector = "#link_button_link_component"

        when: 'I am on the component DLS page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component content should appear on the page'
        waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of buttons on #viewport.label")
    def "Appearance of buttons"() {
        given: '>I am on the components DLS page'
        and: '>the component content is on the DLS page'
        def selector = "#link_buttons_wrapper"

        when: 'I am on the component DLS page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component content should appear on the page'
        waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of outline buttons on #viewport.label")
    def "Appearance of outline buttons"() {
        given: '>I am on the components DLS page'
        and: '>the component content is on the DLS page'
        def selector = "#link_buttons_outline_wrapper"

        when: 'I am on the component DLS page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component content should appear on the page'
        waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of button group on #viewport.label")
    def "Appearance of button group"() {
        given: '>I am on the components DLS page'
        and: '>the component content is on the DLS page'
        def selector = "#link_button_group_wrapper"

        when: 'I am on the component DLS page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component content should appear on the page'
        waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of button sizes on #viewport.label")
    def "Appearance of button sizes"() {
        given: '>I am on the components DLS page'
        and: '>the component content is on the DLS page'
        def selector = "#link_button_sizes_small_wrapper"

        when: 'I am on the component DLS page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component content should appear on the page'
        waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of disabled button on #viewport.label")
    def "Appearance of disabled button"() {
        given: '>I am on the components DLS page'
        and: '>the component content is on the DLS page'
        def selector = "#link_button_disabled_large_component"

        when: 'I am on the component DLS page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component content should appear on the page'
        waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }
}
