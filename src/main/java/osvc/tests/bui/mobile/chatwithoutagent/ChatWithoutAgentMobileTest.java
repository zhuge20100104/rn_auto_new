package osvc.tests.bui.mobile.chatwithoutagent;

import base.BaseMobileTestCase;
import base.sync.SignalCore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.mobile.wechat.ChatActivity;
import osvc.pageobjects.mobile.wechat.ChatMenuBar;
import osvc.pageobjects.mobile.wechat.HomeMenuBar;
import utils.JConfigUtil;

public class ChatWithoutAgentMobileTest extends BaseMobileTestCase {
    @BeforeMethod(alwaysRun = true)
    public void setup() throws Exception{
        setupCase();
    }

    @Test
    public void testChatWithoutAgentMobile() throws Exception {
        HomeMenuBar homeMenuBar = new HomeMenuBar(driver,logger);
        String testWechatAccount = JConfigUtil.getTestAccountAccount1WechatId();

        homeMenuBar.findWechatAccount(testWechatAccount);
        homeMenuBar.clearHistory();
        ChatActivity chatActivity = new ChatActivity(driver,logger);
        chatActivity.checkIsInChat();

        ChatMenuBar chatMenuBar = new ChatMenuBar(driver,logger);
        chatMenuBar.switchToEdit();
        chatMenuBar.sendMessage("1");

        for(int i=0;i<21;i++) {
            chatMenuBar.sendMessage("str"+(i+2));
        }

        chatMenuBar.sendMessage("str"+23);
        chatActivity.checkMaxNumEntries();

        SignalCore.sendSignal(packageKey,"shouldAcceptTheMobileChat");





    }
}
