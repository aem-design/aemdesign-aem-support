package specs.component.lists.langnav


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class LangNavPublishSpec extends ComponentSpec {

    String pathPage = "component/lists/language-navigation"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/langnav"

    def setupSpec() {
        loginAsAdmin()
    }


    @Unroll("Lang Nav: Default with Languages Available in #viewport.label")
    def "Lang Nav: Default with Languages Available"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Language Navigation"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#langnav1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has two list items"
        assert $("${selector} li").size() == 2

        and: "Has first item"
        assert $("${selector} li").firstElement().getAttribute("textContent").trim().contains("EN")

        and: "Has last item"
        assert $("${selector} li").lastElement().getAttribute("textContent").trim().contains("RU")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Lang Nav: Default with Languages Not Available in #viewport.label")
    def "Lang Nav: Default with Languages Not Available"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Language Navigation" > "One Language"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#langnav1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPageUrl("content/aemdesign-showcase/au/en/component/lists/language-navigation/one-language.html")

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has two list items"
        assert $("${selector} li").size() == 1

        and: "Has first item"
        assert $("${selector} li").firstElement().getAttribute("textContent").trim().contains("EN")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }



    @Unroll("Lang Nav: Default with Languages Not Available and Showing Language Root #viewport.label")
    def "Lang Nav: Default with Languages Not Available and Showing Language Root"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Language Navigation" > "One Language"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#langnav2"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPageUrl("content/aemdesign-showcase/au/en/component/lists/language-navigation/one-language.html")

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has two list items"
        assert $("${selector} li").size() == 2

        and: "Has first item"
        assert $("${selector} li").firstElement().getAttribute("textContent").trim().contains("EN")

        and: "Has last item"
        assert $("${selector} li").lastElement().getAttribute("textContent").trim().contains("Russian")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()

    }


}
