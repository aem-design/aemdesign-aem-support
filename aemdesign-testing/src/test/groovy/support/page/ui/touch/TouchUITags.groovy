package support.page.ui.touch

class TouchUITags extends TouchUI {

    static url = "/aem/tags.html"

    static expectedTitle = "AEM Tags"

    static at = { waitFor(10) { title.startsWith(expectedTitle) } }

    static content = {
        pageContent(required: true) { module PageContentModule }

        //image { index -> $(".image") }

        // Active Item
        // foundation-collection-item coral-ColumnView-item is-active

        //pageContentItems(wait: true) { moduleList PageContentRow, $(".coral-ColumnView-column.is-active")}

        //project { module  Article }
    }


    TouchUITags() {
        super()
    }

}

