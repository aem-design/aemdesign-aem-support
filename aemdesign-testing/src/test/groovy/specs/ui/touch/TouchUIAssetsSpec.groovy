package specs.ui.touch

import spock.lang.Ignore
import spock.lang.Stepwise
import support.AuthorSpec
import support.page.ui.touch.TouchUIAssets

@Stepwise
@Ignore
class TouchUIAssetsSpec extends AuthorSpec {

    def setupSpec() {
        loginAsAdmin()
    }

    static final String PATH_DAM = '/content/dam/aemdesign'

    def "Should see if there is a We.Retail Assets folder"() {
        given: "I navigate to Touch UI Assets Page"
        to TouchUIAssets

        when: "I am on the Touch UI Assets Page"
        at TouchUIAssets

        then: "I should see if there is a We.Retail assets folder"
        assert page.find(".foundation-collection-item[data-foundation-collection-item-id='${PATH_DAM}']") != null

    }


}
