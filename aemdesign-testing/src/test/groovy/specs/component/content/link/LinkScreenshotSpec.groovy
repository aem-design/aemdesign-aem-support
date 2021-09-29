package specs.component.content.link

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class LinkScreenshotSpec extends ComponentSpec {

    String pathPage = "component/content/link"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/link"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of Component Variant: Default in #viewport.label")
    def "Appearance of Component Variant: Default"() {

        given: '>I am in the component showcase page'
        def selector = "#link1"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where:
        viewport << viewPorts

    }

    @Unroll("Appearance of Component Variant: Button in #viewport.label")
    def "Appearance of Component Variant: Button"() {

        given: '>I am in the component showcase page'
        def selector = "#link2"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where:
        viewport << viewPorts

    }

    @Unroll("Appearance of Component Variant: Default no Label in #viewport.label")
    def "Appearance of Component Variant: Default no Label"() {

        given: '>I am in the component showcase page'
        def selector = "#link3"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where:
        viewport << viewPorts

    }

    @Unroll("Appearance of Component Variant: Prototype Example in #viewport.label")
    def "Appearance of Component Variant: Prototype Example"() {

        given: '>I am in the component showcase page'
        def selector = "#contentblock4 .contents"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where:
        viewport << viewPorts

    }

}
