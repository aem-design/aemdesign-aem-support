package specs.component.content.contenttemplate

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ContentTemplateScreenshotSpec extends ComponentSpec {

    String pathPage = "component/content/contenttemplate"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/contenttemplate"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of Component Variant: Using HTL Template in #viewport.label")
    def "Appearance of Component Variant: Using HTL Template"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contenttemplate1"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where:
        viewport << getViewPorts()

    }

    @Unroll("Appearance of Component Variant: Using Custom Template in #viewport.label")
    def "Appearance of Component Variant: Using Custom Template"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contenttemplate2"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where:
        viewport << getViewPorts()

    }

}
