package test.utilities;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

final class WebDriverFactory {
    private static final Logger LOG= LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String BROWSER_TYPE_KEY="TEST_BROWSER";

    private WebDriverFactory(){

    }
    static  WebDriver createWebDriver(){
        String browser=System.getProperty(BROWSER_TYPE_KEY,"HEADLESS_CHROME").toLowerCase();

        WebDriver driver;
        switch (browser){
//            case "firefox";
//            driver=FirefoxDriverFactory.headlessFirefoxDriver();
//            break;
            case "chrome":
                driver=ChromeDriverFactory.chromeDriver();
            break;
            case "headless_chrome":
            default:
                driver=ChromeDriverFactory.headlessChromeDriver();

        }
        LOG.info("Configured WebDriver for Browser '{}'", browser);
        return driver;
    }
}
