package base.watcher;

import base.search.engine.parser.JBy;
import base.search.SearchTools;
import com.google.gson.JsonArray;
import config.MobileWatcherConfig;
import config.beans.MobileWatchObject;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Watcher to handle mobile alerts
 */
public class JMobileWatcher extends Thread {
    /** The mobile driver class*/
    private WebDriver driver;

    /** The search tools class*/
    private SearchTools sTools;

    /** Boolean variable to control whether to stop the watcher*/
    private boolean runFlag = false;

    /** The mobile alerts you should handle in the config json file*/
    private List<MobileWatchObject> watchObjectList;


    /**
     * Constructor for mobile watcher
     * @param driver The mobile driver instance
     * @throws Exception This method may throw an exception when mobile driver is null
     */
    public JMobileWatcher(WebDriver driver) throws Exception{
        this.driver = driver;
        sTools = new SearchTools(driver);
        watchObjectList = new MobileWatcherConfig().getWatchObjectList();
    }


    /**
     * Run method of the watcher
     */
    @Override
    public void run() {
        while (runFlag) {
            try {
                Thread.sleep(500);
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
        String currentActivity = sTools.activity((AppiumDriver) driver);
        //For debug only
//        System.out.println("In async click method!!!!");
        for(MobileWatchObject watchObject : this.watchObjectList) {
            String shouldMatchActivity = watchObject.getMatchedActivity();
            if(shouldMatchActivity != null && shouldMatchActivity.equals(currentActivity)){
                try{
                    JBy by = watchObject.getEleToClick();
                    sTools.click(by.getType(),by.getValue());
                }catch(Exception ex) {
                    System.out.println("Click element failure!!");
                }
            }
        }
    }
}
