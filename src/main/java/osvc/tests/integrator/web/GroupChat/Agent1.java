package osvc.tests.integrator.web.GroupChat;

import base.BaseWebTestCase;
import base.annotations.TestCaseInfo;
import base.search.engine.wait.utils.Sleeper;
import base.sync.SignalCore;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.bui.BLoginPage;
import osvc.pageobjects.web.bui.ChatAcceptDialog;
import osvc.pageobjects.web.bui.ChatLogonBar;
import osvc.pageobjects.web.bui.ChatPage;
import osvc.simulators.IWechatSimulator;
import osvc.simulators.SimType;
import osvc.simulators.SimulatorFactory;
import utils.BUIAccount;
import utils.JConfigUtil;

public class Agent1 extends BaseWebTestCase {

    @BeforeMethod
    public  void setup() throws Exception{
        String rightNowName = JConfigUtil.getBUIRightNowName();
        String url = "https://"+rightNowName+".rightnowdemo.com/AgentWeb/";
        setupCase(url);
    }

    @TestCaseInfo(owner = "chenguang.w.wang@oracle.com", caseNo = "PAASEXTRWI-1923")
    @Test(groups = {"chat", "PAASEXTRWI-1923"})
    public void transferChatSession_agent1() throws Exception {
        BLoginPage bLoginPage = new BLoginPage(driver, logger);
        String bui_account = BUIAccount.ACCOUNT2;
        bLoginPage.Login(bui_account);

        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        Thread.sleep(10*1000);
        logonBar.logonChat();

        SignalCore.sendSignal(packageKey,"start_chat");

        SignalCore.waitForSignal(packageKey,"accept_chat");
        ChatAcceptDialog acceptDialog = new ChatAcceptDialog(driver,logger);
        acceptDialog.acceptChat();
        Thread.sleep(5*1000);

        ChatPage chatPage = new ChatPage(driver, logger);
        String TransferAgent_Bot3 = JConfigUtil.getBUIUserName(BUIAccount.ACCOUNT3);
        chatPage.transferToAnotherAgent(TransferAgent_Bot3);

        SignalCore.sendSignal(packageKey, "transfer");

        SignalCore.waitForSignal(packageKey, "shut_down");
    }


    @TestCaseInfo(owner = "chenguang.w.wang@oracle.com", caseNo = "PAASEXTRWI-1925")
    @Test(groups = {"chat", "PAASEXTRWI-1925"})
    public void chatConferenceTest_agent1() throws Exception {
        BLoginPage bLoginPage = new BLoginPage(driver, logger);
        String bui_account = BUIAccount.ACCOUNT2;
        bLoginPage.Login(bui_account);

        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        Thread.sleep(10*1000);
        logonBar.logonChat();
        SignalCore.sendSignal(packageKey,"start_chat");

        SignalCore.waitForSignal(packageKey, "accept_chat");

        ChatAcceptDialog acceptDialog = new ChatAcceptDialog(driver,logger);
        acceptDialog.acceptChat();
        Thread.sleep(5*1000);

        ChatPage chatPage = new ChatPage(driver, logger);
        String TransferAgent_Bot3 = JConfigUtil.getBUIUserName(BUIAccount.ACCOUNT3);
        chatPage.startConference(TransferAgent_Bot3);
        SignalCore.sendSignal(packageKey, "conference");

        SignalCore.waitForSignal(packageKey, "shut_down");
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
