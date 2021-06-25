package specs.component.lists.langnav


import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class LangNavScreenshotSpec extends ComponentSpec {

    String pathPage = "component/lists/language-navigation"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/langnav"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of Component Variant: Default with Fixed List in #viewport.label")
    def "Appearance of Component Variant: Default with Fixed List"() {

        given: '>the page hierarchy is created as "Components" > "Content" > "Language Navigation"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#langnav1"
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



}
