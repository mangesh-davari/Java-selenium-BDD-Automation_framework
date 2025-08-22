package test.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.invoke.MethodHandles;

final class ChromeDriverFactory implements WebDriverFactoryConstants {
    private static final String CHROMIUM_PATH=BROWSER_PATH+"\\chrome-win64\\chromedriver.exe";
    private static final String CHROME_DRIVER_VERSION="139.0.7258.138";
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public ChromeDriverFactory(){}

    static WebDriver chromeDriver() {
        LOG.info("Running in Chromium");
        return setupChromeDriver(chromeOptions());
    }

    static WebDriver headlessChromeDriver(){
        LOG.info("Running in headless Chromium");
        return setupChromeDriver(headlessChromeOptions());
    }
    private static WebDriver setupChromeDriver(ChromeOptions options){
        WebDriverManager
                .chromedriver()
                .version(CHROME_DRIVER_VERSION)
                .targetPath(WEB_DRIVER_PATH.getAbsolutePath())
                .forceCache()
                .setup();
        return new ChromeDriver(options);
    }
    private static ChromeOptions chromeOptions(){
        File chromeBinary=new File(CHROMIUM_PATH);
        ChromeOptions options=new ChromeOptions();
        options.setBinary(chromeBinary.getAbsolutePath());
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--always-authorize-plugins");
        options.addArguments("--incognito");
        options.addArguments("--window-size=800,600");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--ignore-ssl-errors");
        options.addArguments("--ssl-protocol=any");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--no-sandbox");
        options.addArguments("--v");
        options.addArguments("--disable-setuid-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-debugging-port=9222");
        options.setExperimentalOption("useAutomationExtension",false);
        return options;
        }
    private static ChromeOptions headlessChromeOptions() {
        ChromeOptions options = chromeOptions();
        File chromeBinary = new File(CHROMIUM_PATH);
        options.setBinary(chromeBinary.getAbsolutePath());
        options.addArguments("--incognito");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        return options;
    }

}
