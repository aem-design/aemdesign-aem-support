package support.page.ui.touch

import geb.Module
import geb.Page
import groovy.json.JsonOutput

/**
 * Generic Touch UI Functions
 */

class TouchUI extends Page {

    static url = "/aem/start.html"

    static expectedTitle = "Start"

    static at = {
        waitFor {
            js.exec("return (window.Granite?true:false)")
        }
    }

    static class elements {
        static DOCUMENT_BODY = "body"
        static EDITOR_PANEL_SIDE = "#SidePanel"
        static EDITOR_PANEL_CONTENT = "#Content"
    }

    static content = {
//        pageContent (wait: true) { module PageContentModule }

        Editor(wait: true) { module Editor }

    }


    public void pause() {
        js.exec """
           (function() {
               window.__gebPaused = true;
               var div = document.createElement("div");
               div.setAttribute('style', "\\
                   position: absolute; top: 0px;\\
                   z-index: 3000;\\
                   padding: 10px;\\
                   background-color: red;\\
                   border: 2px solid black;\\
                   border-radius: 2px;\\
                   text-align: center;\\
               ");

               var button = document.createElement("button");
               button.innerHTML = "Unpause Geb";
               button.onclick = function() {
                   window.__gebPaused = false;
               }
               button.setAttribute("style", "\\
                   font-weight: bold;\\
               ");

               div.appendChild(button);
               document.getElementsByTagName("body")[0].appendChild(div);
           })();
       """

        waitFor(10) { !js.__gebPaused }
    }
}

class PageContentModule extends Module {
    static base = { $(TouchUI.elements.DOCUMENT_BODY) }
    static content = {
//        contentBody(wait: true) { base.find("coral-shell-content") }
//        contentHeader(wait: true) { base.find("coral-shell-header") }
//        contentMenu(wait: true) { base.find("coral-shell-menu") }
//        contentGlobalNav(wait: true) { base.find("#globalnav-start-home-collection") }
        contentPanelSide(wait: true) { base.find(TouchUI.elements.EDITOR_PANEL_SIDE) }
        contentPanelContent(wait: true) { base.find(TouchUI.elements.EDITOR_PANEL_CONTENT) }
    }
}

class PageContentRow extends Module {
    static content = {
        path { $().attr("data-foundation-collection-item-id") }
        title { $(".foundation-collection-item-title") }
    }

}


class PageContentRail extends Module {
    static content = {
        rail { base.find("#shell-collectionpage-rail") }
    }

}


class PageContentMain extends Module {
    static content = {
        main { base.find(".foundation-layout-panel-content") }
    }

}


class PageGlobalNavModule extends Module {
    static content = {
        body(wait: true) { $("#globalnav-overlay") }
        tabList(wait: true) { $("coral-tablist") }
        tabContent(wait: true) { $("coral-panelstack") }
    }
}

class Editor extends Module {
    static base = { $(TouchUI.elements.DOCUMENT_BODY) }
    static content = {
        contentPanelSide(wait: true) { base.find(TouchUI.elements.EDITOR_PANEL_SIDE) }
        contentPanelContent(wait: true) { base.find(TouchUI.elements.EDITOR_PANEL_CONTENT) }

//        container (wait: true, required: true ) { $("#CQ") }
//        sidekick(wait: true, required: true) { $("#cq-sk") }
//        contentFinder (wait: true, required: true) { $("#cq-cf-west") }
//        sideBySide (wait: true, required: true) { $("#cq-cf-east") }
//        pageContent (wait: true, required: true) { $("#cq-cf-framewrapper") }

    }

    static printDebug(String name, Object values) {
        def json = JsonOutput.toJson(["${name}": values])

        System.out.println(json.toString())
        return true
    }

    def test() {
        return true
    }

    def Window(String action) {
        return Window(action, true)
    }

    def Window(String action, boolean doReturn) {
        String returnPrefix = ""
        String returnSuffix = ""

        action = action.trim()

        if (doReturn) {
            if (!action.startsWith("return")) {
                returnPrefix = "return "
                returnSuffix = ""
            }
        }
        if (!action.startsWith(".")) {
            action = "." + action
        }
        String baseScript = "window"
        String execScript = returnPrefix + baseScript + action + returnSuffix

//        printDebug("TouchUI Window",execScript)

        String result = js.exec(execScript)

//        printDebug("TouchUI Result",result)

        if (doReturn) {
//            printDebug("TouchUI Return", result)
            return result
        } else {
//            printDebug("TouchUI Return Auto", true)
            return true
        }

    }
//
//    def Editables(String action) {
//        return Editables(action, false)
//    }
//    def Editables(String action, boolean doReturn) {
//        String returnPrefix=""
//        if (doReturn) {
//            returnPrefix="return "
//        }
//        String baseScript = "return window.Granite.author.editables"
//        String execScript = returnPrefix + baseScript + action
//
//        println("Editables: "+execScript)
//
//        String result = js.exec ( execScript )
//        if (doReturn) {
//            return result
//        }else {
//            return true
//        }
//
//    }
//
//    def Editable(String action, String path) {
//
//        String baseScript = ".CQ.utils.WCM.getEditable('"+path+"')"
//        String execScript = baseScript + action
//
//        //  println("Editable: "+execScript)
//
//        return Window( execScript )
//
//    }
//
//    def Editable(String action, String path, boolean doReturn) {
//
//        String baseScript = ".CQ.utils.WCM.getEditable('"+path+"')"
//        String execScript = baseScript + action
//
//        //  println("Editable: "+execScript)
//
//        return Window( execScript, doReturn )
//
//    }
//
//    def addComponent(String path, String componentPart) {
//        throw new Error("not implemented")
//
////        return Editable(".createParagraph(CQ.WCM.getContentFinder().getContentWindow().CQ.utils.WCM.getComponentList().allComponents['"+componentPart+"'])",path + "/*")
//
//    }
//
//    def removeComponent(String path) {
//        throw new Error("not implemented")
////        return Editable(".removeParagraph()",path)
//
//    }

    def okDialog(String path) {
        return Window("Granite.author.DialogFrame.currentFloatingDialog.first().find(\".cq-dialog-submit\").click()", false)
    }

    def closeDialog(String path) {
        return js.exec("\$(\"button.cq-dialog-cancel\").click(); return true;")
    }

    def dialogHelp(String path) {
        return Window("Granite.author.DialogFrame.currentFloatingDialog.first().find(\".cq-dialog-help\").click()", false)
    }

    def toggleFullscreen(String path) {
        return Window("Granite.author.DialogFrame.currentFloatingDialog.first().find(\".cq-dialog-layouttoggle\").click()", false)
    }

    def waitForDialogToShow() {
        waitFor(5) { $("coral-dialog.is-open") }
    }

    def isDialogOpen(String path) {

        return js.exec("return \$(\"coral-dialog.is-open\").length != 0")

    }

    def setDialogValue(String path, String name, String value) {
        return Window("Granite.author.DialogFrame.currentFloatingDialog.first().find(\"[name=\'" + name + "\']\").val(\"" + value + "\")")
    }

    def showDialog(String path) {
        if (hasDialog(path)) {
            Window("Granite.author.editableHelper.doConfigure(Granite.author.editables.find(\"" + path + "\")[0]); return true;", false)

            waitForDialogToShow()

        } else {
            //return false
            throw new Error("Component Dialog is not available")
        }

    }

    def hasDialog(String path) {
        return Window("Granite.author.editables.find(\"" + path + "\").length != 0")
    }

    def execDialog(String path, String action) {
        //CQ.utils.WCM.getDialogs()["editdialog-/content/aemdesign-showcase/en/component/layout/contentblocklock/jcr:content/article/par/contentblocklock1"].find("name","permissionCheckTabAccessCheck")
        throw new Error("not implemented")
//        return Window(".CQ.utils.WCM.getDialogs()['editdialog-"+path+"']"+action,true)
    }

    def toDesignMode() {
        throw new Error("not implemented")
//        js.exec ( "return \$(\".cq-sidekick-design\").click()" )
    }

    def toPublishMode() {
        return Window("Granite.author.actions.viewAsPublished()", false)
//        js.exec ( "return \$(\".cq-sidekick-design\").click()" )
    }

    def toEditMode() {
        throw new Error("not implemented")
//        js.exec ( "return \$(\".cq-sidekick-edit\").click()" )
//        js.exec ( "return \$(\".cq-sidekick-reload\").click()"  )
    }


    def checkComponentRender(String path) {
        throw new Error("not implemented")
//        return Editable(".rendered",path)
    }

}



