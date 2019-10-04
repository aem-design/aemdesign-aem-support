package specs.component.lists.newslist

import spock.lang.IgnoreRest
import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class NewsListPublishSpec extends ComponentSpec {

    String pathPage = "component/lists/news-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/newslist"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component with Default variant using Default badge in #viewport.label")
    def "Functionality of Component with Default variant using Default badge"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "News List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#newslist1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has five list items"
        assert $("${selector} li").size() == 6

        and: "Has first item with title from Details component"
        assert $("${selector} li.first").text().trim() == "News Title 1"

        and: "Has last item with title from Details component"
        assert $("${selector} li.last").text().trim() == "News Title 3"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component with Card with Image Title Category Description and Action in #viewport.label")
    def "Functionality of Component with Card with Image Title Category Description and Action"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Page List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#newslist3"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has two list items"
        assert $("${selector} li").size() == 2

        and: "Has card title"
        assert $("${selector} .card-title").size() == 2

        and: "Has card description"
        assert $("${selector} .card-text").size() == 2

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component with Card with Image Title Subtitle Description and Action in #viewport.label")
    def "Functionality of Component with Card with Image Title Subtitle Description and Action"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Page List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#newslist4"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has five list items"
        assert $("${selector} li").size() == 2

        and: "Has card images"
        assert $("${selector} img").size() == 2

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component with Card with Image Title Subtitle Action and Date in #viewport.label")
    def "Functionality of Component with Card with Image Title Subtitle Action and Date"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Page List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#newslist5"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has five list items"
        assert $("${selector} li").size() == 2

        and: "Has card images"
        assert $("${selector} img").size() == 2

        and: "Has card with date"
        assert $("${selector} .card-date time").size() == 2

        and: "Date output has datetime attribute"
        assert $("${selector} .card-date time").firstElement().getAttribute("datetime") == "2019-01-01"

        and: "Date output matches format"
        assert $("${selector} .card-date time").firstElement().getAttribute("innerText") == "Tuesday 01 January 2019"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @IgnoreRest
    @Unroll("Functionality of Component with Card with Image Title Subtitle Action and Date with Order by Publish Date in #viewport.label")
    def "Functionality of Component with Card with Image Title Subtitle Action and Date with Order by Publish Date"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Page List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#newslist6"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has five list items"
        assert $("${selector} li").size() == 6

        and: "First Card is News Title 2"
        assert $("${selector} .card-title").getAt(0).getAttribute("innerText") == "News Title 2"

        and: "Second Card is News Title 1"
        assert $("${selector} .card-title").getAt(1).getAttribute("innerText") == "News Title 1"

        and: "Third Card is News Title 3"
        assert $("${selector} .card-title").getAt(2).getAttribute("innerText") == "News Title 3"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }



}
