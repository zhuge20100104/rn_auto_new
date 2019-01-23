package osvc.tests.integrator.web.RightnowConfig;

import base.BaseWebTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.RightnowConfig.RNConnPage;
import osvc.pageobjects.web.integrator.HomePage;
import osvc.pageobjects.web.integrator.LoginPage;
import utils.JConfigUtil;

public class HomePageBtnTest extends BaseWebTestCase {
    @BeforeMethod
    public  void setup() throws Exception{
        String identityDomain = JConfigUtil.getAdminConsoleIdentityDomain();
        String serviceName = JConfigUtil.getAdminConsoleServiceName();
        String url = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?";
        setupCase(url);
    }

    @Test(groups = {"paas", "setup", "p1"})
    public void homePageImprovementTest() throws Exception {
        LoginPage loginPage = new LoginPage(driver, logger);
        loginPage.Login();
        HomePage homePage = new HomePage(driver, logger);
        homePage.goToRnConnPage();
        RNConnPage rnConnPage = new RNConnPage(driver, logger);
        rnConnPage.clickHomePageBtn();
        Assert.assertTrue(homePage.checkIsHomePageDisplayed(), "Home Page Button works");
    }
}
