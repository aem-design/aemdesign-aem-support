package specs.component.details.contactdetails

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ContactDetailsDAMPublishSpec extends ComponentSpec {

    String pathPage = "component/details/contact-details/contact-details1"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/contactdetails"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component variant Default using DAM Asset as Page Image in #viewport.label")
    def "Functionality of Component variant Default using DAM Asset as Page Image"() {

        given: '>the page hierarchy is created as "Components" > "Details" > "Contact Details"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contact-details1"
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

        and: "Has Image with Alt Title"
        assert $("${selector} img").attr("alt").trim() == "97236996"

        and: "Has Image with Page Image as Thumbnail from Asset Image"
        assert $("${selector} img").attr("src").contains("/content/dam/aemdesign-showcase/en/common/images/abstract.jpg/_jcr_content/renditions/cq5dam.thumbnail.319.319.png")

        and: "Has Title line with content"
        assert $("${selector} .title").text().trim() == "Author: Max Barrass"

        and: "Has Description line with content"
        assert $("${selector} .description").text().trim() == "Founder for AEM.Design max.barrass@gmail.com"

        and: "Has Description has field jobTitle"
        assert $("${selector} .description [itemprop=jobTitle]").text().trim() == "Founder"

        and: "Has Description has field employer"
        assert $("${selector} .description [itemprop=legalName]").text().trim() == "AEM.Design"

        and: "Has Description has field email"
        assert $("${selector} .description [itemprop=email]").text().trim() == "max.barrass@gmail.com"


        where:
        viewport << viewPorts
    }


}
