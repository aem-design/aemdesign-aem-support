import com.google.common.io.Files
import geb.driver.SauceLabsDriverFactory
import groovy.json.JsonOutput
import org.openqa.selenium.Dimension
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.safari.SafariDriver
import support.ReportListener
import io.appium.java_client.remote.MobileCapabilityType
import org.openqa.selenium.remote.DesiredCapabilities

import java.util.logging.Level

import static org.apache.commons.lang3.SystemUtils.*

//find and select driver bin to use
File findDriverExecutable(String named) {

    new File("drivers").listFiles().findAll {
        !it.name.endsWith(".version")
    }.find {
        if (IS_OS_LINUX) {
            it.name.contains(named + "-linux")
        } else if (IS_OS_WINDOWS) {
            it.name.contains(named + "-windows")
        } else if (IS_OS_MAC) {
            it.name.contains(named + "-mac")
        }
    }

}



boolean is64bit = System.getProperty("sun.arch.data.model").contains("64")

reportingListener = new ReportListener()

String GLOBAL_ENV = System.getProperty("geb.env","local-chrome")
String GLOBAL_SCHEME = System.properties.getProperty("aem.scheme","http")
String GLOBAL_HOST = System.properties.getProperty("aem.host","localhost")
String GLOBAL_PORT = System.properties.getProperty("aem.port","4502")
String GLOBAL_USER = System.properties.getProperty("aem.user","admin")
String GLOBAL_PASS = System.properties.getProperty("aem.password","admin")
String GLOBAL_URL = "${GLOBAL_SCHEME}://${GLOBAL_HOST}:${GLOBAL_PORT}"
String GLOBAL_SELENIUMHUB_URL = System.properties.getProperty("selenium.huburl","http://$GLOBAL_HOST:32768/wd/hub")
String GLOBAL_BUILD_DIR = System.properties.getProperty("project.buildDirectory", GLOBAL_ENV)
String GLOBAL_LOGIN_REQUIRED = System.properties.getProperty("login.req", "true")
String GLOBAL_TEST_VIEWPORTS = System.properties.getProperty("test.viewports", "")
String GLOBAL_PROJECT_ROOT_DIR = System.properties.getProperty("project.rootdir", "")
String GLOBAL_PROJECT_ROOT_NAME = System.properties.getProperty("project.rootname", "")
String GLOBAL_PROJECT_CURRENT_NAME = System.properties.getProperty("project.currentname", "")

//save params if have not been defined
System.properties.setProperty("aem.scheme", GLOBAL_SCHEME)
System.properties.setProperty("aem.host", GLOBAL_HOST)
System.properties.setProperty("aem.port", GLOBAL_PORT)
System.properties.setProperty("aem.password", GLOBAL_PASS)
System.properties.setProperty("aem.user", GLOBAL_USER) //used in report
System.properties.setProperty("geb.build.baseUrl", GLOBAL_URL)  //used in report
System.properties.setProperty("selenium.huburl", GLOBAL_SELENIUMHUB_URL)  //used in report
System.properties.setProperty("geb.env", GLOBAL_ENV)  //used in report
System.properties.setProperty("project.buildDirectory", GLOBAL_BUILD_DIR)  //used in report
System.properties.setProperty("login.req", GLOBAL_LOGIN_REQUIRED) //set to no for aem publish instances
System.properties.setProperty("test.viewports", GLOBAL_TEST_VIEWPORTS) //set to no for aem publish instances

printDebug("GLOBAL_BUILD_DIR", GLOBAL_BUILD_DIR)
printDebug("GLOBAL_TEST_VIEWPORTS", GLOBAL_TEST_VIEWPORTS)

String GLOBAL_DRIVER_TYPE = findDriverExecutable("chromedriver").canonicalPath
//remember which driver being used
if (GLOBAL_ENV.startsWith("local-")) {
    System.properties.setProperty("selenium.huburl", "local")
} else {
    GLOBAL_DRIVER_TYPE =  GLOBAL_BUILD_DIR
}
System.properties.setProperty("testingdriver", GLOBAL_DRIVER_TYPE)  //used in report

System.properties.setProperty("HAS_DOCKER", checkCommand("docker -v", "Docker version"))
System.properties.setProperty("HAS_COMPARE", checkCommand("compare -v", "ImageMagick"))


printDebug("SETTINGS",[
        GLOBAL_HOST,
        GLOBAL_PORT,
        GLOBAL_USER,
        '*'.multiply(GLOBAL_PASS.length()),
        GLOBAL_SELENIUMHUB_URL,
        GLOBAL_BUILD_DIR,
        GLOBAL_URL,
        GLOBAL_ENV,
        GLOBAL_DRIVER_TYPE,
        GLOBAL_LOGIN_REQUIRED,
        "project.currentname:" + GLOBAL_PROJECT_CURRENT_NAME,
        "project.rootdir:" + GLOBAL_PROJECT_ROOT_DIR,
        "project.rootname:" + GLOBAL_PROJECT_ROOT_NAME,
        "HAS_DOCKER:" + System.properties.getProperty("HAS_DOCKER", "false"),
        "HAS_COMPARE:" + System.properties.getProperty("HAS_COMPARE", "false")
])

//specific driver
environments {


    // run as -Dgeb.env=local-chrome
    // See: http://code.google.com/p/selenium/wiki/ChromeDriver
    "local-chrome" {
        driver = {

            printDebug("DRIVER", "local-chrome")

            String driverPath = findDriverExecutable("chromedriver").canonicalPath

            printDebug("DRIVER PATH", driverPath)

            if (driverPath.isEmpty()) {
                printDebug("CANT FIND DRIVER", driverPath)
                return
            }
            def driverFile = new File(driverPath)

            if (!driverFile.canExecute()) {
                printDebug("UPDATING DRIVER PERMISSIONS", ["dirty"])
                Runtime.getRuntime().exec("chmod +x $driverPath")
                if (!driverFile.canExecute()) {
                    printDebug("PLEASE FIX DRIVER PERMISSIONS MANUALLY", [$driverPath])
                    System.exit(0)
                }
            }

            System.setProperty("webdriver.chrome.driver",driverPath)

//            printDebug("SETTINGS URL1", System.getProperty("webdriver.gecko.driver"))

            // HashMap<String, Object> chromePrefs = new HashMap<>()
            // chromePrefs.put("profile.default_content_settings.popups", 0)
            // chromePrefs.put("download.default_directory", System.getProperty("user.dir") + TestProperties.getInstance().getProperties().getProperty("chrome.file.save.dir"))
            // options.setExperimentalOption("prefs", chromePrefs)

            ChromeOptions options = new ChromeOptions()
            options.addArguments(Arrays.asList(
                    "--headless",
                    "--enable-automation",
                    "--test-type",
                    "--start-maximized",
                    "--disable-web-security",
                    "--allow-file-access-from-files",
                    "--allow-running-insecure-content",
                    "--allow-cross-origin-auth-prompt",
                    "--allow-file-access",
                    "--no-sandbox",
                    "--ignore-certificate-errors",
                    "--homepage=about:blank",
                    "--no-first-run",
                    "--disable-infobars",
                    "--hide-scrollbars",
                    "--disable-notifications",
                    "--disable-extensions",
                    "--profile-directory=Default",
                    "--user-data-dir=${GLOBAL_BUILD_DIR}/chrome-user-data",
                    "--use-fake-ui-for-media-stream=1",
                    "--disable-popup-blocking",
                    "--disable-gpu"

            )
            );

            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("profile.default_content_settings.popups", 0);
            options.setExperimentalOption("prefs", prefs);

            List<String> excludeSwitches = new ArrayList<String>();
            excludeSwitches.add("disable-component-update");
            options.setExperimentalOption('excludeSwitches', excludeSwitches)

            LoggingPreferences logPrefs = new LoggingPreferences()
            logPrefs.enable(LogType.BROWSER, Level.ALL)

            DesiredCapabilities capabilities = DesiredCapabilities.chrome()
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true)
            capabilities.setCapability(ChromeOptions.CAPABILITY, options)
            capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs)

            return new ChromeDriver(options)

        }
    }

    // run as -Dgeb.env=local-firefox
    // See: http://code.google.com/p/selenium/wiki/FirefoxDriver
    "local-firefox" {
        driver = {
            printDebug("DRIVER", "local-firefox")


            String extention = ""
            String driverPath = ""
            if (SystemUtils.IS_OS_WINDOWS) {
                extention = ".exe"
            }

            driverPath = System.getProperty("user.dir")
                    .concat("/drivers/geckodriver-")
                    .concat(System.properties['os.name'].toLowerCase().split()[0])
                    .concat("-")
                    .concat(is64bit?"64":"32")
                    .concat("bit")
                    .concat(extention)

            printDebug("DRIVER PATH", driverPath)

            System.setProperty("webdriver.gecko.driver",driverPath)

            printDebug("SETTINGS URL", System.getProperty("webdriver.gecko.driver"))

            def profileDir = Files.createTempDir()

            // disable flash
            def profile = new FirefoxProfile(profileDir)
            profile.setPreference("plugin.state.flash", 0)
            profile.setPreference("gfx.font_rendering.graphite.enabled", false)

            DesiredCapabilities capabilities = DesiredCapabilities.firefox()
            capabilities.setCapability(CapabilityType.PROXY, proxy)
            capabilities.setCapability(FirefoxDriver.PROFILE, profile)
            return new FirefoxDriver(capabilities);
        }
    }

    // run as -Dgeb.env=safari
    "local-safari" {
        driver = {
            printDebug("DRIVER", "local-safari")
            return new SafariDriver()
        }
    }

    // run as  -Dgeb.env=phantomjs
    "local-phantomjs" {
        driver = {
            printDebug("DRIVER", "local-phantomjs")
//            DesiredCapabilities capabilities = new DesiredCapabilities()
//            capabilities.setJavascriptEnabled(true)

            DesiredCapabilities capabilities = DesiredCapabilities.phantomjs()

            def phantomBin = "which phantomjs".execute().text.trim()
            def phantom2Bin = "which phantomjs2".execute().text.trim()

            if (!phantomBin && phantom2Bin) {
                phantomBin = phantom2Bin
            }

            printDebug("DRIVER BIN", phantomBin)

            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,phantomBin)
            capabilities.setJavascriptEnabled(true)
//
            capabilities.setCapability("javascriptEnabled",true)
            capabilities.setCapability("browserConnectionEnabled",true)
            capabilities.setCapability("localToRemoteUrlAccessEnabled",true)
            capabilities.setCapability("acceptSslCerts",true)
            capabilities.setCapability("webSecurityEnabled",false)
            capabilities.setCapability("applicationCacheEnabled",true)
            capabilities.setCapability("takesScreenshot",true)
            capabilities.setCapability("cssSelectorsEnabled",true)
            capabilities.setCapability("nativeEvents",true)
            capabilities.setCapability("handlesAlerts",false)
            capabilities.setCapability("databaseEnabled",false)
            capabilities.setCapability("webStorageEnabled",false)
            capabilities.setCapability("rotatable",true)
            capabilities.setCapability("locationContextEnabled",false)

            phantomArgs = ['--webdriver-loglevel=INFO', '--web-security=false', '--ignore-ssl-errors=true', '--ssl-protocol=any']
            phantomArgs2 = ["--logLevel=INFO", "--logColor=true"]

//        capabilities.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:16.0) Gecko/20121026 Firefox/16.0");


            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, phantomArgs2);

            LoggingPreferences loggingprefs = new LoggingPreferences();
            loggingprefs.enable(LogType.BROWSER, Level.ALL);

            capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);

            def driver = new PhantomJSDriver(capabilities)

//            driver.setLogLevel(Level.OFF)
            driver.manage().window().setSize(new Dimension(1920, 1080))
            System.out.println(driver.getCapabilities());
            return driver
        }
    }

    // run as  -Dgeb.env=local-htmlunit
    "local-htmlunit" {
        printDebug("DRIVER", "local-htmlunit")

        driver = { new HtmlUnitDriver() }
    }

    // run as  -Dgeb.env=remote-browserstack
    "remote-browserstack" {
        printDebug("DRIVER", "remote-browserstack")

        //ref: https://www.browserstack.com/automate/capabilities
        String GEB_BROWSERSTACK_USERNAME = System.properties.getProperty("geb.browserstack.username","")
        String GEB_BROWSERSTACK_AUTHKEY = System.properties.getProperty("geb.browserstack.authkey","")
        String GEB_BROWSERSTACK_SCHEMA = System.properties.getProperty("geb.browserstack.schema","https")
        String GEB_BROWSERSTACK_HOST = System.properties.getProperty("geb.browserstack.host","hub-cloud.browserstack.com/wd/hub")
        String GEB_BROWSERSTACK_URL = "$GEB_BROWSERSTACK_SCHEMA://$GEB_BROWSERSTACK_USERNAME:$GEB_BROWSERSTACK_AUTHKEY@$GEB_BROWSERSTACK_HOST"
        String GEB_BROWSERSTACK_BROWSER = System.properties.getProperty("geb.browserstack.browser","Chrome")
        String GEB_BROWSERSTACK_BROSWER_VERSION = System.properties.getProperty("geb.browserstack.browserversion","69.0")
        String GEB_BROWSERSTACK_BUILD = System.properties.getProperty("geb.browserstack.build","")
        String GEB_BROWSERSTACK_OS = System.properties.getProperty("geb.browserstack.os","Windows")
        String GEB_BROWSERSTACK_OS_VERSION = System.properties.getProperty("geb.browserstack.osversion","10")
        String GEB_BROWSERSTACK_DEBUG = System.properties.getProperty("geb.browserstack.debug","true")
        String GEB_BROWSERSTACK_DEVICE = System.properties.getProperty("geb.browserstack.device","")
        String GEB_BROWSERSTACK_REALMOBILE = System.properties.getProperty("geb.browserstack.device","")
        String GEB_BROWSERSTACK_RESOLUTION = System.properties.getProperty("geb.browserstack.resolution","2048x1536")
        String GEB_BROWSERSTACK_LOCAL= System.properties.getProperty("geb.browserstack.local","false")
        String GEB_BROWSERSTACK_LOCALID= System.properties.getProperty("geb.browserstack.localIdentifier","")
        String GEB_BROWSERSTACK_PROJECT= System.properties.getProperty("geb.browserstack.project","")
        String GEB_BROWSERSTACK_ACCESPTSSL= System.properties.getProperty("geb.browserstack.acceptSslCerts","")
        String GEB_BROWSERSTACK_PLATFORM = System.properties.getProperty("geb.browserstack.platform","")
        String GEB_BROWSERSTACK_SELENIUMVERSION = System.properties.getProperty("geb.browserstack.selenium_version","3.5.2")
        String GEB_BROWSERSTACK_NAME = System.properties.getProperty("geb.browserstack.name","")
        String GEB_BROWSERSTACK_CONSOLE = System.properties.getProperty("geb.browserstack.console","")
        String GEB_BROWSERSTACK_VIDEO = System.properties.getProperty("geb.browserstack.video","")
        String GEB_BROWSERSTACK_NETWORKLOGS = System.properties.getProperty("geb.browserstack.networkLogs","")
        String GEB_BROWSERSTACK_TIMEZONE = System.properties.getProperty("geb.browserstack.timezone","")
        String GEB_BROWSERSTACK_GEOLOCATION = System.properties.getProperty("geb.browserstack.geoLocation","")
        String GEB_BROWSERSTACK_NETWORKSPEED = System.properties.getProperty("geb.browserstack.customNetwork","")
        String GEB_BROWSERSTACK_DEVICEORIENTATION = System.properties.getProperty("geb.browserstack.deviceOrientation","")
        printDebug("DRIVER URL", "$GEB_BROWSERSTACK_URL")

        if (GEB_BROWSERSTACK_BROWSER) {
            driver = {

                DesiredCapabilities caps = new DesiredCapabilities();

                // Capabilities from environment
                caps.setCapability("browser", GEB_BROWSERSTACK_BROWSER)
                caps.setCapability("browser_version", GEB_BROWSERSTACK_BROSWER_VERSION)
                caps.setCapability("build", GEB_BROWSERSTACK_BUILD)
                caps.setCapability("os", GEB_BROWSERSTACK_OS)
                caps.setCapability("os_version", GEB_BROWSERSTACK_OS_VERSION)
                caps.setCapability("device", GEB_BROWSERSTACK_DEVICE)
                caps.setCapability("realMobile", GEB_BROWSERSTACK_REALMOBILE)
                caps.setCapability("resolution", GEB_BROWSERSTACK_RESOLUTION)
                caps.setCapability("browserstack.local", GEB_BROWSERSTACK_LOCAL)
                caps.setCapability("browserstack.localIdentifier", GEB_BROWSERSTACK_LOCALID)
                caps.setCapability("project", GEB_BROWSERSTACK_PROJECT)
                caps.setCapability("acceptSslCerts", GEB_BROWSERSTACK_ACCESPTSSL)
                caps.setCapability("platform", GEB_BROWSERSTACK_PLATFORM)
                caps.setCapability("selenium_version", GEB_BROWSERSTACK_SELENIUMVERSION)
                caps.setCapability("name", GEB_BROWSERSTACK_NAME)
                caps.setCapability("browserstack.console", GEB_BROWSERSTACK_CONSOLE)
                caps.setCapability("browserstack.video", GEB_BROWSERSTACK_VIDEO)
                caps.setCapability("browserstack.networkLogs", GEB_BROWSERSTACK_NETWORKLOGS)
                caps.setCapability("browserstack.timezone", GEB_BROWSERSTACK_TIMEZONE)
                caps.setCapability("browserstack.geoLocation", GEB_BROWSERSTACK_GEOLOCATION)
                caps.setCapability("browserstack.customNetwork", GEB_BROWSERSTACK_NETWORKSPEED)
                caps.setCapability("deviceOrientation", GEB_BROWSERSTACK_DEVICEORIENTATION)
                caps.setCapability("browserstack.debug", GEB_BROWSERSTACK_DEBUG)

                URL remoteURL = new URL(GEB_BROWSERSTACK_URL)

                driver = new RemoteWebDriver(remoteURL, caps)
                return driver
            }
        }
    }

    // run as  -Dgeb.env=remote
    "remote-saucelabs" {
        printDebug("DRIVER", "local-saucelabs")

        def sauceLabsBrowser = System.getProperty("geb.saucelabs.browser")
        if (sauceLabsBrowser) {
            driver = {
                def username = System.getenv("GEB_SAUCE_LABS_USER")
                assert username
                def accessKey = System.getenv("GEB_SAUCE_LABS_ACCESS_PASSWORD")
                assert accessKey
                new SauceLabsDriverFactory().create(sauceLabsBrowser, username, accessKey)
            }
        }
    }


    // run as  -Dgeb.env=appium-ios
    "remote-seleniumhub-appium" {

        System.properties.setProperty("GLOBAL_ALLOW_RESIZE", "false")

        driver = {
            printDebug("DRIVER", "remote-seleniumhub-appium")

            DesiredCapabilities capabilities = new DesiredCapabilities()

            //IOS
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS")
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.2")
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari")
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Xr")

            //ANDROID
//            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID)
////            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium")
//            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator1")
////            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest")
////            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10")
//            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1.1")
//            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.CHROME)
//            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_2_API_25")
////            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus_5X_API_29_x86")
//
//            capabilities.setCapability("forceMjsonwp", "true")


//
//// Skip the installation of io.appium.settings app and the UIAutomator 2 server.
//            capabilities.setCapability("skipDeviceInitialization", true);
//            capabilities.setCapability("skipServerInstallation", true);
//
//            ChromeOptions chrome_options = new ChromeOptions()
//            chrome_options.addArguments("ignore-certificate-errors");
//            chrome_options.addArguments("disable-translate");
//            chrome_options.addArguments("no-first-run");
//
//
//            Map<String, Object> prefs = new HashMap<String, Object>();
//            prefs.put("profile.default_content_settings.popups", 0);
//            chrome_options.setExperimentalOption("prefs", prefs);
//
//            capabilities.setCapability(ChromeOptions.CAPABILITY, chrome_options)


            //disable Browser logtype
            LoggingPreferences logPrefs = new LoggingPreferences()
            logPrefs.enable(LogType.BROWSER, Level.OFF)
            capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs)

            URL url = new URL(GLOBAL_SELENIUMHUB_URL)
//            URL url = new URL("http://127.0.0.1:4723/wd/hub")

            return new RemoteWebDriver(url, capabilities)
        }
    }

    "remote-seleniumhub-chrome" {
        driver = {
            printDebug("DRIVER", "remote-seleniumhub-chrome")

            ChromeOptions options = new ChromeOptions()
            options.addArguments(
                    Arrays.asList(
                            "--headless",
                            "--enable-automation",
                            "--disable-web-security",
                            "--allow-file-access-from-files",
                            "--allow-running-insecure-content",
                            "--allow-cross-origin-auth-prompt",
                            "--allow-file-access",
                            "--no-sandbox",
                            "--ignore-certificate-errors",
                            "--homepage=about:blank",
                            "--no-first-run",
                            "--disable-infobars",
                            "--hide-scrollbars",
                            "--disable-notifications",
                            "--disable-extensions",
                            "--use-fake-ui-for-media-stream=1",
                            "--disable-popup-blocking",
                            "--disable-overlay-scrollbar",
                            "--no-default-browser-check",
							"--disable-gpu"
                    )
            )

            URL url = new URL(GLOBAL_SELENIUMHUB_URL)
            return new RemoteWebDriver(url, options)
        }
    }

    "remote-seleniumhub-firefox" {
        driver = {
            printDebug("DRIVER", "remote-seleniumhub-firefox")

            FirefoxOptions options = new FirefoxOptions()
            options.setHeadless(true)

            DesiredCapabilities capabilities = DesiredCapabilities.firefox()
            capabilities.setCapability("-headless", "true")

            URL url = new URL(GLOBAL_SELENIUMHUB_URL)
            return new RemoteWebDriver(url, capabilities)
        }
    }

    "remote-seleniumhub-safari" {
        driver = {
            printDebug("DRIVER", "remote-seleniumhub-safari")

            DesiredCapabilities capabilities = DesiredCapabilities.safari()

            URL url = new URL(GLOBAL_SELENIUMHUB_URL)
            return new RemoteWebDriver(url, capabilities)
        }
    }

    "local-htmlunit" {
        driver = {
            printDebug("DRIVER", "local-htmlunit")
            DesiredCapabilities capabilities = new DesiredCapabilities().htmlUnitWithJs()
            capabilities.setJavascriptEnabled(true)
            def driver = new HtmlUnitDriver(capabilities)
            return driver
        }
    }

}

waiting {
    timeout = 15
    retryInterval = 0.5
    presets {
        aBit {
            timeout = 15
            retryInterval = 2
        }
        slow {
            // Good for specs checking url rewrites (which refreshes every minute)
            timeout = 120
            retryInterval = 1
        }
    }
}


static printDebug(String name, Object values) {
    def json = JsonOutput.toJson(["${name}": values])

    System.out.println(json.toString())
}

static checkCommand(String commandToTest, String stringToFindInOutput) {

    try {
        def sout = new StringBuilder(), serr = new StringBuilder()
        def proc = commandToTest.execute()
        proc.consumeProcessOutput(sout, serr)
        proc.waitForOrKill(1000)
        //println "out> $sout\nerr> $serr"
        if (sout.contains(stringToFindInOutput)) {
            return "true"
        }
    } catch (ignored) {
        //do nothing
    }
    return "false"
}

