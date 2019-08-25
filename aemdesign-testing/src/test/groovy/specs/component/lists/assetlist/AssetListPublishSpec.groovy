package specs.component.lists.assetlist

import spock.lang.IgnoreRest
import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class AssetListPublishSpec extends ComponentSpec {

    String pathPage = "component/lists/asset-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/assetlist"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component with Default variant and Static List in #viewport.label")
    def "Functionality of Component with Default variant and Static List"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Asset List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Should have one img in picture elements"
        assert $("${selector} picture img").size() == 1

        and: "Should have data-href attribute"
        assert $("${selector} img").attr("data-href") == "/content/dam/aemdesign-showcase/en/common/aem-design.png"

        and: "Should have src using renditions"
        assert $("${selector} img").attr("src").contains("_jcr_content")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component with Default variant and Static List with Image Option Rendition in #viewport.label")
    def "Functionality of Component with Default variant and Static List with Image Option Rendition"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Asset List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist2"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Should have only img"
        assert $("${selector} img").size() == 1

        and: "Should have data-href attribute"
        assert $("${selector} img").attr("data-href") == "/content/dam/aemdesign-showcase/en/common/aem-design.png"

        and: "Should have src using renditions"
        assert $("${selector} img").attr("src").contains("_jcr_content")


        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component with Default variant and Static List with Image Option Adaptive in #viewport.label")
    def "Functionality of Component with Default variant and Static List with Image Option Adaptive"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Asset List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist3"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Should have one img in picture elements"
        assert $("${selector} picture img").size() == 1

        and: "Should have data-href attribute"
        assert $("${selector} img").attr("data-href") == "/content/dam/aemdesign-showcase/en/common/aem-design.png"

        and: "Should have src using img selector"
        assert $("${selector} img").attr("src").contains(".img.")


        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component with Default variant and Static List with Multiple Images in #viewport.label")
    def "Functionality of Component with Default variant and Static List with Multiple Images"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Asset List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist4"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Should have four img in picture elements"
        assert $("${selector} picture img").size() == 4


        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component with Default variant and Child List in #viewport.label")
    def "Functionality of Component with Default variant and Child List"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Asset List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist5"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Should have three img in picture elements"
        assert $("${selector} picture img").size() == 5


        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component with Default variant and Descendants List in #viewport.label")
    def "Functionality of Component with Default variant and Descendants List"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Asset List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist6"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Should have three img in picture elements"
        assert $("${selector} picture img").size() == 5

        and: "Should have one video"
        assert $("${selector} video source").getAttribute("type") == "video/mp4"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component with Default variant and Static List with Multiple Asset Types in #viewport.label")
    def "Functionality of Component with Default variant and Static List with Multiple Asset Types"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Asset List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist7"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Should have four img in picture elements"
        assert $("${selector} picture img").size() == 4

        and: "Should have one video"
        assert $("${selector} video source").getAttribute("type") == "video/mp4"

        and: "Should have one audio"
        assert $("${selector} audio source").getAttribute("type") == "audio/mpeg"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


}
