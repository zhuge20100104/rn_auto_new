package base;

import base.utils.languages.ILanguageSupport;
import base.utils.languages.LanguageLoader;
import base.utils.languages.bean.LanguageBean;
import base.search.engine.wait.base.ElementWait;
import config.utils.ConfigUtil;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * The base web component of all web pages
 */
public class BaseWebComponent implements ILanguageSupport {
    /** Get the current language for auto testing */
    protected String currentLanguage;
    /** Holds the current selenium web driver*/
    protected WebDriver driver;
    /** Multi-language support language bean*/
    protected List<LanguageBean> languages = new ArrayList<>();


    /**
     * Construct of base web component
     * @param driver  The selenium web driver
     * @throws Exception This method may throws exceptions
     */
    public BaseWebComponent(WebDriver driver) throws Exception {

        this.driver = driver;
        ElementWait eWait = new ElementWait(driver);
        eWait.waitForPageReady();

        this.currentLanguage = ConfigUtil.getCurrentLocale();
        this.loadLanguage();
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
