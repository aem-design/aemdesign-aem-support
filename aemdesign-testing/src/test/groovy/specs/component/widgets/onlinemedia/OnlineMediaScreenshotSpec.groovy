package specs.component.widgets.onlinemedia

import spock.lang.Ignore
import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class OnlineMediaScreenshotSpec extends ComponentSpec {

    String pathPage = "component/widgets/onlinemedia"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/onlinemedia"

    def setupSpec() {
        loginAsAdmin()
    }


    @Ignore //#onlinemedia1 has no visual
    @Unroll("Online Media: Appearance of Component Variant: Default in #viewport.label")
    def "Appearance of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Widgets" > "OnlineMedia"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#onlinemedia1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Ignore //#onlinemedia2 has no visual as it does not have module to load content
    @Unroll("Online Media: Appearance of Component Variant: Default with YouTube in #viewport.label")
    def "Appearance of Component Variant: Default with YouTube"() {

        given: '>the page hierarchy is created as "Components" > "Widgets" > "OnlineMedia"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#onlinemedia2"
        def selectorContainer = "#contentblock2 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Ignore //#onlinemedia3 has no visual as it does not have module to load content
    @Unroll("Online Media: Appearance of Component Variant: Default with Kaltura in #viewport.label")
    def "Appearance of Component Variant: Default with Kaltura"() {

        given: '>the page hierarchy is created as "Components" > "Widgets" > "OnlineMedia"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#onlinemedia3"
        def selectorContainer = "#contentblock3 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Online Media: Appearance of Component Variant: IFrame with Youtube Video in #viewport.label")
    def "Appearance of Component Variant: IFrame with Youtube Video"() {

        given: '>the page hierarchy is created as "Components" > "Widgets" > "OnlineMedia"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#onlinemedia4"
        def selectorContainer = "#contentblock4 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Online Media: Appearance of Component Variant: IFrame with Vimeo Video in #viewport.label")
    def "Appearance of Component Variant: IFrame with Vimeo Video"() {

        given: '>the page hierarchy is created as "Components" > "Widgets" > "OnlineMedia"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#onlinemedia5"
        def selectorContainer = "#contentblock5 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }
}
