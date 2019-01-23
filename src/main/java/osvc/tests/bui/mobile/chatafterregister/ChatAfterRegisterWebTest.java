package osvc.tests.bui.mobile.chatafterregister;

import base.BaseWebTestCase;
import base.search.engine.wait.utils.Result;
import base.search.engine.wait.utils.Sleeper;
import base.sync.SignalCore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.bui.*;
import utils.BUIAccount;
import utils.JConfigUtil;

public class ChatAfterRegisterWebTest extends BaseWebTestCase {
    @BeforeMethod
    public void setup() throws Exception {
        String buiHostUrl = "";//JConfigUtil.getBUIUrl();
        setupCase(buiHostUrl);
    }

    @Test
    public void testChatAfterRegisterWeb() throws Exception{

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


        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        logonBar.logonChat();
        SignalCore.waitForSignal(packageKey,"shouldAcceptChatRequest");

        ChatAcceptDialog chatAcceptDialog = new ChatAcceptDialog(driver,logger);
        chatAcceptDialog.acceptChat();
        SignalCore.sendSignal(packageKey,"shouldSendTheTestMessage");
        SignalCore.waitForSignal(packageKey, "shouldSendResponseMessgae");

        ChatPage chatPage = new ChatPage(driver,logger);
        chatPage.sendMessageToMobile("Ok,We will check it now...");
        chatPage.terminateChat();
        Sleeper.sleep(5);

        // logOut the current chat or the browser can't be exit
        logonBar.logonOutChat();

        Sleeper.sleep(5);



    }
}
