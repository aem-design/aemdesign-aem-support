package specs.component.layout.columns

import support.page.ui.touch.TouchUIEditor
import spock.lang.Stepwise
import support.ComponentSpec

@Stepwise
class ColumnsAuthorSpec extends ComponentSpec {

    String pathPage = "component/layout/columns"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/colctrl"

    def setupSpec() {
        loginAsAdmin()
    }

    def cleanupSpec() {
        analyzeLog()
    }

    def "Authoring of Component"() {

        given: "Component has already been inserted"
        def selector = "#colctrl1"

        when: "I am on the Component showcase page"
        TouchUIEditor page = waitForTouchUIPage(language)

        then: "The component should be on showcase page"
        //check for overlay
        waitFor {  $(".cq-Overlay[data-path='"+compileComponentPath()+"']")  }
        //check for component placeholder
        waitFor { withFrame(TouchUIEditor.PAGE_FRAME_CONTENT) { $("cq[data-path='"+compileComponentPath()+"']")  } }

        and: "All dialogs are closed"
        page.Editor.isDialogOpen(compileComponentPath()) == false
        report("All dialogs are closed")

        and: "I open the dialog box"
        page.Editor.showDialog(compileComponentPath())

        then: "I should be able to see component author dialog"
        page.Editor.isDialogOpen(compileComponentPath()) == true
        report("I should be able to see component author dialog")

        when: "I close the dialog box"
        page.Editor.closeDialog(compileComponentPath())

        then: "I should be able to close component author dialog"
        page.Editor.isDialogOpen(compileComponentPath()) == false
        report("I should be able to close component author dialog")
    }


}
