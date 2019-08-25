package specs.component.details.eventdetails


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class EventDetailsPublishSpec extends ComponentSpec {

    String pathPage = "component/details/event-details"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/eventdetails"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Event Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#event-details1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Should have sample heading"
        assert $("$selector h1").text().trim().toLowerCase().equals("Event Details".toLowerCase())

        and: "Has Description line with content"
        assert $("${selector} div.description").text().trim() == "Event to showcase all events"

        and: "Has Description line with content"
        assert $("${selector} div.card-subtitle").text().trim() == "Mon 1 October to Fri 5 October"

        and: "Has Description line with content"
        assert $("${selector} div.card-date").text().trim() == "9:20 AM to 9:20 AM"

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Hidden in #viewport.label")
    def "Functionality of Component Variant: Hidden "() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Event Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#event-details2"
        def selectorContainer = "#contentblock2 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
//        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Component should be hidden"
        assert $("${selector}[hidden]").size() == 1

        where:
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Variant: Default with Formatting in #viewport.label")
    def "Functionality of Component Variant: Default with Formatting "() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Event Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#event-details3"
        def selectorContainer = "#contentblock3 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Should have sample heading"
        assert $("$selector h3").text().trim().toLowerCase().equals("Event Details".toLowerCase())

        and: "Has Description line with content"
        assert $("${selector} div.description").text().trim() == "Event to showcase all events"

        and: "Has Description line with content"
        assert $("${selector} div.card-subtitle").text().trim() == "Mon 1 October to Fri 5 October"

        and: "Has Description line with content"
        assert $("${selector} div.card-date").text().trim() == "9:20 am9:20 am"

        and: "Has Description line with content that has startDate time "
        assert $("${selector} div.card-date time[itemprop='startDate']").size() == 1

        and: "Has Description line with content that has endDate time "
        assert $("${selector} div.card-date time[itemprop='endDate']").size() == 1


        where:
        viewport << getViewPorts()
    }


    def "Event Details: Default metadata added to page"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Event Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#event-details"

        when: "I am on the component showcase page"
        setWindowSizeLG()
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Page has metadata field og:title"
        assert $("meta[property='og:title']").attr("content").equals("Event Details")

        and: "Page has metadata field og:type"
        assert $("meta[property='og:type']").attr("content").equals("article")

        and: "Page has metadata field og:image"
        assert $("meta[property='og:image']").attr("content").contains("event-details.thumb.")

        and: "Page has metadata field og:url"
        assert $("meta[property='og:url']").attr("content").contains("details/event-details.html")

        and: "Page has canonical link"
        assert $("link[rel='canonical']").attr("href").contains("details/event-details.html")

    }


}
