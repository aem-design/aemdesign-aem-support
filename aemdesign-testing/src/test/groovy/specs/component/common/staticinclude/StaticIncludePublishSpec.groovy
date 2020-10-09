package specs.component.common.staticinclude

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class StaticIncludePublishSpec extends ComponentSpec {

    String pathPage = "component/common/staticinclude"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/staticinclude"
    String pageSelectors = ""

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Common" > "Static Include"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = ".staticinclude.aem-GridColumn"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The page should have Static Include content"
        assert $("$selector div").text().trim().equals("Static Include")
        takeScreenshot($(selector).firstElement(), "The page should have Static Include content")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

}
