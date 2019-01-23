package osvc.tests.bui.mobile.registerwhilerouting;

import base.BaseMobileTestCase;
import base.sync.SignalCore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.mobile.wechat.ChatActivity;
import osvc.pageobjects.mobile.wechat.HomeMenuBar;
import utils.JConfigUtil;

public class RegisterWhileRoutingMobileTest extends BaseMobileTestCase {
    @BeforeMethod(alwaysRun = true)
    public void setup() throws Exception{
        setupCase();
    }

    @Test
    public void testRegisterWhileRoutingMobile() throws Exception {
        HomeMenuBar homeMenuBar = new HomeMenuBar(driver,logger);
        String testWechatAccount = JConfigUtil.getTestAccountAccount1WechatId();
        homeMenuBar.findWechatAccount(testWechatAccount);
        homeMenuBar.clearHistory();
        ChatActivity chatActivity = new ChatActivity(driver,logger);

        if(chatActivity.checkHasRegistered()) {
            SignalCore.sendSignalWithValue(packageKey,"shouldUnRegisteredUser","true");
        }else {
            SignalCore.sendSignalWithValue(packageKey,"shouldUnRegisteredUser","false");
        }


        SignalCore.waitForSignal(packageKey,"shouldRegisterInMobile");

        String phoneNumber = JConfigUtil.getUserRegisterPhone();
        chatActivity.registerWhileRouting(phoneNumber);
    }
}

