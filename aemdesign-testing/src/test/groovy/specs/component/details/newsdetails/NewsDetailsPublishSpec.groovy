package specs.component.details.newsdetails


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Stepwise
class NewsDetailsPublishSpec extends ComponentSpec {

    String pathPage = "component/details/news-details"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/newsdetails"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component with Excluded Components in #viewport.label")
    def "Functionality of Component with Excluded Components"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Page Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#news-details1"
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


    @Unroll("Functionality of Component with Included Components in #viewport.label")
    def "Functionality of Component with Included Components"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Page Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#news-details2"
        def selectorContainer = "#contentblock2 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()
        report("I am on the component showcase page")

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

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

    @Unroll("Functionality of News Details Component with Background and Formatted Date in #viewport.label")
    def "Functionality of News Details Component with Background and Formatted Date"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "News Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#news-details3"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "The component should have the date in EEEE dd MMMM YYYY format"
        def dateString =  $("${selector} .published time").text()
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.ENGLISH)
        assert LocalDate.parse(dateString, format)

        and: "The component should display tags"
        assert $("${selector} .tags span" , 0).text().contains("News Page") &&
                $("${selector} .tags span" , 1).text().contains("Event")

        and: "The component should have a title"
        assert  $("${selector} h1").text().contains("News Details Title")

        and: "The component should have a thumbnail image"
        assert $("${selector}").css("background-image").contains("city2.jpg")

        and: "Has Breadcrumb visible"
        assert !$("${selector} nav.breadcrumb").empty

        and: "Has Toolbar has sample content"
        assert $("${selector} ol.breadcrumb li").first().text().trim() == "AEM.Design Showcase"

        where:
        viewport << viewPorts
    }

}
