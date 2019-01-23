package osvc.tests.bui.mobile.unstable.multiplecustomerchat;

import base.BaseMobileTestCase;
import base.sync.SignalCore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.mobile.wechat.ChatActivity;
import osvc.pageobjects.mobile.wechat.ChatMenuBar;
import osvc.pageobjects.mobile.wechat.HomeMenuBar;
import utils.JConfigUtil;

public class MultipleCustomerChatMobileTest1 extends BaseMobileTestCase {
    @BeforeMethod(alwaysRun = true)
    public void setup() throws Exception{
        setupCase();
    }

    @Test
    public void testMultipleCustomerChatMobile1() throws Exception {
        HomeMenuBar homeMenuBar = new HomeMenuBar(driver,logger);
        String testWechatAccount = JConfigUtil.getTestAccountAccount1WechatId();

        homeMenuBar.findWechatAccount(testWechatAccount);
        homeMenuBar.clearHistory();
        ChatActivity chatActivity = new ChatActivity(driver,logger);
        chatActivity.checkIsInChat();

        ChatMenuBar chatMenuBar = new ChatMenuBar(driver,logger);
        chatMenuBar.switchToEdit();
        chatMenuBar.sendMessage("1");
        SignalCore.sendSignal(packageKey,"shouldAcceptChatRequest1");
    }



}
