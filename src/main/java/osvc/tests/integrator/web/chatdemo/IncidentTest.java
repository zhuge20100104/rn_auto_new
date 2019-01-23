package osvc.tests.integrator.web.chatdemo;

import base.BaseWebTestCase;
import base.annotations.TestCaseInfo;
import base.exceptions.LogException;
import base.search.engine.wait.utils.Sleeper;
import base.utils.medias.MediaUtil;
import base.utils.medias.beans.AMedia;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.RoutingMenuConfig.RoutingMenuSettingPage;
import osvc.pageobjects.web.SystemParameterConfig.SystemParameterPage;
import osvc.pageobjects.web.WeChatConfig.AutoReplyPage;
import osvc.pageobjects.web.WeChatConfig.WeChatConfigPage;
import osvc.pageobjects.web.bui.BLoginPage;
import osvc.pageobjects.web.bui.ChatLogonBar;
import osvc.pageobjects.web.bui.IncidentPage;
import osvc.pageobjects.web.integrator.HomePage;
import osvc.pageobjects.web.integrator.LoginPage;
import osvc.simulators.IWechatSimulator;
import osvc.simulators.OmsWechatSimulator;
import osvc.simulators.SimType;
import osvc.simulators.SimulatorFactory;
import osvc.simulators.beans.Msg;
import utils.BUIAccount;
import utils.JConfigUtil;

import java.util.List;

public class IncidentTest extends BaseWebTestCase {

    private static String RoutingMenuNum = "";
    private static String RoutingIncidentNum = "";
    private static String RoutingSumNum = "";

    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","descMsg",
                        "incidentCreatedMsg",
                        "incidentStartMsg",
                        "maxEntriesMsg",
                        "incidentCreationCancelMsg",
                        "notBusinessHoursMsg"
                },

                {"zh_CN","请描述您的问题。(最多录入20条。输入\"Q\"随时退出创建 ，输入\"E\"结束录入)",
                        "感谢您的反馈，已为您成功创建系统记录。事件号为",
                        "您的意见对我们非常重要，我们将会记录您的反馈并及时答复。请先输入事件的标题。(输入\"Q\"随时退出创建 )",
                        "已达最大录入数。您的意见对我们非常重要，我们将尽快予以反馈。",
                        "事件创建取消。如需其他帮助请再次发起问询。",
                        "会话发起失败: 非工作时间。"
                },

                {"en_US","Please describe your question. (Maximum 20 entries. Enter \"Q\" to quite the process, enter \"E\" to finish the input)",
                        "Thanks for your feedback. Incident has been successfully created in the system with reference number",
                        "Your request is important to us. We will create an incident to record your request. Please input the subject. (Enter \"Q\" to quite the process)",
                        "Maximum number of entries is reached. We will keep your request and feedback you soon.",
                        "Incident creation cancelled.",
                        "Request chat failed: Outside of operating hours."
                }
        };
        return languages;
    }

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

    private void getIncidentCount() throws Exception {
        String identityDomain = JConfigUtil.getAdminConsoleIdentityDomain();
        String serviceName = JConfigUtil.getAdminConsoleServiceName();
        String adminUrl = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?";
        setupCase(adminUrl);
        LoginPage loginPage = new LoginPage(driver, logger);
        loginPage.Login();
        HomePage homePage = new HomePage(driver, logger);
        homePage.goToRoutingSettingPage();
        RoutingMenuSettingPage routingMenuSettingPage = new RoutingMenuSettingPage(driver, logger);
        routingMenuSettingPage.getInPage_HistoryIncidents();
        routingMenuSettingPage.enableRoutingMenu();
        routingMenuSettingPage.enableHistoryIncidentLevelMenu();
        routingMenuSettingPage.turnOnNotifyAgent();
        RoutingIncidentNum = routingMenuSettingPage.checkMessageDisplayedNum();
        RoutingMenuNum = routingMenuSettingPage.getRoutingNum();
        RoutingSumNum = String.valueOf(Integer.parseInt(RoutingIncidentNum) + Integer.parseInt(RoutingMenuNum));
    }

    private void setIfCreateIncidentInNBHAllowed(boolean isCreateIncidentInNBHAllowed) throws Exception {
        setupCase("https://" + JConfigUtil.getAdminConsoleServiceName() + "-" + JConfigUtil.getAdminConsoleIdentityDomain() + ".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?");
        new LoginPage(driver, logger).Login();
        new HomePage(driver, logger).gotoParameterPage();
        SystemParameterPage systemParameterPage = new SystemParameterPage(driver, logger);
        systemParameterPage.chooseIsCustomersAllowedToCreateIncidentInNBH(isCreateIncidentInNBHAllowed);
        Thread.sleep(2*1000); // wait browser send data to back-end
        stopBrowser();
    }

    @BeforeMethod
    public void setup() throws Exception {
        changeChatLanguage();
    }

    @TestCaseInfo(owner = "chenguang", caseNo = "PAASEXTRWI-791")
    @Test(groups = {"incident", "PAASEXTRWI-791"})
    public void routingMenuIncidentNumTest() throws Exception {
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);

        getIncidentCount();
        stopBrowser();
        //增加相应数目的incident
        incidentTest();
        for (int i = 0; i < Integer.parseInt(RoutingIncidentNum); i++) {
            pasSimulator.sendStartIncidentMsg(toUserName, fromUserName);
            pasSimulator.sendTextMessage(toUserName,fromUserName,"incident title by chenguang");
            boolean isTextMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("descMsg").toString());
            Assert.assertTrue(isTextMsgArrived,"Text message not arrived!!!");
            pasSimulator.sendTextMessage(toUserName,fromUserName,"content by chenguang");
            pasSimulator.sendTextMessage(toUserName,fromUserName,"E");
            boolean isIncidentCreated = pasSimulator.queryMessageToWechat(fromUserName,getProp("incidentCreatedMsg").toString());
            Assert.assertTrue(isIncidentCreated,"Text message not arrived!!!");
        }

        Sleeper.sleep(10);

        // 检查是否有最后一条
        boolean isLastIncidentMsgExist = pasSimulator.queryMessageToWechat(fromUserName,  RoutingSumNum + ":");
        Assert.assertTrue(isLastIncidentMsgExist);
        // 检查是否显示多余的incident
        boolean isExtraIncidentMsgExist = pasSimulator.queryMessageToWechat(fromUserName,  String.valueOf(Integer.parseInt(RoutingSumNum) + 1) + ":");
        Assert.assertFalse(isExtraIncidentMsgExist);
    }


    @Test(groups = {"incident","PAASEXTRWI-941"})
    public void incidentTest() throws Exception {

        //Pas wechat simulator send start chat message
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        //DEBUG mode, no sense, should use this statement to start chat:
        pasSimulator.sendStartIncidentMsg(toUserName, fromUserName);
        pasSimulator.sendTextMessage(toUserName,fromUserName,"Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssggg(243 characters)");
        boolean isTextMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("descMsg").toString());
        Assert.assertTrue(isTextMsgArrived,"Text message not arrived!!!");

        AMedia media = MediaUtil.uploadImage("1.jpg","image/jpeg");
        pasSimulator.sendImageMessage(toUserName,fromUserName,media);


        AMedia mediaAudio = MediaUtil.uploadAudio("1.amr","audio/amr");
        pasSimulator.sendVoiceMessage(toUserName,fromUserName,mediaAudio);

        AMedia mediaVideo = MediaUtil.uploadVideo("1.mp4","video/mp4");
        pasSimulator.sendVideoMessage(toUserName,fromUserName,mediaVideo);

        pasSimulator.sendTextMessage(toUserName,fromUserName,"E");

        Sleeper.sleep(10); // wait for log

        boolean isChatEnd = pasSimulator.queryMessageToWechat(fromUserName,getProp("incidentCreatedMsg").toString());
        Assert.assertTrue(isChatEnd,"Text message not arrived!!!");

    }


    @TestCaseInfo(owner = "fredric", caseNo = "PAASEXTRWI-942")
    @Test(groups = {"incident","PAASEXTRWI-942"})
    public void moreThan20EntriesIncidents() throws Exception {
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        //DEBUG mode, no sense, should use this statement to start chat:
        pasSimulator.sendStartIncidentMsg(toUserName, fromUserName);
        boolean isIncidentStartMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("incidentStartMsg").toString());
        Assert.assertTrue(isIncidentStartMsgArrived ,"Text message not arrived!!!");


        pasSimulator.sendTextMessage(toUserName,fromUserName,"Problem");
        boolean isTextMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("descMsg").toString());
        Assert.assertTrue(isTextMsgArrived,"Text message not arrived!!!");

        AMedia media = MediaUtil.uploadImage("1.jpg","image/jpeg");
        pasSimulator.sendImageMessage(toUserName,fromUserName,media);

        AMedia mediaAudio = MediaUtil.uploadAudio("1.amr","audio/amr");
        pasSimulator.sendVoiceMessage(toUserName,fromUserName,mediaAudio);

        AMedia mediaVideo = MediaUtil.uploadVideo("1.mp4","video/mp4");
        pasSimulator.sendVideoMessage(toUserName,fromUserName,mediaVideo);


        pasSimulator.sendTextMessage(toUserName,fromUserName,"http://cn.bing.com");


        for(int i = 0 ;i < 19; i++) {
            pasSimulator.sendTextMessage(toUserName,fromUserName,i+"");
        }

        Sleeper.sleep(10);

        boolean isMaxEntries = pasSimulator.queryMessageToWechat(fromUserName,getProp("maxEntriesMsg").toString());

        Assert.assertTrue(isMaxEntries,"Max entries message not arrived!!!");
    }


    @TestCaseInfo(owner = "fredric", caseNo = "PAASEXTRWI-944")
    @Test(groups = {"incident","PAASEXTRWI-944"})
    public void cancelIncident() throws Exception {
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);

        //DEBUG mode, no sense, should use this statement to start chat:
        pasSimulator.sendStartIncidentMsg(toUserName, fromUserName);
        Sleeper.sleep(5);
        boolean isIncidentStartMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("incidentStartMsg").toString());
        Assert.assertTrue(isIncidentStartMsgArrived ,"Text message not arrived!!!");

        pasSimulator.sendTextMessage(toUserName,fromUserName,"Problem");
        Sleeper.sleep(5);
        boolean isTextMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("descMsg").toString());
        Assert.assertTrue(isTextMsgArrived,"Text message not arrived!!!");

        pasSimulator.sendTextMessage(toUserName,fromUserName,"Q");
        Sleeper.sleep(5);
        boolean isCancelMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("incidentCreationCancelMsg").toString());
        Assert.assertTrue(isCancelMsgArrived,"Text message not arrived!!!");


        //Re-do the same thing
        pasSimulator.sendStartIncidentMsg(toUserName, fromUserName);
        isIncidentStartMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("incidentStartMsg").toString());
        Assert.assertTrue(isIncidentStartMsgArrived ,"Text message not arrived!!!");

        pasSimulator.sendTextMessage(toUserName,fromUserName,"This is the content");
        isTextMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("descMsg").toString());
        Assert.assertTrue(isTextMsgArrived,"Text message not arrived!!!");

        pasSimulator.sendTextMessage(toUserName,fromUserName,"Q");
        isCancelMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("incidentCreationCancelMsg").toString());
        Assert.assertTrue(isCancelMsgArrived,"Text message not arrived!!!");
    }

    @Test(groups = {"incident","skip" ,"PAASEXTRWI-1935"})
    public void createIncidentOutOfBusinessHours() throws Exception {
        // Check is Business hours
        boolean IsBusinessHours = "true".equals(JConfigUtil.isAgentInWorkHour());
        if (IsBusinessHours) {
            return;
        }

        // Turn on the "creating incident" and start test
        setIfCreateIncidentInNBHAllowed(true);

        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);

        pasSimulator.sendStartChatMsg(toUserName,fromUserName);
        boolean isNBHMsgDisplayed = pasSimulator.queryMessageToWechat(fromUserName,  getProp("notBusinessHoursMsg").toString());
        Assert.assertTrue(isNBHMsgDisplayed,"Not Business Hour Message not arrived!!!");
        boolean isCreateIncidentMsg = pasSimulator.queryMessageToWechat(fromUserName,  getProp("incidentStartMsg").toString());
        Assert.assertTrue(isCreateIncidentMsg,"Create Incident Message not arrived!!!");

        pasSimulator.sendStartIncidentMsg(toUserName, fromUserName);
        pasSimulator.sendTextMessage(toUserName, fromUserName, "Turn On Incident");
        boolean isIncidentCreateMsgArrived = pasSimulator.queryMessageToWechat(fromUserName, getProp("descMsg").toString());
        Assert.assertTrue(isIncidentCreateMsgArrived, "Create Incident Message is not arrived!");
        pasSimulator.sendTextMessage(toUserName, fromUserName, "E");
        boolean isIncidentCreateSuccessMsgArrived = pasSimulator.queryMessageToWechat(fromUserName, getProp("descMsg").toString());
        Assert.assertTrue(isIncidentCreateSuccessMsgArrived, "Create Incident Success Message is not arrived!");
    }

    @TestCaseInfo(owner = "chenguang", caseNo = "PAASEXTRWI-1918")
    @Test(groups = {"incident", "PAASEXTRWI-1918"})
    public void createIncidentOutOfHoliday() throws Exception {
        // Check is Holiday
        boolean IsHoliday = "true".equals(JConfigUtil.isAgentInHoliday());
        if (!IsHoliday) {
            return;
        }

        // Turn on the "creating incident" and start test
        setIfCreateIncidentInNBHAllowed(true);

        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);

        pasSimulator.sendStartChatMsg(toUserName,fromUserName);
        Sleeper.sleep(10);
        boolean isHolidayMsgDisplayed = pasSimulator.queryMessageToWechat(fromUserName,  getProp("notBusinessHoursMsg").toString());
        Assert.assertTrue(isHolidayMsgDisplayed,"Not Business Hour Message not arrived!!!");
        boolean isCreateIncidentMsg = pasSimulator.queryMessageToWechat(fromUserName,  getProp("incidentStartMsg").toString());
        Assert.assertTrue(isCreateIncidentMsg,"Create Incident Message not arrived!!!");

        pasSimulator.sendTextMessage(toUserName, fromUserName, "Turn On Incident");
        Sleeper.sleep(10);
        boolean isIncidentCreateMsgArrived = pasSimulator.queryMessageToWechat(fromUserName, getProp("descMsg").toString());
        Assert.assertTrue(isIncidentCreateMsgArrived, "Create Incident Message is not arrived!");
        pasSimulator.sendTextMessage(toUserName, fromUserName, "E");
        Sleeper.sleep(10);
        boolean isIncidentCreateSuccessMsgArrived = pasSimulator.queryMessageToWechat(fromUserName, getProp("incidentCreatedMsg").toString());
        Assert.assertTrue(isIncidentCreateSuccessMsgArrived, "Create Incident Success Message is not arrived!");
    }

    @Test(groups = {"incident", "PAASEXTRWI-1936"}) // todo 改一下
    public void createIncidentOutOfBusinessHours_TurnOff() throws Exception {
        // Check is Business hours
        boolean IsBusinessHours = "true".equals(JConfigUtil.isAgentInWorkHour());
        if (IsBusinessHours) {
            return;
        }

        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);

        // Turn off the "creating incident" and try again
        setIfCreateIncidentInNBHAllowed(false);
        pasSimulator.sendStartChatMsg(toUserName,fromUserName);
        boolean isNBHMsgDisplayed_TurnOff = pasSimulator.queryMessageToWechat(fromUserName,  getProp("notBusinessHoursMsg").toString());
        Assert.assertTrue(isNBHMsgDisplayed_TurnOff,"Not Business Hour Message not arrived!!!");
        boolean isCreateIncidentMsg_TurnOff = pasSimulator.queryMessageToWechat(fromUserName,  getProp("incidentCreatedMsg").toString());
        Assert.assertFalse(isCreateIncidentMsg_TurnOff,"Create Incident Message is arrived!!!");
    }

    @Test(groups = {"incident", "PAASEXTRWI-1366"})
    public void createIncidentOutOfHoliday_TurnOff() throws Exception {
        // Check is Holiday
        boolean IsHoliday = "true".equals(JConfigUtil.isAgentInHoliday());
        if (!IsHoliday) {
            return;
        }

        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);

        // Turn off the "creating incident" and try again
        setIfCreateIncidentInNBHAllowed(false);
        pasSimulator.sendStartChatMsg(toUserName,fromUserName);
        Sleeper.sleep(10);
        boolean isHolidaysgDisplayed_TurnOff = pasSimulator.queryMessageToWechat(fromUserName,  getProp("notBusinessHoursMsg").toString());
        Assert.assertTrue(isHolidaysgDisplayed_TurnOff,"Not Business Hour Message not arrived!!!");
        boolean isCreateIncidentMsg_TurnOff = pasSimulator.queryMessageToWechat(fromUserName,  getProp("incidentCreatedMsg").toString());
        Assert.assertFalse(isCreateIncidentMsg_TurnOff,"Create Incident Message is arrived!!!");
    }

    @TestCaseInfo(owner = "chenguang.w.wang", caseNo = "PAASEXTRWI-1913")
    @Test(groups = {"incident"})
    public void checkIncidentInRN() throws Exception {
        String incident_test_title = "incident test title";
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartIncidentMsg(toUserName, fromUserName);
        pasSimulator.sendTextMessage(toUserName, fromUserName, incident_test_title);
        pasSimulator.sendTextMessage(toUserName, fromUserName, "incident test content");
        pasSimulator.sendTextMessage(toUserName, fromUserName, "E");

        pasSimulator.sendMenuClickEventMessage(toUserName, fromUserName,"SRV_WA");

        pasSimulator.queryMessageToWechat(fromUserName, incident_test_title);
        List<Msg> toWechatMsgs = pasSimulator.getMessageToWechat(fromUserName);
        int length = toWechatMsgs.size();
        String expect_content = "";
        for(int i = length-1; i>=0;i--) {
            if(toWechatMsgs.get(i).getContent().contains("incident test title")) {
                expect_content = toWechatMsgs.get(i).getContent();
            }
        }
        if (expect_content.isEmpty()) {
            return;
        }
        expect_content = expect_content.split("4: ")[1];
        expect_content = expect_content.split("5: ")[0];
        expect_content = expect_content.split(" incident test title")[0];

        setupCase(JConfigUtil.getBUIUrl());
        BLoginPage bLoginPage = new BLoginPage(driver, logger);
        bLoginPage.Login(BUIAccount.ACCOUNT2);

        ChatLogonBar chatLogonBar = new ChatLogonBar(driver, logger);
        chatLogonBar.getInIncidentPage();
        IncidentPage incidentPage = new IncidentPage(driver, logger);
        boolean isIncidentExist = incidentPage.isIncidentExist(expect_content);
        Assert.assertTrue(isIncidentExist, "incident is not displayed in RN");
    }
}
