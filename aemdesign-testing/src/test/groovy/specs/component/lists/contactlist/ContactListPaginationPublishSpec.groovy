package specs.component.lists.contactlist

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ContactListPaginationPublishSpec extends ComponentSpec {

    String pathPage = "component/lists/contact-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/contactlist"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component with Card with Image, Title, Category, Description and Action with Pagination in #viewport.label")
    def "Functionality of Component with Card with Image, Title, Category, Description and Action with Pagination"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Contact List" > "Pagination"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contactlist6"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has two list items"
        assert $("${selector} ul.list > li").size() == 2

        and: "Only next link exists on first page, without previous link"
        scrollIntoView($("${selector} .pagination").firstElement())
        assert $("${selector} .pagination .next").isDisplayed()
        assert !$("${selector} .pagination .previous").isDisplayed()

        and: "Has pagination details"
        assert $("${selector} .pagination .current").text().trim() == "1"
        takeScreenshot($(selector).firstElement(), "The component should be on first page")

        and: "Can select page 2"
        scrollIntoView($("${selector} .pagination").firstElement())
        $("${selector} .pagination .next a").click()
        assert $("${selector} .pagination .current").text().trim() == "2"
        takeScreenshot($(selector).firstElement(), "The component should be on second page")

        and: "Can select page 3"
        scrollIntoView($("${selector} .pagination").firstElement())
        $("${selector} .pagination .next a").click()
        assert $("${selector} .pagination .current").text().trim() == "3"
        takeScreenshot($(selector).firstElement(), "The component should be on third page")

        and: "Only previous link exists on last page, without next link"
        scrollIntoView($("${selector} .pagination").firstElement())
        assert !$("${selector} .pagination .next").isDisplayed()
        assert $("${selector} .pagination .previous").isDisplayed()

        and: "Can select previous page"
        scrollIntoView($("${selector} .pagination").firstElement())
        $("${selector} .pagination .previous a").click()
        assert $("${selector} .pagination .current").text().trim() == "2"
        takeScreenshot($(selector).firstElement(), "The component should be on forth page")


        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }
}
