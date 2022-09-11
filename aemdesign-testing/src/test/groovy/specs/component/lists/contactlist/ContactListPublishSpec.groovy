package specs.component.lists.contactlist

import spock.lang.IgnoreRest
import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ContactListPublishSpec extends ComponentSpec {

    String pathPage = "component/lists/contact-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/contactlist"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component with Default variant using Default badge in #viewport.label")
    def "Functionality of Component with Default variant using Default badge"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Contact List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contactlist1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has five list items"
        assert $("${selector} ul.list > li").size() == 6

        and: "Has first item"
        assert $("${selector} li.first").text().trim() == "Mr Max Barrass"

        and: "Has last item"
        assert $("${selector} li.last").text().trim() == "Author: Max Barrass"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component with Card Action with Icon, Title, Category and Description in #viewport.label")
    def "Functionality of Component with Card Action with Icon, Title, Category and Description"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Contact List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contactlist2"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has two list items"
        assert $("${selector} ul.list > li").size() == 2

        and: "Has card title"
        assert $("${selector} .card-title").size() == 2

        and: "Has card tags"
        assert $("${selector} .card-category").size() == 2

        and: "Has card description"
        assert $("${selector} .card-text").size() == 2

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component with Card Action with Image, Title, Category and Description in #viewport.label")
    def "Functionality of Component with Card Action with Image, Title, Category and Description"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Contact List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contactlist3"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has two list items"
        assert $("${selector} ul.list > li").size() == 2

        and: "Has card has image"
        assert $("${selector} img").size() == 2

        and: "Has card title"
        assert $("${selector} .card-title").size() == 2

        and: "Has card tags"
        assert $("${selector} .card-category").size() == 2

        and: "Has card description"
        assert $("${selector} .card-text").size() == 2

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component with Card with Icon, Title, Category, Description and Action in #viewport.label")
    def "Functionality of Component with Card with Icon, Title, Category, Description and Action"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Contact List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contactlist4"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has two list items"
        assert $("${selector} ul.list > li").size() == 2

        and: "Has card title"
        assert $("${selector} .card-title").size() == 2

        and: "Has card tags"
        assert $("${selector} .card-category").size() == 2

        and: "Has card description"
        assert $("${selector} .card-text").size() == 2

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Functionality of Component with Card with Image, Title, Category, Description and Action in #viewport.label")
    def "Functionality of Component with Card with Image, Title, Category, Description and Action"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Contact List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contactlist5"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has two list items"
        assert $("${selector} ul.list > li").size() == 2

        and: "Has card has image"
        assert $("${selector} img").size() == 2

        and: "Has card title"
        assert $("${selector} .card-title").size() == 2

        and: "Has card tags"
        assert $("${selector} .card-category").size() == 2

        and: "Has card description"
        assert $("${selector} .card-text").size() == 2

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    //#contactlist6 is in ContactListPaginationPublishSpec

    @Unroll("Functionality of Component with Card with Image, Title, Category, Description and Email Action Format in #viewport.label")
    def "Functionality of Component with Card with Image, Title, Category, Description and Email Action Format"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Contact List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contactlist7"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has card description"
        assert $("${selector} .card-link").getAt(0).getAttribute("href").contains("mailto:")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


}
