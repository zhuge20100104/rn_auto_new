package base.watcher;

import base.search.engine.parser.JBy;
import base.search.SearchTools;
import config.WebWatcherConfig;
import config.beans.WebWatchObject;
import org.openqa.selenium.WebDriver;

import java.util.List;


/**
 * Watcher for unexpected web alerts
 */
public class JWebWatcher extends Thread {
    /** Selenium web driver instance*/
    private WebDriver driver;

    /** Search tools instance*/
    private SearchTools sTools;

    /** Boolean variable to control whether to stop the watcher*/
    private boolean runFlag = false;

    /** The web alerts you should handle in the config json file*/
    private List<WebWatchObject> shouldHandledAlerts;


    /**
     * Constructor for web watcher
     * @param driver Selenium web driver
     * @throws Exception  This method may throw exception when web driver is null
     */
    public JWebWatcher(WebDriver driver) throws Exception{
        this.driver = driver;
        sTools = new SearchTools(driver);
        shouldHandledAlerts = new WebWatcherConfig().getAlertWatchObjectList();
    }


    /**
     * Run method of the watcher
     */
    @Override
    public void run() {
        while (runFlag) {
            try {
                Thread.sleep(10);
                asyncClick();
            }catch (Exception ex) {

            }
        }
    }


    /**
     * Get the boolean run flag
     * @return The boolean run flag
     */
    public boolean isRunFlag() {
        return runFlag;
    }


    /**
     * Set the boolean run flag
     * @param runFlag   The run flag to be set
     */
    public void setRunFlag(boolean runFlag) {
        this.runFlag = runFlag;
    }


    /**
     * Method to do async click when the alert is present
     * @throws Exception  This method will throw an exception when click failed
     */
    public void asyncClick() throws Exception {
       for(WebWatchObject watchObject: shouldHandledAlerts) {
           JBy by = watchObject.getBy();

           if(driver.getPageSource().contains(watchObject.getText()) &&
                   (sTools.find(by.getType(),by.getValue())!=null)){
               try {
                   sTools.click(by.getType(),by.getValue());
               }catch (Exception ex) {
                   System.out.println("Click on web by failed in JWebDivAlertWatcher!!"+ex.getStackTrace());
               }
           }
       }
    }
}
