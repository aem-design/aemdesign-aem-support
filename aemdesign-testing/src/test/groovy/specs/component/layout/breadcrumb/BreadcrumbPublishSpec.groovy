package specs.component.layout.breadcrumb

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class BreadcrumbPublishSpec extends ComponentSpec {

    String pathPage = "component/layout/breadcrumb"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/breadcrumb"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component in #viewport.label")
    def "Functionality of Component"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "Breadcrumb"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#breadcrumb1"
        def selectorContainer = "#contentblock1 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: 'There should be a HREF value associated with Components link'
        $(selector + " a[href='/content/aemdesign-showcase/au/en/component.html']", text: "Components")

        and: 'There should be a HREF value associated with Layout link'
        $(selector + " a[href='/content/aemdesign-showcase/au/en/component/layout.html']", text: "Layout")

        and: 'There should be a HREF value associated with Breadcrumb link'
        $(selector + " li.active span", text: "Breadcrumb")

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component with Changed Start Level in #viewport.label")
    def "Functionality of Component with Changed Start Level"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "Breadcrumb"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#breadcrumb2"
        def selectorContainer = "#contentblock2 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: 'First link should be Layout'
        assert $("$selector li").getAt(0).text().trim() == "Layout"

        and: 'Last link should be Breadcrumb'
        assert $("$selector li").getAt($("$selector li").size()-1).text().trim() == "Breadcrumb"

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component with Changed Start Level and End Level in #viewport.label")
    def "Functionality of Component with Changed Start Level and End Level"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "Breadcrumb"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#breadcrumb3"
        def selectorContainer = "#contentblock3 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: 'First link should be Components'
        assert $("$selector li").getAt(0).text().trim() == "Components"

        and: 'Last link should be Layout'
        assert $("$selector li").getAt($("$selector li").size()-1).text().trim() == "Layout"

        where:
        viewport << getViewPorts()
    }

    @Unroll("Functionality of Component with Show hidden in #viewport.label")
    def "Functionality of Component with Show hidden"() {

        given: '>the page hierarchy is created as "Components" > "Layout" > "Breadcrumb"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#breadcrumb4"
        def selectorContainer = "#contentblock4 .contents"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: 'First link should be English'
        assert $("$selector li").getAt(0).text().trim() == "English"

        and: 'Last link should be Layout'
        assert $("$selector li").getAt($("$selector li").size()-1).text().trim() == "Layout"

        where:
        viewport << getViewPorts()
    }

}
