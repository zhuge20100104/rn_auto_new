package osvc.tests.integrator.web.chatdemo;

import base.BaseWebTestCase;
import base.search.engine.wait.utils.Sleeper;
import org.testng.Assert;
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

public class BasicChatTest extends BaseWebTestCase  {
    @BeforeMethod
    public  void setup() throws Exception{
        String rightNowName = JConfigUtil.getBUIRightNowName();
        String url = "https://"+rightNowName+".rightnowdemo.com/AgentWeb/";
        setupCase(url);
    }


    @Test(groups = { "chat"})
    public void basicChatTest() throws Exception {
        BLoginPage bLoginPage = new BLoginPage(driver,logger);
        bLoginPage.Login(BUIAccount.ACCOUNT1);


        //Pas wechat simulator send start chat message
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        //DEBUG mode, no sense, should use this statement to start chat:



        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        Thread.sleep(10*1000);
        logonBar.logonChat();


        Sleeper.sleep(3);
        pasSimulator.sendStartChatMsg(toUserName,fromUserName);


        //Accept chat from BUI
        ChatAcceptDialog acceptDialog = new ChatAcceptDialog(driver,logger);
        acceptDialog.acceptChat();


        //Send message from bui to mobile
        ChatPage chatPage = new ChatPage(driver,logger);
        chatPage.sendMessageToMobile("hello888");

        //Assert that wechat simulator get the message from right now

        boolean queryWechatResult = pasSimulator.queryMessageToWechat(fromUserName,"hello888");
        Assert.assertTrue(queryWechatResult);


        //Send Message From mobile to right now
        pasSimulator.sendTextMessage(toUserName,fromUserName,"hello888");

        //Check that to RN messages contains the message "hello"
        String toRNUserName = JConfigUtil.getTestAccountAccount1Name();
        String buiRNName = JConfigUtil.getBUIRightNowName();

        boolean queryRightNowResult = pasSimulator.queryMessageToRightNow(toRNUserName,fromUserName,buiRNName,"hello888");

        Assert.assertTrue(queryRightNowResult);

        Thread.sleep(10*1000);
    }


    @AfterMethod
    public void teardown() throws Exception{
        //Maybe there is no chats available
        try {
            ChatPage chatPage = new ChatPage(driver, logger);
            chatPage.terminateIfHasChats();
        }catch (Exception ex) {

        }
        super.teardown();
    }
}
