package support.page.ui.classic

class ClassicUIAssets extends ClassicUI {

    static url = "/damadmin"

    static expectedTitle = "AEM Assets"

    static at = { waitFor(30) { title.startsWith(expectedTitle) } }

    static content = {


    }

    ClassicUIAssets() {
        super()
    }
}
