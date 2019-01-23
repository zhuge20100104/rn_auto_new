package base;

import base.sync.SignalCore;
import base.utils.Log4JUtil;
import base.utils.driver.WebDriverManager;
import base.utils.languages.ILanguageSupport;
import base.utils.languages.LanguageLoader;
import base.utils.languages.bean.LanguageBean;
import base.search.engine.wait.utils.Sleeper;
import base.watcher.JWebWatcher;
import config.utils.ConfigUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.List;

/**
 * The base class for all web test cases
 */
public class BaseWebTestCase implements ILanguageSupport {

    /** The variable holds the current language */
    protected   String currentLanguage = "en_US";

    /** The selenium web driver instance*/
    protected WebDriver driver;

    /** The package name used to sync multiple classes in the same package,
     * <br> typically used in SignalCore class.
     * <br> See also {@link base.sync.SignalCore}*/
    protected String packageKey;

    /** Web watcher used to watch for unexpected alerts */
    protected JWebWatcher watcher;

    /** logger object used to do logging */
    protected Logger logger = null;

    /**  Multi-language support language bean*/
    protected List<LanguageBean> languages = new ArrayList<>();


    /**
     * The method executes before all tests run in this class
     * @throws Exception This method may throw exceptions
     */
    @BeforeClass
    public void BeforeClass() throws Exception {
        Log4JUtil.config();

        //Load multi-language part
        currentLanguage = ConfigUtil.getCurrentLocale();
        this.loadLanguage();

        // Run time will get the child package key automatically, no need to pass the class name
        packageKey =this.getClass().getPackage().getName();
        logger = Logger.getLogger(this.getClass());
    }


    /**
     * Start a web browser and load specific url
     * @param url The url you want to load
     * @throws Exception  When browser starts failure, this method may throw an exception
     */
    protected void startBrowser(String url) throws Exception {
        driver = WebDriverManager.getDriver(url);
    }


    /**
     * Method to stop browsers and web watchers
     * @throws Exception This method may throw exceptions when stop failure.
     */
    protected void stopBrowser() throws Exception{
        watcher.setRunFlag(false);
        watcher.stop();
        if(driver!=null) {
            try {
                driver.quit();
            }catch (Exception ex) {
                logger.info("Driver quit failed!!!Usually caused by manually close the browsers!!!");
                logger.info(ex.getMessage());
            }
        }
        WebDriverManager.removeDriver();
    }


    /**
     * Start web browsers and watchers, and do all required setups for web test cases
     * @param url  The web url you want to load
     * @throws Exception  This method may throw exceptions when start up failure.
     */
    protected void setupCase(String url) throws Exception{

        SignalCore.clearAllSignals(packageKey);
        this.startBrowser(url);

        try {
            watcher = new JWebWatcher(driver);
            watcher.setRunFlag(true);
            watcher.start();
        }catch (Exception ex) {
            System.out.println("Web alert watcher exception"+ex.getStackTrace());
        }
    }

    /**
     * Method execute when  a test finishes
     * @throws Exception  This method may throw exception
     */
    @AfterMethod
    public void teardown() throws Exception {
        this.stopBrowser();
        Sleeper.sleep(10);
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
