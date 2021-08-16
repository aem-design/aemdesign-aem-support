package support

import com.assertthat.selenium_shutterbug.core.Shutterbug
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy
import geb.spock.GebReportingSpec
import groovy.json.JsonOutput
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.logging.LogEntries
import org.openqa.selenium.logging.LogEntry
import org.openqa.selenium.logging.LogType
import support.page.AdminLoginPage
import support.page.AdminLogoutPage

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.security.MessageDigest

abstract class FunctionalSpec extends GebReportingSpec {
    private static final String IMAGE_TYPE = "png"
    private static final String DRIVER_TYPE = System.properties.getProperty("project.buildDirectory", "unknown")
    private static final String SCREENSHOTS_REFERENCE_DIR = "src/test/screenshots"
    private static final String SCREENSHOTS_CURRENT_DIR = "test-screenshots"
    private static final double COMPARE_THRESHOLD = 0.01
    private static final double VERY_DIFFERENT = 1.0
    private static final boolean GENEREATE_FIRST_RENDTITION = true

    def getListViewPorts(label) {
        return getListViewPorts(label, true)
    }

    def getListViewPorts(label, boolean filter) {
        def config = getViewPorts(filter)

        config = config.find {
            label.equals(it.label)
        }

        //if found and it has properties return it otherwise return its name
        if (config instanceof LinkedHashMap && config.size() > 0) {
            return config
        }

        return label
    }

    def getViewPorts() {
        return getViewPorts(true)
    }

    def getViewPorts(Boolean filter) {
        def viewports =  [
            [label: "XS", width: 320, height: 480],
            [label: "SM", width: 640, height: 480],
            [label: "MD", width: 1024, height: 768],
            [label: "LG", width: 1366, height: 1024],
            [label: "XLG", width: 1920, height: 1280],
            [label: "XXLG", width: 2560, height: 1440]
        ]

        if (filter && System.properties.getProperty("test.viewports", "") != "") {
            def requiredviewports = System.properties.getProperty("test.viewports", "").split(",")

            viewports = viewports.findAll {
                requiredviewports.contains(it.label)
            }

        }

        return viewports
    }
    def getDialogViewPort() {
        return [
            [label: "MD", width: 1024, height: 768],
        ]
    }

    def setWindowSizeXS() {
        setWindowSize("XS", false)
    }

    def setWindowSizeSM() {
        setWindowSize("SM", false)
    }

    def setWindowSizeMD() {
        setWindowSize("MD", false)
    }

    def setWindowSizeLG() {
        setWindowSize("LG", false)
    }

    def setWindowSizeXLG() {
        setWindowSize("XLG", false)
    }

    def setWindowSizeXXLG() {
        setWindowSize("XXLG", false)
    }

    def setWindowsSize(int width, int height) {
        if (System.properties.getProperty("GLOBAL_ALLOW_RESIZE", "true").equals("true")) {
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(width, height))
        }
    }

    def setWindowSize(Object viewport) {
        setWindowSize(viewport, true)
    }

    def setWindowSize(Object viewport, boolean filter) {
        def viewportName

        //set default size
        def viewPortWidth = 1200
        def viewPortHeight = 900

        if (viewport instanceof LinkedHashMap) {
            viewportName = viewport.label
            viewPortWidth = viewport.width
            viewPortHeight = viewport.height
        } else if (viewport) {
            viewportName = viewport.toString()

            def viewPorts = getViewPorts(filter)
            def viewPort = viewPorts.find { value -> value.label == viewportName }

            if (!viewPort) {
                viewPort = viewPorts[3]
            }
            viewPortWidth = viewPort.width
             viewPortHeight = viewPort.height
        }

        setWindowsSize(viewPortWidth, viewPortHeight)

        return true
    }

    static String getHostName() {
        return System.properties.getProperty("aem.host", "localhost")
    }

    static String getUIMode() {
        return System.properties.getProperty("aem.authoruimode", "CLASSIC")
    }

    def login(iUsername, iPassword, pageToWait = null) {

//        System.out.println("Base URL: " + browser.getBaseUrl() )

        via AdminLogoutPage
        at AdminLoginPage

        if ( page.$("#submit-button-ims") ) {
//            System.out.println("ADMIN TASK ONLY LOGIN PAGE: activating local login form: " + page.getPageUrl())
            $("coral-accordion-item-label").click()
//            System.out.println("Clicked label page:" + $("coral-accordion-item-label"))
//            System.out.println("Waiting for:" + $("#login"))
//            System.out.println("Waiting for:" + $("form"))

            waitFor(10) { $("form").displayed }
//            System.out.println("Form is showing: " + $("form").displayed )
        }

//        System.out.println("Fill login form: " + page.getPageUrl() )

        if ( page.$("form#login [name=j_username]") ) {
//            System.out.println("Set value into j_username element: " + $("form#login [name=j_username]").getAttribute("outerHTML"))

            $("form#login [name=j_username]").value(iUsername)

//            System.out.println("Set value into j_username element: " + $("form#login [name=j_username]").getAttribute("value"))
        } else {
            throw new RuntimeException("Cant find j_username field on login page.", ex)
        }

        if ( page.$("form#login [name=j_password]") ) {
//            System.out.println("Set value into j_password element: " + $("form#login [name=j_password]").getAttribute("outerHTML") )

            $("form#login [name=j_password]").value(iPassword)

//            System.out.println("Set value into j_password element: " + $("form#login [name=j_password]").getAttribute("value") )
        } else {
            throw new RuntimeException("Can't find j_password field on login page.", ex)
        }

        if ( page.$("form#login [name=resource]") ) {
//            System.out.println("Set value into resource element: " + $("form#login [name=resource]").getAttribute("outerHTML"))

            js.exec("\$(\"form#login [name=resource]\").attr(\"value\",\"/content.xml\")")

//            System.out.println("Set value into resource element: " + $("form#login [name=resource]").getAttribute("value"))
        }

//        System.out.println("Submit login form: " + page.getPageUrl() )
//        System.out.println("Click submit: " + $("form#login [type=submit]").getAttribute("outerHTML") )

        $("form#login [type=submit]").click()
        // Go to the page, because it doesn't always land on the page you think it does
        if (pageToWait) {
            to pageToWait
        }

    }

    def loginAsAdmin() {
        setWindowSizeMD()

        def username = getAdminUsername()
        def password = getAdminPassword()

        login(username, password, null)
    }

    def getAdminUsername() {
        return System.properties.getProperty("aem.user", "admin")
    }

    def getAdminPassword() {
        return System.properties.getProperty("aem.password", "admin")
    }

    def getElementHeight(WebElement element) {
        return element.getSize().height == 0 ? driver.manage().window().getSize().height : element.getSize().height
    }

    def getElementWidth(WebElement element) {
        return element.getSize().width == 0 ? driver.manage().window().getSize().width : element.getSize().width
    }

    void takeScreenshot(WebElement element, String label) {
        scrollIntoView(element)

        String screenshotFile = fixFilePath("${browser.reportGroup.toString()}/${specificationContext.currentIteration.name}-${label}.png")

        screenshotFile = screenshot(element, screenshotFile)

        File referenceFile = new File(screenshotFile)

        if (referenceFile.exists()) {
            ReportListener tempRL = new ReportListener()

            tempRL.writeReportResource(
                browser.config.reportsDir.path.toString(),
                browser.reportGroup.toString(),
                browser.driver.currentUrl.toString(),
                browser.page.toString(),
                createReportLabel("screenshot"),
                [referenceFile]
            )
        }
    }

    def fixFilePath(String filepath) {
        return filepath.replaceAll(/[^a-zA-Z0-9\.\-\/]/, "-").replaceAll("--", "-")
    }
    /***
     * validate screenshot with exisitng file
     * @param filePath - generate String[] using {@link support.ComponentSpec#compileComponentScreenshotFileNamePath2
     * @return
     */
    def designReferenceFull(String[] filePath) {
        def fixedFilePath = "${DRIVER_TYPE}/${SCREENSHOTS_CURRENT_DIR}/${getWebDriverName()}/".concat(fixFilePath(filePath[0]))
        def fixedFileName = fixFilePath(filePath[1])
        def referenceFilePath = "${SCREENSHOTS_REFERENCE_DIR}/${DRIVER_TYPE}/${getWebDriverName()}/${fixedFilePath}"
        def referenceFilename = "${referenceFilePath}/${fixedFileName}"
        def referenceDiffFilePath = "${browser.reportGroup.toString()}/${specificationContext.currentIteration.name}"

        def screenshotFilename = screenshotFull(fixedFilePath, fixedFileName)
        def referenceDiffFilename = "${fixedFilePath}/".concat(fixedFileName.concat(".diff"))

        File referenceFile = new File(referenceFilename.concat(".png"))

        if (!referenceFile.exists()) {
            new File(referenceFile.parent).mkdirs()

            FileUtils.copyFile(new File(screenshotFilename.concat(".png")), referenceFile)

            ReportListener tempRL = new ReportListener()

            tempRL.writeReportResource(
                browser.config.reportsDir.path.toString(),
                browser.reportGroup.toString(),
                browser.driver.currentUrl.toString(),
                browser.page.toString(),
                createReportLabel("compareToDesign"),
                [referenceFile]
            )

            return true
        }

        def isTestPass = false
        def result = VERY_DIFFERENT

        for (def i = 0; i < 1; i++) {
            result = compare(screenshotFilename.concat(".png"),
                referenceFilename.concat(".png"), referenceDiffFilename.concat(".png"))

            isTestPass = (result <= COMPARE_THRESHOLD)
        }

        File screenshotFile = new File(screenshotFilename.concat(".png"))
        File differenceFile = new File(referenceDiffFilename.concat(".png"))

        ReportListener tempRL = new ReportListener()

        tempRL.writeReportResource(
            browser.config.reportsDir.path.toString(),
            browser.reportGroup.toString(),
            browser.driver.currentUrl.toString(),
            browser.page.toString(),
            createReportLabel("compareToDesign"),
            [referenceFile, screenshotFile, differenceFile]
        )

        return isTestPass
    }

    def designReference(WebElement element) {
        return designReference(element,
            "${browser.reportGroup.toString()}/${specificationContext.currentIteration.name}.png")
    }

    def designReference(WebElement element, String filePath) {
        scrollIntoView(element)

        def screenshotFilename
        def differenceFilename
        def returnVal = false
        def fixedFilePath = fixFilePath(filePath)
        def referenceFilename = "${SCREENSHOTS_REFERENCE_DIR}/${DRIVER_TYPE}/${getWebDriverName()}/${fixedFilePath}"

        File referenceFile = new File(referenceFilename)

        if (!referenceFile.exists()) {
            screenshotFilename = screenshot(element, fixedFilePath)

            new File(referenceFile.parent).mkdirs()

            if (GENEREATE_FIRST_RENDTITION) {
                FileUtils.copyFile(new File(screenshotFilename), referenceFile)

                returnVal = true
            } else {
                returnVal = false
            }

            ReportListener tempRL = new ReportListener()

            tempRL.writeReportResource(
                browser.config.reportsDir.path.toString(),
                browser.reportGroup.toString(),
                browser.driver.currentUrl.toString(),
                browser.page.toString(),
                createReportLabel("compareToDesign"),
                [referenceFile]
            )

            return returnVal
        }

        def isTestPass = false
        def result = VERY_DIFFERENT

        for (def i = 0; i < 3; i++) {
            screenshotFilename = screenshot(element, fixedFilePath)
            differenceFilename = screenshotFilename.replaceFirst(/^(.*)\.png$/, "\$1.diff.png")
            result = compare(screenshotFilename, referenceFilename, differenceFilename)

            isTestPass = (result <= COMPARE_THRESHOLD)
        }

        File screenshotFile = new File(screenshotFilename)
        File differenceFile = new File(differenceFilename)

        ReportListener tempRL = new ReportListener()

        tempRL.writeReportResource(
            browser.config.reportsDir.path.toString(),
            browser.reportGroup.toString(),
            browser.driver.currentUrl.toString(),
            browser.page.toString(),
            createReportLabel("compareToDesign"),
            [referenceFile, screenshotFile, differenceFile]
        )

        return isTestPass
    }

    private def screenshotFull(String filePath, String fileName) {
        def screenshotFilename = "${filePath}/${fileName}"

        Shutterbug.shootPage(driver, ScrollStrategy.WHOLE_PAGE_CHROME, true)
            .withName(fileName)
            .save(filePath)

        return screenshotFilename
    }

    private def screenshot(WebElement element, String filePath) {
        return screenshot(element, filePath, true, true, true)
    }

    private def screenshot(WebElement element, String filePath, Boolean focus, Boolean highlight, Boolean crop) {
        if (element == null || filePath.isEmpty()) {
            return ""
        }

        Actions act = new Actions(driver)
        WebElement documentBody = driver.findElement(By.xpath("//body"))

        act.moveToElement(documentBody).build().perform()

        def screenshotFilename = "${DRIVER_TYPE}/${SCREENSHOTS_CURRENT_DIR}/${getWebDriverName()}/${filePath}"

        if (element != null) {
            int scrollTopValue = 0
            JavascriptExecutor js = (JavascriptExecutor) driver

            js.executeScript("document.querySelector('html').style.overflow = 'hidden';")

            if (focus) {
                js.executeScript("arguments[0].scrollIntoView();", element)
                scrollTopValue = (Integer) js.executeScript("return window.pageYOffset;")
            }

            if (highlight) {
                js.executeScript("arguments[0].setAttribute('style', 'box-shadow: 0 0 0 99999px rgba(0, 0, 0, 1);position: relative;z-index: 9999;')", element)
            }

            File imageFile = (driver as TakesScreenshot).getScreenshotAs(OutputType.FILE)
            BufferedImage bufferedImage = ImageIO.read(imageFile)

            if (crop) {
                Dimension windowSize = driver.manage().window().getSize()

                Point location = element.getLocation()
                Dimension size = element.getSize()

                int width = size.getWidth()
                int height = size.getHeight()

                int left = location.getX()
                def top = location.getY() - scrollTopValue
                def right = left + size.getWidth()
                def bottom = top + size.getHeight()

                if (width > bufferedImage.getWidth()) {
                    right = bufferedImage.getWidth() - left
                }

                if (height > bufferedImage.getHeight()) {
                    bottom = bufferedImage.getHeight() - top
                }

                def elmentCoord = js.executeScript("return arguments[0].getBoundingClientRect();", element)

                printDebug("ELEMENT POSITION: ", elmentCoord)

                printDebug("SCREENSHOT: ", [
                    filePath,
                    "scrollTopValue", scrollTopValue,
                    "left", left,
                    "top", top,
                    "right", right,
                    "bottom", bottom,
                    "width", bufferedImage.getWidth(),
                    "height", bufferedImage.getHeight(),
                    "windowSize", windowSize])

                int cropHight = elmentCoord["height"] as int
                int cropWidth = elmentCoord["width"] as int

                int viewportHight = windowSize.height
                int viewportWidth = windowSize.width

                int posX = elmentCoord["x"] as int
                int posY = elmentCoord["y"] as int

                if ((posX + cropWidth) > viewportWidth) {
                    cropWidth = viewportWidth - posX
                }

                if ((posY + cropHight) > viewportHight) {
                    cropHight = viewportHight - posY
                }

                BufferedImage newImage = bufferedImage.getSubimage(
                    elmentCoord["x"] as int,
                    elmentCoord["y"] as int,
                    cropWidth,
                    cropHight)

                ImageIO.write(newImage, IMAGE_TYPE, imageFile)
            }

            File screenshotFile = new File(screenshotFilename)

            FileUtils.copyFile(imageFile, screenshotFile)
        } else {
            File imageFile = (driver as TakesScreenshot).getScreenshotAs(OutputType.FILE)
            File screenshotFile = new File(screenshotFilename)

            FileUtils.copyFile(imageFile, screenshotFile)
        }

        return screenshotFilename
    }

    private def compare(String screenshotFilename, String referenceFilename, String differenceFilename) {
        def compareCmd = "compare -verbose -metric mae -fuzz 10% \"${screenshotFilename}\" \"${referenceFilename}\" \"${differenceFilename}\" 2>&1 || convert \"${screenshotFilename}\" \"${referenceFilename}\" -compose difference -composite +level-colors black,red \"${differenceFilename}\" 2>&1 | echo \"all: 1.0 (1.0)\""

        def processErrorText = ""

        if (System.properties.getProperty("HAS_COMPARE", "false") == "false" && System.properties.getProperty("HAS_DOCKER", "false") == "false") {
            printDebug("NO COMPARE", ["SKIPPING", System.properties.getProperty("HAS_COMPARE", false), System.properties.getProperty("HAS_DOCKER", false)])
            return VERY_DIFFERENT
        } else if ( System.properties.getProperty("HAS_DOCKER", "false") == "true" && System.properties.getProperty("HAS_COMPARE", "false") == "false" ) {
            compareCmd = "compare.sh ${screenshotFilename} ${referenceFilename} ${differenceFilename}"
//            printDebug("RUNNING COMPARE IN DOCKER", [compareCmd])
            processErrorText = getRunDockerCommandOutput(compareCmd)

        } else {
            processErrorText = getCommandOutput(compareCmd)
        }

//        printDebug("COMPARE OUTPUT", [processErrorText])

        def res = processErrorText =~ /all: [\d\.]+ \((.+)\)/

        if (res) {
            return Double.valueOf(res[0][1])
        }

        return VERY_DIFFERENT
    }

    private static String loadStream(InputStream s) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(s))
        StringBuilder sb = new StringBuilder()

        String line
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n")
        }

        return sb.toString()
    }

    private def getWebDriverName() {
        return driver.class.simpleName
    }

    static String MD5(byte[] bytes) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5")

            byte[] array = md.digest(bytes)
            StringBuffer sb = new StringBuffer()

            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3))
            }

            return sb.toString()
        } catch (java.security.NoSuchAlgorithmException e) {}

        return ""
    }

    static String SHA1(byte[] bytes) {
        try {
            java.security.MessageDigest md = MessageDigest.getInstance("SHA-1")
            md.update(bytes)

            byte[] array = md.digest()
            StringBuffer sb = new StringBuffer()

            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3))
            }

            return sb.toString()
        } catch (java.security.NoSuchAlgorithmException e) {}

        return ""
    }

    def verifyAssetDownload(String href) {
        verifyAssetDownload(href, "")
    }

    def verifyAssetDownload(String href, String sha1) {
        try {
            def downloadBytes = downloadBytes(href)
            def downloadBytesSHA1 = SHA1(downloadBytes)

            if (sha1.empty) {
                def verifyFileUrl = href.concat("/jcr:content/metadata/dam:sha1.json")
                def downloadSHA1 = downloadContent(verifyFileUrl)
                def verifyFileUrlJson = IOUtils.toString(new InputStreamReader(downloadSHA1))

                return verifyFileUrlJson.contains(downloadBytesSHA1)
            } else {
                return downloadBytesSHA1 == sha1
            }
        } catch (Exception ex) {
            printDebug("verifyAssetDownload", [ex])
        }

        return false
    }

    def getWindowWidthName() {
        def driverWidth = driver.manage().window().size.width
        def viewPorts = getViewPorts()

        def viewPort = viewPorts.find { value ->
            value.width == driverWidth
        }

        if (!viewPort) {
            viewPort = viewPorts[3] //LG
        }

        return viewPort.label
    }

    void ExtractJSLogs() {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER)

        for (LogEntry entry : logEntries) {
            System.out.println("BROWSER CONSOLE ERROR: " + new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage())
        }
    }

    static printDebug(String name, Object values) {
        def json = JsonOutput.toJson(["${name}": values])

        System.out.println(json.toString())

        return true
    }

    def waitForImagesToLoad(imagesList) {
        for (def item in imagesList) {
            def imageSrc = item.attr("src")

            waitFor {
                !js.exec("return jQuery(\"img[src='${imageSrc}']\").not(\"[class='is-loading']\").css(\"opacity\")==1;")
            }
        }

        return true
    }

    def waitForImagesToLoad2(imagesList) {
        for (def item in imagesList) {
            def imageSrc = item.attr("src")

            waitFor { !js.exec("return jQuery(\"img[src='${imageSrc}']\").complete;") }
        }

        return true
    }

    def waitForDocumentReady() {
        return waitFor { js.exec("return document.readyState=='complete';") }
    }

    def waitForPageToLoad() {
        return waitFor {
            js.exec("return (window.\$.active==0?true:false))") == true
        }
    }

    def getImageWidth(imgUrl) {
        def usernamePassword = getAdminUsername() + ":" + getAdminUsername()
        def encodedCreds = Base64.encoder.encode(usernamePassword.getBytes())
        def url = new URL(imgUrl)

        def urlConnection = url.openConnection()
        urlConnection.setRequestProperty("Authorization", "Basic " + encodedCreds)

        def img = ImageIO.read(urlConnection.getInputStream())

        return img.getWidth()
    }

    def analyzeLog() {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER)

        for (LogEntry entry : logEntries) {
            printDebug("CONSOLE.LOG", [
                new Date(entry.getTimestamp()),
                entry.getLevel(),
                entry.getMessage()])
        }
    }

    def scrollIntoView(element) {
        Actions actions = new Actions(driver)
        actions.moveToElement(element)
        actions.perform()

        JavascriptExecutor js = (JavascriptExecutor) driver
        js.executeScript("arguments[0].scrollIntoView();", element)

        return true
    }

    def clickOnElement(element) {
        scrollIntoView(element)

        Actions actions = new Actions(driver)
        actions.click()
        actions.perform()

        return true
    }


    def getRunDockerCommandOutput(String commandToRun, int wait = 5000) {
        String GLOBAL_PROJECT_ROOT_DIR = System.properties.getProperty("project.rootdir", "")
        String GLOBAL_PROJECT_ROOT_NAME = System.properties.getProperty("project.rootname", "")
        String GLOBAL_PROJECT_CURRENT_NAME = System.properties.getProperty("project.currentname", "")

//        printDebug("RUNNING IN DOCKER", ["docker run --rm -v ${GLOBAL_PROJECT_ROOT_DIR}:/build/${GLOBAL_PROJECT_ROOT_NAME} -w \"/build/${GLOBAL_PROJECT_CURRENT_NAME}\" aemdesign/centos-java-buildpack bash -l -c '${commandToRun}'"])
        return getCommandOutput("docker run --rm -v ${GLOBAL_PROJECT_ROOT_DIR}:/build/${GLOBAL_PROJECT_ROOT_NAME} -w \"/build/${GLOBAL_PROJECT_CURRENT_NAME}\" aemdesign/centos-java-buildpack bash -l ${commandToRun}")
    }


    def getCommandOutput(String commandToRun, int wait = 5000) {

        try {

            def sout = new StringBuilder(), serr = new StringBuilder()
            def proc = commandToRun.execute()
            proc.consumeProcessOutput(sout, serr)
            proc.waitForOrKill(wait)
//            printDebug("COMMAND", [sout,serr])
            return sout

        } catch (ignored) {
//            printDebug("COMMAND ERROR", [ignored])
            //do nothing
        }
        return ""
    }
}

