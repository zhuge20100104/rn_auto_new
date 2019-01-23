package osvc.tests.integrator.web.WeChatConfig;

import base.BaseWebTestCase;
import base.annotations.TestCaseInfo;
import base.search.engine.wait.utils.Sleeper;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.WeChatConfig.AutoReplyPage;
import osvc.pageobjects.web.WeChatConfig.WeChatConfigPage;
import osvc.pageobjects.web.integrator.HomePage;
import osvc.pageobjects.web.integrator.LoginPage;
import osvc.simulators.IWechatSimulator;
import osvc.simulators.SimType;
import osvc.simulators.SimulatorFactory;
import osvc.simulators.beans.Msg;
import utils.JConfigUtil;

import java.util.List;

public class AutoReplyTest extends BaseWebTestCase {
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

    @BeforeMethod
    public  void setup() throws Exception{
        changeChatLanguage();
        String identityDomain = JConfigUtil.getAdminConsoleIdentityDomain();
        String serviceName = JConfigUtil.getAdminConsoleServiceName();
        String url = "https://"+serviceName+"-"+identityDomain+".java.us2.oraclecloudapps.com/rnwechatjetadmin/index.html?";
        setupCase(url);
        LoginPage loginPage = new LoginPage(driver, logger);
        loginPage.Login();
        HomePage homePage = new HomePage(driver, logger);
        homePage.goToWechatPage();
    }


    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","disabled",
                        "chatQueueMsg",
                        "chatQueueMsgHead",
                        "removedText",
                        "ChatQueueTitle"
                },

                {"zh_CN","不启用",
                        "您当前排在第{position}位[, 预计等待时间为{wait_time}秒]",
                        "您当前排在第",
                        "我们将尽快为你提供服务",
                        "聊天队列提示"
                },

                {"en_US","Disabled",
                        "You are {position} in the queue[, the estimated waiting time is {wait_time} seconds]",
                        "in the queue",
                        "we will soon provide you with services",
                        "Chat queue"
                }
        };
        return languages;
    }

    private void rollBackChatQueueMsg() throws Exception {
        AutoReplyPage autoReplyPage = new AutoReplyPage(driver, logger);
        autoReplyPage.getInChatMessage();
        autoReplyPage.rollBackMsgToDefault(getProp("ChatQueueTitle").toString());
    }

    @TestCaseInfo(owner = "chenguang", caseNo = "PAASEXTRWI-940, PAASEXTRWI-1539")
    @Test
    public void ChangePositionMsgAndCheckOnMobile() throws Exception {
        WeChatConfigPage weChatConfigPage = new WeChatConfigPage(driver, logger);
        weChatConfigPage.chooseAccountWithAccountName(JConfigUtil.getTestAccountAccount1Name());
        weChatConfigPage.getInAutoReplyTab();
        AutoReplyPage autoReplyPage = new AutoReplyPage(driver, logger);
        autoReplyPage.getInChatMessage();
        autoReplyPage.editCardContent(getProp("ChatQueueTitle").toString(), getProp("chatQueueMsg").toString());

        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        pasSimulator.sendStartChatMsg(toUserName, fromUserName);
        Sleeper.sleep(10);
        List<Msg> msgs = pasSimulator.getMessageToWechat(fromUserName);
        String content = msgs.get(msgs.size() - 1).getContent();
        boolean isMsgRight = content.contains(getProp("chatQueueMsgHead").toString());
        boolean isRemovedTextDisplayed = content.contains(getProp("removedText").toString());
        Assert.assertTrue(isMsgRight);
        Assert.assertFalse(isRemovedTextDisplayed);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        rollBackChatQueueMsg();
        super.teardown();
    }
}
