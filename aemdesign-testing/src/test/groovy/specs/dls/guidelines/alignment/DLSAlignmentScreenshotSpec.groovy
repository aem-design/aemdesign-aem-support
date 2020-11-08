package specs.dls.guidelines.alignment

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class DLSAlignmentScreenshotSpec extends ComponentSpec {
    String pathPage = "dls/guidelines/alignment"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of text alignment left on #viewport.label")
    def "Appearance of text alignment left"() {
        given: '>I am on the guidelines DLS page'
        and: '>the guidelines content is on the DLS page'
        def selector = "#contentblock_left_text_align_component"

        when: 'I am on the guidelines DLS page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The guidelines content should appear on the page'
        waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Appearance of text alignment center on #viewport.label")
    def "Appearance of text alignment center"() {
        given: '>I am on the guidelines DLS page'
        and: '>the guidelines content is on the DLS page'
        def selector = "#contentblock_center_text_align_component"

        when: 'I am on the guidelines DLS page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The guidelines content should appear on the page'
        waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Appearance of text alignment right on #viewport.label")
    def "Appearance of text alignment right"() {
        given: '>I am on the guidelines DLS page'
        and: '>the guidelines content is on the DLS page'
        def selector = "#contentblock_right_text_align_component"

        when: 'I am on the guidelines DLS page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The guidelines content should appear on the page'
        waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }
}
