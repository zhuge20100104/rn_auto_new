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


/**
 *  This Agent accept the chat request, and start a conference
 */
public class ChatConferenceWebTest1 extends BaseWebTestCase{
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

        SignalCore.sendSignal(packageKey,"shouldLoginTheSecondAgent");
        SignalCore.waitForSignal(packageKey,"shouldStartTheConference");
        //Agent1 start the current conference
        ChatPage chatPage = new ChatPage(driver,logger);
//        chatPage.startConference(); todo ??? zhongliang

        SignalCore.sendSignal(packageKey,"shouldAcceptTheConference");

        SignalCore.waitForSignal(packageKey,"shouldLogoutTheCurrentChat");

        //Terminate the current chat
        chatPage.terminateChat();

        Sleeper.sleep(10);

        SignalCore.sendSignal(packageKey, "shouldLogOutSecondWeb");


//        ChatNaviBar chatNaviBar = new ChatNaviBar(driver,logger);
//        chatNaviBar.naviToIncidentsByContacts();
//        chatNaviBar.deleteLastIncident();
//        Sleeper.sleep(8);

        // logOut the current chat or the browser can't be exit
        logonBar.logonOutChat();
        Sleeper.sleep(5);

        //Start to delete the current incident


        Sleeper.sleep(5);

    }

}
