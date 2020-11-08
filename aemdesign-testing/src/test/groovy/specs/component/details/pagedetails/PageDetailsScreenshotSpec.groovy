package specs.component.details.pagedetails

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class PageDetailsScreenshotSpec extends ComponentSpec {

    String pathPage = "component/details/page-details"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/pagedetails"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of Component with Excluded Components in #viewport.label")
    def "Appearance of Component with Excluded Components"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#page-details1"

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

    @Unroll("Appearance of Component with Background and Included Components in #viewport.label")
    def "Appearance of Component with Background and Included Components"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#page-details2"

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
