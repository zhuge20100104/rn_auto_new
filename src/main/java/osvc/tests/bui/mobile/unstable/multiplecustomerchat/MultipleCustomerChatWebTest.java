package osvc.tests.bui.mobile.unstable.multiplecustomerchat;

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

public class MultipleCustomerChatWebTest extends BaseWebTestCase{
    @BeforeMethod
    public void setup() throws Exception {
        String buiHostUrl = "";//JConfigUtil.getBUIUrl();
        setupCase(buiHostUrl);
    }

    @Test
    public void testMultipleCustomerChatWeb() throws Exception {
        BLoginPage page = new BLoginPage(driver,logger);
        page.Login(BUIAccount.ACCOUNT1);

        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        logonBar.logonChat();

        ChatPage chatPage = new ChatPage(driver,logger);
        chatPage.terminateIfHasChats();

        SignalCore.waitForSignal(packageKey,"shouldAcceptChatRequest1");
        SignalCore.waitForSignal(packageKey,"shouldAcceptChatRequest2");

        ChatAcceptDialog chatAcceptDialog = new ChatAcceptDialog(driver,logger);

        try {
            chatAcceptDialog.acceptChat();
            Sleeper.sleep(2);
            chatAcceptDialog.acceptChat();
        }catch (Exception ex) {

        }
        Sleeper.sleep(8);
        chatPage = new ChatPage(driver,logger);
        // Terminate the first chat

        //Sleep for the second agent can be terminate

        try {
            chatPage.terminateTwoChats();
        }catch (Exception ex) {

        }

//        logonBar.logonOutChat();
    }

}
