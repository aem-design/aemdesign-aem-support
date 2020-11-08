package specs.component.lists.pagelist

import spock.lang.IgnoreRest
import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class PageListScreenshotSpec extends ComponentSpec {

    String pathPage = "component/lists/page-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/pagelist"


    def setupSpec() {
        loginAsAdmin()
    }


    @Unroll("Appearance of Component with Default variant and Default Badge in #viewport.label")
    def "Appearance of Component with Default variant and Default Badge"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist1"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Appearance of Component with Default variant and Icon Badge in #viewport.label")
    def "Appearance of Component with Default variant and Icon Badge"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist2"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Appearance of Component with Default variant and Image Badge in #viewport.label")
    def "Appearance of Component with Default variant and Image Badge"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist3"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }


    @Unroll("Appearance of Component with Default variant and Card Badge in #viewport.label")
    def "Appearance of Component with Default variant and Card Badge"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist4"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }



    @Unroll("Appearance of Component with Basic list with links in #viewport.label")
    def "Appearance of Component with Basic list with links"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist11"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }


    @Unroll("Appearance of Component with Basic list with links inline in #viewport.label")
    def "Appearance of Component with Basic list with links inline"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist12"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }


    @Unroll("Appearance of Component as a Carousel in #viewport.label")
    def "Appearance of Component as Carousel"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist13"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Appearance of Component as Medium Cards in #viewport.label")
    def "Appearance of Component as Medium Cards"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist14"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }


    @Unroll("Appearance of Component as Medium Cards Tall in #viewport.label")
    def "Appearance of Component as Medium Cards Tall"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist15"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Appearance of Component as Medium Cards (flush side-by-side) in #viewport.label")
    def "Appearance of Component as Medium Cards (flush side-by-side)"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist16"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Appearance of Component as Large Card in #viewport.label")
    def "Appearance of Component as Large Card"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist17"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Appearance of Component as Card with Title, Description and Action with override on Badge CTA in #viewport.label")
    def "Appearance of Component as Card with Title, Description and Action with override on Badge CTA"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist18"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Badge: Card Coloured with Image, Title, Subtitle, Description and Action in #viewport.label")
    def "Badge: Card Coloured with Image, Title, Subtitle, Description and Action"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist19"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Badge: Card with Image, Title and Action in #viewport.label")
    def "Badge: Card with Image, Title and Action"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist20"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Badge: Card with Image, Title and Action and Card Style set to outline with override on Badge CTA in #viewport.label")
    def "Badge: Card with Image, Title and Action and Card Style set to outline with override on Badge CTA"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist21"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Badge: Card with Image, Title and Action and Card Style set to outline in #viewport.label")
    def "Badge: Card with Image, Title and Action and Card Style set to outline"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist22"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Badge: Card with Title, Description and Action without Override in #viewport.label")
    def "Badge: Card with Title, Description and Action without Override"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist23"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Badge: Selectable Card with Title, Tags and Description in #viewport.label")
    def "Badge: Selectable Card with Title, Tags and Description"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist24"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Badge: Card with Image, Title and Description in #viewport.label")
    def "Badge: Card with Image, Title and Description"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist25"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Badge: Card with Icon and Description in #viewport.label")
    def "Badge: Card with Icon and Description"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist26"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Badge: Card with Icon in #viewport.label")
    def "Badge: Card with Icon"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist27"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }

    @Unroll("Appearance of Component as Card with Title, Description and Action with override for Card Style and Link Style in #viewport.label")
    def "Appearance of Component as Card with Title, Description and Action with override for Card Style and Link Style"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#pagelist39"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts


    }


}
