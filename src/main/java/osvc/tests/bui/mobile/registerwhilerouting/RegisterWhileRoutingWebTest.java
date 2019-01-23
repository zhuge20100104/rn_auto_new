package osvc.tests.bui.mobile.registerwhilerouting;

import base.BaseWebTestCase;
import base.search.engine.wait.utils.Result;
import base.sync.SignalCore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.bui.BLoginPage;
import osvc.pageobjects.web.bui.BUnRegisterPage;
import utils.BUIAccount;
import utils.JConfigUtil;

public class RegisterWhileRoutingWebTest extends BaseWebTestCase {

    @BeforeMethod
    public void setup() throws Exception {
        String buiHostUrl ="";// JConfigUtil.getBUIUrl();
        setupCase(buiHostUrl);
    }

    @Test
    public void testRegisterWhileRoutingWeb() throws Exception {

        BLoginPage page = new BLoginPage(driver,logger);
        page.Login(BUIAccount.ACCOUNT1);

        Result<Boolean> resultUnRegistered = SignalCore.waitForSignalWithValue(packageKey,"shouldUnRegisteredUser");
        boolean shouldUnRegister = Boolean.parseBoolean(resultUnRegistered.getMessage());
        if(shouldUnRegister) {
            BUnRegisterPage unRegisterPage = new BUnRegisterPage(driver,logger);
            String wechatId = JConfigUtil.getUserRegisterOfficialAccount1();
            String phoneNumber = JConfigUtil.getUserRegisterPhone();
            unRegisterPage.unRegisterUserInBuiByPhone(wechatId,phoneNumber);
        }


        SignalCore.sendSignal(packageKey,"shouldRegisterInMobile");
    }
}
