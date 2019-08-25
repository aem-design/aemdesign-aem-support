package specs.ui.touch

import spock.lang.Ignore
import support.page.ui.touch.TouchUISites
import spock.lang.Stepwise
import support.AuthorSpec

@Stepwise
@Ignore
class TouchUISitesSpec extends AuthorSpec {

    def setupSpec() {
        loginAsAdmin()
    }

    static final String PATH_SITE = '/content/aemdesign'

    def "Should see if there is a Site"() {
        given: "I navigate to Touch UI Sites"
        to TouchUISites

        when: "I am on the Sites Page"
        at TouchUISites

        then: "I should see if there is a Site Item"
        assert page.find(".foundation-collection-item[data-foundation-collection-item-id='${PATH_SITE}']") != null

    }


}
