package base;

import base.sync.SignalCore;
import base.utils.Log4JUtil;
import base.utils.adb.ADBTools;
import base.utils.adb.Device;
import base.utils.adb.UsedDevices;
import base.utils.driver.MobileDriverManager;
import base.utils.languages.ILanguageSupport;
import base.utils.languages.LanguageLoader;
import base.utils.languages.bean.LanguageBean;
import base.search.engine.wait.utils.Sleeper;
import base.watcher.JMobileWatcher;
import config.utils.ConfigUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.List;

public class BaseMobileTestCase implements ILanguageSupport {

    /** The variable holds the current language */
    protected   String currentLanguage = "en_US";

    /** The device information for current mobile instance
     * <br> See also {@link base.utils.adb.Device} */
    protected Device device;

    /** The Appium driver instance*/
    protected WebDriver driver;

    /** logger object used to do logging */
    protected Logger logger;

    /** The package name used to sync multiple classes in the same package,
     * <br> typically used in SignalCore class.
     * <br> See also {@link base.sync.SignalCore}*/
    protected String packageKey;

    /** Mobile watcher used to watch for unexpected alerts */
    protected  JMobileWatcher watcher;

    /**  Multi-language support language bean*/
    protected List<LanguageBean> languages = new ArrayList<>();


    /**
     * The method executes before all tests run in this class
     * @throws Exception This method may throw exceptions
     */
    @BeforeClass
    public void BeforeClass() throws Exception {
        UsedDevices.removeAllDevices();
        Log4JUtil.config();

        currentLanguage = ConfigUtil.getCurrentLocale();
        this.loadLanguage();
        logger = Logger.getLogger(this.getClass());
        packageKey = this.getClass().getPackage().getName();
    }


    /**
     * Start Mobile apps and watchers, and do all required setups for Mobile test cases
     * @throws Exception  This method may throw exceptions when start up failure.
     */
    public void setupCase() throws Exception {

        // Runtime will get the child class automatically
        String baseServerPortStr = ConfigUtil.getMobileBaseServerPort();
        int baseServerPort = Integer.parseInt(baseServerPortStr);
        device = ADBTools.getDeviceDiff(baseServerPort);

        driver = MobileDriverManager.getDriver(device);
        SignalCore.clearAllSignals(packageKey);
        try {
            watcher = new JMobileWatcher(driver);
            watcher.setRunFlag(true);
            watcher.start();
        }catch (Exception ex) {
            System.out.println("watcher exception"+ex.getStackTrace());
        }
    }


    /**
     * Method execute when  a test finishes
     * @throws Exception  This method may throw exception
     */
    @AfterMethod(alwaysRun = true)
    public void teardown() throws Exception {
        watcher.setRunFlag(false);
        watcher.stop();
        UsedDevices.removeADevice(device.getDeviceName());
        driver.quit();
        MobileDriverManager.stop();
        Sleeper.sleep(15);
    }



    //region multi-language related part
    /**
     * Load multi-language to language beans
     */
    private void loadLanguage() {
        Object [][] languageArr = getLanguages();
        LanguageLoader.loadLanguageArrayToBeans(languageArr,languages);
    }


    /**
     * Get the current multi-language map
     * <br> <b>You should override this method in sub class to support multi-language</b>
     * <br> The returned array is something as follows,
     * <br>
     *     public Object[][] getLanguages() {
     *        <br> Object [] [] languages = {
     *        <br>          {"keys","save",
     *                         "saved"},
     *
     *       <br>           {"zh_CN","保存",
     *                         "保存"},
     *
     *      <br>           {"en_US","Save",
     *                         "saved"}
     *         };
     *      <br> return languages;
     *     }
     * @return the current multi-language map
     */
    @Override
    public Object[][] getLanguages() {
        return new Object[0][0];
    }


    /**
     * Get a multi-language value using multi-language key
     * @param name  The key in multi-language keys
     * @return  The multi-language value corresponding to the key
     */
    @Override
    public Object getProp(String name) {
        return LanguageLoader.getProp(this.getClass(),languages,currentLanguage,name);
    }

    //endregion



}
