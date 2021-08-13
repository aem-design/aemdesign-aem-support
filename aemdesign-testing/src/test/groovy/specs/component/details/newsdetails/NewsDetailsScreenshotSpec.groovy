package specs.component.details.newsdetails

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class NewsDetailsScreenshotSpec extends ComponentSpec {

    String pathPage = "component/details/news-details"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/newsdetails"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Appearance of News Details Component with Excluded Components in #viewport.label")
    def "Appearance of News Details Component with Excluded Components"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#news-details1"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where:
        viewport << getViewPorts()

    }

    @Unroll("Appearance of News Details Component with Included Components in #viewport.label")
    def "Appearance of News Details Component with Included Components"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#news-details2"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where:
        viewport << getViewPorts()

    }
    @Unroll("Appearance of News Details Component with Background and Formatted Date in #viewport.label")
    def "Appearance of News Details Component with Background and Formatted Date"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#news-details3"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where:
        viewport << getViewPorts()

    }
}
