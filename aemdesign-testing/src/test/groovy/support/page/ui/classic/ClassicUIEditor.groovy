package support.page.ui.classic

class ClassicUIEditor extends ClassicUI {

    static url = "/cf#"

    static expectedTitle = "AEM Content Finder"

    static at = {
        waitFor {
            js.exec("return (window.CQ?true:false)")
        }
    }

    static content = {

        container(wait: true, required: true) { $("#CQ") }

        pageContent(wait: true) { module PageContentModule }

        currentWindow(wait: true) { module CurrentWindow }


    }

    ClassicUIEditor() {
        super()
    }
}
