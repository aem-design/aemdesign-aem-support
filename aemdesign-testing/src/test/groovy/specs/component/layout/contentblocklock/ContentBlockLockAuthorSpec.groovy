package specs.component.layout.contentblocklock

import support.page.ui.touch.TouchUIEditor
import spock.lang.Stepwise
import support.ComponentSpec

@Stepwise
class ContentBlockLockAuthorSpec extends ComponentSpec {

    String pathPage = "component/layout/contentblocklock"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblocklock1"

    def setupSpec() {
        loginAsAdmin()
    }

    def cleanupSpec() {
        analyzeLog()
    }

    def "Authoring of Component"() {

        given: "Component has already been inserted"
        def selector = "#contentblocklock1"

        when: "I am on the Component showcase page"
        TouchUIEditor page = waitForTouchUIPage(language)

        then: "The component should be on showcase page"
        waitFor { withFrame(TouchUIEditor.PAGE_FRAME_CONTENT) { $(selector) } }

        and: "All dialogs are closed"
        page.Editor.isDialogOpen(compileComponentPath()) == false
        report("All dialogs are closed")

        and: "I open the dialog box"
        page.Editor.showDialog(compileComponentPath())

        then: "I should be able to see component author dialog"
        page.Editor.isDialogOpen(compileComponentPath()) == true
        report("I should be able to see component author dialog")

//        then: "The component should have the restricted field"
//        assert page.Editor.execDialog(compileComponentPath(),".find(\"name\",\"permissionCheckTabAccessCheck\").length==1")
//        designRef("#cq-cf-frame-ct", "admin-")

        when: "I close the dialog box"
        page.Editor.closeDialog(compileComponentPath())

        then: "I should be able to close component author dialog"
        page.Editor.isDialogOpen(compileComponentPath()) == false
        report("I should be able to close component author dialog")
    }

}
