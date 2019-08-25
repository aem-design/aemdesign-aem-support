package support.page.ui.classic

class ClassicUISites extends ClassicUI {

    static url = "/siteadmin"

    static expectedTitle = "AEM WCM"

    static at = { waitFor(30) { title.startsWith(expectedTitle) } }

    static content = {


    }

    ClassicUISites() {
        super()
    }
}
