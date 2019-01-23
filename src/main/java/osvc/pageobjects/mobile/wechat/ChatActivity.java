package osvc.pageobjects.mobile.wechat;

import base.BaseMobileComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class ChatActivity extends BaseMobileComponent {
    private SearchTools sTools;
    private Logger logger;
    private WebDriver driver;
    private ChatMenuBar chatMenuBar;

    public ChatActivity(WebDriver driver, Logger logger) throws Exception{
        sTools = new SearchTools(driver);
        chatMenuBar = new ChatMenuBar(driver,logger);
        this.driver = driver;
        this.logger = logger;
    }


    public void  checkIsInChat() throws Exception{
//        chatMenuBar.clickAgent();
//        boolean isInChat = sTools.waitForText(get("inChatProgress"));
//        if(isInChat) {
//            //Send message Y to terminate the previous chat progress
//            chatMenuBar.switchToEdit();
//            chatMenuBar.sendMessage("Y");
//            chatMenuBar.switchToServices();
//        }
//
//        boolean isChatStart = sTools.waitForText(get("question"));
//        Assert.assertTrue(isChatStart,"Start chat process failure, network error!");

    }


    public void registerWhileRouting(String phoneNumber) throws Exception {
//        this.checkIsInChat();
//        chatMenuBar.switchToEdit();
//        chatMenuBar.sendMessage("1");
//        chatMenuBar.switchToServices();
//        chatMenuBar.clickRegistration();
//
//        boolean isLeavingConversation = sTools.waitForText(get("leaveConversationText"));
//        Assert.assertTrue(isLeavingConversation,"Leaving conversation text not shown!!");
//
//        chatMenuBar.switchToEdit();
//        chatMenuBar.sendMessage("Y");
//
//
//        sTools.waitForText(get("inputPhoneText"));
//
//        String tmpPhoneNumber = "+86"+phoneNumber;
//        chatMenuBar.sendMessage(tmpPhoneNumber);
//
//        boolean isCodeSent = sTools.waitForText(get("codeSendText"));
//        Assert.assertTrue(isCodeSent,"Code sent failure, can't do register using mock service");
//
//        //Get Registration code from mock service and then send the code
//        String getRegistrationCodeURL = JConfigUtil.getUserRegisterGetRegisterCodeURL();
//        getRegistrationCodeURL = getRegistrationCodeURL + phoneNumber;
//        String registrationCode = OkHttpUtil.httpGet(getRegistrationCodeURL);
//        chatMenuBar.sendMessage(registrationCode);
//
//        boolean isRegisterSuccess = sTools.waitForText(get("registerSuccessText"));
//        Assert.assertTrue(isRegisterSuccess,"Register user failure!!!");
    }

    public void checkChatInQueue() throws Exception {
//        boolean isInQueue = sTools.waitForText(get("chatInQueue"));
//        Assert.assertTrue(isInQueue,"Is In Chat Queue text shown,No agent available!");
    }

    public void createIncident() throws Exception{
//        chatMenuBar.switchToEdit();
//        chatMenuBar.sendMessage("3");
//        sTools.waitForText(get("incidentImportant"));
//        chatMenuBar.sendMessage("1111");
//        sTools.waitForText(get("incidentDesc"));
//        chatMenuBar.sendMessage("E");
    }

    public void checkMaxNumEntries() throws Exception{
//       boolean maxNumEntriesShown = sTools.waitForText(get("maxNumEntries"));
//       Assert.assertTrue(maxNumEntriesShown,"Max num entries not shown,network error!!");
    }

    public void checkAgentLeftMessage() throws Exception {
//        boolean agentLeftMessageShown = sTools.waitForText(get("agentLeftMessage"));
//        Assert.assertTrue(agentLeftMessageShown,"Agent left message not shown, network error!!!");
    }

    public boolean checkHasRegistered() throws Exception {
//        chatMenuBar.clickRegistration();
//
//        boolean isInChat = sTools.waitForText(get("inChatProgress"));
//        if(isInChat) {
//            //Send message Y to terminate the previous chat progress
//            chatMenuBar.switchToEdit();
//            chatMenuBar.sendMessage("Y");
//            chatMenuBar.switchToServices();
//        }
//
//        return sTools.waitForText(get("haveRegistered"));

        return true;
    }

    public void registerUser(String phoneNumber) throws Exception {
//        chatMenuBar.clickRegistration();
//        sTools.waitForText(get("inputPhoneText"));
//        chatMenuBar.switchToEdit();
//
//        String tmpPhoneNumber = "+86"+phoneNumber;
//        chatMenuBar.sendMessage(tmpPhoneNumber);
//
//        boolean isCodeSent = sTools.waitForText(get("codeSendText"));
//        Assert.assertTrue(isCodeSent,"Code sent failure, can't do register using mock service");
//
//        //Get Registration code from mock service and then send the code
//        String getRegistrationCodeURL = JConfigUtil.getUserRegisterGetRegisterCodeURL();
//        getRegistrationCodeURL = getRegistrationCodeURL + phoneNumber;
//        String registrationCode = OkHttpUtil.httpGet(getRegistrationCodeURL);
//        chatMenuBar.sendMessage(registrationCode);
//
//        boolean isRegisterSuccess = sTools.waitForText(get("registerSuccessText"));
//        Assert.assertTrue(isRegisterSuccess,"Register user failure!!!");
    }
}
