package osvc.pageobjects.web.SMSConfig;

import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.JConfigUtil;

public class SMSPage extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    public SMSPage(WebDriver driver, Logger logger) throws Exception{
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
    }

    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","save",
                        "ok",
                        "settingsSaved",
                        "busyNumberMsg"
                },

                {"zh_CN","保存",
                        "确定",
                        "设置已保存",
                        "系统忙，请稍后再试。"
                },

                {"en_US","Save",
                        "OK",
                        "Your settings are saved",
                        "System busy. Please try later."
                }
        };
        return languages;
    }


    public void clickEditBtn() throws Exception {
        sTools.click("button", "title=Edit");
    }

    public void inputWebServiceUrl (String WebServiceUrl) throws Exception {
        sTools.find("textarea", "id=webServiceUrl").clear();
        sTools.find("textarea", "id=webServiceUrl").sendKeys(WebServiceUrl);
    }

    public void inputDefaultWebServiceUrl () throws Exception {
        inputWebServiceUrl(JConfigUtil.getSMSWebServiceUrl());
    }

    public void inputAccessAccount (String AccessAccount) throws Exception {
        sTools.find("input", "id=smsAccount").clear();
        sTools.find("input", "id=smsAccount").sendKeys(AccessAccount);
    }

    public void inputDefaultAccessAccount () throws Exception {
        inputAccessAccount(JConfigUtil.getSMSAccessAccount());
    }

    public void inputPassword (String Password) throws Exception {
        sTools.find("input", "id=smsKey").clear();
        sTools.find("input", "id=smsKey").sendKeys(Password);
    }

    public void inputDefaultPassword () throws Exception {
        inputPassword(JConfigUtil.getSMSPassword());
    }

    public void clickCancelBtn() throws Exception {
        sTools.click("span", "text=Cancel");
    }

    public void clickSaveBtn() throws Exception {
        sTools.click("span", "text="+getProp("save"));
    }

    public void inputMobileNumber(String MobileNumber) throws Exception {
        sTools.find("input", "id=text-input").clear();
        sTools.find("input", "id=text-input").sendKeys(MobileNumber);
    }

    public void inputDefaultMobileNumber() throws Exception {
        inputMobileNumber(JConfigUtil.getSMSMobileNumber());
    }

    public void inputBusyMobileNumber(String BusyMobileNumber) throws Exception {
        sTools.find("input", "id=text-input").clear();
        sTools.find("input", "id=text-input").sendKeys(BusyMobileNumber);
    }

    public void inputDefaultBusyMobileNumber() throws Exception {
        inputMobileNumber(JConfigUtil.getSMSBusyMobileNumber());
    }

    public void clickOKBtn() throws Exception {
        sTools.click("span", "text="+getProp("ok"));
    }

    private String getToastMsg() throws Exception {
        return sTools.find("span", "class*=messageDetail").getText();
    }

    public boolean checkSaveSuccessToast() throws Exception {
        return getProp("settingsSaved").equals(getToastMsg());
    }

    public boolean checkBusyNumberToast() throws Exception {
        return getProp("busyNumberMsg").equals(getToastMsg());
    }

    public void clickClearToastBtn() throws Exception {
        sTools.click("span", "id^=toast_clear");
    }

    public void clearAllToast() throws Exception {
        while (sTools.find("span", "id^=toast_clear").isDisplayed()) {
            clickClearToastBtn();
        }
    }
}
