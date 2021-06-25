package specs.component.details.genericdetails

import spock.lang.Stepwise
import support.AuthorDialogSpec

@Stepwise
class GenericDetailsAuthorDialogSpec extends AuthorDialogSpec {

    String pathPage = "components/details"
    String pathSite = "mnt/override/apps/aemdesign"
    String language = ""
    String componentPath = "generic-details/v1/generic-details/_cq_dialog"
    String pageExtensionSuffix = "/content/aemdesign-showcase/au/en/component/details/generic-details/jcr:content/article/par/contentblock7/par/genericdetails"

    def setupSpec() {
        loginAsAdmin()
    }

    def cleanupSpec() {
        analyzeLog()
    }

    def "Dialog of Component with Content"() {

        given: "Component dialog exist"
        def selector = "coral-dialog"

        when: "I open dialog with content"
        go "/${pathSite}/${pathPage}/${componentPath}${pageExtension}${pageExtensionSuffix}"

        then: "The component dialog should render"
        waitFor { $(selector) }

        and: "Dialog should be able to open"
        showDialogDirect()
        report("Dialog should be able to open")

        when: "When I select Layout tab"
        selectDialogTab("Layout")
        waitFor { tabSelected("Layout") }
        report("Layout tab selected")

        then: "Then it should have the Custom Layout options selected and matching configuration options"
        designRefFull(selector, "tab-layout")

        where:
        viewport << getDialogViewPort()

    }
}
