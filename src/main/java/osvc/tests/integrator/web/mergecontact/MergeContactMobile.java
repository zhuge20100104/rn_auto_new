package osvc.tests.integrator.web.mergecontact;

import base.BaseWebTestCase;
import base.annotations.TestCaseInfo;
import base.sync.SignalCore;
import org.testng.annotations.Test;
import osvc.simulators.IWechatSimulator;
import osvc.simulators.SimType;
import osvc.simulators.SimulatorFactory;
import utils.JConfigUtil;

public class MergeContactMobile extends BaseWebTestCase {
    @TestCaseInfo(owner = "chenguang.w.wang", caseNo = "PAASEXTRWI-1300")
    @Test
    public void MergeContact_Mobile() throws Exception {
        SignalCore.waitForSignal(packageKey, "startchat");

        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartChatMsg(toUserName, fromUserName);

        SignalCore.sendSignal(packageKey, "accept");
    }
}
