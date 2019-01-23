package osvc.tests.integrator.web.chatdemo;

import base.BaseWebTestCase;
import base.annotations.TestCaseInfo;
import base.search.engine.wait.utils.Sleeper;
import base.sync.SignalCore;
import base.utils.medias.MediaUtil;
import base.utils.medias.beans.AMedia;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.RightnowConfig.RNConnPage;
import osvc.pageobjects.web.WeChatConfig.AutoReplyPage;
import osvc.pageobjects.web.WeChatConfig.WeChatChannelMarkPage;
import osvc.pageobjects.web.WeChatConfig.WeChatConfigPage;
import osvc.pageobjects.web.bui.BLoginPage;
import osvc.pageobjects.web.bui.ChatAcceptDialog;
import osvc.pageobjects.web.bui.ChatLogonBar;
import osvc.pageobjects.web.bui.ChatPage;
import osvc.pageobjects.web.integrator.HomePage;
import osvc.pageobjects.web.integrator.LoginPage;
import osvc.simulators.IWechatSimulator;
import osvc.simulators.SimType;
import osvc.simulators.SimulatorFactory;
import utils.BUIAccount;
import utils.JConfigUtil;

public class ChatTest extends BaseWebTestCase {

    private void changeChatLanguage() throws Exception{
        String identityDomain = JConfigUtil.getAdminConsoleIdentityDomain();
        String serviceName = JConfigUtil.getAdminConsoleServiceName();
        String adminUrl = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?";
        setupCase(adminUrl);

        LoginPage loginPage = new LoginPage(driver, logger);
        loginPage.Login();
        HomePage homePage = new HomePage(driver, logger);
        homePage.goToWechatPage();

        WeChatConfigPage weChatConfigPage = new WeChatConfigPage(driver, logger);
        String wechatAccountName = JConfigUtil.getTestAccountAccount1Name();
        weChatConfigPage.chooseAccountWithAccountName(wechatAccountName);
        weChatConfigPage.getInAutoReplyTab();

        Sleeper.sleep(5);


        AutoReplyPage autoReplyPage = new AutoReplyPage(driver, logger);
        autoReplyPage.getInChatMessage();

        if(JConfigUtil.getCurrentLocale().equals("zh_CN")){
            autoReplyPage.switchToChinese();
        }else {
            autoReplyPage.switchToEnglish();
        }

        Sleeper.sleep(5);
        stopBrowser();
    }


    private void startAndAcceptChat() throws Exception {
        String rightNowName = JConfigUtil.getBUIRightNowName();
        String url = "https://"+rightNowName+".rightnowdemo.com/AgentWeb/";
        setupCase(url);

        BLoginPage bLoginPage = new BLoginPage(driver,logger);
        bLoginPage.Login(BUIAccount.ACCOUNT1);

        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        Thread.sleep(10*1000);
        logonBar.logonChat();

        //Accept chat from BUI
        ChatAcceptDialog acceptDialog = new ChatAcceptDialog(driver,logger);
        acceptDialog.acceptChat();
    }


    @BeforeMethod   //当前 测试类 的每一个 测试方法 执行之前执行
    public  void setup() throws Exception{
        changeChatLanguage();
    }


    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys", "waitForProcessMsg",
                        "soonProvideMsg",
                        "descMsg",
                        "maxEntriesMsg",
                        "incidentCreatedMsg",
                        "inChatProcessMsg",
                        "2048Msg",
                        "last1Msg",
                        "waitCustomerTimeoutMsg",
                        "queueWaitTimeoutMsg",
                        "idleTimeoutMsg",
                        "noAgentAvailable",
                        "chatSessionSwitch",
                        "outOfBusinessHour",
                        "desSubject"

                },

                {"zh_CN", "您的请求已提交，请耐心等待。",
                        "我们将尽快为你提供服务。",   //
                        "请描述您的问题。(最多录入20条。输入\"Q\"随时退出创建 ，输入\"E\"结束录入)",
                        "已达最大记录数，请您稍事休息。我们的座席人员将稍后为您服务。",
                        "感谢您的反馈，已为您成功创建系统记录。事件号为",
                        "您当前处于交谈中。回复 Y 结束当前交谈并发起新的请求。",
                        "AaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrLkkk",
                        "p",
                        "输入超时，请重试。",
                        "会话结束：队列等待超时。",
                        "会话结束：超时未响应。",
                        "请求失败：",
                        "请回复Y确认结束当前会话,或回复N返回当前会话。",
                        "会话发起失败: 非工作时间。",
                        "您的意见对我们非常重要"
                },

                {"en_US", "Please wait while your request is being processed.",
                        "we will soon provide you with services.",
                        "Please describe your question. (Maximum 20 entries. Enter \"Q\" to quite the process, enter \"E\" to finish the input)",
                        "Maximum number of entries is reached. Our agents will provide you services soon.",
                        "Thanks for your feedback. Incident has been successfully created in the system with reference number",
                        "You are currently in the chat process. Reply Y to quit current process and start a new chat.",
                        "AaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrLkkk",
                        "p",
                        "Input time out, please restart.",
                        "Chat end: Queue time out.",
                        "Chat end: Idle time out.",
                        "Request chat failed：No agent available",
                        "You are leaving current conversation, reply Y to confirm or N to keep current conversation.",
                        "Request chat failed: Outside of operating hours.",
                        "Your request is important to us"
                }
        };
        return languages;
    }


    @TestCaseInfo(owner = "fredriczhu.zhu@oracle.com", caseNo = "PAASEXTRWI-1914")
    @Test(groups = { "chat"})
    public void chatMaxEntryTest() throws Exception {

        //Pas wechat simulator send start chat message
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        //DEBUG mode, no sense, should use this statement to start chat:
        pasSimulator.sendStartChatMsg(toUserName,fromUserName);

        boolean isWaitForProcessMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("waitForProcessMsg").toString());
        Assert.assertTrue(isWaitForProcessMsgArrived ,"Wait for process message not arrived!!!");

        //soonProvideMsg
        boolean isSoonProvideMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("soonProvideMsg").toString());
        Assert.assertTrue(isSoonProvideMsgArrived  ,"Soon provide message not arrived!!!");

        pasSimulator.sendTextMessage(toUserName,fromUserName,"Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssggg(243 characters)");

        AMedia media = MediaUtil.uploadImage("1.jpg","image/jpeg");
        pasSimulator.sendImageMessage(toUserName,fromUserName,media);


        AMedia mediaAudio = MediaUtil.uploadAudio("1.amr","audio/amr");
        pasSimulator.sendVoiceMessage(toUserName,fromUserName,mediaAudio);


        AMedia mediaVideo = MediaUtil.uploadVideo("1.mp4","video/mp4");
        pasSimulator.sendVideoMessage(toUserName,fromUserName,mediaVideo);



        pasSimulator.sendTextMessage(toUserName,fromUserName,"http://cn.bing.com");

        for(int i=1;i<=18;i++) {
            pasSimulator.sendTextMessage(toUserName,fromUserName,i+"");
        }


        Sleeper.sleep(10);

        boolean isMaxEntriesMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("maxEntriesMsg").toString());
        Assert.assertTrue(isMaxEntriesMsgArrived,"Max entries message doesn't arrived!!!");

        //start BUI and accept chat
        this.startAndAcceptChat();



        String toRNUserName = JConfigUtil.getTestAccountAccount1Name();
        String buiRNName = JConfigUtil.getBUIRightNowName();

        for(int i=1;i<=10;i++) { // todo getRightNowMsg have a bug.
            boolean queryRNResult = pasSimulator.queryMessageToRightNow(toRNUserName,fromUserName,buiRNName,i+"");
            Assert.assertTrue(queryRNResult);
        }

    }

    @TestCaseInfo(owner = "chenguang", caseNo = "PAASEXTRWI-1495")
    @Test(groups = {"chat", "PAASEXTRWI-1495"})
    public void channelMarkTest() throws Exception {
        // Add Channel Mark
        setupCase("https://" + JConfigUtil.getAdminConsoleServiceName() + "-" + JConfigUtil.getAdminConsoleIdentityDomain() + ".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?");
        new LoginPage(driver, logger).Login();
        new HomePage(driver, logger).goToWechatPage();
        WeChatConfigPage weChatConfigPage = new WeChatConfigPage(driver, logger);
        weChatConfigPage.chooseAccountWithAccountName(JConfigUtil.getTestAccountAccount1Name());
        weChatConfigPage.getInChannelMarkTab();
        WeChatChannelMarkPage weChatChannelMarkPage = new WeChatChannelMarkPage(driver, logger);
        if(weChatChannelMarkPage.isFirstTime()) {
            weChatChannelMarkPage.clickExpandSubListBtn();
            weChatChannelMarkPage.chooseSubList("test_text_area");
            weChatChannelMarkPage.inputValueForWeChatCase("666");
            weChatChannelMarkPage.clickDone();
        }else {
            weChatChannelMarkPage.clickEditButton();
            weChatChannelMarkPage.clickExpandSubListBtn();
            weChatChannelMarkPage.chooseSubList("test_text_area");
            weChatChannelMarkPage.inputValueForWeChatCase("666");
            weChatChannelMarkPage.clickDone();
        }
        Sleeper.sleep(2);
        stopBrowser();
        // End of "Add Channel Mark"

        // Get into BUI and check channel mark.
        setupCase(JConfigUtil.getBUIUrl());
        BLoginPage bLoginPage = new BLoginPage(driver, logger);
        bLoginPage.Login(BUIAccount.ACCOUNT5);
        ChatLogonBar chatLogonBar = new ChatLogonBar(driver, logger);
        chatLogonBar.logonChat();
        ChatPage chatPage = new ChatPage(driver, logger);
        chatPage.terminateIfHasChats();

        // Start Chat
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartChatMsg(toUserName, fromUserName);
        // End of "Start Chat"

        ChatAcceptDialog chatAcceptDialog = new ChatAcceptDialog(driver,logger);
        try {
            chatAcceptDialog.acceptChat();
            Sleeper.sleep(2);
            chatAcceptDialog.acceptChat();
        } catch (Exception ex) {

        }
        Sleeper.sleep(8);
        chatPage = new ChatPage(driver,logger);
        // Terminate the first chat

        //Sleep for the second agent can be terminate

        try {
            chatPage.terminateChat();
        } catch (Exception ex) {

        }
    }

    @Test(groups = {"chat", "PAASEXTRWI-1903"})
    public void AutoReplyMsgTest() throws Exception {
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartChatMsg(toUserName, fromUserName);
        boolean isWaitForProcessMsgArrived = pasSimulator.queryMessageToWechat(fromUserName, getProp("waitForProcessMsg").toString());
        Assert.assertTrue(isWaitForProcessMsgArrived, "\"Wait for process message\" is not arrived!");
        boolean isQueueMsgArrived = pasSimulator.queryMessageToWechat(fromUserName, getProp("soonProvideMsg").toString());
        Assert.assertTrue(isQueueMsgArrived, "\"Queue message\" is not arrived!");
    }

    @Test(groups = {"chat", "PAASEXTRWI-1432"})
    public void getChatStatusTestFromRoutingMenu() throws Exception {
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendMenuClickEventMessage(toUserName,fromUserName,"SRV_WA");
        pasSimulator.sendTextMessage(toUserName,fromUserName,"Y");
        Thread.sleep(5*1000);
        boolean isWaitForProcessMsgArrived = pasSimulator.queryMessageToWechat(fromUserName, getProp("waitForProcessMsg").toString());
        Assert.assertTrue(isWaitForProcessMsgArrived, "\"Wait for process message\" is not arrived!");

        //select chat in routing menu, then re-click chat
        pasSimulator.sendMenuClickEventMessage(toUserName,fromUserName,"SRV_WA");
        boolean isInProcessMsgDisplayed = pasSimulator.queryMessageToWechat(fromUserName, getProp("inChatProcessMsg").toString());
        Assert.assertTrue(isInProcessMsgDisplayed, "\"In Process Message\" is not arrived!");
    }

    @Test(groups = {"chat", "PAASEXTRWI-1953"})
    public void getChatStatusInIncidentTest() throws Exception {
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartIncidentMsg(toUserName, fromUserName);
        Thread.sleep(5*1000);
        boolean isWaitForProcessMsgArrived = pasSimulator.queryMessageToWechat(fromUserName, getProp("waitForProcessMsg").toString());
        Assert.assertTrue(isWaitForProcessMsgArrived, "\"Wait for process message\" is not arrived!");
        //select incident in routing menu, then re-click chat
        pasSimulator.sendMenuClickEventMessage(toUserName,fromUserName,"SRV_WA");
        boolean isInProcessMsgDisplayed = pasSimulator.queryMessageToWechat(fromUserName, getProp("inChatProcessMsg").toString());
        Assert.assertTrue(isInProcessMsgDisplayed, "\"In Chat Process Message\" is not arrived!");
    }

    @Test(groups = {"chat", "PAASEXTRWI-1954"})
    public void getChatStatusInNBHTest() throws Exception {
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartChatMsg(toUserName,fromUserName);
        Thread.sleep(10*1000);
        boolean isWaitForProcessMsgArrived = pasSimulator.queryMessageToWechat(fromUserName, getProp("desSubject").toString());
        Assert.assertTrue(isWaitForProcessMsgArrived, "\"Describe subject Message\" is not arrived!");
        //click chat in NBH turn to create incident, then re-click chat
        pasSimulator.sendMenuClickEventMessage(toUserName,fromUserName,"SRV_WA");
        boolean isInProcessMsgDisplayed = pasSimulator.queryMessageToWechat(fromUserName, getProp("outOfBusinessHour").toString());
        Assert.assertTrue(isInProcessMsgDisplayed, "\"Out Of Business Hour Message\" is not arrived!");
    }

    @Test(groups = {"chat", "PAASEXTRWI-1955"})
    public void getChatStatusInWaitQueueTest() throws Exception {
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartChatMsg(toUserName, fromUserName);
        Thread.sleep(8*1000);
        boolean isWaitForProcessMsgArrived = pasSimulator.queryMessageToWechat(fromUserName, getProp("soonProvideMsg").toString());
        Assert.assertTrue(isWaitForProcessMsgArrived, "\"Soon provide message\" is not arrived!");

        //In process of queue wait, then re-click chat
        pasSimulator.sendMenuClickEventMessage(toUserName,fromUserName,"SRV_WA");
        boolean isInProcessMsgDisplayed = pasSimulator.queryMessageToWechat(fromUserName, getProp("inChatProcessMsg").toString());
        Assert.assertTrue(isInProcessMsgDisplayed, "\"In Process Message\" is not arrived!");
    }

    @Test(groups = {"chat", "PAASEXTRWI-1956"})
    public void getChatStatusInChatTest() throws Exception {
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartIncidentMsg(toUserName, fromUserName);
        Thread.sleep(5*1000);
        boolean isWaitForProcessMsgArrived = pasSimulator.queryMessageToWechat(fromUserName, getProp("waitForProcessMsg").toString());
        Assert.assertTrue(isWaitForProcessMsgArrived, "\"Wait for process message\" is not arrived!");
        pasSimulator.sendTextMessage(toUserName,fromUserName,"hello");
        //chat in process, then re-click chat
        pasSimulator.sendMenuClickEventMessage(toUserName,fromUserName,"SRV_WA");
        boolean isInProcessMsgDisplayed = pasSimulator.queryMessageToWechat(fromUserName, getProp("inChatProcessMsg").toString());
        Assert.assertTrue(isInProcessMsgDisplayed, "\"In Process Message\" is not arrived!");
    }

    @Test(groups = {"chat", "PAASEXTRWI-1957"})
    public void getChatStatusFromRegisterTest() throws Exception {
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartIncidentMsg(toUserName, fromUserName);
        Thread.sleep(5*1000);
        boolean isWaitForProcessMsgArrived = pasSimulator.queryMessageToWechat(fromUserName, getProp("waitForProcessMsg").toString());
        Assert.assertTrue(isWaitForProcessMsgArrived, "\"Wait for process message\" is not arrived!");
        pasSimulator.sendTextMessage(toUserName,fromUserName,"hello");
        //switch chat and register,then re-click chat
        pasSimulator.sendMenuClickEventMessage(toUserName,fromUserName,"SRV_RG");
        pasSimulator.sendMenuClickEventMessage(toUserName,fromUserName,"SRV_WA");
        boolean isInProcessMsgDisplayed = pasSimulator.queryMessageToWechat(fromUserName, getProp("chatSessionSwitch").toString());
        Assert.assertTrue(isInProcessMsgDisplayed, "\"Chat Session Switch Message\" is not arrived!");
    }





    @Test(groups = {"chat", "PAASEXTRWI-1926"})
    public void splitAgentToWechatLongMsg() throws Exception {
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartChatMsg(toUserName, fromUserName);

        // send message on bui
        setupCase(JConfigUtil.getBUIUrl());

        BLoginPage bLoginPage = new BLoginPage(driver, logger);
        bLoginPage.Login(BUIAccount.ACCOUNT2);
        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        Thread.sleep(10*1000);
        logonBar.logonChat();
        ChatAcceptDialog chatAcceptDialog = new ChatAcceptDialog(driver, logger);
        chatAcceptDialog.acceptChat();
        ChatPage chatPage = new ChatPage(driver, logger);
        chatPage.sendMessageToMobile("AaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrLkkkp");

        stopBrowser();

        // check on wechat
        boolean is2048MsgArrived = pasSimulator.queryMessageToWechat(toUserName, getProp("2048Msg").toString());
        Assert.assertTrue(is2048MsgArrived, "2048 message is not arrived!");
        boolean isLast1MsgArrived = pasSimulator.queryMessageToWechat(toUserName, getProp("last1Msg").toString());
        Assert.assertTrue(isLast1MsgArrived, "last 1 message is not arrived!");
    }

    @Test(groups = {"chat", "PAASEXTRWI-1927"})
    public void chatTimeOutTest() throws Exception {
        // chat test
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendMenuClickEventMessage(toUserName, fromUserName, "SRV_WA");
        pasSimulator.sendTextMessage(toUserName, fromUserName, "Y");

        // wait for at least 15min
        for (int i = 0; i < 16; i++) {
            Thread.sleep(60 * 1000);
        }

        boolean isChatTimeOutMsgDisplayed = pasSimulator.queryMessageToWechat(toUserName, getProp("waitCustomerTimeoutMsg").toString());
        Assert.assertTrue(isChatTimeOutMsgDisplayed, "Time out message is not arrived!");
    }

    @Test(groups = {"chat"})
    public void RegiTimeOutTest() throws Exception {
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);

        // register test
        pasSimulator.sendMenuClickEventMessage(toUserName, fromUserName,"SRV_RG");
        pasSimulator.sendTextMessage(toUserName, fromUserName,"Y");

        // wait for at least 15min
        for (int i = 0; i < 16; i++) {
            Thread.sleep(60*1000);
        }

        boolean isRegTimeOutMsgDisplayed = pasSimulator.queryMessageToWechat(toUserName, getProp("waitCustomerTimeoutMsg").toString());
        Assert.assertTrue(isRegTimeOutMsgDisplayed, "Time out message is not arrived!");
    }

    @TestCaseInfo(owner = "chenguang.w.wang", caseNo = "PAASEXTRWI-1928")
    @Test(groups = {"chat","bug", "PAASEXTRWI-1928"})
    public void queueTimeOutTest() throws Exception {
        String rightNowName = JConfigUtil.getBUIRightNowName();
        String url = "https://"+rightNowName+".rightnowdemo.com/AgentWeb/";
        setupCase(url);

        BLoginPage bLoginPage = new BLoginPage(driver,logger);
        bLoginPage.Login(BUIAccount.ACCOUNT1);

        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        Thread.sleep(10*1000);
        logonBar.logonChat();

        // chat test
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartChatMsg(toUserName, fromUserName);

        // wait for at least 120 sec
        Sleeper.sleep(3*60);

        boolean isChatTimeOutMsgDisplayed = pasSimulator.queryMessageToWechat(fromUserName, getProp("queueWaitTimeoutMsg").toString());
        Assert.assertTrue(isChatTimeOutMsgDisplayed, "Queue Time out message is not arrived!");
    }

    @TestCaseInfo(owner = "chenguang.w.wang", caseNo = "PAASEXTRWI-1929")
    @Test(groups = {"chat", "PAASEXTRWI-1929"})
    public void noResponseTimeOutTest() throws Exception {
        // chat test
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);


        String rightNowName = JConfigUtil.getBUIRightNowName();
        String url = "https://"+rightNowName+".rightnowdemo.com/AgentWeb/";
        setupCase(url);

        BLoginPage bLoginPage = new BLoginPage(driver, logger);
        String bui_account = BUIAccount.ACCOUNT2;
        bLoginPage.Login(bui_account);

        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        Thread.sleep(10*1000);
        logonBar.logonChat();


        pasSimulator.sendStartChatMsg(toUserName, fromUserName);

//        SignalCore.waitForSignal("GroupChat", "start_chat");

        ChatAcceptDialog acceptDialog = new ChatAcceptDialog(driver,logger);
        acceptDialog.acceptChat();
        Thread.sleep(5*1000);

        // wait for at least 2 min
        for (int i = 0; i < 6; i++) {
            Thread.sleep(60*1000);
        }

        boolean isNoResponseMsgDisplayed = pasSimulator.queryMessageToWechat(fromUserName, getProp("idleTimeoutMsg").toString());
        Assert.assertTrue(isNoResponseMsgDisplayed, "Idle Time out message is not arrived!");
    }

    @TestCaseInfo(owner = "chenguang", caseNo = "PAASEXTRWI-1915")
    @Test
    public void noAgentOnline()throws Exception{
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartChatMsg(toUserName, fromUserName);

        Sleeper.sleep(30);  // wait for log

        boolean isWaitForProcessMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("waitForProcessMsg").toString());
        Assert.assertTrue(isWaitForProcessMsgArrived ,"Wait for process message not arrived!!!");

//        this.startAndAcceptChat();
//
//        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
//        Thread.sleep(10*1000);
//        logonBar.logonOutChat();

        boolean isNoAgentAvailable=pasSimulator.queryMessageToWechat(fromUserName, getProp("noAgentAvailable").toString());
        Assert.assertTrue(isNoAgentAvailable, "No Agent Available message not arrived!!!");
    }



    @AfterMethod
    public void teardown() throws Exception{
        ChatPage chatPage = new ChatPage(driver,logger);
        chatPage.terminateIfHasChats();

        super.teardown();
    }


}
