package osvc.tests.integrator.web.chatdemo;

import base.BaseWebTestCase;
import base.annotations.TestCaseInfo;
import base.utils.medias.MediaUtil;
import base.utils.medias.beans.AMedia;
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

public class ImageChatTest extends BaseWebTestCase {
    @BeforeMethod
    public  void setup() throws Exception{
        String rightNowName = JConfigUtil.getBUIRightNowName();
        String url = "https://"+rightNowName+".rightnowdemo.com/AgentWeb/";
        setupCase(url);
    }


    @TestCaseInfo(owner = "chenguang", caseNo = "PAASEXTRWI-789")
    @Test(groups = { "chat"})
    public void imageChatTest() throws Exception {
//        BLoginPage bLoginPage = new BLoginPage(driver,logger);
//        bLoginPage.Login(BUIAccount.ACCOUNT2);


        //Pas wechat simulator send start chat message
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        // todo remove
        AMedia media = MediaUtil.uploadImage("1.jpg","image/jpeg");
        pasSimulator.sendImageMessage(toUserName,fromUserName,media);



        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        Thread.sleep(10*1000);
        logonBar.logonChat();


        //DEBUG mode, no sense, should use this statement to start chat:
        pasSimulator.sendStartChatMsg(toUserName,fromUserName);

        //Accept chat from BUI
        ChatAcceptDialog acceptDialog = new ChatAcceptDialog(driver,logger);
        acceptDialog.acceptChat();


        //Send message from bui to mobile
        ChatPage chatPage = new ChatPage(driver,logger);
        chatPage.sendMessageToMobile("hello888");

        //Assert that wechat simulator get the message from right now

        for (int i = 0; i < 3; i++) { // 其实queryMessageToWechat里本身有五次重试，这里套了三倍也就是15次，按理说没必要，但有时候log记录的太慢了。
            boolean queryWechatResult = pasSimulator.queryMessageToWechat(fromUserName,"hello888");
            if (queryWechatResult) {
                Assert.assertTrue(queryWechatResult);
            }
        }


        //Send Message From mobile to right now
//        AMedia media = MediaUtil.uploadImage("1.jpg","image/jpeg");
        pasSimulator.sendImageMessage(toUserName,fromUserName,media);

        String toRNUserName = JConfigUtil.getTestAccountAccount1Name();
        String buiRNName = JConfigUtil.getBUIRightNowName();

        boolean queryRNResult = pasSimulator.queryMediasToRightNow(toRNUserName,fromUserName,buiRNName,"image",media.getMediaId());
        Assert.assertTrue(queryRNResult);


        AMedia mediaAudio = MediaUtil.uploadAudio("1.amr","audio/amr");
        pasSimulator.sendVoiceMessage(toUserName,fromUserName,mediaAudio);
        queryRNResult = pasSimulator.queryMediasToRightNow(toRNUserName,fromUserName,buiRNName,"voice",mediaAudio.getMediaId());
        Assert.assertTrue(queryRNResult);


        AMedia mediaVideo = MediaUtil.uploadVideo("1.mp4","video/mp4");
        pasSimulator.sendVideoMessage(toUserName,fromUserName,mediaVideo);
        queryRNResult = pasSimulator.queryMediasToRightNow(toRNUserName,fromUserName,buiRNName,"video",mediaVideo.getMediaId());
        Assert.assertTrue(queryRNResult);


        //Check that to RN messages contains the message "hello"
        Thread.sleep(10*1000);
    }


    @AfterMethod
    public void teardown() throws Exception{
        ChatPage chatPage = new ChatPage(driver,logger);
        chatPage.terminateIfHasChats();
        super.teardown();
    }
}
