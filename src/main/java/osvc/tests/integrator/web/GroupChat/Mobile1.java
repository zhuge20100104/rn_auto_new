package osvc.tests.integrator.web.GroupChat;

import base.BaseWebTestCase;
import base.annotations.TestCaseInfo;
import base.sync.SignalCore;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.bui.ChatPage;
import osvc.simulators.IWechatSimulator;
import osvc.simulators.SimType;
import osvc.simulators.SimulatorFactory;
import utils.JConfigUtil;

public class Mobile1 extends BaseWebTestCase {
    public Object[][] getLanguages(){
        Object[][] language={
                {"keys","ConfeMsg"},
                {"zh_CN","已加入群聊"},
                {"en_US","has joined the chat"}

        };
        return language;
    }

    @TestCaseInfo(owner = "chenguang.w.wang@oracle.com", caseNo = "PAASEXTRWI-1923, PAASEXTRWI-1925")
    @Test(groups = {"group_chat", "PAASEXTRWI-1923", "PAASEXTRWI-1925"})
    public void transferChatSession_mobile1() throws Exception {
        SignalCore.waitForSignal(packageKey,"start_chat");

        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartChatMsg(toUserName, fromUserName);
        SignalCore.sendSignal(packageKey, "accept_chat");

        SignalCore.waitForSignal(packageKey,"assert message");
        Thread.sleep(5*1000);
//        boolean isConfMsgDisplayed = driver.getPageSource().contains(getProp("ConfeMsg").toString());
        boolean isConfMsgDisplayed = pasSimulator.queryMessageToRightNow(toUserName, fromUserName, JConfigUtil.getBUIRightNowName(), getProp("ConfeMsg").toString());
        Assert.assertTrue(isConfMsgDisplayed, "ConfeMsg is not arrived!");
        SignalCore.sendSignal(packageKey,"asserted");


    }

    @AfterMethod
    public void teardown() throws Exception{
        try {
            super.teardown();
        } catch (Exception e) {

        }
    }
}
