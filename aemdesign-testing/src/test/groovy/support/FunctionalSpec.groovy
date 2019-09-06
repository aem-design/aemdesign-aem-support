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

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.security.MessageDigest

abstract class FunctionalSpec extends GebReportingSpec {

    private static final String IMAGE_TYPE = "png"
    private static final String DRIVER_TYPE = System.properties.getProperty("project.buildDirectory","unknown")
    private static final String SCREENSHOTS_REFERENCE_DIR = "src/test/screenshots"
    private static final String SCREENSHOTS_CURRENT_DIR = "test-screenshots"
    private static final double COMPARE_THRESHOLD = 0.01
    private static final double VERY_DIFFERENT = 1.0
    private static final boolean GENEREATE_FIRST_RENDTITION = true
    // Small phone
    // Big phone
    // Tablet
    // Laptop
    // Desktop
    // 4k
    def getListViewPorts(label) {
        def config = getViewPorts()
        String[] labels = getViewPorts()[0]
        for (int i = 0; i < labels.length; i++) {
            if (label != "") {
                if (config[i].label == label) {
                    return config[i].label
                }
            }
            labels[i] = config[i].label
        }
        return labels
    }

    def getViewPorts() {
        return [
                [label: "XS", width: 320, height: 480],
                [label: "SM", width: 640, height: 480],
                [label: "MD", width: 1024, height: 768],
                [label: "LG", width: 1366, height: 1024],
                [label: "XLG", width: 1920, height: 1280],
                [label: "XXLG", width: 2560, height: 1440]
        ]
    }

//    Bootstrap
//    <576px  Extra small
//    ≥576px  Small
//    ≥768px  Medium
//    ≥992px  Large
//    ≥1200px Extra large

// iPhone4  [320, 480, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
// iPhone5  [320, 568, 'Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X; en-us) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53'],
// Nexus 5  [360, 598, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
// iPhone6  [375, 667, 'Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4'],
// Nexus5s  [412, 684, 'Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4'],
// iPhone6+ [414, 736, 'Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4'],
// Nexus 7  [600, 960, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//          [600, 1024, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
// iPadAir  [768, 1024, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//          [800, 600), "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
// Nexus10  [800, 1280), "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
// iPad4    [1024, 768, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
// iPad4P   [1024, 1366, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//    [1152, 864, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//    [1280, 768, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//    [1280, 800, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//    [1280, 1024, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//    [1360, 768, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//    [1366, 768, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//    [1440, 900, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//    [1600, 900, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//    [1600, 1200, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//    [1680, 1050, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//    [1920, 1080, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//    [1920, 1200, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"],
//    [2560, 1440, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"]
//    [2560, 1600, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"]
// 4K [3840, 2160, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"]


    def setWindowSizeXS() {
        setWindowSize(getListViewPorts("XS"))
    }

    def setWindowSizeSM() {
        setWindowSize(getListViewPorts("SM"))
    }

    def setWindowSizeMD() {
        setWindowSize(getListViewPorts("MD"))
    }

    def setWindowSizeLG() {
        setWindowSize(getListViewPorts("LG"))
    }

    def setWindowSizeXLG() {
        setWindowSize(getListViewPorts("XLG"))
    }

    def setWindowSizeXXLG() {
        setWindowSize(getListViewPorts("XXLG"))
    }

    def setWindowsSize(int width, int height) {
//        printDebug("setWindowsSize set", [width,height])

        if (System.properties.getProperty("GLOBAL_ALLOW_RESIZE","true").equals("true")) {
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(width, height))
        }

//        printDebug("setWindowsSize after set", driver.manage().window().getSize())
    }


    def setWindowSize(Object viewport) {

        def viewportName

        if (viewport instanceof LinkedHashMap) {
            viewportName = viewport.label
        } else {
            viewportName = viewport.toString()
        }

        def viewPorts = getViewPorts()

        def viewPort = viewPorts.find { value -> value.label == viewportName }

        if (!viewPort) {
            viewPort = viewPorts[3]
        }

        def viewPortWidth = viewPort.width
        def viewPortHeight = viewPort.height

//        printDebug("Testing View Port",[
//                setWindowSize:viewportName,
//                width:viewPortWidth,
//                height:viewPortHeight,
//        ])

        setWindowsSize(viewPortWidth, viewPortHeight)

        return true
    }

    static String getHostName() {
        return System.properties.getProperty("crx.host", "localhost")
    }


    static String getUIMode() {
        return System.properties.getProperty("crx.authoruimode", "CLASSIC")
    }


    def login(iUsername, iPassword, pageToWait = null) {
        to AdminLoginPage
        go()
        at AdminLoginPage

        page.username.value(iUsername)
        page.password.value(iPassword)
        signIn.click()

        try {
            // Go to the page, because it doesn't always land on the page you think it does
            if (pageToWait) {
                to pageToWait
            }
        }
        catch (ex) {
            report "afterLogin"
            throw new RuntimeException("Error logging in! Please check target directory for afterLogin.[html,png] for more details", ex)
        }
    }


    def loginAsAdmin() {

        setWindowSizeMD()

//        driver.manage().window().size = new Dimension(640, 960)

        def username = getAdminUsername()
        def password = getAdminPassword()
        login(username, password, null)

//        Cookie cookie = new Cookie.Builder("cq-authoring-mode", getUIMode())
//                .domain(getHostName())
//                .build()
//
//        browser.driver.manage().addCookie(cookie)

    }

    def getAdminUsername() {
        return System.properties.getProperty("crx.user", "admin")
    }

    def getAdminPassword() {
        return System.properties.getProperty("crx.password", "admin")
    }

//    def designReference(String filePath) {
//        designReference(null as WebElement, filePath)
//    }

//    def designReference(Navigator navigator, String filePath) {
//        designReference(navigator.firstElement(), filePath)
//    }

//    def designReference(Page page, String filePath) {
//        designReference(page.getNavigator(), filePath)
//    }

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
//            printDebug("SCREENSHOT", [screenshotFile,referenceFile.exists()])

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


    def designReferenceFull(String[] filePath) {

        def fixedFilePath = "${DRIVER_TYPE}/${SCREENSHOTS_CURRENT_DIR}/${getWebDriverName()}/".concat(fixFilePath(filePath[0]))
        def fixedFileName = fixFilePath(filePath[1])
        def referenceFilePath = "${SCREENSHOTS_REFERENCE_DIR}/${DRIVER_TYPE}/${getWebDriverName()}/${fixedFilePath}"
        def referenceFilename = "${referenceFilePath}/${fixedFileName}"
        def referenceDiffFilePath = "${browser.reportGroup.toString()}/${specificationContext.currentIteration.name}"


        //printDebug("DRFULL-REF", [referenceFilename])
        //take current screen shot
        def screenshotFilename = screenshotFull(fixedFilePath,fixedFileName)
        //printDebug("DRFULL-SCR", [screenshotFilename])

        def referenceDiffFilename = "${fixedFilePath}/".concat(fixedFileName.concat(".diff"))
        //printDebug("DRFULL-DIFF", [referenceDiffFilename])

        //check if reference does not exist if not create the reference image
        File referenceFile = new File(referenceFilename.concat(".png"))
        if (!referenceFile.exists()) {

            new File(referenceFile.parent).mkdirs()

            FileUtils.copyFile(new File(screenshotFilename.concat(".png")), referenceFile)

            //screenshotFilename = screenshotFull(referenceFilePath, fixedFileName)

            //printDebug("DRFULL-NREF", [referenceFile])

            //referenceFile = new File(screenshotFilename.concat(".png"))

            ReportListener tempRL = new ReportListener()
            tempRL.writeReportResource(
                    browser.config.reportsDir.path.toString(),
                    browser.reportGroup.toString(),
                    browser.driver.currentUrl.toString(),
                    browser.page.toString(),
                    createReportLabel("compareToDesign"),
                    [referenceFile]
            )

            return true //nothing to compare
        }

        //check if reference does not exist if exist compare
//        def isTestPass = compareFull(referenceFilename,referenceDiffFilename)

        def isTestPass = false;
        def result = VERY_DIFFERENT
        for (def i = 0; i < 1; i++) {
//            screenshotFilename = screenshot(element, fixedFilePath)
//            referenceDiffFilename = screenshotFilename.replaceFirst(/^(.*)\.png$/, "\$1.diff.png")
            result = compare(screenshotFilename.concat(".png"), referenceFilename.concat(".png"), referenceDiffFilename.concat(".png"))

//            printDebug("COMPARE RESULT",result <= COMPARE_THRESHOLD)

            isTestPass = (result <= COMPARE_THRESHOLD)
        }

        //printDebug("DRFULL-3", [isTestPass])

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
        return designReference(element, "${browser.reportGroup.toString()}/${specificationContext.currentIteration.name}.png")
    }

    def designReference(WebElement element, String filePath) {
        scrollIntoView(element)
//        printDebug("DESIGN REFERENCE1", [element.getSize(),filePath])

        def screenshotFilename
        def differenceFilename
        def returnVal = false
        def fixedFilePath = fixFilePath(filePath)
        def referenceFilename = "${SCREENSHOTS_REFERENCE_DIR}/${DRIVER_TYPE}/${getWebDriverName()}/${fixedFilePath}"

//        printDebug("DESIGN REFERENCE2", [fixedFilePath, referenceFilename])

//        printDebug("REPORT WRITER",[
//                FEATURE: specificationContext.currentFeature.name,
//                SPEC: specificationContext.currentSpec.name,
//                ITERATION: specificationContext.currentIteration.name,
//                reportDir: browser.config.reportsDir.path.toString(),
//                reportGroup: browser.reportGroup.toString(),
//                currentUrl: browser.driver.currentUrl.toString(),
//                page: browser.page.toString()
//        ])


        File referenceFile = new File(referenceFilename)

        if (!referenceFile.exists()) {
            screenshotFilename = screenshot(element, fixedFilePath)
            new File(referenceFile.parent).mkdirs()
//            System.out.println("")
            if (GENEREATE_FIRST_RENDTITION) {
                FileUtils.copyFile(new File(screenshotFilename), referenceFile)

//                printDebug("DESIGN REFERENCE",[
//                        CREATE:true,
//                        FILEPATH:fixedFilePath,
//                        REFERENCE:referenceFilename,
//                        LABEL: specificationContext.currentIteration.name
//                ])

                returnVal = true
            } else {

//                printDebug("DESIGN REFERENCE",[
//                        CREATE:false,
//                        FILEPATH:fixedFilePath,
//                        SOURCE:screenshotFilename,
//                        REFERENCE:referenceFilename,
//                        LABEL: specificationContext.currentIteration.name
//                ])

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
        def isTestPass = false;
        def result = VERY_DIFFERENT
        for (def i = 0; i < 3; i++) {
            screenshotFilename = screenshot(element, fixedFilePath)
            differenceFilename = screenshotFilename.replaceFirst(/^(.*)\.png$/, "\$1.diff.png")
            result = compare(screenshotFilename, referenceFilename, differenceFilename)

//            printDebug("COMPARE RESULT",result <= COMPARE_THRESHOLD)

            isTestPass = (result <= COMPARE_THRESHOLD)
        }

        File screenshotFile = new File(screenshotFilename)
        File differenceFile = new File(differenceFilename)

//        printDebug("DESIGN REFERENCE",[
//                    MATCH:isTestPass,
//                    SOURCE:referenceFile.absolutePath,
//                    DESTINATION:screenshotFile.absolutePath,
//                    VARIANCE:result,
//                    RESULT:differenceFile.absolutePath,
//                    LABEL: specificationContext.currentIteration.name
//            ])

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

        Shutterbug.shootPage(driver, ScrollStrategy.WHOLE_PAGE_CHROME,true).withName(fileName).save(filePath)

        return screenshotFilename
    }

    private def screenshot(WebElement element, String filePath) {
        return screenshot(element, filePath, true, true, true)
    }

    private def screenshot(WebElement element, String filePath, Boolean focus, Boolean highlight, Boolean crop) {

        if (element == null || filePath.isEmpty()) {
            return ""
        }

        Actions act = new Actions(driver);
        WebElement documentBody = driver.findElement(By.xpath("//body"));
        act.moveToElement(documentBody).build().perform();

        def screenshotFilename = "${DRIVER_TYPE}/${SCREENSHOTS_CURRENT_DIR}/${getWebDriverName()}/${filePath}"

//        WebElement image = driver.findElement(By.xpath("xpath"));
//        Point p = image.getLocation();
//        BufferedImage cropImg = documentBody.getSubimage(cropStartX, cropStartY, imageWidth-p.getX(),
//                imageHeight-p.getY());

        if (element != null) {
            //new chrome only takes screenshots of current View Port

            int scrollTopValue = 0;
            JavascriptExecutor js = (JavascriptExecutor) driver;

            //remove scrollbars
            js.executeScript("document.querySelector('html').style.overflow = 'hidden';")

            //element - scroll into view
            if (focus) {
                js.executeScript("arguments[0].scrollIntoView();", element);
                scrollTopValue = (Integer) js.executeScript("return window.pageYOffset;");
            }

            //element - highlight
            if (highlight) {
                js.executeScript("arguments[0].setAttribute('style', 'box-shadow: 0 0 0 99999px rgba(0, 0, 0, 1);position: relative;z-index: 9999;')", element);
            }


            File imageFile = (driver as TakesScreenshot).getScreenshotAs(OutputType.FILE);

            BufferedImage bufferedImage = ImageIO.read(imageFile);

            if (crop) {

                Dimension windowSize = driver.manage().window().getSize()

                Point location = element.getLocation()
                Dimension size = element.getSize()

                int width = size.getWidth();
                int height = size.getHeight();

                int left = location.getX();
                def top = location.getY() - scrollTopValue;
                def right = left + size.getWidth();
                def bottom = top + size.getHeight();

                if (width > bufferedImage.getWidth()) {
                    right = bufferedImage.getWidth() - left;
                }
                if (height > bufferedImage.getHeight()) {
                    bottom = bufferedImage.getHeight() - top;
                }

                def elmentCoord = js.executeScript("return arguments[0].getBoundingClientRect();", element);

                printDebug("ELEMENT POSITION: ", elmentCoord)

                printDebug("SCREENSHOT: ", [filePath, "scrollTopValue", scrollTopValue, "left", left, "top", top, "right", right, "bottom", bottom,
                                            "width", bufferedImage.getWidth(), "height", bufferedImage.getHeight(), "windowSize", windowSize])

                int cropHight = elmentCoord["height"] as int
                int cropWidth = elmentCoord["width"] as int

                int viewportHight = windowSize.height
                int viewportWidth = windowSize.width

                int posX = elmentCoord["x"] as int
                int posY = elmentCoord["y"] as int


                if ((posX + cropWidth) > viewportWidth ) {
                    cropWidth = viewportWidth - posX
                }

                if ((posY + cropHight) > viewportHight ) {
                    cropHight = viewportHight - posY
                }

//                int x, int y, int w, int h
                BufferedImage newImage = bufferedImage.getSubimage(
                        elmentCoord["x"] as int,
                        elmentCoord["y"] as int,
                        cropWidth,
                        cropHight)
//                BufferedImage newImage = bufferedImage.getSubimage(left, top, right, bottom)

                //use new buffered image
                ImageIO.write(newImage, IMAGE_TYPE, imageFile)

            }

            File screenshotFile = new File(screenshotFilename);
            FileUtils.copyFile(imageFile, screenshotFile);

        } else {
            //just take screenshot of current viewport
            File imageFile = (driver as TakesScreenshot).getScreenshotAs(OutputType.FILE);
            File screenshotFile = new File(screenshotFilename);
            FileUtils.copyFile(imageFile, screenshotFile);
        }


        return screenshotFilename
    }

    private def compare(String screenshotFilename, String referenceFilename, String differenceFilename) {
        // run command and wait for process to complete
        def compareCmd = "compare -verbose -metric mae -fuzz 10% \"${screenshotFilename}\" \"${referenceFilename}\" \"${differenceFilename}\" 2>&1 || convert \"${screenshotFilename}\" \"${referenceFilename}\" -compose difference -composite +level-colors black,red \"${differenceFilename}\" 2>&1 | echo \"all: 1.0 (1.0)\""

        //println("{\"COMMAND\": \"${compareCmd}\"}");
        //printDebug("COMPARE",["compare -verbose -metric mae -fuzz 10%", screenshotFilename,  referenceFilename, differenceFilename])

        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", compareCmd);

        //| sed -e 's/all: [\d\.]+ [()\(.+\)[)]//p' | awk '{print $2}'

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();


        String output = loadStream(process.getInputStream());
        String error = loadStream(process.getErrorStream());
        int rc = process.waitFor();

        //printDebug("COMPARE OUTPUT",output)
//        println("{\"COMPARE OUTPUT\": \"${output}\"}");

        if (StringUtils.isNotEmpty(error)) {
            //printDebug("COMPARE ERROR",error)
//            println("{\"COMPARE ERROR\": \"${error}\"}");
        }

        def processErrorText = output
        def res = processErrorText =~ /all: [\d\.]+ \((.+)\)/
        if (res) {
            return Double.valueOf(res[0][1])
        }

        return VERY_DIFFERENT
    }

    private static String loadStream(InputStream s) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(s));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            sb.append(line).append("\n");
        return sb.toString();
    }

    private def getWebDriverName() {
        return driver.class.simpleName
    }

    static String MD5(byte[] bytes) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(bytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return "";
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

        } catch (java.security.NoSuchAlgorithmException e) {
        }

        return ""
    }

    def verifyAssetDownload(String href) {
        verifyAssetDownload(href,"")

    }
    def verifyAssetDownload(String href, String sha1) {

        try {

            def downloadBytes = downloadBytes(href)
            def downloadBytesSHA1 = SHA1(downloadBytes)
//            printDebug("Downloaded File SHA1 is: ",[downloadBytesSHA1])

            if (sha1.empty) {
                def verifyFileUrl = href.concat("/jcr:content/metadata/dam:sha1.json")
//            printDebug("Downloading DAM:SHA from DAM URL",[verifyFileUrl])

                def downloadSHA1 = downloadContent(verifyFileUrl)
//            printDebug("Downloading DAM:SHA",[downloadSHA1])

                def verifyFileUrlJson = IOUtils.toString(new InputStreamReader(downloadSHA1))
//            printDebug("Got DAM:SHA JSON",[verifyFileUrlJson])

                return verifyFileUrlJson.contains(downloadBytesSHA1)
            } else {
                return downloadBytesSHA1 == sha1
            }


        } catch (Exception ex) {
            printDebug("verifyAssetDownload",[ex])
        }
        return false

    }

    def getWindowWidthName() {
        def driverWidth = driver.manage().window().size.width
        def viewPorts = getViewPorts()

        def viewPort = viewPorts.find { value -> value.width == driverWidth }

//        printDebug("getWindowWidthName", [driverWidth,viewPorts,viewPort])

        if (!viewPort) {
            viewPort = viewPorts[3] //LG
        }

        return viewPort.label
    }

    void ExtractJSLogs() {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            System.out.println("BROWSER CONSOLE ERROR: " + new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
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
//            printDebug("WAIT FOR", imageSrc)
            waitFor {
                !js.exec("return jQuery(\"img[src='${imageSrc}']\").not(\"[class='is-loading']\").css(\"opacity\")==1;")
            }
        }
        return true
    }

    def waitForImagesToLoad2(imagesList) {
        for (def item in imagesList) {
            def imageSrc = item.attr("src")
//            printDebug("WAIT FOR", imageSrc)
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
        def usernamePassword = getAdminUsername() + ":" + getAdminUsername();
        def encodedCreds =   Base64.encoder.encode(usernamePassword.getBytes())
        def url = new URL(imgUrl)
        def urlConnection = url.openConnection()
        urlConnection.setRequestProperty("Authorization", "Basic " + encodedCreds);
        def img = ImageIO.read(urlConnection.getInputStream())

        return img.getWidth()
    }

    public def analyzeLog() {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER)
        for (LogEntry entry : logEntries) {
            printDebug("CONSOLE.LOG", [new Date(entry.getTimestamp()), entry.getLevel(), entry.getMessage()])
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

}

