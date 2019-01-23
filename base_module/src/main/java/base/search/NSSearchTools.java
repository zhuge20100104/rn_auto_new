package base.search;

import base.search.wrapper.PageWrapper;
import netsuite.components.TabWrapper;
import org.openqa.selenium.WebDriver;

/**
 * Netsuite Search Tools
 */
public class NSSearchTools extends SearchTools {

    public NSSearchTools(WebDriver driver) {
        super(driver);
    }

    public void waitForNSPageToLoad() {
        PageWrapper pageWrapper = new PageWrapper(driver);
        pageWrapper.waitForNSPageToLoad();
    }

    //region NS Web Tab related part
    public void clickNSFormTab(String tabName) throws Exception{
        TabWrapper tabWrapper = new TabWrapper(driver);
        tabWrapper.clickFormTab(tabName);
    }

    public void clickNSFormSubTab(String tabName) throws Exception{
        TabWrapper tabWrapper = new TabWrapper(driver);
        tabWrapper.clickTab(tabName,true);
    }

    //endregion

}
