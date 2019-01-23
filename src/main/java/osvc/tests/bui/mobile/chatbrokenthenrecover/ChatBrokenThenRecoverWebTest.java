package osvc.tests.bui.mobile.chatbrokenthenrecover;

import base.BaseWebTestCase;
import base.search.engine.wait.utils.Sleeper;
import base.sync.SignalCore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.bui.BLoginPage;
import osvc.pageobjects.web.bui.ChatAcceptDialog;
import osvc.pageobjects.web.bui.ChatLogonBar;
import osvc.pageobjects.web.bui.ChatPage;
import utils.BUIAccount;
import utils.JConfigUtil;

public class ChatBrokenThenRecoverWebTest extends BaseWebTestCase {
    @BeforeMethod
    public void setup() throws Exception {
        String buiHostUrl = "";//JConfigUtil.getBUIUrl();
        setupCase(buiHostUrl);
    }

    @Test
    public void testBrokenThenRecoverWeb() throws Exception {
        BLoginPage page = new BLoginPage(driver,logger);
        page.Login(BUIAccount.ACCOUNT1);
        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        logonBar.logonChat();
        SignalCore.waitForSignal(packageKey,"shouldAcceptChatRequest");
        ChatAcceptDialog chatAcceptDialog = new ChatAcceptDialog(driver,logger);
        chatAcceptDialog.acceptChat();

        Sleeper.sleep(20);
        logonBar.logonOutChat();
        SignalCore.sendSignal(packageKey,"shouldVerifyAgentQuitMessage");
        Sleeper.sleep(8);

        SignalCore.waitForSignal(packageKey,"shouldTerminateWebChatAndQuit");
        logonBar.logonChat();
        try {
            chatAcceptDialog.acceptChat();
        }catch (Exception ex) {
            logger.info("Accept chat failure:"+ex.getMessage());
        }

        Sleeper.sleep(10);
        ChatPage chatPage = new ChatPage(driver,logger);
        //Terminate the current chat
        chatPage.terminateChat();

        Sleeper.sleep(8);
        logonBar.logonOutChat();

        Sleeper.sleep(8);
    }
}
