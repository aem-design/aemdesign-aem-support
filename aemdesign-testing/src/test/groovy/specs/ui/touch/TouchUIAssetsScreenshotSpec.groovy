package specs.ui.touch

import spock.lang.Ignore
import support.page.ui.touch.TouchUI
import support.page.ui.touch.TouchUIAssets
import spock.lang.Stepwise
import spock.lang.Unroll
import support.AuthorSpec

@Stepwise
@Ignore
class TouchUIAssetsScreenshotSpec extends AuthorSpec {

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Default Assets screen should match design reference in #viewport.label")
    def "Default Assets screen should match reference design reference"() {
        given: "I navigate to Assets Page"
        setWindowSize(viewport)
        to TouchUIAssets
        report "I navigate to Assets Page"

        when: "I am on the Assets Page"
        at TouchUIAssets
        waitForImagesToLoad(page.pageContentLoaded)
        report "I am on the Assets Page"

        then: "The page should match the design reference"
        $(TouchUI.elements.DOCUMENT_BODY).allElements().eachWithIndex { element, index ->
            designReference(element)
        }

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()

    }

}
