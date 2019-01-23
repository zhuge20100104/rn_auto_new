package osvc.tests.integrator.web.WeChatConfig;

import base.BaseWebTestCase;
import base.search.engine.wait.utils.Sleeper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.WeChatConfig.WeChatChannelMarkPage;
import osvc.pageobjects.web.WeChatConfig.WeChatConfigPage;
import osvc.pageobjects.web.integrator.HomePage;
import osvc.pageobjects.web.integrator.LoginPage;
import utils.JConfigUtil;

public class WeChatChannelMarkTest extends BaseWebTestCase {
    @BeforeMethod
    public  void setup() throws Exception{
        String identityDomain = JConfigUtil.getAdminConsoleIdentityDomain();
        String serviceName = JConfigUtil.getAdminConsoleServiceName();
        String url = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?";
        setupCase(url);
        LoginPage loginPage = new LoginPage(driver, logger);
        loginPage.Login();
        HomePage homePage = new HomePage(driver, logger);
        homePage.goToWechatPage();
    }


    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","disabled"
                },

                {"zh_CN","不启用"

                },

                {"en_US","Disabled"
                }
        };
        return languages;
    }

    @Test
    public void setChannelMark() throws Exception {
        WeChatConfigPage weChatConfigPage = new WeChatConfigPage(driver, logger);
        String wechatAccountName = JConfigUtil.getTestAccountAccount1Name();
        weChatConfigPage.chooseAccountWithAccountName(wechatAccountName);
        weChatConfigPage.getInChannelMarkTab();
        WeChatChannelMarkPage weChatChannelMarkPage = new WeChatChannelMarkPage(driver, logger);
        Sleeper.sleep(3); // make stable
        if(weChatChannelMarkPage.isFirstTime()) {
            weChatChannelMarkPage.clickExpandSubListBtn();
            weChatChannelMarkPage.chooseSubList(getProp("disabled").toString());
            weChatChannelMarkPage.clickDone();
        }else {
            weChatChannelMarkPage.clickEditButton();
            weChatChannelMarkPage.clickExpandSubListBtn();
            weChatChannelMarkPage.chooseSubList(getProp("disabled").toString());
            weChatChannelMarkPage.clickDone();
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        super.teardown();
    }
}
