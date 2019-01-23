package osvc.pageobjects.web.RightnowConfig;

import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utils.BUIAccount;
import utils.JConfigUtil;

public class RNConnPage extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    public RNConnPage(WebDriver driver, Logger logger) throws Exception {
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
    }


    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","save",
                        "saved"},

                {"zh_CN","保存",
                        "保存"},

                {"en_US","Save",
                        "saved"}
        };
        return languages;
    }

    public void clickEditBtn() throws Exception {
        sTools.find("button", "title=Edit").click();
    }

    public void  inputSiteURL() throws Exception {
        sTools.find("textarea", "id=siteUrl").clear();
        sTools.find("textarea", "id=siteUrl").sendKeys("https://rnowgse00147.rightnowdemo.com/cgi-bin/rnowgse00147.cfg/services");
    }

    public void inputInterface() throws Exception {
        sTools.find("input", "id=interfaceStr").clear();
        sTools.find("input", "id=interfaceStr").sendKeys("1");
    }

    public void inputUserName() throws Exception {
        String userName = JConfigUtil.getBUIUserName(BUIAccount.ACCOUNT1);
        sTools.waitAndSendKeys("input", "id=userName",userName);
    }

    public void inputPassword() throws Exception {
        String password = JConfigUtil.getBUIPassword(BUIAccount.ACCOUNT1);
        sTools.waitAndSendKeys("input", "id=rnKey",password);
    }

    public void clickSave() throws Exception {
        sTools.click("span", "text="+getProp("save"));
    }

    public boolean verifySuccessToast() throws Exception {
        return sTools.find("span", "text*="+getProp("saved")).isDisplayed();
    }

    public void clickHomePageBtn() throws Exception {
        sTools.click("span", "role=img|title=Oracle Logo");
    }
}
