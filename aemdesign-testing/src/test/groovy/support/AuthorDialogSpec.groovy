package support

abstract class AuthorDialogSpec extends ComponentSpec {

    def showDialogDirect() {
        return js.exec( """
                document.querySelector('.cq-Dialog').show(); return true;
            """)
    }

    def selectDialogTab(String tabText, String tabsSelector = ".coral-TabPanel-tab") {

        return js.exec("""
            \$("${tabsSelector}").map(function (i, el) {
            if (el.innerText.toLowerCase() == "${tabText}".toLowerCase()) {
                {return el;}
            }
            }).click();
            return true;
        """)
    }

    def tabSelected(String tabText, String tabsSelector = ".coral-TabPanel-tab", String tabsSelectorActive = ".is-active") {

        return js.exec("""
            return \$("${tabsSelector}${tabsSelectorActive}").map(function (i, el) {
                if (el.innerText.toLowerCase() == "${tabText}".toLowerCase()) {
                    if (\$("#" + el.getAttribute("aria-controls")).get(0).getAttribute("aria-hidden") === "false") {
                        return true;
                    }
                }
            }).length > 0;
        """)
    }

}
