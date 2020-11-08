package specs.component.media.image

import spock.lang.IgnoreRest
import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ImagePublishSpec extends ComponentSpec {

    String pathPage = "component/media/image"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/image"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} picture img").attr("src").contains("/city3.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")


        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "319",
                "SM"  : "1280",
                "MD"  : "1280",
                "LG"  : "1280",
                "XLG" : "1280",
                "XXLG": "1280"
        ]
        assert $("${selector} picture img")
                .getAttribute("currentSrc")
                .contains(
                expectSizes.get(viewport.label)
        )

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Image Only in #viewport.label")
    def "Functionality of Component Variant: Image Only"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image2"
        def selectorContainer = "#contentblock2 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} picture img").attr("src").contains("/city3.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")


        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "319",
                "SM"  : "1280",
                "MD"  : "1280",
                "LG"  : "1280",
                "XLG" : "1280",
                "XXLG": "1280"
        ]
        assert $("${selector} picture img")
                .getAttribute("currentSrc")
                .contains(
                expectSizes.get(viewport.label)
        )

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Card in #viewport.label")
    def "Functionality of Component Variant: Card"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image3"
        def selectorContainer = "#contentblock3 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} img").attr("src").contains("/city3.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")


        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "1280",
                "SM"  : "1280",
                "MD"  : "1280",
                "LG"  : "1280",
                "XLG" : "1280",
                "XXLG": "1280"
        ]
        assert $("${selector} img")
                .getAttribute("src")
                .contains(
                expectSizes.get(viewport.label)
        )

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Image Title and Description in #viewport.label")
    def "Functionality of Component Variant: Image Title and Description"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image4"
        def selectorContainer = "#contentblock4 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} picture img").attr("src").contains("/city3.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")


        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "319",
                "SM"  : "1280",
                "MD"  : "1280",
                "LG"  : "1280",
                "XLG" : "1280",
                "XXLG": "1280"
        ]
        assert $("${selector} picture img")
                .getAttribute("currentSrc")
                .contains(
                expectSizes.get(viewport.label)
        )

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Functionality of Component Variant: Default with Licensed Image in #viewport.label")
    def "Functionality of Component Variant: Default with Licensed Image"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image5"
        def selectorContainer = "#contentblock5 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} picture img").attr("src").contains("/city1.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")


        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "319",
                "SM"  : "1280",
                "MD"  : "1280",
                "LG"  : "1280",
                "XLG" : "1280",
                "XXLG": "1280"
        ]
        assert $("${selector} picture img")
                .getAttribute("currentSrc")
                .contains(
                expectSizes.get(viewport.label)
        )

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Image Only with Licensed Image in #viewport.label")
    def "Functionality of Component Variant: Image Only with Licensed Image"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image6"
        def selectorContainer = "#contentblock6 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} picture img").attr("src").contains("/city1.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")


        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "319",
                "SM"  : "1280",
                "MD"  : "1280",
                "LG"  : "1280",
                "XLG" : "1280",
                "XXLG": "1280"
        ]
        assert $("${selector} picture img")
                .getAttribute("currentSrc")
                .contains(
                expectSizes.get(viewport.label)
        )

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Card with Licensed Image in #viewport.label")
    def "Functionality of Component Variant: Card with Licensed Image"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image7"
        def selectorContainer = "#contentblock7 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} img").attr("src").contains("/city1.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")


        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "1280",
                "SM"  : "1280",
                "MD"  : "1280",
                "LG"  : "1280",
                "XLG" : "1280",
                "XXLG": "1280"
        ]
        assert $("${selector} img")
                .getAttribute("currentSrc")
                .contains(
                expectSizes.get(viewport.label)
        )

        and: "Has title line"
        assert $("${selector} .card-title").text() == "Licensed Asset Title"

        and: "Has description line"
        assert $("${selector} .card-text").text() == "Licensed Asset Description"

        and: "Has license line"
        assert $("${selector} .license").text().trim() == "© 2017 Creator Contributor Copyright Image Owner Copyright Owner"


        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Image Title and Description with Licensed Image in #viewport.label")
    def "Functionality of Component Variant: Image Title and Description with Licensed Image"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image8"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} picture img").attr("src").contains("/city1.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "319",
                "SM"  : "1280",
                "MD"  : "1280",
                "LG"  : "1280",
                "XLG" : "1280",
                "XXLG": "1280"
        ]
        assert $("${selector} picture img")
                .getAttribute("currentSrc")
                .contains(
                expectSizes.get(viewport.label)
        )

        and: "Has title line"
        assert $("${selector} figure figcaption").text() == "Licensed Asset Title"

        and: "Has description line"
        assert $("${selector} figure .description")[0].getAttribute("innerText").equals("Licensed Asset Description")

        and: "Has license line"
        assert $("${selector} figure .license")[0].getAttribute("innerText").trim() == "© 2017 Creator Contributor Copyright Image Owner Copyright Owner"


        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Functionality of Component Variant: Default Empty in #viewport.label")
    def "Functionality of Component Variant: Default Empty"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image9"
        def selectorContainer = "#contentblock9 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should be empty"
        assert $(selector).children().size() == 0

        and: "Should have empty attribute"
        assert $(selector + "[empty]").size() == 1

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Functionality of Component Variant: Default with Overrides in #viewport.label")
    def "Functionality of Component Variant: Default with Overrides"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image10"
        def selectorContainer = "#contentblock10 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} picture img").attr("src").contains("/city2.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "319",
                "SM"  : "1280",
                "MD"  : "1280",
                "LG"  : "1280",
                "XLG" : "1280",
                "XXLG": "1280"
        ]
        assert $("${selector} picture img")
                .getAttribute("currentSrc")
                .contains(
                expectSizes.get(viewport.label)
        )

        and: "Has overridden title"
        assert $("${selector} img").attr("title") == "Can override Image Title"

        and: "Has overridden alt"
        assert $("${selector} img").attr("alt") == "Can override Image Headline"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Functionality of Component Variant: Image Only with Overrides in #viewport.label")
    def "Functionality of Component Variant: Image Only with Overrides"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image11"
        def selectorContainer = "#contentblock11 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} picture img").attr("src").contains("/city2.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "319",
                "SM"  : "1280",
                "MD"  : "1280",
                "LG"  : "1280",
                "XLG" : "1280",
                "XXLG": "1280"
        ]
        assert $("${selector} picture img")
                .getAttribute("currentSrc")
                .contains(
                expectSizes.get(viewport.label)
        )

        and: "Has overridden title"
        assert $("${selector} img").attr("title") == "Can override Image Title"

        and: "Has overridden alt"
        assert $("${selector} img").attr("alt") == "Can override Image Headline"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Functionality of Component Variant: Card with Overrides in #viewport.label")
    def "Functionality of Component Variant: Card with Overrides"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image12"
        def selectorContainer = "#contentblock12 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} img").attr("src").contains("/city2.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "1280",
                "SM"  : "1280",
                "MD"  : "1280",
                "LG"  : "1280",
                "XLG" : "1280",
                "XXLG": "1280"
        ]
        assert $("${selector} img")
                .getAttribute("currentSrc")
                .contains(
                expectSizes.get(viewport.label)
        )

        and: "Has overridden title with overridden title"
        assert $("${selector} .card-title").text() == "Can override Image Title"

        and: "Has overridden description with overridden title"
        assert $("${selector} .card-text").text() == "Can override Image Description"

        and: "Has image with overridden title with overridden title"
        assert $("${selector} img").attr("title") == "Can override Image Title"

        and: "Has image with overridden alt with overridden title"
        assert $("${selector} img").attr("alt") == "Can override Image Headline"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Image Title and Description with Overrides in #viewport.label")
    def "Functionality of Component Variant: Image Title and Description with Overrides"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image13"
        def selectorContainer = "#contentblock13 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} picture img").attr("src").contains("/city2.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "319",
                "SM"  : "1280",
                "MD"  : "1280",
                "LG"  : "1280",
                "XLG" : "1280",
                "XXLG": "1280"
        ]
        assert $("${selector} picture img")
                .getAttribute("currentSrc")
                .contains(
                expectSizes.get(viewport.label)
        )

        and: "Has title line with overridden title"
        assert $("${selector} figure figcaption").text() == "Can override Image Title"

        and: "Has description line with overridden title"
        assert $("${selector} figure .description")[0].getAttribute("innerText") == "Can override Image Description"

        and: "Has image with overridden title with overridden title"
        assert $("${selector} img").attr("title") == "Can override Image Title"

        and: "Has image with overridden alt with overridden title"
        assert $("${selector} img").attr("alt") == "Can override Image Headline"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Image Only Empty in #viewport.label")
    def "Functionality of Component Variant: Image Only Empty"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image14"
        def selectorContainer = "#contentblock14 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should be empty"
        assert $(selector).children().size() == 0

        and: "Should have empty attribute"
        assert $(selector + "[empty]").size() == 1

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Functionality of Component Variant: Card Empty in #viewport.label")
    def "Functionality of Component Variant: Card Empty"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image15"
        def selectorContainer = "#contentblock15 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should be empty"
        assert $(selector).children().size() == 0

        and: "Should have empty attribute"
        assert $(selector + "[empty]").size() == 1

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Functionality of Component Variant: Image Title and Description Empty in #viewport.label")
    def "Functionality of Component Variant: Image Title and Description Empty"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image16"
        def selectorContainer = "#contentblock16 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should be empty"
        assert $(selector).children().size() == 0

        and: "Should have empty attribute"
        assert $(selector + "[empty]").size() == 1

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Functionality of Component Variant: Default with Rendition in #viewport.label")
    def "Functionality of Component Variant: Default with Rendition"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image17"
        def selectorContainer = "#contentblock17 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} img").attr("src").contains("/city3.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")


        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "319",
                "SM"  : "319",
                "MD"  : "319",
                "LG"  : "319",
                "XLG" : "319",
                "XXLG": "319"
        ]
        assert $("${selector} img")
                .getAttribute("src")
                .contains(
                expectSizes.get(viewport.label)
        )

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Functionality of Component Variant: Image Title and Description with Asset Metadata in #viewport.label")
    def "Functionality of Component Variant: Image Title and Description with Asset Metadata"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image18"
        def selectorContainer = "#contentblock18 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} picture img").attr("src").contains("/city2.jpg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "319",
                "SM"  : "1280",
                "MD"  : "1280",
                "LG"  : "1280",
                "XLG" : "1280",
                "XXLG": "1280"
        ]
        assert $("${selector} picture img")
                .getAttribute("currentSrc")
                .contains(
                expectSizes.get(viewport.label)
        )

        and: "Has title line"
        assert $("${selector} figure figcaption").text() == "Asset Title"

        and: "Has description line embedded in Asset"
        assert $("${selector} figure div.description")[0].getAttribute("innerText") == "Asset Description";

        and: "Has no license line"
        assert $("${selector} .license").size() == 0


        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Image Title and Description with Adaptive Image in #viewport.label")
    def "Functionality of Component Variant: Image Title and Description with Adaptive Image"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image19"
        def selectorContainer = "#contentblock19 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should be using sample image as source"
        assert $(selector).attr("data-analytics-filename").endsWith("/city2.jpg")

        and: "Should be rendering sample image using current resource"
        assert $("${selector} picture img").attr("src").contains("/city2")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "480",
                "SM"  : "640",
                "MD"  : "1024",
                "LG"  : "1440",
                "XLG" : "1920",
                "XXLG": "2048"
        ]
        assert $("${selector} picture img")
                .getAttribute("currentSrc")
                .contains(
                expectSizes.get(viewport.label)
        )

        and: "Has title line"
        assert $("${selector} figure figcaption").text() == "Asset Title"

        and: "Has description line embedded in Asset"
        assert $("${selector} figure div.description")[0].getAttribute("innerText") == "Asset Description"

        and: "Has no license line"
        assert $("${selector} .license").size() == 0


        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component Variant: Default with Generated Image in #viewport.label")
    def "Functionality of Component Variant: Default with Generated Image"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image20"
        def selectorContainer = "#contentblock20 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $("${selector} img").attr("src").contains(".img.jpeg/")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Adaptive Image Sizing in #viewport.label")
    def "Functionality of Adaptive Image Sizing"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image19"
        def selectorContainer = "#contentblock19 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)


        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "480",
                "SM"  : "640",
                "MD"  : "1024",
                "LG"  : "1440",
                "XLG" : "1920",
                "XXLG": "2048"
        ]
        def imgUrl = $("${selector} picture img").getAttribute("currentSrc")
        assert imgUrl.contains(expectSizes.get(viewport.label))
//         assert expectSizes.get(viewport.label) ==  getImageWidth(imgUrl).toString()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Default Variant with Image Option - Manual MediaQuery with Rendition in #viewport.label")
    def "Functionality of Default Variant with Image Option - Manual MediaQuery with Rendition"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image24"
        def selectorContainer = "#contentblock24 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)


        and: "Image dimension should match media query"
        def expectSizes = [
                "XS"  : "1280",
                "SM"  : "1280",
                "MD"  : "aem-design-logo",
                "LG"  : "aem-design-logo",
                "XLG" : "aem-design-logo",
                "XXLG": "aem-design-logo"
        ]
        def imgUrl = $("${selector} picture img").getAttribute("currentSrc")
        assert imgUrl.contains(expectSizes.get(viewport.label))
//         assert expectSizes.get(viewport.label) ==  getImageWidth(imgUrl).toString()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

}
