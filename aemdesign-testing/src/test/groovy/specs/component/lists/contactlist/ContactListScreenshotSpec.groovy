package specs.component.lists.contactlist

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class ContactListScreenshotSpec extends ComponentSpec {

    String pathPage = "component/lists/contact-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/contactlist"

    def setupSpec() {
        loginAsAdmin()
    }


    @Unroll("Appearance of Component with Default variant and Default Badge in #viewport.label")
    def "Appearance of Component with Default variant and Default Badge"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contactlist1"

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


    @Unroll("Appearance of Component with Card Action with Icon, Title, Category and Description in #viewport.label")
    def "Appearance of Component with Card Action with Icon, Title, Category and Description"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contactlist2"

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


    @Unroll("Appearance of Component with Card Action with Image, Title, Category and Description in #viewport.label")
    def "Appearance of Component with Card Action with Image, Title, Category and Description"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contactlist3"

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


    @Unroll("Appearance of Component with Card with Icon, Title, Category, Description and Action in #viewport.label")
    def "Appearance of Component with Card with Icon, Title, Category, Description and Action"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contactlist4"

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


    @Unroll("Appearance of Component with Card with Image, Title, Category, Description and Action in #viewport.label")
    def "Appearance of Component with Card with Image, Title, Category, Description and Action"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contactlist5"

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


    @Unroll("Appearance of Component with Card with Image, Title, Category, Description and Action with Pagination in #viewport.label")
    def "Appearance of Component with Card with Image, Title, Category, Description and Action with Pagination"() {

        given: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#contactlist6"

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
