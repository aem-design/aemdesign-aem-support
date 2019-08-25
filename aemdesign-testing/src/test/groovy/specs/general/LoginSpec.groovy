package specs.general

import spock.lang.Ignore
import support.page.AdminLoginPage
import support.page.ui.touch.TouchUI
import support.page.ui.touch.TouchUIHome
import spock.lang.Stepwise
import spock.lang.Unroll
import support.FunctionalSpec

@Stepwise
@Ignore
class LoginSpec extends FunctionalSpec {

    String pageElement = "body .background"

    @Unroll("Should be able to see login page in #viewport.label")
    def "Should be able to see login page"() {
        when: "I am on the Admin Login page"
        setWindowSize(viewport)
        to AdminLoginPage

        then: "The page should match the design reference"
        $(pageElement).allElements().eachWithIndex { element, index ->
            designReference(element)
        }

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()

    }

    @Unroll("Should not log in with wrong username and password in #viewport.label")
    def "Should not log in with wrong username and password"() {
        given: "I am on the Admin Login page"
        setWindowSize(viewport)
        to AdminLoginPage

        when: "I enter the wrong username and password"
        login("blah", "blah")

        then: "I should see an error message"
        assert $("#error").text().trim() == "User name and password do not match"

        then: "The page should match the design reference"
        $(pageElement).allElements().eachWithIndex { element, index ->
            designReference(element)
        }

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }

    @Unroll("Should log in with valid username and password in #viewport.label")
    def "Should log in with valid username and password"() {
        given: "I am on the Admin Login page"
        setWindowSize(viewport.label)
        to AdminLoginPage

        when: "I enter the wrong username and password"
        login("admin", "admin")

        then: "I should see AEM Start Page"
        at TouchUIHome

        then: "The page should match the design reference"
        $(TouchUI.elements.DOCUMENT_BODY).allElements().eachWithIndex { element, index ->
            designReference(element)
        }

        where: "Browser size width: #viewport.width and height: #viewport.height"
        viewport << getViewPorts()
    }


}
