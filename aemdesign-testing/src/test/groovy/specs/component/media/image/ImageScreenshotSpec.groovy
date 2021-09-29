package specs.component.media.image


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ImageScreenshotSpec extends ComponentSpec {

    String pathPage = "component/media/image"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/image"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of Component Variant: Default in #viewport.label")
    def "Appearance of Component Variant: Default"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Appearance of Component Variant: Image Only in #viewport.label")
    def "Appearance of Component Variant: Image Only"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Appearance of Component Variant: Card in #viewport.label")
    def "Appearance of Component Variant: Card"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Appearance of Component Variant: Image Title and Description in #viewport.label")
    def "Appearance of Component Variant: Image Title and Description"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Appearance of Component Variant: Default with Licensed Image in #viewport.label")
    def "Appearance of Component Variant: Default with Licensed Image"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Appearance of Component Variant: Image Only with Licensed Image in #viewport.label")
    def "Appearance of Component Variant: Image Only with Licensed Image"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Appearance of Component Variant: Card with Licensed Image in #viewport.label")
    def "Appearance of Component Variant: Card with Licensed Image"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Appearance of Component Variant: Image Title and Description with Licensed Image in #viewport.label")
    def "Appearance of Component Variant: Image Title and Description with Licensed Image"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image8"
        def selectorContainer = "#contentblock8 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    //#image9 has no visual

    @Unroll("Appearance of Component Variant: Default with Overrides in #viewport.label")
    def "Appearance of Component Variant: Default with Overrides"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Appearance of Component Variant: Image Only with Overrides in #viewport.label")
    def "Appearance of Component Variant: Image Only with Overrides"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Appearance of Component Variant: Card with Overrides in #viewport.label")
    def "Appearance of Component Variant: Card with Overrides"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Appearance of Component Variant: Image Title and Description with Overrides in #viewport.label")
    def "Appearance of Component Variant: Image Title and Description with Overrides"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    //#image14 has no visual

    //#image15 has no visual

    //#image16 has no visual

    @Unroll("Appearance of Component Variant: Default with Rendition in #viewport.label")
    def "Appearance of Component Variant: Default with Rendition"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Appearance of Component Variant: Image Title and Description with Asset Metadata in #viewport.label")
    def "Appearance of Component Variant: Image Title and Description with Asset Metadata"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Appearance of Component Variant: Image Title and Description with Adaptive Image in #viewport.label")
    def "Appearance of Component Variant: Image Title and Description with Adaptive Image"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Appearance of Component Variant: Default with Generated Image in #viewport.label")
    def "Appearance of Component Variant: Default with Generated Image"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Appearance of Component Variant: Image and Descripton in #viewport.label")
    def "Appearance of Component Variant: Image and Descripton "() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Image"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#image21"
        def selectorContainer = "#contentblock21 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Appearance of Component Variant: Default with Image Option - Manual MediaQuery with Rendition in #viewport.label")
    def "Appearance of Component Variant: Default with Image Option - Manual MediaQuery with Rendition"() {

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

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }
}
