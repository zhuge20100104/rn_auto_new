package osvc.tests.integrator.web.mergecontact;

import base.BaseWebTestCase;
import base.annotations.TestCaseInfo;
import base.sync.SignalCore;
import org.testng.Assert;
import org.testng.annotations.Test;
import osvc.pageobjects.web.bui.*;
import osvc.simulators.IWechatSimulator;
import osvc.simulators.SimType;
import osvc.simulators.SimulatorFactory;
import utils.BUIAccount;
import utils.JConfigUtil;

public class MergeContactWeb extends BaseWebTestCase {

//    @BeforeMethod
//    public void setup() throws Exception {
//        setupCase(JConfigUtil.getBUIUrl());
//        BLoginPage bLoginPage = new BLoginPage(driver, logger);
//        bLoginPage.Login(BUIAccount.ACCOUNT2);
//    }

    @TestCaseInfo(owner = "chenguang.w.wang", caseNo = "PAASEXTRWI-1300")
    @Test
    public void MergeContact_Agent() throws Exception {
        setupCase(JConfigUtil.getBUIUrl());
        BLoginPage bLoginPage = new BLoginPage(driver, logger);
        bLoginPage.Login(BUIAccount.ACCOUNT2);

        ChatLogonBar chatLogonBar = new ChatLogonBar(driver, logger);
        chatLogonBar.getInContactPage();

        ContactPage contactPage = new ContactPage(driver, logger);
        String PrimaryContactID = contactPage.getContactIdFrom_AutomationA();
        contactPage.inputPrimaryContactId(PrimaryContactID);

        SignalCore.sendSignal(packageKey, "startchat");

        SignalCore.waitForSignal(packageKey, "acceptchat");


        chatLogonBar.logonOutChat();
        ChatAcceptDialog chatAcceptDialog = new ChatAcceptDialog(driver, logger);
        chatAcceptDialog.acceptChat();

        ChatPage chatPage = new ChatPage(driver, logger);
        String chat_email = chatPage.getChatEmail();
        Assert.assertTrue(chat_email.contains(JConfigUtil.getMergeContactEmail()));
    }


}
