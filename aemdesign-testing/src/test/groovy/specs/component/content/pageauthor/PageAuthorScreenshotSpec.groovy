package specs.component.content.pageauthor


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class PageAuthorScreenshotSpec extends ComponentSpec {

    String pathPage = "component/content/pageauthor"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/pageauthor"

    def setupSpec() {
        loginAsAdmin()
    }


    @Unroll("Appearance of Component Variant: Default in #viewport.label")
    def "Appearance of Component Variant: Default"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pageauthor1"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where:
        viewport << getViewPorts()

    }


    @Unroll("Appearance of Component Variant: Simple in #viewport.label")
    def "Appearance of Component Variant: Simple"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pageauthor2"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where:
        viewport << getViewPorts()

    }


    @Unroll("Appearance of Component Variant: Default with Override in #viewport.label")
    def "Appearance of Component Variant: Default with Override"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pageauthor3"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where:
        viewport << getViewPorts()

    }


    @Unroll("Appearance of Component Variant: Simple with Override in #viewport.label")
    def "Appearance of Component Variant: Simple with Override"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pageauthor4"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where:
        viewport << getViewPorts()

    }


}
