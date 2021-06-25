package specs.component.lists.listnav

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ListNavScreenshotSpec extends ComponentSpec {

    String pathPage = "component/lists/list-nav"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/listnav"

    def setupSpec() {
        loginAsAdmin()
    }



    @Unroll("Appearance of Component with Default variant and Static List in #viewport.label")
    def "Appearance of Component with Default variant and List of Children"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#listnav1"

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
