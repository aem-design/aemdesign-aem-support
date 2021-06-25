package specs.component.lists.list

import spock.lang.IgnoreRest
import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ListPublishSpec extends ComponentSpec {

    String pathPage = "component/lists/list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/list"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("List: Default variant using Default badge in #viewport.label")
    def "List: Default variant using Default badge"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#list1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has five list items"
        assert $("${selector} li").size() == 5

        and: "Has first item"
        assert $("${selector} li.first").size() == 1

        and: "Has last item"
        assert $("${selector} li.last").size() == 1

        and: "Has three plain items"
        assert $("${selector} li.item").size() == 3

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("List: Pages with no Details in #viewport.label")
    def "List: Pages with no Details"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#list7"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has five items of page missing details"
        assert $("${selector} .page-missing-details").size() == 5

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("List: Pages search with By Tags in #viewport.label")
    def "List: Pages search with By Tags"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#list8"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has two items in the list"
        assert $("${selector} li").size() == 2

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("List: Pages search with By Tags with Tags not Specified in #viewport.label")
    def "List: Pages search with By Tags with Tags not Specified"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#list9"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Has zero items in the list"
        assert $("${selector} li").size() == 0

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("List: Default Search in #viewport.label")
    def "List: Default Search"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#list18"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has one list items"
        assert $("${selector} li").size() == 1

        and: "Has title line"
        assert compareInnerTextIgnoreCase("${selector} li","Page 5 Contact Details")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

}
