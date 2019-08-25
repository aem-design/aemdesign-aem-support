package specs.component.lists.assetlist

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class AssetListScreenshotSpec extends ComponentSpec {

    String pathPage = "component/lists/asset-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/assetlist"

    def setupSpec() {
        loginAsAdmin()
    }



    @Unroll("Appearance of Component with Default variant and Static List in #viewport.label")
    def "Appearance of Component with Default variant and List of Children"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist1"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()


    }


    @Unroll("Appearance of Component with Default variant and Static List with Image Option Rendition in #viewport.label")
    def "Appearance of Component with Default variant and Static List with Image Option Rendition"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist2"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()


    }


    @Unroll("Appearance of Component with Default variant and Static List with Image Option Adaptive in #viewport.label")
    def "Appearance of Component with Default variant and Static List with Image Option Adaptive"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist3"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()


    }



    @Unroll("Appearance of Component with Default variant and Static List with Multiple Images in #viewport.label")
    def "Appearance of Component with Default variant and Static List with Multiple Images"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist4"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()


    }


    @Unroll("Appearance of Component with Default variant and Child List in #viewport.label")
    def "Appearance of Component with Default variant and Child List"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist5"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()


    }


    @Unroll("Appearance of Component with Default variant and Descendants List in #viewport.label")
    def "Appearance of Component with Default variant and Descendants List"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist6"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()


    }


    @Unroll("Appearance of Component with Default variant and Static List with Multiple Asset Types in #viewport.label")
    def "Appearance of Component with Default variant and Static List with Multiple Asset Types"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#assetlist7"

        when: 'I am in the component showcase page'
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: 'The component should appear on the page'
        def component = waitForComponent(selector)

        then: "It should match the #viewport.width and height: #viewport.height reference image."
        designRef(selector)

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()


    }


}
