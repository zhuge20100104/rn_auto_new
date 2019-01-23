package osvc.tests.integrator.web.WeChatConfig;

import base.BaseWebTestCase;
import base.annotations.TestCaseInfo;
import base.utils.menus.MenuUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.TestInstance;
import osvc.pageobjects.web.WeChatConfig.WeChatConfigMenuSettingPage;
import osvc.pageobjects.web.WeChatConfig.WeChatConfigPage;
import osvc.pageobjects.web.integrator.HomePage;
import osvc.pageobjects.web.integrator.LoginPage;
import utils.JConfigUtil;

public class WeChatMenuSettingTest extends BaseWebTestCase {
    @BeforeMethod
    public  void setup() throws Exception{
        String identityDomain = JConfigUtil.getAdminConsoleIdentityDomain();
        String serviceName = JConfigUtil.getAdminConsoleServiceName();
        String url = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?";
        setupCase(url);

        LoginPage loginPage = new LoginPage(driver, logger);
        HomePage homePage = new HomePage(driver, logger);

        loginPage.Login();
        homePage.goToWechatPage();
    }

    @Test(groups = {"paas", "p1"})
    public void deleteMainMenu() throws Exception {
        WeChatConfigPage weChatConfigPage = new WeChatConfigPage(driver, logger);
        WeChatConfigMenuSettingPage weChatConfigMenuSettingPage = new WeChatConfigMenuSettingPage(driver, logger);

        String wechatAccountName = JConfigUtil.getTestAccountAccount1Name();
        weChatConfigPage.chooseAccountWithAccountName(wechatAccountName);
        weChatConfigPage.getInMenuSettingTab();
        Thread.sleep(5*1000);

        MenuUtil.getInstance().deleteMenu();


        weChatConfigMenuSettingPage.refreshPage();
        weChatConfigPage.chooseAccountWithAccountName(wechatAccountName);
        weChatConfigPage.getInMenuSettingTab();
        Thread.sleep(5*1000);


        String leftMenu = JConfigUtil.getWechatLayoutLeftMenu();
        boolean noMenuExist = weChatConfigMenuSettingPage.isLeftMenuNotExist(leftMenu);
        Assert.assertTrue(noMenuExist,"Menus are not deleted!!!");
    }

    @TestCaseInfo(owner = "chenguang", caseNo = "PAASEXTRWI-829")
    @Test(groups = {"paas", "setup", "p1"})
    public void createMainMenu() throws Exception {
        WeChatConfigPage weChatConfigPage = new WeChatConfigPage(driver, logger);
        WeChatConfigMenuSettingPage weChatConfigMenuSettingPage = new WeChatConfigMenuSettingPage(driver, logger);

        String wechatAccountName = JConfigUtil.getTestAccountAccount1Name();
        weChatConfigPage.chooseAccountWithAccountName(wechatAccountName);

        weChatConfigPage.getInMenuSettingTab();
        Thread.sleep(5*1000);

        String leftMenu = JConfigUtil.getWechatLayoutLeftMenu();
        String middleMenu = JConfigUtil.getWechatLayoutMiddleMenu();
        String rightMenu = JConfigUtil.getWechatLayoutRightMenu();

        String createMenuStr = "{\"button\":[{\"type\":\"click\",\"name\":\""+leftMenu+"\",\"key\":\"SRV_WA\"}," +
                "{\"type\":\"view\",\"name\":\""+middleMenu+"\",\"url\":\"http://cn.bing.com\"}," +
                "{\"type\":\"click\",\"name\":\""+rightMenu+"\",\"key\":\"SRV_RG\"}]}";

        MenuUtil.getInstance().createMenu(createMenuStr);

        weChatConfigMenuSettingPage.refreshPage();
        weChatConfigPage.chooseAccountWithAccountName(wechatAccountName);
        weChatConfigPage.getInMenuSettingTab();
        Thread.sleep(5*1000);

        boolean leftMenuExist = weChatConfigMenuSettingPage.isLeftMenuExist(leftMenu);
        Assert.assertTrue(leftMenuExist,"Menus are not exist!!!");

    }

    @TestCaseInfo(owner = "chenguang", caseNo = "PAASEXTRWI-830")
    @Test(groups = {"paas", "p1"})
    public void addSubMenuTest() throws Exception {
        WeChatConfigPage weChatConfigPage = new WeChatConfigPage(driver, logger);
        WeChatConfigMenuSettingPage weChatConfigMenuSettingPage = new WeChatConfigMenuSettingPage(driver, logger);

        String wechatAccountName = JConfigUtil.getTestAccountAccount1Name();
        weChatConfigPage.chooseAccountWithAccountName(wechatAccountName);

        weChatConfigPage.getInMenuSettingTab();
        Thread.sleep(5*1000);

        String leftMenu = JConfigUtil.getWechatLayoutLeftMenu();
        String middleMenu = JConfigUtil.getWechatLayoutMiddleMenu();
        String rightMenu = JConfigUtil.getWechatLayoutRightMenu();

        String createMenuStr = "{\"button\":[{\"type\":\"click\",\"name\":\""+leftMenu+"\",\"key\":\"SRV_WA\"},\n" +
                "{\"type\":\"click\", \"sub_button\":[{\"type\":\"click\",\"name\":\"subTest\",\"key\":\"mykey\"}],\"name\":\""+middleMenu+"\",\"key\":\"SRV_RG\"},\n" +
                "{\"type\":\"click\",\"name\":\""+rightMenu+"\",\"key\":\"3\"}]}";

        MenuUtil.getInstance().deleteMenu();
        MenuUtil.getInstance().createMenu(createMenuStr);

        weChatConfigMenuSettingPage.refreshPage();
        weChatConfigPage.chooseAccountWithAccountName(wechatAccountName);
        weChatConfigPage.getInMenuSettingTab();
        Thread.sleep(5*1000);



        boolean isSubMenuExist = weChatConfigMenuSettingPage.checkSubMenu(middleMenu,"subTest");
        Assert.assertTrue(isSubMenuExist,"Sub menu not exists!!!");
    }

    @Test(groups = {"paas", "p1"})
    public void deleteSubMenuTest() throws Exception {
        WeChatConfigPage weChatConfigPage = new WeChatConfigPage(driver, logger);
        WeChatConfigMenuSettingPage weChatConfigMenuSettingPage = new WeChatConfigMenuSettingPage(driver, logger);
        String wechatAccountName = JConfigUtil.getTestAccountAccount1Name();
        weChatConfigPage.chooseAccountWithAccountName(wechatAccountName);
        weChatConfigPage.getInMenuSettingTab();
        Thread.sleep(5*1000);

        String leftMenu = JConfigUtil.getWechatLayoutLeftMenu();
        String middleMenu = JConfigUtil.getWechatLayoutMiddleMenu();
        String rightMenu = JConfigUtil.getWechatLayoutRightMenu();

        String createMenuStr = "{\"button\":[{\"type\":\"click\",\"name\":\""+leftMenu+"\",\"key\":\"SRV_WA\"}," +
                "{\"type\":\"view\",\"name\":\""+middleMenu+"\",\"url\":\"http://cn.bing.com\"}," +
                "{\"type\":\"click\",\"name\":\""+rightMenu+"\",\"key\":\"SRV_RG\"}]}";

        MenuUtil.getInstance().createMenu(createMenuStr);

        weChatConfigMenuSettingPage.refreshPage();
        weChatConfigPage.chooseAccountWithAccountName(wechatAccountName);
        weChatConfigPage.getInMenuSettingTab();
        Thread.sleep(5*1000);


        boolean subMenuNotExist = weChatConfigMenuSettingPage.checkSubMenuNotExist(middleMenu,"subTest");
        Assert.assertTrue(subMenuNotExist,"Sub menu exist, failed!!!");
    }

    @TestCaseInfo(owner = "chenguang", caseNo = "PAASEXTRWI-865, PAASEXTRWI-875")
    @Test(groups = {"paas", "adminui", "PAASEXTRWI-865"})
    public void unSupportMenuSetting() throws Exception {
        String createMenuStr = "{\"button\":[{\"type\":\"scancode_push\",\"name\":\"TestEdit\",\"key\":\"1\"},{\"type\":\"click\",\"name\":\"WeChatMenu\",\"key\":\"2\"},{\"type\":\"click\",\"name\":\"InWeChat\",\"key\":\"3\"}]}";

        MenuUtil.getInstance().createMenu(createMenuStr);

        WeChatConfigPage weChatConfigPage = new WeChatConfigPage(driver, logger);
        WeChatConfigMenuSettingPage weChatConfigMenuSettingPage = new WeChatConfigMenuSettingPage(driver, logger);
        String wechatAccountName = JConfigUtil.getTestAccountAccount1Name();
        weChatConfigPage.chooseAccountWithAccountName(wechatAccountName);
        weChatConfigPage.getInMenuSettingTab();
        Thread.sleep(5*1000);

        weChatConfigMenuSettingPage.clickEditOrPublishBtn();
        weChatConfigMenuSettingPage.clickFirstMenu();
        boolean IsUnsupportLabelDisplayed = weChatConfigMenuSettingPage.isUnsupportLabelDisplayed();
        Assert.assertTrue(IsUnsupportLabelDisplayed, "unsupported message is not displayed!");
    }

    @TestCaseInfo(owner = "chenguang", caseNo = "PAASEXTRWI-829")
    @Test
    public void createMenuInIntegratorCheckOnWechat() throws Exception {
        String get_menu = MenuUtil.getInstance().getMenu().replace("\\", "");
        String defalt_menu = MenuUtil.DefaultMenu.replace("\\", "");
        Assert.assertTrue(get_menu.contains(defalt_menu));
    }


    @AfterMethod
    public void teardown() throws Exception{

        //Restore to origin test menu
        String leftMenu = JConfigUtil.getWechatLayoutLeftMenu();
        String middleMenu = JConfigUtil.getWechatLayoutMiddleMenu();
        String rightMenu = JConfigUtil.getWechatLayoutRightMenu();
        String createMenuStr = "{\"button\":[{\"type\":\"click\",\"name\":\""+leftMenu+"\",\"key\":\"SRV_WA\"}," +
                "{\"type\":\"view\",\"name\":\""+middleMenu+"\",\"url\":\"http://cn.bing.com\"}," +
                "{\"type\":\"click\",\"name\":\""+rightMenu+"\",\"key\":\"SRV_RG\"}]}";

        MenuUtil.getInstance().createMenu(createMenuStr);

        super.teardown();
    }
}
