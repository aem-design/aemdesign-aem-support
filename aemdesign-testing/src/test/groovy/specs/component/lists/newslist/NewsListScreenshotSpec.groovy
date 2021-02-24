package specs.component.lists.newslist

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class NewsListScreenshotSpec extends ComponentSpec {

    String pathPage = "component/lists/news-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/newslist"

    def setupSpec() {
        loginAsAdmin()
    }


    @Unroll("Appearance of Component with Default variant and Default Badge in #viewport.label")
    def "Appearance of Component with Default variant and Default Badge"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#newslist1"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()


    }

    @Unroll("Appearance of Component with Default variant and Icon Badge in #viewport.label")
    def "Appearance of Component with Default variant and Icon Badge"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#newslist2"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()


    }

    @Unroll("Appearance of Component with Default variant and Image Badge in #viewport.label")
    def "Appearance of Component with Default variant and Image Badge"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#newslist3"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()


    }


    @Unroll("Appearance of Component with Default variant and Card Badge in #viewport.label")
    def "Appearance of Component with Default variant and Card Badge"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#newslist4"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width by #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()


    }


}
