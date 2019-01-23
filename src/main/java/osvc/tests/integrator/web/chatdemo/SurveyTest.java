package osvc.tests.integrator.web.chatdemo;

import base.BaseWebTestCase;
import base.annotations.TestCaseInfo;
import base.search.engine.wait.utils.Sleeper;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.LogConfig.LogPage;
import osvc.pageobjects.web.WeChatConfig.AutoReplyPage;
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
import osvc.simulators.beans.Msg;
import utils.BUIAccount;
import utils.JConfigUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import static com.oracle.jrockit.jfr.ContentType.Timestamp;

public class SurveyTest extends BaseWebTestCase {

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
        bLoginPage.Login(BUIAccount.ACCOUNT2);

        ChatLogonBar logonBar = new ChatLogonBar(driver,logger);
        Thread.sleep(10*1000);
        logonBar.logonChat();

        //Accept chat from BUI
        ChatAcceptDialog acceptDialog = new ChatAcceptDialog(driver,logger);
        acceptDialog.acceptChat();
    }

    @BeforeMethod
    public  void setup() throws Exception{
//        changeChatLanguage();
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
                        "idleTimeoutMsg"
                },

                {"zh_CN", "您的请求已提交，请耐心等待。",
                        "我们将尽快为你提供服务。",
                        "请描述您的问题。(最多录入20条。输入\"Q\"随时退出创建 ，输入\"E\"结束录入)",
                        "已达最大记录数，请您稍事休息。我们的座席人员将稍后为您服务。",
                        "感谢您的反馈，已为您成功创建系统记录。事件号为",
                        "您当前处于交谈中。回复 Y 结束当前交谈并发起新的请求。",
                        "AaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssgggAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrLkkk",
                        "p",
                        "输入超时，请重试。",
                        "会话结束：队列等待超时。",
                        "会话结束：超时未响应。"
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
                        "Chat end: Idle time out."
                }
        };
        return languages;
    }



    @TestCaseInfo(owner = "chenguang.w.wang", caseNo = "PAASEXTRWI-1852")
    @Test(groups = {"survey","skip"})
    public void receiveAndGetInSurvey() throws Exception {
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);

        pasSimulator.sendStartChatMsg(toUserName, fromUserName);
        startAndAcceptChat();
        ChatPage chatPage = new ChatPage(driver, logger);
        chatPage.makeIncidentInChatTobeSolved();
        Thread.sleep(5000);
        stopBrowser();

        Thread.sleep(30*1000);
        List<String> urls = pasSimulator.queryTemplateMsgToWechat(fromUserName);
        Assert.assertNotEquals(urls.size(), 0,"Can't get survey message!!!");
    }


    @TestCaseInfo(owner = "chenguang.w.wang", caseNo = "PAASEXTRWI-801")
    @Test(groups = {"survey","skip"})
    public void checkSurveyLogs() throws Exception {
        receiveAndGetInSurvey();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        long lts = ts.getTime();

        String identityDomain = JConfigUtil.getAdminConsoleIdentityDomain();
        String serviceName = JConfigUtil.getAdminConsoleServiceName();
        String url = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?";
        setupCase(url);
        LoginPage loginPage = new LoginPage(driver, logger);
        loginPage.Login();
        HomePage homePage = new HomePage(driver, logger);
        homePage.goToLogPage();
        LogPage logPage = new LogPage(driver, logger);
        boolean isContainsCurrentTime = logPage.isContainsCurrentTime(lts);
        stopBrowser();

        Assert.assertTrue(isContainsCurrentTime, "There is no such time in log page");
    }

    @AfterMethod
    public void teardown() throws Exception{
        super.teardown();
    }
}
