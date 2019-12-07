def openReport(file) {
    System.println("Trying to open report: ${file.getPath()}")

    if (!file.isFile()) {
        System.err.println("Report file not found: ${file.getPath()}")
    } else {
        def url = "file://${file.canonicalPath}"

        System.println("Opening report: ${url}")
        java.awt.Desktop.desktop.browse url.toURI()
    }
}

if (System.properties.getProperty("test.openReport", "true") == "true") {
    try {
        String reportDir = System.properties.getProperty("project.buildDirectory", "remote-seleniumhub-chrome")

        File fileHtml = new File("./$reportDir/generated-docs/html/summary.html")
        openReport(fileHtml)

        File filePdf = new File("./$reportDir/generated-docs/pdf/summary.pdf")
        openReport(filePdf)
    } catch (Throwable t) {
        t.printStackTrace()
        return false
    }
} else {
    System.println("Skipping opening report")
}

return true
