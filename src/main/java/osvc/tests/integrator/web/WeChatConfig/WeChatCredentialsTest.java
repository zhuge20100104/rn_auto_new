package osvc.tests.integrator.web.WeChatConfig;

import base.BaseWebTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.WeChatConfig.WeChatConfigCredentialsPage;
import osvc.pageobjects.web.WeChatConfig.WeChatConfigPage;
import osvc.pageobjects.web.integrator.HomePage;
import osvc.pageobjects.web.integrator.LoginPage;
import utils.JConfigUtil;

public class WeChatCredentialsTest extends BaseWebTestCase {
    @BeforeMethod
    public  void setup() throws Exception{
        String identityDomain = JConfigUtil.getAdminConsoleIdentityDomain();
        String serviceName = JConfigUtil.getAdminConsoleServiceName();
        String url = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?";
        setupCase(url);
    }

    @Test(groups = {"paas", "setup", "p1"})
    public void AddAAccount() throws Exception {
        WeChatConfigCredentialsPage weChatConfigCredentialsPage = new WeChatConfigCredentialsPage(driver, logger);
        WeChatConfigPage weChatConfigPage = new WeChatConfigPage(driver, logger);
        LoginPage loginPage = new LoginPage(driver, logger);
        HomePage homePage = new HomePage(driver, logger);

        loginPage.Login();
        homePage.goToWechatPage();

        String wechatAccountName = JConfigUtil.getTestAccountAccount1Name();
        weChatConfigPage.chooseAccountWithAccountName(wechatAccountName);

        weChatConfigPage.getInCredentialsTab();
        weChatConfigPage.clickAddAccountBtn();
        weChatConfigCredentialsPage.inputOfficialAccountName();
        weChatConfigCredentialsPage.inputOfficialAccountAppId();
        weChatConfigCredentialsPage.inputOfficialAccountAppSecret();
        weChatConfigCredentialsPage.inputOfficialAccountToken();
        weChatConfigCredentialsPage.chooseIsThirdPartyIntegration(false);
        weChatConfigCredentialsPage.acceptTermsAndConditionsCheckBox();
        weChatConfigCredentialsPage.clickTestBtn();
        Assert.assertTrue(weChatConfigCredentialsPage.checkSuccessToastMsg(), "Add Account Success");
        weChatConfigCredentialsPage.clickSaveBtn();
    }
}
