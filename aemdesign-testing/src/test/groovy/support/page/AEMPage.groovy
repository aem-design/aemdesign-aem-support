package support.page

import geb.Page

class AEMPage extends Page {

    static url = ""

    static class WCMMODE {
        static final DISABLED = "disabled"
        static final PREVIEW = "preview"
        static final EDIT = "edit"
        static final QUERYSTRINGPARAMNAME = "wcmmode"
    }

    static at = {
        waitFor(wait: true) { js."document.readyState" == "complete" }
    }

    static content = {

    }

    public static toMode(String url, String mode) {
        if (System.properties.getProperty("test.dispatcher", "false") == "true") {
            return url
        }

        return url + "?" + WCMMODE.QUERYSTRINGPARAMNAME + "=" + mode
    }

    public static toLanguage(String siteRoot, String language, String path) {
        return siteRoot + "/" + (language.isEmpty() ? "" : language + "/") + path
    }

    AEMPage() {
        super()
    }

}
