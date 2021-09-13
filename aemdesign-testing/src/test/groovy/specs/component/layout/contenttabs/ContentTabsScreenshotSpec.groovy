package specs.component.layout.contenttabs

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ContentTabsScreenshotSpec extends ComponentSpec {

    String pathPage = "component/layout/contenttabs"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock/par/contenttabs"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of Component List of Children Pages in #viewport.label")
    def "Appearance of Component with List of Children Pages"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contenttabs1"

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

    @Unroll("Appearance of Component with List of Static Pages in #viewport.label")
    def "Appearance of Component with List of Static Pages"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contenttabs2"

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

    @Unroll("Appearance of Component Empty in #viewport.label")
    def "Appearance of Component Empty"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contenttabs3"

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
