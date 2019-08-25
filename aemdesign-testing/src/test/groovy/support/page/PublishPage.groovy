package support.page

import geb.Module

class PublishPage extends AEMPage {

    static url = ""

    static at = {

    }

    static content = {
        pageContent(required: true) { module PageContentModule }
    }

    PublishPage() {
        super()
    }

}

class PageContentModule extends Module {
    static base = { $("#main") }
    static content = {

    }

}
