package osvc.tests.integrator.web.GroupChat;

import base.BaseWebTestCase;
import base.annotations.TestCaseInfo;
import base.sync.SignalCore;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.bui.BLoginPage;
import osvc.pageobjects.web.bui.ChatAcceptDialog;
import osvc.pageobjects.web.bui.ChatLogonBar;
import osvc.pageobjects.web.bui.ChatPage;
import utils.BUIAccount;
import utils.JConfigUtil;

public class Agent2 extends BaseWebTestCase {

    @BeforeMethod
    public  void setup() throws Exception {
        String rightNowName = JConfigUtil.getBUIRightNowName();
        String url = "https://"+rightNowName+".rightnowdemo.com/AgentWeb/";
        setupCase(url);
    }

    @TestCaseInfo(owner = "chenguang.w.wang@oracle.com", caseNo = "PAASEXTRWI-1923")
    @Test(groups = {"chat", "PAASEXTRWI-1923"})
    public void transferChatSession_agent2() throws Exception {
        BLoginPage bLoginPage = new BLoginPage(driver, logger);
        String bui_account = BUIAccount.ACCOUNT3;
        bLoginPage.Login(bui_account);

        SignalCore.waitForSignal(packageKey, "transfer");

        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        Thread.sleep(10*1000);
        logonBar.logonChat();


        ChatAcceptDialog chatAcceptDialog = new ChatAcceptDialog(driver, logger);
        chatAcceptDialog.acceptChatInTransfer();

        SignalCore.sendSignal(packageKey, "shut_down");
    }

    @TestCaseInfo(owner = "chenguang.w.wang@oracle.com", caseNo = "PAASEXTRWI-1925")
    @Test(groups = {"chat", "PAASEXTRWI-1925"})
    public void chatConferenceTest_agent2() throws Exception {
        BLoginPage bLoginPage = new BLoginPage(driver, logger);
        String bui_account = BUIAccount.ACCOUNT3;
        bLoginPage.Login(bui_account);

        SignalCore.waitForSignal(packageKey, "conference");

        ChatAcceptDialog chatAcceptDialog = new ChatAcceptDialog(driver, logger);
        chatAcceptDialog.acceptChat();
        SignalCore.sendSignal(packageKey,"assert message");// todo

        SignalCore.waitForSignal(packageKey,"asserted");

        SignalCore.sendSignal(packageKey, "shut_down");
    }

    @AfterMethod
    public void teardown() throws Exception{
        try {
            ChatPage chatPage = new ChatPage(driver, logger);
            chatPage.terminateIfHasChats();
        }catch (Exception ex) {

        }
        super.teardown();
    }

}
