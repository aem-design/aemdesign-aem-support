package support.page.ui.touch

class TouchUISites extends TouchUI {

    static url = "/sites.html"

    static expectedTitle = "AEM Sites"

    static at = { waitFor(10) { title.startsWith(expectedTitle) } }

    static content = {
        pageContent(required: true) { module PageContentModule }

        image { index -> $(".image") }

        pageContentItems(wait: true) { moduleList PageContentRow, $(".foundation-collection-item") }

        pageContentLoaded(required: true, wait: true) { $("img") }

    }


    TouchUISites() {
        super()
    }

}

