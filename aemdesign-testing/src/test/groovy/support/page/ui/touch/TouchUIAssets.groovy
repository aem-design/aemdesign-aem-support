package support.page.ui.touch

class TouchUIAssets extends TouchUI {

    static url = "/assets.html"

    static expectedTitle = "AEM Assets"

    static at = { waitFor(10) { title.startsWith(expectedTitle) } }

    static content = {
        pageContent(required: true) { module PageContentModule }

        image { index -> $(".image") }

        pageContentItems(wait: true) { moduleList PageContentRow, $(".foundation-collection-item") }

        pageContentLoaded(required: true, wait: true) { $("img") }
    }

    TouchUIAssets() {
        super()
    }
}
