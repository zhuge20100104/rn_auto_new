package osvc.tests.bui.mobile.chatconference;

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

/**
 * This class accept the conference
 */
public class ChatConferenceWebTest2 extends BaseWebTestCase{
    @BeforeMethod
    public void setup() throws Exception {
        String buiHostUrl = "";//JConfigUtil.getBUIUrl();
        setupCase(buiHostUrl);
    }

    @Test
    public  void testAcceptRequest() throws Exception{
        BLoginPage page = new BLoginPage(driver,logger);
        page.Login(BUIAccount.ACCOUNT2);

        SignalCore.waitForSignal(packageKey,"shouldLoginTheSecondAgent");
        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        logonBar.logonChat();
        SignalCore.sendSignal(packageKey,"shouldStartTheConference");

        SignalCore.waitForSignal(packageKey,"shouldAcceptTheConference");
        ChatAcceptDialog acceptDialog = new ChatAcceptDialog(driver,logger);
        acceptDialog.acceptChat();


        SignalCore.sendSignal(packageKey,"shouldLogoutTheCurrentChat");

        SignalCore.waitForSignal(packageKey, "shouldLogOutSecondWeb");

        Sleeper.sleep(15);
        ChatPage chatPage = new ChatPage(driver,logger);

        try {
            logonBar.logonOutChat();
        }catch (Exception ex) {
            logger.info("Log out failed!");
        }
        Sleeper.sleep(10);

    }

}
