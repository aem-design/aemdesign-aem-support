package specs.component.details.pagedetails


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class PageDetailsPublishSpec extends ComponentSpec {

    String pathPage = "component/details/page-details"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/pagedetails"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component with Excluded Components in #viewport.label")
    def "Functionality of Component with Excluded Components"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Page Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#page-details1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has Breadcrumb hidden"
        assert $("${selector} .breadcrumb").empty

        and: "Has Toolbar hidden"
        assert $("${selector} .navbar").empty

        and: "Has Parsys hidden"
        assert $("${selector} .text").empty

        where:
        viewport << viewPorts
    }


    @Unroll("Functionality of Component with Background and Included Components in #viewport.label")
    def "Functionality of Component with Background and Included Components"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Page Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#page-details2"
        def selectorContainer = "#contentblock2 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        report("I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: 'Section should have a background image'
        assert $(selector).css("background-image").indexOf("/content/dam/aemdesign-showcase/en/components/media/image/city2.jpg") > 0

        and: "Has Breadcrumb visible"
        assert !$("${selector} nav.breadcrumb").empty

        and: "Has Toolbar has sample content"
        assert $("${selector} ol.breadcrumb li").first().text().trim() == "AEM.Design Showcase"

        and: "Has Toolbar visible"
        assert !$("${selector} .navbar").empty

        and: "Has Toolbar has sample content"
        assert $("${selector} #text_in_toolbar").text().trim() == "Text in Toolbar"

        and: "Has Parsys visible"
        assert !$("${selector} .text").empty

        and: "Has Parsys has sample content"
        assert $("${selector} #text_in_parsys").text().trim() == "Text in Parsys"


        where:
        viewport << viewPorts
    }

    @Unroll("Functionality of Component with Hidden Variant in #viewport.label")
    def "Functionality of Component with Hidden Variant"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Page Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#page-details3"
        def selectorContainer = "#contentblock3"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Has Breadcrumb hidden"
        assert $("${selector} nav.breadcrumb").empty

        and: "Has Toolbar hidden"
        assert $("${selector} .navbar").empty

        and: "Has Parsys hidden"
        assert $("${selector} .text").empty

        where:
        viewport << viewPorts
    }

    @Unroll("Functionality of Component with Background and Inherited Toolbar in #viewport.label")
    def "Functionality of Component with Background and Inherited Toolbar"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Page Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#page-details4"
        def selectorContainer = "#contentblock4 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        report("I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: 'Section should have a background image'
        assert $(selector).css("background-image").indexOf("/content/dam/aemdesign-showcase/en/components/media/image/city2.jpg") > 0

        and: "Has Breadcrumb visible"
        assert !$("${selector} nav.breadcrumb").empty

        and: "Has Toolbar has sample content"
        assert $("${selector} nav.breadcrumb li").first().text().trim() == "AEM.Design Showcase"

        and: "Has Toolbar visible"
        assert !$("${selector} .navbar").empty

        and: "Has Toolbar has sample content"
        assert $("${selector} #text_in_parent_toolbar").text().trim() == "Text in Parent Toolbar"

        and: "Has Parsys visible"
        assert !$("${selector} .text").empty

        and: "Has Custom Title"
        assert $("${selector} #text_in_parsys").text().trim() == "Text in Parsys"

        and: "Has Custom Description"
        assert $("${selector} header h1").text().trim() == "Page Title 4"
        assert $("${selector} header .description").text().trim() == "Custom Description"


        where:
        viewport << viewPorts
    }


    @Unroll("Page Details: Default without included components and hidden Description in #viewport.label")
    def "Page Details: Default without included components and hidden Description"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Page Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#page-details5"
        def selectorContainer = "#contentblock5"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has Breadcrumb hidden"
        assert $("${selector} nav.breadcrumb").empty

        and: "Has Toolbar hidden"
        assert $("${selector} .navbar").empty

        and: "Has Parsys hidden"
        assert $("${selector} .text").empty

        and: "Has Description hidden"
        assert $("${selector} .description").empty

        and: "Has Title showing"
        assert !$("${selector} h1").empty

        where:
        viewport << viewPorts
    }

    @Unroll("Page Details: Default without included components and hidden Title and Description in #viewport.label")
    def "Page Details: Default without included components and hidden Title and Description"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Page Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#page-details6"
        def selectorContainer = "#contentblock6"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has Breadcrumb hidden"
        assert $("${selector} nav.breadcrumb").empty

        and: "Has Toolbar hidden"
        assert $("${selector} .navbar").empty

        and: "Has Parsys hidden"
        assert $("${selector} .text").empty

        and: "Has Description hidden"
        assert $("${selector} .description").empty

        and: "Has Description hidden"
        assert $("${selector} .description").empty

        and: "Has Title showing"
        assert $("${selector} h1").empty

        where:
        viewport << viewPorts
    }

    def "Page Details: Default metadata added to page"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Page Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#page-details"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Page has metadata field og:title from Details component"
        assert $("meta[property='og:title']").attr("content").equals("Page Title")

        and: "Page has metadata field og:type"
        assert $("meta[property='og:type']").attr("content").equals("article")

        and: "Page has metadata field og:image"
        assert $("meta[property='og:image']").attr("content").contains("page-details.thumb.")

        and: "Page has metadata field og:url"
        assert $("meta[property='og:url']").attr("content").contains("details/page-details.html")

        and: "Page has canonical link"
        assert $("link[rel='canonical']").attr("href").contains("details/page-details.html")

    }

}
