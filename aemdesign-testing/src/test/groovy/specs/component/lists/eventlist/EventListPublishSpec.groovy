package specs.component.lists.eventlist


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class EventListPublishSpec extends ComponentSpec {

    String pathPage = "component/lists/event-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/eventlist"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Event List: Default variant using Default badge in #viewport.label")
    def "Event List: Default variant using Default badge"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Event List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#eventlist1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has five list items"
        assert $("${selector} li").size() == 5

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Event List: Default with ICS Feed Enabled in #viewport.label")
    def "Event List: Default with ICS Feed Enabled"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Event List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#eventlist6"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has five list items"
        assert $("${selector} li").size() == 5

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

}
