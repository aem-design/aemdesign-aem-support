package specs.component.media.video

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class VideoScreenshotSpec extends ComponentSpec {

    String pathPage = "component/media/video"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/video"

    def setupSpec() {
        loginAsAdmin()
    }


    @Unroll("Appearance of Component Variant: Default in #viewport.label")
    def "Appearance of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Video"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#video1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        assert js.exec( "\$(\"$selector\").find(\"video\")[0].pause(); return true;")
        assert js.exec( "\$(\"$selector\").find(\"video\")[0].currentTime=2; return true;")
        designRef(selectorContainer)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }



}
