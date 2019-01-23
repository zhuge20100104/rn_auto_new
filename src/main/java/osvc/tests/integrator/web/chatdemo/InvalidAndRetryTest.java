package osvc.tests.integrator.web.chatdemo;

import base.BaseWebTestCase;
import base.annotations.TestCaseInfo;
import base.search.engine.wait.utils.Sleeper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import osvc.pageobjects.web.WeChatConfig.AutoReplyPage;
import osvc.pageobjects.web.WeChatConfig.WeChatConfigPage;
import osvc.pageobjects.web.integrator.HomePage;
import osvc.pageobjects.web.integrator.LoginPage;
import osvc.simulators.IWechatSimulator;
import osvc.simulators.SimType;
import osvc.simulators.SimulatorFactory;
import utils.JConfigUtil;

public class InvalidAndRetryTest extends BaseWebTestCase {

    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys", "incidentStartMsg",
                        "invalidOptionMsg",
                        "maxNumberRetry"
                },

                {"zh_CN", "您的意见对我们非常重要，我们将会记录您的反馈并及时答复。请先输入事件的标题。(输入\"Q\"随时退出创建 )",
                        "您的输入无效，请根据提示输入相应的编号。",
                        "已达到最大重试次数，当前服务终止。"
                },

                {"en_US", "Your request is important to us. We will create an incident to record your request. Please input the subject. (Enter \"Q\" to quite the process)",
                        "Invalid input. Please enter the correct number.",
                        "Service terminated due to the maximum number of retry reached."

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


    @BeforeMethod
    public void setup() throws Exception {
        changeChatLanguage();
    }

    @TestCaseInfo(owner = "fredric", caseNo = "PAASEXTRWI-1579")
    @Test(groups = {"incident","PAASEXTRWI-1579"})
    public void invalidAndRetryTest() throws Exception {

        //Pas wechat simulator send start chat message
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        //DEBUG mode, no sense, should use this statement to start chat:
        pasSimulator.sendMenuClickEventMessage(toUserName, fromUserName,"SRV_WA");
        pasSimulator.sendTextMessage(toUserName, fromUserName,"Y");

        //Not a  valid message
        for(int i=0;i<4;i++) {
            pasSimulator.sendTextMessage(toUserName, fromUserName, "hehe");
            if (i == 0) {
                Sleeper.sleep(10);
            }
            boolean isInvalidMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("invalidOptionMsg").toString());
            Assert.assertTrue(isInvalidMsgArrived ,"Invalid message not arrived!!!");
        }


//        boolean isInvalidMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("invalidOptionMsg").toString());
//        Assert.assertTrue(isInvalidMsgArrived ,"Invalid message not arrived!!!");



        //Send a valid option, start incident
        pasSimulator.sendTextMessage(toUserName, fromUserName, "3");

        //Check incident start message arrived
        boolean isIncidentStartMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("incidentStartMsg").toString());
        Assert.assertTrue(isIncidentStartMsgArrived ,"Text message not arrived!!!");
    }

    @TestCaseInfo(owner = "fredric", caseNo = "PAASEXTRWI-1943")
    @Test(groups = {"incident","PAASEXTRWI-1943"})
    public void invalidMaxRetryCount() throws Exception {

        //Pas wechat simulator send start chat message
        String toUserName = JConfigUtil.getWechatSubscriptionNumber();
        String fromUserName = JConfigUtil.getWechatUserOpenId1();
        IWechatSimulator pasSimulator = SimulatorFactory.getSimulator(SimType.PAS);
        //DEBUG mode, no sense, should use this statement to start chat:
        pasSimulator.sendMenuClickEventMessage(toUserName, fromUserName,"SRV_WA");
        pasSimulator.sendTextMessage(toUserName, fromUserName,"Y");

        //Not a  valid message
        for(int i=0;i<5;i++) {
            pasSimulator.sendTextMessage(toUserName, fromUserName, "hehe");
        }

        Sleeper.sleep(10);

        boolean isInvalidMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("maxNumberRetry").toString());
        Assert.assertTrue(isInvalidMsgArrived ,"Max retry message not arrived!!!");

        pasSimulator.sendStartIncidentMsg(toUserName,fromUserName);
        //Check incident start message arrived
        boolean isIncidentStartMsgArrived = pasSimulator.queryMessageToWechat(fromUserName,getProp("incidentStartMsg").toString());
        Assert.assertTrue(isIncidentStartMsgArrived ,"Incident Start message not arrived!!!");
    }
}
