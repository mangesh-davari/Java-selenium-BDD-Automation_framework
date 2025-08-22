package test.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverUtil {
    public static long DEFAULT_WAIT = 20;
    protected static WebDriver driver;

    public static WebDriver getDefaultDriver() {
        if (driver != null) {
            return driver;
        }
        driver = Browser.newDriver();
        return driver;
    }

    public static WebElement waitAndGetElementByCssSelector(WebDriver driver, String selector, int seconds) {
        By selection = By.cssSelector(selector);
        return (new WebDriverWait(driver, seconds)).until(
                ExpectedConditions.visibilityOfElementLocated(selection));

    }

    public static void closeDriver() {
        if (driver != null) {
            try {
                driver.close();
                driver.quit();
            } catch (NoSuchMethodError nsme) {
            } catch (NoSuchSessionException nsse) {
            } catch (SessionNotCreatedException snce) {
            }
            driver = null;
        }
    }
}
