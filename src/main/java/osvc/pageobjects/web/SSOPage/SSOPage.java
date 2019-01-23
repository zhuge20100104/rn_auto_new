package osvc.pageobjects.web.SSOPage;

import base.BaseWebComponent;
import base.search.SearchTools;
import base.search.engine.wait.base.ElementWait;
import base.utils.JFileUtils;
import base.utils.ocr.beans.OneWord;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utils.JConfigUtil;

import java.util.Set;

public class SSOPage extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    public SSOPage(WebDriver driver, Logger logger) throws Exception{
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
    }


    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","save",
                        "testSso",
                        "contactTest",
                        "ssoFail",
                        "smsConn",
                        "ssoConf",
                        "discard",
                        "cancel"
                },

                {"zh_CN","保存并继续",
                        "测试 OSvC 单点登录",
                        "测试",
                        "证书验证失败。 请检查输入证书和私钥。",
                        "SMS连接配置",
                        "单点登录配置",
                        "放弃",
                        "取消"
                },

                {"en_US","Save and Continue",
                        "Test OSvC SSO",
                        "Test",
                        "SSO verification failed. Please check the entered certificate and private key.",
                        "SMS Connection",
                        "Single Sign-On",
                        "Discard",
                        "Cancel"
                }
        };
        return languages;
    }

    public void clickEditPriavteKeyAndCertificateBtn() throws Exception {
        sTools.clickChild("setting-step", "step=1", "button", "title=Edit", false);
    }

    public void clickEditSSOLinkBtn() throws Exception {
        sTools.clickChild("setting-step", "step=2", "button", "title=Edit", false);
    }

    public void inputPrivateKey(String PrivateKey) throws Exception {
        sTools.find("textarea", "id=key").clear();
        sTools.find("textarea", "id=key").sendKeys(PrivateKey);
    }

    public void inputDefaultPrivateKey() throws Exception {
        inputPrivateKey(getPrivateKey());
    }

    public void inputCertificate(String Certificate) throws Exception {
        sTools.find("textarea", "id=certificate").clear();
        sTools.find("textarea", "id=certificate").sendKeys(Certificate);
    }

    public void inputDefaultCertificate() throws Exception {
        inputCertificate(getCertificate());
    }

    public void clickPrivateKeyAndCertificateSaveBtn() throws Exception {
        sTools.click("span", "text="+getProp("save"));
    }

    public void inputRNSSOLink(String RNSSOLink) throws Exception {
        sTools.find("textarea", "id=right-now-url").clear();
        sTools.find("textarea", "id=right-now-url").sendKeys(RNSSOLink);
    }

    public void clickTestOSvCSSO() throws Exception {
        sTools.click("span", "text="+getProp("testSso"));
    }

    public void inputContactId(String ContactId) throws Exception {
        sTools.find("textarea", "id=testId").clear();
        sTools.find("textarea", "id=testId").sendKeys(ContactId);
    }

    public void inputDefaultContactId() throws Exception {
        inputContactId(JConfigUtil.getSSORNContactId());
    }

    public void clickContactIdTestBtn() throws Exception {
        sTools.click("span", "text="+getProp("contactTest"));
    }

    public void clickContactIdCancleBtn() throws Exception {
        sTools.click("span", "text=Cancel");
    }

    public String getCurrentHandle() {
//        logger.info(driver.getWindowHandle());
        return driver.getWindowHandle();
    }

    public void switchWinddowHandle(String Handle) {
        driver.switchTo().window(Handle);
    }

    public void switchAnotherWindow(String oldWindow) {
        Set<String> handles= driver.getWindowHandles();
        for (String handle : handles) {
            if (!oldWindow.equals(handle)) {
                driver.switchTo().window(handle);
            }
        }
    }

    public boolean checkWrongRNSSOLink() throws Exception {
        String WrongMsg = sTools.find("span","").getText();
        return "SSO verification failed. Please check the entered certificate and private key.".equals(WrongMsg);
    }

    public void clickSaveBtn() throws Exception {
        sTools.click("span", "Save");
    }

    public boolean checkAuthorizeFailed() throws Exception {
        return sTools.find("h1", "text=Authentication Failed").isDisplayed();
    }

    public boolean checkAuthorizeSuccess() throws Exception {
        return sTools.find("h3", "text=Featured Support Categories").isDisplayed();
    }

    public boolean checkAuthroizeWrongPriOrCer() throws Exception {
        return sTools.find("span", "text="+getProp("ssoFail")).isDisplayed();
    }

    private String getPrivateKey() throws Exception {
        return JFileUtils.readFile("./src/main/resources/device.key");
    }

    private String getCertificate() throws Exception {
        return JFileUtils.readFile("./src/main/resources/device.crt");
    }

    public void clickDiscardBtn() throws Exception {
        sTools.click("span", "text="+getProp("discard"));
    }

    public void clickCancelBtn() throws Exception {
        sTools.click("span", "text="+getProp("cancel"));
    }

    public String getSSORNUrlInTextArea() throws Exception {
        ElementWait elementWait = new ElementWait(driver);
        elementWait.waitForPageReady();        // 现实等待页面加载完成
        String rightNowName = JConfigUtil.getBUIRightNowName();
        OneWord oneWord = sTools.findWordsContainsByOcrWeb(rightNowName);
        return oneWord.getWords();
//        return ClipBoardUtil.getValueFromTextArea(sTools, "textarea", "id=right-now-url");
    }

    public void clickAnotherpage() throws Exception {
        sTools.click("span", "text="+getProp("smsConn"));
    }

    public void clickbackToSSOpage() throws Exception {
        sTools.click("span", "text="+getProp("ssoConf"));
    }
}
