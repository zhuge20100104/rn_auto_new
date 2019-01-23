package osvc.pageobjects.mobile.wechat;

import base.BaseMobileComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utils.JConfigUtil;

public class ChatMenuBar extends BaseMobileComponent {
    private SearchTools sTools;
    private Logger logger;
    private WebDriver driver;
    public ChatMenuBar(WebDriver driver,Logger logger) throws Exception{
        sTools = new SearchTools(driver);
        this.driver = driver;
        this.logger = logger;
    }



    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","comments",
                        "service",
                        "send"
                },

                {"zh_CN","消息",
                        "服务按钮",
                        "发送"
                },

                {"en_US","Comments",
                        "Service",
                        "Send"
                }
        };
        return languages;
    }



    public void clickAgent() throws Exception {
        String wechatAgentStr = JConfigUtil.getWechatLayoutLeftMenu();
        sTools.clickByUi("text="+wechatAgentStr);
    }


    public void clickRegistration() throws Exception {
        String  registrationStr = JConfigUtil.getWechatLayoutRightMenu();
        sTools.click("text="+registrationStr);
    }

    public void switchToEdit() throws Exception {
        sTools.clickByUi("desc="+getProp("comments"));
    }

    public void switchToServices() throws  Exception{
        sTools.clickByUi("desc="+getProp("service"));
    }

    public void sendMessage(String message) throws Exception {
        sTools.waitAndSendKeysUi("res=com.tencent.mm:id/aie",message);
        sTools.clickByUi("text="+getProp("send"));
    }


}
