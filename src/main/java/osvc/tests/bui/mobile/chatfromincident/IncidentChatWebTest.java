package osvc.tests.bui.mobile.chatfromincident;

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

public class IncidentChatWebTest extends BaseWebTestCase {
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




        Sleeper.sleep(5);

        SignalCore.sendSignal(packageKey,"shouldTerminateChatOnMobile");

        SignalCore.waitForSignal(packageKey,"shouldTerminateChatOnWeb");
        ChatPage chatPage = new ChatPage(driver,logger);
        chatPage.terminateChat();

        Sleeper.sleep(10);

        // logOut the current chat or the browser can't be exit
        logonBar.logonOutChat();
        Sleeper.sleep(5);

        //Start to delete the current incident


        Sleeper.sleep(5);

    }
}
