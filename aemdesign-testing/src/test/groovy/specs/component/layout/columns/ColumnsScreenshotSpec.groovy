package specs.component.layout.columns

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ColumnsScreenshotSpec extends ComponentSpec {

    String pathPage = "component/layout/columns"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/colctrl"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of Component Variant: Default using 1 Column Layout in #viewport.label")
    def "Appearance of Component Variant: Default using 1 Column Layout"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#colctrl1"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()

    }

    @Unroll("Appearance of Component Variant: Default using 2 Column Layout in #viewport.label")
    def "Appearance of Component Variant: Default using 2 Column Layout"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#colctrl2"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()

    }


    @Unroll("Appearance of Component Variant: Default using 3 Column Layout in #viewport.label")
    def "Appearance of Component Variant: Default using 3 Column Layout"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#colctrl3"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()

    }


    @Unroll("Appearance of Component Variant: Default using 4 Column Layout in #viewport.label")
    def "Appearance of Component Variant: Default using 4 Column Layout"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#colctrl4"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()

    }


    @Unroll("Appearance of Component Variant: Default using 5 Column Layout in #viewport.label")
    def "Appearance of Component Variant: Default using 5 Column Layout"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#colctrl5"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()

    }


    @Unroll("Appearance of Component Variant: Default using 6 Column Layout in #viewport.label")
    def "Appearance of Component Variant: Default using 6 Column Layout"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#colctrl6"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()

    }


    @Unroll("Appearance of Component Variant: Default with 5 Column Layout Varied Width in #viewport.label")
    def "Appearance of Component Variant: Default with 5 Column Layout Varied Width"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#colctrl7"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()

    }


}
