package specs.component.content.pagetags

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class PageTagsScreenshotSpec extends ComponentSpec {

    String pathPage = "component/content/pagetags"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/pagetags"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of Component Variant: Default in #viewport.label")
    def "Appearance of Component Variant: Default"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagetags1"

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
