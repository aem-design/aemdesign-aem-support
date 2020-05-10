package specs.dls.guidelines

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class DLSTypographyScreenshotSpec extends ComponentSpec {
    String pathPage = "dls/guidelines/typography"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of headings in #viewport.label")
    def "Appearance of headings"() {
        given: '>I am on the guidelines DLS page'
        and: '>the guidelines content is on the DLS page'
        def selector = "#typography_headings_component"

        when: 'I am on the guidelines DLS page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The guidelines content should appear on the page'
        waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }
}
