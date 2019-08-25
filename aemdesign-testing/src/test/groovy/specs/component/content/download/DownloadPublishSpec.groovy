package specs.component.content.download


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class DownloadPublishSpec extends ComponentSpec {

    String pathPage = "component/content/download"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/download"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "When asset is downloaded it matches repository asset file signature"
        assert verifyAssetDownload($(selector).firstElement().getAttribute("href"))

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Simple in #viewport.label")
    def "Functionality of Component Variant: Simple"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download2"
        def selectorContainer = "#contentblock2 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Has sample icon line"
        assert $("${selector} .icon").size() != 0

        and: "Has title line"
        assert $("${selector} div.title").text() == "Asset Title"

        and: "Has description line"
        assert $("${selector} div.description").text() == "Asset Description"

        and: "Has info line"
        assert $("${selector} div.info").text().contains("jpeg file")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Card in #viewport.label")
    def "Functionality of Component Variant: Card"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download3"
        def selectorContainer = "#contentblock3 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Has sample icon line"
        assert $("${selector} .icon").size() != 0

        and: "Has title line"
        assert $("${selector} .card-title").text() == "Asset Title"

        and: "Has description line"
        assert $("${selector} .card-description").text() == "Asset Description"

        and: "Has info line"
        assert $("${selector} .card-info").text().contains("jpeg file")

        and: "Has button line"
        assert $("${selector} .btn").text().toLowerCase() == "Download".toLowerCase()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Variant: Default with Licensed Image in #viewport.label")
    def "Functionality of Component Variant: Default with Licensed Image"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download4"
        def selectorContainer = "#contentblock4 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Licensed Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Has license line"
        assert $("${selector} .license").text().trim() == "© 2017 Creator Contributor Copyright Image Owner Copyright Owner"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Simple with Licensed Image in #viewport.label")
    def "Functionality of Component Variant: Simple with Licensed Image"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download5"
        def selectorContainer = "#contentblock5 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Licensed Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Has sample icon line"
        assert $("${selector} .icon").size() != 0

        and: "Has title line"
        assert $("${selector} div.title").text() == "Licensed Asset Title"

        and: "Has description line"
        assert $("${selector} div.description").text() == "Licensed Asset Description"

        and: "Has info line"
        assert $("${selector} div.info").text().contains("jpeg file")

        and: "Has license line"
        assert $("${selector} .license").text().trim() == "© 2017 Creator Contributor Copyright Image Owner Copyright Owner"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Card with Licensed Image in #viewport.label")
    def "Functionality of Component Variant: Card with Licensed Image"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download6"
        def selectorContainer = "#contentblock6 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Licensed Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Has sample icon line"
        assert $("${selector} span").size() != 0

        and: "Has title line"
        assert $("${selector} .card-title").text() == "Licensed Asset Title"

        and: "Has description line"
        assert $("${selector} .card-description").text() == "Licensed Asset Description"

        and: "Has info line"
        assert $("${selector} .card-info").text().contains("jpeg file")

        and: "Has button line"
        assert $("${selector} .btn").text().toLowerCase() == "Download".toLowerCase()

        and: "Has license line"
        assert $("${selector} .license").text().trim() == "© 2017 Creator Contributor Copyright Image Owner Copyright Owner"

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Default without Authored Content in #viewport.label")
    def "Functionality of Component Variant: Default without Authored Content"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
            def selector = "#download7"
        def selectorContainer = "#contentblock7 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have a placehoder image"
        assert $("${selector}[empty]").getAttribute("innerHTML").trim() == ""

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Variant: Default with Title and Description in #viewport.label")
    def "Functionality of Component Variant: Default with Title and Description"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download8"
        def selectorContainer = "#contentblock8 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Can override non-Licensed Image Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Has license line"
        assert $("${selector} .license").isEmpty()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Simple with Title and Description in #viewport.label")
    def "Functionality of Component Variant: Simple with Title and Description"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download9"
        def selectorContainer = "#contentblock9 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Can override non-Licensed Image Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Has sample icon line"
        assert $("${selector} .icon").size() != 0

        and: "Has title line"
        assert $("${selector} div.title").text() == "Can override non-Licensed Image Title"

        and: "Has description line"
        assert $("${selector} div.description").text() == "Can override non-Licensed Image Description"

        and: "Has info line"
        assert $("${selector} div.info").text().contains("jpeg file")

        and: "Has license line"
        assert $("${selector} .license").isEmpty()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Card with Title and Description in #viewport.label")
    def "Functionality of Component Variant: Card with Title and Description"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download10"
        def selectorContainer = "#contentblock10 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Can override non-Licensed Image Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Has sample icon line"
        assert $("${selector} .icon").size() != 0

        and: "Has title line"
        assert $("${selector} .card-title").text() == "Can override non-Licensed Image Title"

        and: "Has description line"
        assert $("${selector} .card-description").text() == "Can override non-Licensed Image Description"

        and: "Has info line"
        assert $("${selector} .card-info").text().contains("jpeg file")

        and: "Has button line"
        assert $("${selector} .btn").text().toLowerCase() == "Download".toLowerCase()

        and: "Has license line"
        assert $("${selector} .license").isEmpty()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Variant: Simple with Thumbnail Icon in #viewport.label")
    def "Functionality of Component Variant: Simple with Thumbnail Icon"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download11"
        def selectorContainer = "#contentblock11 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Has sample icon line"
        assert $("${selector} .icon").size() != 0

        and: "Has title line"
        assert $("${selector} div.title").text() == "Asset Title"

        and: "Has description line"
        assert $("${selector} div.description").text() == "Asset Description"

        and: "Has info line"
        assert $("${selector} div.info").text().contains("jpeg file")

        and: "Has license line"
        assert $("${selector} .license").isEmpty()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Card with Thumbnail Icon in #viewport.label")
    def "Functionality of Component Variant: Card with Thumbnail Icon"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download12"
        def selectorContainer = "#contentblock12 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Has sample icon line"
        assert $("${selector} span.card-icon").size() != 0

        and: "Has title line"
        assert $("${selector} .card-title").text() == "Asset Title"

        and: "Has description line"
        assert $("${selector} .card-description").text() == "Asset Description"

        and: "Has info line"
        assert $("${selector} .card-info").text().contains("jpeg file")

        and: "Has button line"
        assert $("${selector} .btn").text().toLowerCase() == "Download".toLowerCase()

        and: "Has license line"
        assert $("${selector} .license").isEmpty()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Simple with Thumbnail using Asset DAM Rendition in #viewport.label")
    def "Functionality of Component Variant: Simple with Thumbnail using Asset DAM Rendition Icon"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download13"
        def selectorContainer = "#contentblock13 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Uses Asset Rendition as thumbnail"
        assert $("${selector} img").attr("src").contains($("${selector}").attr("href"))

        and: "Has title line"
        assert $("${selector} div.title").text() == "Asset Title"

        and: "Has description line"
        assert $("${selector} div.description").text() == "Asset Description"

        and: "Has info line"
        assert $("${selector} div.info").text().contains("jpeg file")

        and: "Has license line"
        assert $("${selector} .license").isEmpty()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Card with Thumbnail using Asset DAM Rendition in #viewport.label")
    def "Functionality of Component Variant: Card with Thumbnail using Asset DAM Rendition"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download14"
        def selectorContainer = "#contentblock14 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Uses Asset Rendition as thumbnail"
        assert $("${selector} img").attr("src").contains($("${selector}").attr("href"))

        and: "Has title line"
        assert $("${selector} .card-title").text() == "Asset Title"

        and: "Has description line"
        assert $("${selector} .card-description").text() == "Asset Description"

        and: "Has info line"
        assert $("${selector} .card-info").text().contains("jpeg file")

        and: "Has button line"
        assert $("${selector} .btn").text().toLowerCase() == "Download".toLowerCase()

        and: "Has license line"
        assert $("${selector} .license").isEmpty()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Simple with Thumbnail using Thumbnail DAM Rendition in #viewport.label")
    def "Functionality of Component Variant: Simple with Thumbnail using Thumbnail DAM Rendition"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download15"
        def selectorContainer = "#contentblock15 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Does not use Asset Rendition as thumbnail"
        assert $("${selector} img").attr("src").contains($("${selector}").attr("href")) == false

        and: "Uses Thumbnail Asset Rendition"
        assert $("${selector} img").attr("src").contains(".thumbnail.")

        and: "Has title line"
        assert $("${selector} div.title").text() == "Asset Title"

        and: "Has description line"
        assert $("${selector} div.description").text() == "Asset Description"

        and: "Has info line"
        assert $("${selector} div.info").text().contains("jpeg file")

        and: "Has license line"
        assert $("${selector} .license").isEmpty()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Card with Thumbnail using Thumbnail DAM Rendition in #viewport.label")
    def "Functionality of Component Variant: Card with Thumbnail using Thumbnail DAM Rendition"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download16"
        def selectorContainer = "#contentblock16 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Does not use Asset Rendition as thumbnail"
        assert $("${selector} img").attr("src").contains($("${selector}").attr("href")) == false

        and: "Uses Thumbnail Asset Rendition"
        assert $("${selector} img").attr("src").contains(".thumbnail.")

        and: "Has title line"
        assert $("${selector} .card-title").text() == "Asset Title"

        and: "Has description line"
        assert $("${selector} .card-description").text() == "Asset Description"

        and: "Has info line"
        assert $("${selector} .card-info").text().contains("jpeg file")

        and: "Has button line"
        assert compareInnerTextIgnoreCase("${selector} .btn","Download")

        and: "Has license line"
        assert $("${selector} .license").isEmpty()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Simple with Custom Thumbnail Rendition in #viewport.label")
    def "Functionality of Component Variant: Simple with Custom Thumbnail Rendition"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download17"
        def selectorContainer = "#contentblock17 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Does not use Asset Rendition as thumbnail"
        assert $("${selector} img").attr("src").contains($("${selector}").attr("href")) == false

        and: "Uses Thumbnail Asset Rendition"
        assert $("${selector} img").attr("src").contains("/thumbnail.img.")

        and: "Has title line"
        assert compareInnerTextIgnoreCase("${selector} .title","Asset Title")

        and: "Has description line"
        assert compareInnerTextIgnoreCase("${selector} .description","Asset Description")

        and: "Has info line"
        assert compareInnerTextContains("${selector} .info","jpeg file")

        and: "Has license line"
        assert $("${selector} .license").isEmpty()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Card with Custom Thumbnail Rendition in #viewport.label")
    def "Functionality of Component Variant: Card with Custom Thumbnail Rendition"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download18"
        def selectorContainer = "#contentblock18 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "All images have loaded"
        waitForImagesToLoad2($("img"))

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Does not use Asset Rendition as thumbnail"
        assert $("${selector} img").attr("src").contains($("${selector}").attr("href")) == false

        and: "Uses Thumbnail Asset Rendition"
        assert $("${selector} img").attr("src").contains("/thumbnail.img.")

        and: "Has title line"
        assert compareInnerTextIgnoreCase("${selector} .card-title","Asset Title")

        and: "Has description line"
        assert compareInnerTextIgnoreCase("${selector} .card-description","Asset Description")

        and: "Has info line"
        assert compareInnerTextContains("${selector} .card-info","jpeg file")

        and: "Has button line"
        assert compareInnerTextIgnoreCase("${selector} .btn","Download")

        and: "Has license line"
        assert $("${selector} .license").isEmpty()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Functionality of Component Variant: Simple with Thumbnail using Asset DAM Rendition and Width Set in #viewport.label")
    def "Functionality of Component Variant: Simple with Thumbnail using Asset DAM Rendition and Width Set Icon"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download19"
        def selectorContainer = "#contentblock19 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "All images have loaded"
        waitForImagesToLoad2($("img"))

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Uses Asset Rendition as thumbnail"
        assert $("${selector} img").attr("src").contains($("${selector}").attr("href"))

        and: "Thumbnail has width specified"
        assert $("${selector}").attr("thumbnailwidth").equals("50")

        and: "Has title line"
        assert compareInnerTextIgnoreCase("${selector} .title","Asset Title")

        and: "Has description line"
        assert compareInnerTextIgnoreCase("${selector} .description","Asset Description")

        and: "Has info line"
        assert compareInnerTextContains("${selector} .info","jpeg file")

        and: "Has license line"
        assert $("${selector} .license").isEmpty()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component Variant: Simple with Thumbnail using Asset DAM Rendition and Height Set in #viewport.label")
    def "Functionality of Component Variant: Simple with Thumbnail using Asset DAM Rendition and Height Set Icon"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download20"
        def selectorContainer = "#contentblock20 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).text().trim().startsWith("Asset Title")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Uses Asset Rendition as thumbnail"
        assert $("${selector} img").attr("src").contains($("${selector}").attr("href"))

        and: "Thumbnail has height specified"
        assert $("${selector}").attr("thumbnailheight").equals("50")

        and: "Has title line"
        assert compareInnerTextIgnoreCase("${selector} .title","Asset Title")

        and: "Has description line"
        assert compareInnerTextIgnoreCase("${selector} .description","Asset Description")

        and: "Has info line"
        assert compareInnerTextContains("${selector} .info","jpeg file")

        and: "Has license line"
        assert $("${selector} .license").isEmpty()

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


}
