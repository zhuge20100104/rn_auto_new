package osvc.pageobjects.web.WeChatConfig;

import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utils.JConfigUtil;

public class WeChatConfigCredentialsPage extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    private WebDriver driver;
    public WeChatConfigCredentialsPage(WebDriver driver, Logger logger) throws Exception {
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
        this.driver = driver;
    }


    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","yes",
                        "no",
                        "connSucceed",
                        "save"
                },

                {"zh_CN","是",
                        "否",
                        "连接测试成功",
                        "保存"
                },

                {"en_US","Yes",
                        "No",
                        "Connection test succeeded.",
                        "Save"
                }
        };
        return languages;
    }

    public void clickEditBtn() throws Exception {
        sTools.click("div", "class=oj-button-text|text=Edit");
    }

    public void inputOfficialAccountName() throws Exception {
        sTools.find("input", "id=offical-account-name").clear();
        sTools.find("input", "id=offical-account-name").sendKeys(JConfigUtil.getTestAccountAccount1Name());
    }

    public void inputOfficialAccountAppId() throws Exception {
        sTools.find("input", "id=app-id").clear();
        sTools.find("input", "id=app-id").sendKeys(JConfigUtil.getTestAccountAccount1AppId());
    }

    public void inputOfficialAccountAppSecret() throws Exception {
        sTools.find("input", "id=app-secret").clear();
        sTools.find("input", "id=app-secret").sendKeys(JConfigUtil.getTestAccountAccount1AppSecret());
    }

    public void inputOfficialAccountToken() throws Exception {
        sTools.find("input", "id=token").clear();
        sTools.find("input", "id=token").sendKeys(JConfigUtil.getTestAccountAccount1Token());
    }

    public void chooseIsThirdPartyIntegration(boolean isThirdPartyIntegration) throws Exception {
        if (isThirdPartyIntegration == true) {
            sTools.clickChild("label", "for=yes", "span", "text="+getProp("yes"), true);
        } else {
            sTools.clickChild("label", "for=no", "span", "text="+getProp("no"), true);
        }
    }

    public void acceptTermsAndConditionsCheckBox() throws Exception {
        sTools.click("input", "id=agreement-checkbox");
    }

    public void clickTestBtn() throws Exception {
        sTools.click("img", "id=test-wechat-account");
    }

    public void clickSaveBtn() throws Exception {
        sTools.click("span", "class=oj-button-text|text="+getProp("save"));
    }

    public void clickCancelBtn() throws Exception {
        sTools.click("span", "class=oj-button-text|text=Cancel");
    }

    private String getToastMsg() throws Exception {
        return sTools.find("span", "class=oj-message-detail messageDetail").getText();
    }

    public boolean checkSuccessToastMsg() throws Exception {
        return getProp("connSucceed").equals(getToastMsg());
    }

    public boolean checkAlreadyExitsToastMsg() throws Exception {
        return "The official account already exits in the system.".equals(getToastMsg());
    }

    public boolean checkConnectionFailedToastMsg() throws Exception {
        return "Connect test failed, please check your configuration.".equals(getToastMsg());
    }

    private boolean isToastExit() throws Exception {
        boolean isToastClearExitFlag = false;
        try {
            isToastClearExitFlag = driver.findElement(By.cssSelector("span[id^=toast_clear]")).isDisplayed();
        } catch (NoSuchElementException e) {
            logger.info(e);
        }
        return isToastClearExitFlag;
    }

    private boolean isToastClearExit() throws Exception {
        boolean isToastClearExitFlag = false;
        try {
            isToastClearExitFlag = driver.findElement(By.cssSelector("span[id^=toast_clear]")).isDisplayed();
        } catch (NoSuchElementException e) {
            logger.info(e);
        }
        return isToastClearExitFlag;
    }

    public void clearToasts() throws Exception {
        while (isToastClearExit()) {
            sTools.click("span", "id^=toast_clear");
        }
    }
}
