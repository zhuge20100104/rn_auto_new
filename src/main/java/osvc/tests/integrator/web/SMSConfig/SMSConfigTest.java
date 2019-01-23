package osvc.tests.integrator.web.SMSConfig;

import base.BaseWebTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.SMSConfig.SMSPage;
import osvc.pageobjects.web.integrator.HomePage;
import osvc.pageobjects.web.integrator.LoginPage;
import utils.JConfigUtil;

public class SMSConfigTest extends BaseWebTestCase {
    @BeforeMethod
    public  void setup() throws Exception{
        String identityDomain = JConfigUtil.getAdminConsoleIdentityDomain();
        String serviceName = JConfigUtil.getAdminConsoleServiceName();
        String url = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?";
        setupCase(url);
        LoginPage loginPage = new LoginPage(driver, logger);
        loginPage.Login();
        HomePage homePage = new HomePage(driver, logger);
        homePage.goToSMSPage();
    }

    @Test(groups = { "paas2", "setup", "p1"})
    public void setUpSMSConnection() throws Exception {
        SMSPage smsPage = new SMSPage(driver, logger);
        smsPage.clickEditBtn();
        smsPage.inputDefaultWebServiceUrl();
        smsPage.inputDefaultAccessAccount();
        smsPage.inputDefaultPassword();
        smsPage.clickSaveBtn();
        smsPage.inputDefaultMobileNumber();
        smsPage.clickOKBtn();
        Assert.assertTrue(smsPage.checkSaveSuccessToast(), "SMS Connection Successful");
    }

    @Test(groups = { "paas", "setup", "p1"})
    public void setUpSMSBusyNumberConnection() throws Exception {
        SMSPage smsPage = new SMSPage(driver, logger);
        smsPage.clickEditBtn();
        smsPage.inputDefaultWebServiceUrl();
        smsPage.inputDefaultAccessAccount();
        smsPage.inputDefaultPassword();
        smsPage.clickSaveBtn();
        smsPage.inputDefaultBusyMobileNumber();
        smsPage.clickOKBtn();
        Assert.assertTrue(smsPage.checkBusyNumberToast(), "SMS Busy Number Connection Successful");
    }
}
