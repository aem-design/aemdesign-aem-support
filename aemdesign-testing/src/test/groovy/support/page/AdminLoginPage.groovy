package support.page

import geb.Page
import org.openqa.selenium.By

class AdminLoginPage extends Page {

    static url = "/system/sling/logout.html"

    static at = {
        waitFor(30) { title == "AEM Sign In" }
    }

    static content = {
        username(wait: true) { $("input", id: "username") }
        password(wait: true) { $("input", id: "password") }
        signIn(wait: true) {
            driver.findElement(By.xpath("//button[@type = 'submit']"))
        }

        error {
            $("div", id: "error")
        }
    }

    AdminLoginPage() {
        super()
    }

}
