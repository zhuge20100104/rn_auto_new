package osvc.tests.bui.mobile.simplechat;

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

public class SimpleChatWebTest extends BaseWebTestCase{
    @BeforeMethod
    public void setup() throws Exception {
        String buiHostUrl = "";//JConfigUtil.getBUIUrl();
        setupCase(buiHostUrl);
    }

    @Test
    public  void testAcceptRequest() throws Exception{
        BLoginPage page = new BLoginPage(driver,logger);
        page.Login(BUIAccount.ACCOUNT1);
        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        logonBar.logonChat();
        SignalCore.waitForSignal(packageKey,"shouldAcceptChatRequest");

        ChatAcceptDialog chatAcceptDialog = new ChatAcceptDialog(driver,logger);
        chatAcceptDialog.acceptChat();
        SignalCore.sendSignal(packageKey,"shouldSendTheTestMessage");
        SignalCore.waitForSignal(packageKey, "shouldSendResponseMessgae");

        ChatPage chatPage = new ChatPage(driver,logger);
        chatPage.sendMessageToMobile("Ok,We will check it now...");
        chatPage.terminateChat();
        Sleeper.sleep(5);

        // logOut the current chat or the browser can't be exit
        logonBar.logonOutChat();

        Sleeper.sleep(5);

    }


}
