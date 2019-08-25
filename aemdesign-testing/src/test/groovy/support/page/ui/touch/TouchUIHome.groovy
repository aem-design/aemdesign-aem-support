package support.page.ui.touch

class TouchUIHome extends TouchUI {

    static url = "/aem/start.html"

    static expectedTitle = "Start"

    static at = { waitFor(10) { title.startsWith(expectedTitle) } }

    static content = {

        globalMenu { module PageGlobalNavModule }

    }

    TouchUIHome() {
        super()
    }

}

