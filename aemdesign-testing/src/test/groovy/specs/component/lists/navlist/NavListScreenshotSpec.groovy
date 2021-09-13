package specs.component.lists.navlist


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class NavListScreenshotSpec extends ComponentSpec {

    String pathPage = "component/lists/nav-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/navlist"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of Component Variant: Default with Fixed List in #viewport.label")
    def "Appearance of Component Variant: Default with Fixed List"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Nav List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#navlist1"
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

    @Unroll("Appearance of Component Variant: Simple with Fixed List in #viewport.label")
    def "Appearance of Component Variant: Simple with Fixed List"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Nav List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#navlist2"
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

    @Unroll("Appearance of Component Variant: Stacked with Fixed List in #viewport.label")
    def "Appearance of Component Variant: Stacked with Fixed List"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Nav List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#navlist3"
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

    @Unroll("Appearance of Component Variant: Simple with Children List in #viewport.label")
    def "Appearance of Component Variant: Simple with Children List"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Nav List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#navlist4"
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

    @Unroll("Appearance of Component Variant: Stacked with Children List in #viewport.label")
    def "Appearance of Component Variant: Stacked with Children List"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Nav List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#navlist5"
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


    @Unroll("Appearance of Component Inherit from Parent in #viewport.label")
    def "Appearance of Component Inherit from Parent"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Nav List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#inheritedListInAside"
        def selectorContainer = "#aside"

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
