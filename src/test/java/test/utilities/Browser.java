package test.utilities;

import org.slf4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Set;

public final class Browser {
    private static final Logger LOG= LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
private Browser(){
    }
    public static WebDriver newDriver(){
    WebDriver driver=WebDriverFactory.createWebDriver();

    //set the window size
        driver.manage().window().setSize(new Dimension(1280,1024));
    //add a shutdown hoook so the browser will get close properly.
    Runtime.getRuntime().addShutdownHook(new Thread()){
        @Override
                public void run(){
            driver.quit();
            }

        });
    return driver;
}
public static void selectTab(WebDriver driver,int tabNumber){
    waitFornumberOfWindowsToEqual(driver,2);
    waitForLoad(driver);
    Set<String>windowHandles=driver.getWindowHandles();
    Object[] tabs=windowHandles.toArray();
    /* switch to new tab*/
    driver.switchTo().window((String) tabs[tabNumber]);
}

public static void waitFornumberOfWindowsToEqual(final WebDriver driver,final int numberOfWindows){
    new WebDriverWait(driver,10){
}.until(new ExpectedCondition<Boolean>(){
    @Override
        public Boolean apply(WebDriver driver){
        Set<String> handles=driver.getWindowHandles();
        int size=handles.size();
        return size==numberOfWindows;
    }
    });
}
public static void waitForLoad(WebDriver driver){
    try{
        WebDriverWait wait=new WebDriverWait(driver,30);
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    } catch (TimeoutException e){
        LOG.error("Received timeout while waiting for page to load : {}", e.getMessage(), e);
        throw e;
    }
}
}
