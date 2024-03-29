package support

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class ReportConverter {
    static convertJsonReport() {

        String gebEnvReportsDir = System.getProperty("geb.build.reportsDir")
        def reportsDir = "${gebEnvReportsDir}/"
        def thisPath = new File('.').canonicalPath.replaceAll('\\\\', '/')
        def gebReports = new File(reportsDir, "gebReportInfo.json").text.replaceAll('\\\\', '/')
        def allReports = [specs: []]
        def jsonSlurper = new JsonSlurper()
        gebReports.eachLine { json ->
            def reportLine = jsonSlurper.parseText(json)
            if (!(reportLine.spec.label in allReports.specs.label)) {
                allReports.specs << [label: reportLine.spec.label,
                                     class: reportLine.spec.label.replaceAll("/", "."),
                                     tests: []]
            }
            def spec = allReports.specs.find { spec -> spec.label == reportLine.spec.label }
            if (!(reportLine.spec.test.num in spec.tests.num)) {
                spec.tests << [
                        num    : reportLine.spec.test.num,
                        label  : reportLine.spec.test.label,
                        urls   : [],
                        reports: []
                ]
            }
            def test = spec.tests.find { test -> test.num == reportLine.spec.test.num }
            if (!(reportLine.spec.test.report.num in test.reports.num)) {
                //create a new element in reports list
                test.reports << [
                        time : reportLine.spec.test.report.time,
                        num  : reportLine.spec.test.report.num,
                        label: reportLine.spec.test.report.label,
                        url  : reportLine.spec.test.report.url,
                        files: []
                ]

                //add url to list of urls if its not already there
                if (test.urls.indexOf(reportLine.spec.test.report.url) == -1) {
                    test.urls.add(reportLine.spec.test.report.url)
                }
            } else {
            }
            def report = test.reports.find { report -> report.num == reportLine.spec.test.report.num }
            if (!(reportLine.spec.test.report.files in report.files)) {
                report.files += reportLine.spec.test.report.files.collect {
                    "." + it.replaceAll('//', '/') - thisPath - reportsDir
                }
            } else {
                println "*" * 80
                println report.files.toString()
            }
        }

        def newJson = JsonOutput.toJson(allReports)
        File outputFile = new File(reportsDir, "gebReportInfo2.json")
        outputFile.write(JsonOutput.prettyPrint(newJson))
        return outputFile.absolutePath
    }

}
