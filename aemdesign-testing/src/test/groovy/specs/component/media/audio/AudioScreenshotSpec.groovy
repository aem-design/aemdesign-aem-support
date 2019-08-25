package specs.component.media.audio

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class AudioScreenshotSpec extends ComponentSpec {

    String pathPage = "component/media/audio"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/audio"

    def setupSpec() {
        loginAsAdmin()
    }


    @Unroll("Appearance of Component Variant: Default in #viewport.label")
    def "Appearance of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Audio"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#audio1"
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



}
