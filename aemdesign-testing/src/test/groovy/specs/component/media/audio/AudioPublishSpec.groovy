package specs.component.media.audio

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class AudioPublishSpec extends ComponentSpec {

    String pathPage = "component/media/audio"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/audio"

    def setupSpec() {
        loginAsAdmin()
    }


    @Unroll("Functionality of Component Variant: Default with Audio Source in #viewport.label")
    def "Functionality of Component Variant: Default with Audio Source"() {

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
        report("The component should be on the page")

        and: "Should have sample content"
        assert $("${selector} audio source").attr("src").contains("/recit.mp3")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Should have sample content loaded"
        assert $("${selector} audio").firstElement().getAttribute("readyState") == "4"

        and: "Should have sample content not playing"
        assert js.exec("return \$(\"${selector} audio\")[0].paused == true;")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Functionality of Component Variant: Default with Video Source in #viewport.label")
    def "Functionality of Component Variant: Default with Video Source"() {

        given: '>the page hierarchy is created as "Components" > "Media" > "Audio"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#audio2"
        def selectorContainer = "#contentblock2 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        report("The component should be on the page")

        and: "Should have sample content"
        assert $("${selector} audio source").attr("src").contains("/FishTank.mp4")
        takeScreenshot($(selector).firstElement(), "Should have sample content")

        and: "Should have sample content loaded"
        assert $("${selector} audio").firstElement().getAttribute("readyState") == "4"

        and: "Should have sample content not  playing"
        assert js.exec("return \$(\"${selector} audio\")[0].paused == true;")

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


}
