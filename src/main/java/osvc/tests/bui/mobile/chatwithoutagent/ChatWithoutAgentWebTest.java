package osvc.tests.bui.mobile.chatwithoutagent;

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

public class ChatWithoutAgentWebTest extends BaseWebTestCase {
    @BeforeMethod
    public void setup() throws Exception {
        String buiHostUrl = "";//JConfigUtil.getBUIUrl();
        setupCase(buiHostUrl);
    }


    @Test
    public  void testChatWithoutAgentWeb() throws Exception {
        BLoginPage page = new BLoginPage(driver, logger);
        page.Login(BUIAccount.ACCOUNT1);
        SignalCore.waitForSignal(packageKey,"shouldAcceptTheMobileChat");

        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        logonBar.logonChat();

        ChatAcceptDialog chatAcceptDialog = new ChatAcceptDialog(driver,logger);
        chatAcceptDialog.acceptChat();

        Sleeper.sleep(18);
        ChatPage chatPage = new ChatPage(driver,logger);
        chatPage.checkLastMessageExists();


        chatPage.terminateChat();
        Sleeper.sleep(10);

        // logOut the current chat or the browser can't be exit
        logonBar.logonOutChat();
        Sleeper.sleep(5);

        //Start to delete the current incident
        Sleeper.sleep(5);

    }
}
