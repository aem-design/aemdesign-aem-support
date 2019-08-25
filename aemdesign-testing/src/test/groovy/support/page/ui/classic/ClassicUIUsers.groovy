package support.page.ui.classic

class ClassicUIUsers extends ClassicUI {

    static url = "/useradmin"

    static expectedTitle = "AEM Security"

    static at = { waitFor(30) { title.startsWith(expectedTitle) } }

    static content = {


    }

    ClassicUIUsers() {
        super()
    }
}
