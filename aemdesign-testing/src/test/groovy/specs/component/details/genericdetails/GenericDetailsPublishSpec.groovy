package specs.component.details.genericdetails

import spock.lang.IgnoreRest
import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class GenericDetailsPublishSpec extends ComponentSpec {

    String pathPage = "component/details/generic-details"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/genericdetails"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Default without included components in #viewport.label")
    def "Default without included components"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Generic Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#generic-details1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has Breadcrumb hidden"
        assert $("${selector} .breadcrumb").isEmpty() == true

        and: "Has Toolbar hidden"
        assert $("${selector} .navbar").isEmpty() == true

        and: "Has Parsys hidden"
        assert $("${selector} .text").isEmpty() == true

        where:
        viewport << getViewPorts()
    }

    @Unroll("Default with included components in #viewport.label")
    def "Default with included components"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Generic Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#generic-details2"

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
        assert $("${selector} nav.breadcrumb").isEmpty() == false

        and: "Has Breadcrumb has sample content"
        assert $("${selector} nav.breadcrumb li").first().text().trim() == "AEM.Design Showcase"

        and: "Has Breadcrumb has Navigation Title"
        assert $("${selector} nav.breadcrumb li").last().text().trim() == "Page Properties - Navigation Title"

        and: "Has Toolbar has sample content"
        assert compareInnerTextContains("${selector} #text_in_toolbar", "Text in Toolbar")

        and: "Has Page Date Value"
        assert compareInnerTextContains("${selector} .pagedate time", "September 15, 2019")

        and: "Has Title"
        assert compareInnerTextContains("${selector} header > h1", "Page Properties - Page Title")

        and: "Has Description"
        assert compareInnerTextContains("${selector} header > .description", "Page Properties - Description")

        and: "Has Parsys Text"
        assert compareInnerTextContains("${selector} #text_in_parsys","Text in Parsys")


        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component with Hidden Variant in #viewport.label")
    def "Functionality of Component with Hidden Variant"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Generic Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#generic-details3"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should be empty"
        assert $(selector).children().size() == 0

        and: "Should have hidden attribute"
        assert $("[hidden]${selector}").size() == 1

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component with Background and Inherited Toolbar in #viewport.label")
    def "Functionality of Component with Background and Inherited Toolbar"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Generic Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#generic-details4"
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

        and: "Has Breadcrumb has sample content"
        assert $("${selector} nav.breadcrumb li").first().text().trim() == "AEM.Design Showcase"

        and: "Has Breadcrumb has Navigation Title"
        assert $("${selector} nav.breadcrumb li").last().text().trim() == "Page Properties - Navigation Title"

        and: "Has Toolbar has sample content"
        assert compareInnerTextContains("${selector} #text_in_parent_toolbar", "Text in Parent Toolbar")

        and: "Has Page Date Value"
        assert compareInnerTextContains("${selector} .pagedate time", "September 15, 2019")

        and: "Has Title"
        assert compareInnerTextContains("${selector} header .card-title", "Page Properties - Page Title")

        and: "Has Description"
        assert compareInnerTextContains("${selector} header .card-text", "Page Properties - Description")

        and: "Has Parsys Text"
        assert compareInnerTextContains("${selector} #text_in_parsys", "Text in Parsys")


        where:
        viewport << getViewPorts()
    }


    @Unroll("Page Details: Default without included components and hidden Description in #viewport.label")
    def "Page Details: Default without included components and hidden Description"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Generic Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#generic-details5"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has Breadcrumb hidden"
        assert $("${selector} nav.breadcrumb").isEmpty() == true

        and: "Has Toolbar hidden"
        assert $("${selector} .navbar").isEmpty() == true

        and: "Has Page Date visible"
        assert $("${selector} .pagedate time").isEmpty() == true

        and: "Has Title showing"
        assert $("${selector} header .card-title").isEmpty() == false

        and: "Has Description hidden"
        assert $("${selector} header .card-text").isEmpty() == true

        and: "Has Parsys hidden"
        assert $("${selector} .text").isEmpty() == true

        where:
        viewport << getViewPorts()
    }

    @Unroll("Page Details: Default without included components and hidden Title and Description in #viewport.label")
    def "Page Details: Default without included components and hidden Title and Description"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Generic Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#generic-details6"
        def selectorContainer = "#contentblock6"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has Breadcrumb hidden"
        assert $("${selector} nav.breadcrumb").isEmpty() == true

        and: "Has Toolbar hidden"
        assert $("${selector} .navbar").isEmpty() == true

        and: "Has Page Date visible"
        assert $("${selector} .pagedate time").isEmpty() == false

        and: "Has Title showing"
        assert $("${selector} h1").isEmpty() == true

        and: "Has Description hidden"
        assert $("${selector} .description").isEmpty() == true

        and: "Has Parsys hidden"
        assert $("${selector} .text").isEmpty() == true

        where:
        viewport << getViewPorts()
    }

    @Unroll("Custom Variant using Field Template Card with selected Fields Subtitle, Title, Description and Action in #viewport.label")
    def "Custom Variant using Field Template Card with selected Fields Subtitle, Title, Description and Action"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Generic Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#generic-details7"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has Sub Title"
        assert compareInnerTextContains("${selector} .card-subtitle", "Page Properties - Subtitle")

        and: "Has Title"
        assert compareInnerTextContains("${selector} .card-title", "Page Properties - Page Title")

        and: "Has Description"
        assert compareInnerTextContains("${selector} .card-text", "Page Properties - Description")

        and: "Has Action"
        assert compareInnerTextContains("${selector} .card-action", "Page Properties - Navigation Title")

        where:
        viewport << getViewPorts()
    }

    @Unroll("Variant Template with Breadcrumb, SubTitle, Title, Description and Action in #viewport.label")
    def "Variant Template with Breadcrumb, SubTitle, Title, Description and Action"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Generic Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#generic-details8"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has Sub Title visible"
        assert $("${selector} .breadcrumb").isEmpty() == false

        and: "Has Title visible"
        assert $("${selector} .card-subtitle").isEmpty() == false

        and: "Has Title visible"
        assert $("${selector} .card-title").isEmpty() == false

        and: "Has Description visible"
        assert $("${selector} .card-text").isEmpty() == false

        and: "Has Action visible"
        assert $("${selector} .card-action").isEmpty() == false

        where:
        viewport << getViewPorts()
    }

    @Unroll("Default without included components and Page Date, Custom Title and Description in #viewport.label")
    def "Default without included components and Page Date, Custom Title and Description"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Generic Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#generic-details9"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has Breadcrumb hidden"
        assert $("${selector} nav.breadcrumb").isEmpty() == true

        and: "Has Toolbar hidden"
        assert $("${selector} .navbar").isEmpty() == true

        and: "Has Page Date visible"
        assert $("${selector} .pagedate time").isEmpty() == false

        and: "Has Title showing"
        assert $("${selector} .card-title").isEmpty() == false

        and: "Has Description hidden"
        assert $("${selector} .card-text").isEmpty() == false

        and: "Has Parsys hidden"
        assert $("${selector} .text").isEmpty() == true

        where:
        viewport << getViewPorts()
    }



    def "Page Details: Default metadata added to page"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Generic Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#generic-details"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Page has metadata field og:title from Details component"
        assert $("meta[property='og:title']").attr("content").equals("Page Properties - Page Title")

        and: "Page has metadata field og:type"
        assert $("meta[property='og:type']").attr("content").equals("article")

        and: "Page has metadata field og:image"
        assert $("meta[property='og:image']").attr("content").contains("generic-details.thumb.")

        and: "Page has metadata field og:url"
        assert $("meta[property='og:url']").attr("content").contains("details/generic-details.html")

        and: "Page has canonical link"
        assert $("link[rel='canonical']").attr("href").contains("details/generic-details.html")

    }

}
