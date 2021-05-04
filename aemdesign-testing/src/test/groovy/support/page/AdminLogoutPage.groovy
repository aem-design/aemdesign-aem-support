package support.page

import geb.Page
import org.openqa.selenium.By

class AdminLogoutPage extends Page {

    static url = "/system/sling/logout.html"

    AdminLogoutPage() {
        super()

        System.out.println("Logout page: " + getPageUrl() );
    }

}
