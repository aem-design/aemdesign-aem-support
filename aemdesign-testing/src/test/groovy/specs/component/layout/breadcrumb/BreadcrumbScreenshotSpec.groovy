package specs.component.layout.breadcrumb

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class BreadcrumbScreenshotSpec extends ComponentSpec {

    String pathPage = "component/layout/breadcrumb"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/breadcrumb"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of Component in #viewport.label")
    def "Appearance of Component"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#breadcrumb1"

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


    @Unroll("Appearance of Component with Changed Start Level in #viewport.label")
    def "Appearance of Component with Changed Start Level"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#breadcrumb2"

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

    @Unroll("Appearance of Component with Changed Start Level and End Level in #viewport.label")
    def "Appearance of Component with Changed Start Level and End Level"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#breadcrumb3"

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

    @Unroll("Appearance of Component with Show hidden in #viewport.label")
    def "Appearance of Component with Show hidden"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#breadcrumb4"

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
