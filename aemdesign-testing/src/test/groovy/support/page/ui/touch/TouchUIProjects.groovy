package support.page.ui.touch

class TouchUIProjects extends TouchUI {

    static url = "/projects.html"

    static expectedTitle = "AEM Projects"

    static at = { waitFor(10) { title.startsWith(expectedTitle) } }

    static content = {
        pageContent(required: true) { module PageContentModule }

        image { index -> $(".image") }

        pageContentItems(wait: true) { moduleList PageContentRow, $(".foundation-collection-item") }

        pageContentLoaded(required: true, wait: true) { $("img") }

    }


    TouchUIProjects() {
        super()
    }

}

