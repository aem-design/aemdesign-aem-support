package specs.component.lists.taglist

import spock.lang.IgnoreRest
import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class TagListPublishSpec extends ComponentSpec {

    String pathPage = "component/lists/tag-list"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/taglist"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component with Default variant and no content in #viewport.label")
    def "Functionality of Component with Default variant  and no content"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Tag List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Component does not have any content"
        assert $("${selector}").children().size() == 0

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component with Default variant and List of Children in #viewport.label")
    def "Functionality of Component with Default variant and List of Children"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Tag List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist2"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has 10 list items"
        assert $("${selector} li").size() == 10

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component with Default variant and List of Descendants in #viewport.label")
    def "Functionality of Component with Default variant and List of Descendants"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Tag List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist3"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has 19 list items"
        assert $("${selector} li").size() == 19

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component with Default variant and Fixed List in #viewport.label")
    def "Functionality of Component with Default variant and Fixed List"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Tag List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist4"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has 3 list items"
        assert $("${selector} li").size() == 3

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


    @Unroll("Functionality of Component with Options List variant and no content in #viewport.label")
    def "Functionality of Component with Options List variant  and no content"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Tag List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist5"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Component only has an empty option"
        assert $("${selector}").children().size() == 1

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component with Options List variant and List of Children in #viewport.label")
    def "Functionality of Component with Options List variant and List of Children"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Tag List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist6"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has 10 list items"
        assert $("${selector} option").size() == 11

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component with Options List variant and List of Descendants in #viewport.label")
    def "Functionality of Component with Options List variant and List of Descendants"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Tag List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist7"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has 19 list items"
        assert $("${selector} option").size() == 20

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }

    @Unroll("Functionality of Component with Options List variant and Fixed List in #viewport.label")
    def "Functionality of Component with Options List variant and Fixed List"() {

        given: '>the page hierarchy is created as "Components" > "Lists" > "Tag List"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#taglist8"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)
        takeScreenshot($(selector).firstElement(), "The component should be on the page")

        and: "Has 3 list items"
        assert $("${selector} option").size() == 4

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << viewPorts
    }


}
