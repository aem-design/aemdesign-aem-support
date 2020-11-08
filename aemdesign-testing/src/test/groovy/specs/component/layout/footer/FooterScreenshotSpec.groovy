package specs.component.layout.footer

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class FooterScreenshotSpec extends ComponentSpec {

    String pathPage = "component/layout/footer"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/footer"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of Component in #viewport.label")
    def "Appearance of Component"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#plainfooter"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

}
