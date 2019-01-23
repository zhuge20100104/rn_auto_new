package osvc.tests.bui.mobile.chatafterregister;

import base.BaseMobileTestCase;
import base.search.engine.wait.utils.Sleeper;
import base.sync.SignalCore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.mobile.wechat.ChatActivity;
import osvc.pageobjects.mobile.wechat.ChatMenuBar;
import osvc.pageobjects.mobile.wechat.HomeMenuBar;
import utils.JConfigUtil;

public class ChatAfterRegisterMobileTest extends BaseMobileTestCase {
    @BeforeMethod(alwaysRun = true)
    public void setup() throws Exception{
        setupCase();
    }

    @Test
    public void testChatAfterRegisterMobile() throws Exception {
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

        //Register current user
        String phoneNumber = JConfigUtil.getUserRegisterPhone();
        chatActivity.registerUser(phoneNumber);
        //End register current user


        //Start chat after register
        ChatMenuBar chatMenuBar = new ChatMenuBar(driver,logger);
        chatMenuBar.switchToServices();
        chatActivity.checkIsInChat();

        chatMenuBar.switchToEdit();
        chatMenuBar.sendMessage("1");
        SignalCore.sendSignal(packageKey,"shouldAcceptChatRequest");

        SignalCore.waitForSignal(packageKey,"shouldSendTheTestMessage");
        chatMenuBar.sendMessage("Hi,I have some questions about ...");
        SignalCore.sendSignal(packageKey, "shouldSendResponseMessgae");

        Sleeper.sleep(15);



    }
}
