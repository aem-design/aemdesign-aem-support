package specs.ui.touch

import spock.lang.Ignore
import support.page.ui.touch.TouchUI
import support.page.ui.touch.TouchUIProjects
import spock.lang.Stepwise
import spock.lang.Unroll
import support.AuthorSpec

@Stepwise
@Ignore
class TouchUIProjectsScreenshotSpec extends AuthorSpec {

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Default Project screen should match reference design reference in #viewport.label")
    def "Default Project screen should match reference design reference"() {
        given: "I am on the Projects Page"
        setWindowSize(viewport.label)
        to TouchUIProjects

        when: "I am on the Projects Page"
        at TouchUIProjects
        waitForImagesToLoad(page.pageContentLoaded)

        then: "The page should match the design reference"
        $(TouchUI.elements.DOCUMENT_BODY).allElements().eachWithIndex { element, index ->
            designReference(element)
        }

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()

    }

}
