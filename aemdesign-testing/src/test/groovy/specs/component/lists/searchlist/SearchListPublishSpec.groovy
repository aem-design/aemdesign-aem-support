package specs.component.lists.searchlist

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class SearchListPublishSpec extends ComponentSpec {

    String pathPage = "component/lists/search-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock2/par/searchlist"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Search List Component Variant: Default without Query in #viewport.label")
    def "Functionality of Search List Component Variant: Default without Query"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Search List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#searchlist1"
        def selectorContainer = "#contentblock2 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Should be empty and contain default text"
        assert $(selector).children().size() == 1 &&  $(selector).children().text().contains("Invalid query given!")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Search List Component Default with Query in #viewport.label")
    def "Functionality of Search List Component Default with Query"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Search List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#searchlist1"
        def stringQuery = "fulltext=city\n" +
                "group.p.or=true\n" +
                "group.1_group.path=/content/aemdesign-showcase/au/en/component/lists/search-list\n" +
                "group.1_group.type=cq:Page\n" +
                "group.1_group.property=@jcr:content/hideInNav\n" +
                "group.1_group.property.operation=exists\n" +
                "group.1_group.property.value=false\n" +
                "group.2_group.path=/content/dam/aemdesign-showcase\n" +
                "group.2_group.type=dam:Asset\n" +
                "orderby=path"

        def query_string = "q=" + URLEncoder.encode(stringQuery, "UTF-8")

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPageWithQuery(language, query_string)

        printDebug("URL", driver.currentUrl)

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Should contain results"
        assert $(selector).find(".results").children().size() > 0

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }
}
