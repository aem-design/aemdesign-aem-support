package support.page.ui.classic

class ClassicUITools extends ClassicUI {

    static url = "/miscadmin"

    static expectedTitle = "AEM Tools"

    static at = { waitFor(30) { title.startsWith(expectedTitle) } }

    static content = {


    }

    ClassicUITools() {
        super()
    }
}
