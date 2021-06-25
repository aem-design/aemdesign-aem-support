package specs.component.content.download

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class DownloadScreenshotSpec extends ComponentSpec {

    String pathPage = "component/content/download"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/download"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of Component Variant: Default in #viewport.label")
    def "Appearance of Component Variant: Default"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of Component Variant: Simple in #viewport.label")
    def "Appearance of Component Variant: Simple"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of Component Variant: Card in #viewport.label")
    def "Appearance of Component Variant: Card"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Appearance of Component Variant: Default with Licensed Image  in #viewport.label")
    def "Appearance of Component Variant: Default with Licensed Image "() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of Component Variant: Simple with Licensed Image  in #viewport.label")
    def "Appearance of Component Variant: Simple with Licensed Image "() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of Component Variant: Card with Licensed Image in #viewport.label")
    def "Appearance of Component Variant: Card with Licensed Image"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Appearance of Component Variant: Default with Title and Description in #viewport.label")
    def "Appearance of Component Variant: Default with Title and Description"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of Component Variant: Simple with Title and Description in #viewport.label")
    def "Appearance of Component Variant: Simple with Title and Description"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of Component Variant: Card with Title and Description in #viewport.label")
    def "Appearance of Component Variant: Card with Title and Description"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of Component Variant: Simple with Thumbnail Icon in #viewport.label")
    def "Appearance of Component Variant: Simple with Thumbnail Icon"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download11"
        def selectorContainer = "#contentblock11 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        and: "All images have loaded"
        waitForImagesToLoad2($("img"))

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Appearance of Component Variant: Card with Thumbnail Icon in #viewport.label")
    def "Appearance of Component Variant: Card with Thumbnail Icon"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Download"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#download12"
        def selectorContainer = "#contentblock12 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        and: "All images have loaded"
        waitForImagesToLoad2($("img"))

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Appearance of Component Variant: Simple with Thumbnail using Asset DAM Rendition Icon in #viewport.label")
    def "Appearance of Component Variant: Simple with Thumbnail using Asset DAM Rendition Icon"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Appearance of Component Variant: Card with Thumbnail using Asset DAM Rendition in #viewport.label")
    def "Appearance of Component Variant: Card with Thumbnail using Asset DAM Rendition"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Appearance of Component Variant: Simple with Thumbnail using Thumbnail DAM Rendition in #viewport.label")
    def "Appearance of Component Variant: Simple with Thumbnail using Thumbnail DAM Rendition"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Appearance of Component Variant: Card with Thumbnail using Thumbnail DAM Rendition in #viewport.label")
    def "Appearance of Component Variant: Card with Thumbnail using Thumbnail DAM Rendition"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Appearance of Component Variant: Simple with Custom Thumbnail Rendition in #viewport.label")
    def "Appearance of Component Variant: Simple with Custom Thumbnail Rendition"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Appearance of Component Variant: Card with Custom Thumbnail Rendition in #viewport.label")
    def "Appearance of Component Variant: Card with Custom Thumbnail Rendition"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Appearance of Component Variant: Simple with Thumbnail using Asset DAM Rendition and Width Set Icon in #viewport.label")
    def "Appearance of Component Variant: Simple with Thumbnail using Asset DAM Rendition and Width Set Icon"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


    @Unroll("Appearance of Component Variant: Simple with Thumbnail using Asset DAM Rendition and Height Set Icon in #viewport.label")
    def "Appearance of Component Variant: Simple with Thumbnail using Asset DAM Rendition and Height Set Icon"() {

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

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


}
