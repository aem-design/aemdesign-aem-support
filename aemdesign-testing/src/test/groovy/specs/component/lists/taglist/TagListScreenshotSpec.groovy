package specs.component.lists.taglist

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class TagListScreenshotSpec extends ComponentSpec {

    String pathPage = "component/lists/tag-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/taglist"

    def setupSpec() {
        loginAsAdmin()
    }



    @Unroll("Appearance of Component with Default variant and List of Children in #viewport.label")
    def "Appearance of Component with Default variant and List of Children"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist2"

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


    @Unroll("Appearance of Component with Default variant and List of Descendants in #viewport.label")
    def "Appearance of Component with Default variant and List of Descendants"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist3"

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


    @Unroll("Appearance of Component with Default variant and Fixed List in #viewport.label")
    def "Appearance of Component with Default variant and Fixed List"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist4"

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



    @Unroll("Appearance of Component with Options List variant and List of Children in #viewport.label")
    def "Appearance of Component with Options List variant and List of Children"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist6"

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


    @Unroll("Appearance of Component with Options List variant and List of Descendants in #viewport.label")
    def "Appearance of Component with Options List variant and List of Descendants"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist7"

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


    @Unroll("Appearance of Component with Options List variant and Fixed List in #viewport.label")
    def "Appearance of Component with Options List variant and Fixed List"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist8"

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
