package specs.component.lists.locationlist

import spock.lang.IgnoreRest
import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class LocationListPublishSpec extends ComponentSpec {

    String pathPage = "component/lists/location-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/locationlist"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Location List: Default variant using Default badge in #viewport.label")
    def "Location List: Default variant using Default badge"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Location List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#locationlist1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has five list items"
        assert $("${selector} li").size() == 6

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Location List: Map Data in #viewport.label")
    def "Location List: Map Data"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Location List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#locationlist2"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has five features items"
        assert js.exec(" return locationlist2.features.length == 6;")

        and: "Map has been created"
        assert js.exec(" return AEMDESIGN.components.locationlist.googleMapsInstances[\$(\"#locationlist2\").data(\"map-index\")]?true:false;")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @IgnoreRest
    @Unroll("Location List: Card with Image, Title, Category, Description and Action badge in #viewport.label")
    def "Location List: Card with Image, Title, Category, Description and Action badge"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Location List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#locationlist3"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has six list items"
        assert $("${selector} > div > ul > li").size() == 6

        and: "With image"
        assert $("${selector} .card img").size() == 6

        and: "With Title"
        assert $("${selector} .card .card-title").size() == 6

        and: "With Category"
        assert $("${selector} .card .card-taglist").size() == 6

        and: "With Description"
        assert $("${selector} .card .card-text").size() == 6

        and: "And Action"
        assert $("${selector} .card .card-action").size() == 6

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

}
