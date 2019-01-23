package osvc.tests.integrator.web.RightnowConfig;

import base.BaseWebTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.RightnowConfig.RNConnPage;
import osvc.pageobjects.web.integrator.HomePage;
import osvc.pageobjects.web.integrator.LoginPage;
import utils.JConfigUtil;

public class RNConnTest extends BaseWebTestCase {

    @BeforeMethod
    public  void setup() throws Exception{
        String identityDomain = JConfigUtil.getAdminConsoleIdentityDomain();
        String serviceName = JConfigUtil.getAdminConsoleServiceName();
        String url = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?";
        setupCase(url);
    }

    @Test(groups = { "paas", "setup", "p1"})
    public void rnConnTest() throws Exception {
        LoginPage loginPage = new LoginPage(driver, logger);
        loginPage.Login();

        HomePage homePage = new HomePage(driver, logger);
        homePage.goToRnConnPage();
        RNConnPage rnConnPage = new RNConnPage(driver, logger);
        rnConnPage.clickEditBtn();
        rnConnPage.inputSiteURL();
        rnConnPage.inputInterface();
        rnConnPage.inputUserName();
        rnConnPage.inputPassword();
        rnConnPage.clickSave();
        Assert.assertTrue(rnConnPage.verifySuccessToast(), "Toast isn't comming");
    }
}
