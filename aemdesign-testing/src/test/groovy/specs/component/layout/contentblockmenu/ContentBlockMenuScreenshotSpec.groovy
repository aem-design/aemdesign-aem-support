package specs.component.layout.contentblockmenu

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ContentBlockMenuScreenshotSpec extends ComponentSpec {

    String pathPage = "component/layout/contentblockmenu"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblockmenu1"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of Component Menu Source: Page in #viewport.label")
    def "Appearance of Component Menu Source: Page "() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblockmenu1"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector, "page")

        where:
        viewport << getViewPorts()


    }

    @Unroll("Appearance of Component Menu Source: Page Path  in #viewport.label")
    def "Appearance of Component Menu Source: Page Path"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contentblockmenu2"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector, "pagepath")

        where:
        viewport << getViewPorts()


    }

}
