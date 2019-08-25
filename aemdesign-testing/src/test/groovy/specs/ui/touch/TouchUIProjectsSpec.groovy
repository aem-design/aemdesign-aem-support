package specs.ui.touch

import spock.lang.Ignore
import support.page.ui.touch.TouchUIProjects
import spock.lang.Stepwise
import support.AuthorSpec

@Stepwise
@Ignore
class TouchUIProjectsSpec extends AuthorSpec {

    def setupSpec() {
        loginAsAdmin()
    }

    static final String PATH_PROJECT = '/content/projects/aemdesign'

    def "Should see if there is a Project"() {
        given: "I navigate to Touch UI Projects Page"
        to TouchUIProjects

        when: "I am on the Touch UI Projects Page"
        at TouchUIProjects

        then: "See if there is a Project"
        assert page.find(".foundation-collection-item[data-foundation-collection-item-id='${PATH_PROJECT}']")
                .find(".foundation-collection-item-title", text: "We.Retail")
    }


}
