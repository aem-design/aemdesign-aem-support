package specs.component.content.pagetitle

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class PageTitleScreenshotSpec extends ComponentSpec {

    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String pathPage = "component/content/pagetitle"
    String componentPath = "jcr:content/article/par/contentblock1/par/pagetitle"

    def setupSpec() {
        loginAsAdmin()
    }


    @Unroll("Appearance of Component Variant: Default in #viewport.label")
    def "Appearance of Component Variant: Default"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagetitle1"

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
