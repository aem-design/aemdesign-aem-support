package specs.component.forms.form

import spock.lang.Stepwise
import spock.lang.Unroll
import support.ComponentSpec

@Stepwise
class FormPublishSpec extends ComponentSpec {

    String pathPage = "component/forms/form"
    String pathSite = "content/aemdesign-showcase"
    String language = "au/en"
    String componentPath = "jcr:content/article/par/contentblock1/par/form"

    def setupSpec() {
        loginAsAdmin()
    }

    @Unroll("Functionality of Component Variant: Default in #viewport.label")
    def "Functionality of Component Variant: Default"() {

        given: '>the page hierarchy is created as "Components" > "Forms" > "Form"'
        and: '>I am in the component showcase page'
        and: '>the component is on the showcase page'
        def selector = "#form1"

        when: "I am on the component showcase page"
        setWindowSize(viewport)
        waitForAuthorPreviewPage()

        then: "The component should be on the page"
        def component = waitForComponent(selector)

        and: "Should have default fields"
        assert $(selector).children().size() == 2

        where:
        viewport << getViewPorts()
    }


}
