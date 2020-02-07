package specs.component.forms.hidden

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class HiddenPublishSpec extends ComponentSpec {

    String pathPage = "component/forms/hidden"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/hidden"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Hidden"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#hidden1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have sample content"
        assert $(selector).value().equals("hidden")

        where:
        viewport << getViewPorts()
    }


}
