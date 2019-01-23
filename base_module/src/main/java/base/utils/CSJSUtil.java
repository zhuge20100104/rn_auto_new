package base.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * Javascript related utilities
 */
public class CSJSUtil {


    // To see if jquery is loaded

    /**
     * Check whether jquery is loaded
     * @param driver  Selenium web driver instance
     * @return  Whether jquery is loaded
     */
    public static Boolean jQueryLoaded(WebDriver driver) {
        Boolean loaded;
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        try{
            loaded = (Boolean) jse.executeScript("return "+ "jQuery()!=null");
        } catch(WebDriverException e) {
            loaded = false;
        }
        return loaded;
    }


    /**
     * Wait for document is ready
     * @param driver  Selenium web driver instance
     * @return  Whether web document is ready, including document ready and ajax ready
     */
    public static boolean documentReady(WebDriver driver) {
        if(!jQueryLoaded(driver)) {
            return isDocumentReady(driver);
        }else {
            return isDocumentReady(driver) && isAjaxReady(driver);
        }
    }

    /**
     * Check the ready state of web document
     * @param driver Selenium web driver instance
     * @return  whether document.readyState equals complete
     */
    public static boolean isDocumentReady(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        try{
            String readyState =  jse.executeScript("return "+ "document.readyState;").toString();
            return readyState.equals("complete");
        } catch(WebDriverException e) {
            return false;
        }
    }

    /**
     * Check whether ajax loading is ready
     * @param driver  Selenium web driver instance
     * @return  Whether ajax loading is ready
     */
    public static boolean isAjaxReady(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        try{
            String readyState =  jse.executeScript("return "+ "jQuery.active;").toString();
            return readyState.equals("0");
        } catch(WebDriverException e) {
            return false;
        }
    }


}
