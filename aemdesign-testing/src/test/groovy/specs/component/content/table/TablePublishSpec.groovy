package specs.component.content.table

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class TablePublishSpec extends ComponentSpec {

    String pathPage = "component/content/table"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/table"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Table"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#table1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Header")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Has sample table content"
        assert !$("${selector} table").empty

        and: "Has sample link"
        assert !$("${selector} a").empty


        where:
        viewport << viewPorts
    }


}
