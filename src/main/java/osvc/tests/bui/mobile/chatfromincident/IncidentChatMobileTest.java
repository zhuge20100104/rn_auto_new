package osvc.tests.bui.mobile.chatfromincident;

import base.BaseMobileTestCase;
import base.search.engine.wait.utils.Sleeper;
import base.sync.SignalCore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.mobile.wechat.ChatActivity;
import osvc.pageobjects.mobile.wechat.ChatMenuBar;
import osvc.pageobjects.mobile.wechat.HomeMenuBar;
import utils.JConfigUtil;

public class IncidentChatMobileTest extends BaseMobileTestCase{
    @BeforeMethod(alwaysRun = true)
    public void setup() throws Exception{
        setupCase();
    }

    @Test
    public void testChatFromIncident() throws Exception {
        HomeMenuBar homeMenuBar = new HomeMenuBar(driver,logger);
        String testWechatAccount = JConfigUtil.getTestAccountAccount1WechatId();

        homeMenuBar.findWechatAccount(testWechatAccount);
        homeMenuBar.clearHistory();
        ChatActivity chatActivity = new ChatActivity(driver,logger);
        chatActivity.checkIsInChat();

        //Create a incident
        chatActivity.createIncident();

        ChatMenuBar chatMenuBar = new ChatMenuBar(driver,logger);
        chatMenuBar.switchToServices();

        ChatActivity activity = new ChatActivity(driver,logger);
        activity.checkIsInChat();
        chatMenuBar.switchToEdit();
        chatMenuBar.sendMessage("4");

        SignalCore.sendSignal(packageKey,"shouldAcceptChatRequest");

        SignalCore.waitForSignal(packageKey,"shouldTerminateChatOnMobile");

        chatMenuBar.switchToServices();
        chatMenuBar.clickAgent();
        Sleeper.sleep(10);

        SignalCore.sendSignal(packageKey,"shouldTerminateChatOnWeb");
    }
}
