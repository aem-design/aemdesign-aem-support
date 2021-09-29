package specs.component.lists.listnav


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ListNavPublishSpec extends ComponentSpec {

    String pathPage = "component/lists/list-nav"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/listnav"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component with Default variant and Child List in #viewport.label")
    def "Functionality of Component with Default variant and Child List"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "List Nav"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#listnav1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Should have next link"
        assert $("${selector} .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page2")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    def "Functionality of Component Inherited in Footer"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "List Nav"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#listnavDescendants"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Descendants list should point to first element in list"
        assert $("#listnavDescendants .first").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1")

        and: "Static list should point to first element in list"
        assert $("#listnavStatic .first").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages")

        and: "Looping Descendants list should point to first element in list"
        assert $("#listnavDescendantsLoop .first").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1")

        and: "Looping Descendants with Badge list should point to first element in list"
        assert $("#listnavDescendantsLoopBadge .first .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1")

        when: "I navigate to next page"
        waitForAuthorPreviewPageUrl("content/aemdesign-showcase/au/en/component/lists/list-nav/pages/page1.html")

        then: "Descendants list should point to next element in list"
        assert $("#listnavDescendants .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1")

        and: "Static list should point to previous element in list"
        assert $("#listnavStatic .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages")

        and: "Static list should point to next element in list"
        assert $("#listnavStatic .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1")

        and: "Looping Descendants list should point to next element in list"
        assert $("#listnavDescendantsLoop .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1")

        and: "Looping Descendants with Badge list should point to next element in list"
        assert $("#listnavDescendantsLoopBadge .next .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1")

        when: "I navigate to next page"
        waitForAuthorPreviewPageUrl("content/aemdesign-showcase/au/en/component/lists/list-nav/pages/page1/page1.html")

        then: "Descendants list should point to previous element in list"
        assert $("#listnavDescendants .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1")

        and: "Descendants list should point to next element in list"
        assert $("#listnavDescendants .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1 > Page1")

        and: "Static list should point to previous element in list"
        assert $("#listnavStatic .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1")

        and: "Static list should point to next element in list"
        assert $("#listnavStatic .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page2")

        and: "Looping Descendants list should point to previous element in list"
        assert $("#listnavDescendantsLoop .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1")

        and: "Looping Descendants list should point to next element in list"
        assert $("#listnavDescendantsLoop .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1 > Page1")

        and: "Looping Descendants with Badge list should point to previous element in list"
        assert $("#listnavDescendantsLoopBadge .previous .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1")

        and: "Looping Descendants with Badge list should point to next element in list"
        assert $("#listnavDescendantsLoopBadge .next .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1 > Page1")

        when: "I navigate to next page"
        waitForAuthorPreviewPageUrl("content/aemdesign-showcase/au/en/component/lists/list-nav/pages/page1/page1/page1.html")

        then: "Descendants list should point to previous element in list"
        assert $("#listnavDescendants .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1")

        and: "Descendants list should point to next element in list"
        assert $("#listnavDescendants .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1 > Page2")

        and: "Static list should point to first element in list"
        assert $("#listnavStatic .first").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages")

        and: "Looping Descendants list should point to previous element in list"
        assert $("#listnavDescendantsLoop .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1")

        and: "Looping Descendants list should point to next element in list"
        assert $("#listnavDescendantsLoop .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1 > Page2")

        and: "Looping Descendants with Badge list should point to previous element in list"
        assert $("#listnavDescendantsLoopBadge .previous .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1")

        and: "Looping Descendants with Badge list should point to next element in list"
        assert $("#listnavDescendantsLoopBadge .next .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1 > Page2")

        when: "I navigate to next page"
        waitForAuthorPreviewPageUrl("content/aemdesign-showcase/au/en/component/lists/list-nav/pages/page1/page1/page2.html")

        then: "Descendants list should point to previous element in list"
        assert $("#listnavDescendants .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1 > Page1")

        and: "Descendants list should point to next element in list"
        assert $("#listnavDescendants .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2")

        and: "Static list should point to first element in list"
        assert $("#listnavStatic .first").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages")

        and: "Looping Descendants list should point to previous element in list"
        assert $("#listnavDescendantsLoop .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1 > Page1")

        and: "Looping Descendants list should point to next element in list"
        assert $("#listnavDescendantsLoop .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2")

        and: "Looping Descendants with Badge list should point to previous element in list"
        assert $("#listnavDescendantsLoopBadge .previous .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1 > Page1")

        and: "Looping Descendants with Badge list should point to next element in list"
        assert $("#listnavDescendantsLoopBadge .next .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2")


        when: "I navigate to next page"
        waitForAuthorPreviewPageUrl("content/aemdesign-showcase/au/en/component/lists/list-nav/pages/page1/page2.html")

        then: "Descendants list should point to previous element in list"
        assert $("#listnavDescendants .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1 > Page2")

        and: "Descendants list should point to next element in list"
        assert $("#listnavDescendants .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2 > Page1")

        and: "Static list should point to first element in list"
        assert $("#listnavStatic .first").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages")

        and: "Looping Descendants list should point to previous element in list"
        assert $("#listnavDescendantsLoop .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1 > Page2")

        and: "Looping Descendants list should point to next element in list"
        assert $("#listnavDescendantsLoop .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2 > Page1")

        and: "Looping Descendants with Badge list should point to previous element in list"
        assert $("#listnavDescendantsLoopBadge .previous .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1 > Page2")

        and: "Looping Descendants with Badge list should point to next element in list"
        assert $("#listnavDescendantsLoopBadge .next .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2 > Page1")


        when: "I navigate to next page"
        waitForAuthorPreviewPageUrl("content/aemdesign-showcase/au/en/component/lists/list-nav/pages/page1/page2/page1.html")

        then: "Descendants list should point to previous element in list"
        assert $("#listnavDescendants .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2")

        and: "Descendants list should point to next element in list"
        assert $("#listnavDescendants .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2 > Page2")

        and: "Static list should point to first element in list"
        assert $("#listnavStatic .first").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages")

        and: "Looping Descendants list should point to previous element in list"
        assert $("#listnavDescendantsLoop .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2")

        and: "Looping Descendants list should point to next element in list"
        assert $("#listnavDescendantsLoop .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2 > Page2")

        and: "Looping Descendants with Badge list should point to previous element in list"
        assert $("#listnavDescendantsLoopBadge .previous .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2")

        and: "Looping Descendants with Badge list should point to next element in list"
        assert $("#listnavDescendantsLoopBadge .next .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2 > Page2")



        when: "I navigate to next page"
        waitForAuthorPreviewPageUrl("content/aemdesign-showcase/au/en/component/lists/list-nav/pages/page1/page2/page2.html")

        then: "Descendants list should point to previous element in list"
        assert $("#listnavDescendants .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2 > Page1")

        and: "Descendants list should point to next element in list"
        assert $("#listnavDescendants .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page3")

        and: "Static list should point to first element in list"
        assert $("#listnavStatic .first").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages")

        and: "Looping Descendants list should point to previous element in list"
        assert $("#listnavDescendantsLoop .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2 > Page1")

        and: "Looping Descendants list should point to next element in list"
        assert $("#listnavDescendantsLoop .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page3")

        and: "Looping Descendants with Badge list should point to previous element in list"
        assert $("#listnavDescendantsLoopBadge .previous .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2 > Page1")

        and: "Looping Descendants with Badge list should point to next element in list"
        assert $("#listnavDescendantsLoopBadge .next .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page3")



        when: "I navigate to next page"
        waitForAuthorPreviewPageUrl("content/aemdesign-showcase/au/en/component/lists/list-nav/pages/page1/page3.html")

        then: "Descendants list should point to previous element in list"
        assert $("#listnavDescendants .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2 > Page2")

        and: "Descendants list should point to next element in list"
        assert $("#listnavDescendants .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page3 > Page1")

        and: "Static list should point to first element in list"
        assert $("#listnavStatic .first").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages")

        and: "Looping Descendants list should point to previous element in list"
        assert $("#listnavDescendantsLoop .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2 > Page2")

        and: "Looping Descendants list should point to next element in list"
        assert $("#listnavDescendantsLoop .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page3 > Page1")

        and: "Looping Descendants with Badge list should point to previous element in list"
        assert $("#listnavDescendantsLoopBadge .previous .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page2 > Page2")

        and: "Looping Descendants with Badge list should point to next element in list"
        assert $("#listnavDescendantsLoopBadge .next .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page3 > Page1")




        when: "I navigate to next page"
        waitForAuthorPreviewPageUrl("content/aemdesign-showcase/au/en/component/lists/list-nav/pages/page1/page3/page1.html")

        then: "Descendants list should point to previous element in list"
        assert $("#listnavDescendants .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page3")

        and: "Descendants list should point to next element in list"
        assert $("#listnavDescendants .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page2")

        and: "Static list should point to first element in list"
        assert $("#listnavStatic .first").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages")

        and: "Looping Descendants list should point to previous element in list"
        assert $("#listnavDescendantsLoop .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page3")

        and: "Looping Descendants list should point to next element in list"
        assert $("#listnavDescendantsLoop .next").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page2")

        and: "Looping Descendants with Badge list should point to previous element in list"
        assert $("#listnavDescendantsLoopBadge .previous .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page3")

        and: "Looping Descendants with Badge list should point to next element in list"
        assert $("#listnavDescendantsLoopBadge .next .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page2")




        when: "I navigate to next page"
        waitForAuthorPreviewPageUrl("content/aemdesign-showcase/au/en/component/lists/list-nav/pages/page2.html")

        then: "Descendants list should point to previous element in list"
        assert $("#listnavDescendants .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page3 > Page1")

        and: "Static list should point to previous element in list"
        assert $("#listnavStatic .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page1")

        and: "Looping Descendants list should point to previous element in list"
        assert $("#listnavDescendantsLoop .previous").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page3 > Page1")

        and: "Looping Descendants list should point to first element in list"
        assert $("#listnavDescendantsLoop .first").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1")

        and: "Looping Descendants with Badge list should point to previous element in list"
        assert $("#listnavDescendantsLoopBadge .previous .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1 > Page3 > Page1")

        and: "Looping Descendants with Badge list should point to first element in list"
        assert $("#listnavDescendantsLoopBadge .first .card-link").firstElement().getAttribute("textContent").trim().contains("List Nav > Pages > Page1")

    }


}
