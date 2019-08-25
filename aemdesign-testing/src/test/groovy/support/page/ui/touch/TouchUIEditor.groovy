package support.page.ui.touch

class TouchUIEditor extends TouchUI {

    static url = "/editor.html"

    static expectedTitle = "AEM Editor"

    static PAGE_FRAME_CONTENT = "ContentFrame"

    static at = {
        waitFor(10) {
            !$("#SidePanel").isEmpty() && !$("#Content").isEmpty()
        }
    }

    static content = {
        pageContent(required: true) { module PageContentModule }

        //image { index -> $(".image") }

        // Active Item
        // foundation-collection-item coral-ColumnView-item is-active

        //pageContentItems(wait: true) { moduleList PageContentRow, $(".coral-ColumnView-column.is-active")}

        //project { module  Article }
    }

    TouchUIEditor() {
        super()
    }

//    TouchUIEditor(String pageTitle) {
//        if (pageTitle != null && !pageTitle.isEmpty()) {
//            expectedTitle = pageTitle
//        }
//    }

    def waitForPageContent() {
        return waitFor {
            return $("#Content")
        }
    }
}

