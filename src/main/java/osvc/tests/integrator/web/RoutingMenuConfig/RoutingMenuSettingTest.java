package osvc.tests.integrator.web.RoutingMenuConfig;

import base.BaseWebTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.RoutingMenuConfig.RoutingMenuSettingPage;
import osvc.pageobjects.web.integrator.HomePage;
import osvc.pageobjects.web.integrator.LoginPage;
import utils.JConfigUtil;

public class RoutingMenuSettingTest extends BaseWebTestCase {
    @BeforeMethod
    public  void setup() throws Exception{
        String identityDomain = JConfigUtil.getAdminConsoleIdentityDomain();
        String serviceName = JConfigUtil.getAdminConsoleServiceName();
        String url = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?";
        setupCase(url);
        LoginPage loginPage = new LoginPage(driver, logger);
        loginPage.Login();
        HomePage homePage = new HomePage(driver, logger);
        homePage.goToRoutingSettingPage();
    }

    @Test
    public void setupRoutingMenuTest() throws Exception {
        RoutingMenuSettingPage routingMenuSettingPage = new RoutingMenuSettingPage(driver, logger);
        routingMenuSettingPage.getInPage_1();
        routingMenuSettingPage.enableRoutingMenu();
        routingMenuSettingPage.enableRoutingLevelMenu();

        //todo delete these lines
//        routingMenuSettingPage.clickDeleteBtn();
//        routingMenuSettingPage.clickDeleteBtn();
//        routingMenuSettingPage.clickDeleteBtn();
//
//        routingMenuSettingPage.setNewRouting(false, "automation question", "Category", "", "Ordering", "1", "Request chat", "chat");
//        routingMenuSettingPage.setNewRouting(false, "automation question", "", "", "Wechat", "2", "Create Incident", "create incident");
    }
}
