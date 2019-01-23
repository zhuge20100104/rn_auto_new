package osvc.tests.integrator.mobile.webmobiledemo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.BaseMobileTestCase;
import base.sync.SignalCore;
import osvc.pageobjects.mobile.wechat.ChatMenuBar;
import osvc.pageobjects.mobile.wechat.HomeActivity;
import osvc.pageobjects.mobile.wechat.HomeMenuBar;
import utils.JConfigUtil;

public class WechatFollowTest5  extends BaseMobileTestCase{

    @BeforeMethod
    public void beforeMethod() throws Exception{
        setupCase();
    }

    @Test
    public void test4() throws Exception {

        HomeMenuBar homeMenuBar = new HomeMenuBar(driver,logger);
        String testWechatAccount = JConfigUtil.getTestAccountAccount1WechatId();
        homeMenuBar.findWechatAccount(testWechatAccount);
        homeMenuBar.clearHistory();


        ChatMenuBar chatMenuBar = new ChatMenuBar(driver,logger);
        chatMenuBar.clickAgent();
        chatMenuBar.switchToEdit();
        chatMenuBar.sendMessage("xixi");


    }
}
