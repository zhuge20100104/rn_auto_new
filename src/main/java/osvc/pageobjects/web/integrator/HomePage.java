package osvc.pageobjects.web.integrator;

import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class HomePage extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    public HomePage(WebDriver driver, Logger logger) throws Exception{
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
    }

    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","serviceCloudLink",
                        "wechatHomeWelcome",
                        "smsConnection",
                        "ssoPage",
                        "wechatPage",
                        "routeMenuPage",
                        "systemParametersPage",
                        "logPage"
                        },

                {"zh_CN","服务云连接配置",
                        "欢迎使用Oracle Service Cloud-微信集成管理平台！",
                        "SMS连接配置",
                        "单点登录配置",
                        "微信账户配置",
                        "聊天路由菜单设置",
                        "参数设置",
                        "日志"
                       },

                {"en_US","Service Cloud Connection",
                        "Welcome to Service Cloud - WeChat Administration Console!",
                        "SMS Connection",
                        "Single Sign-On",
                        "WeChat Account",
                        "Chat Routing Menu",
                        "System Parameters",
                        "Log"
                     }
        };
        return languages;
    }


    public void goToWechatPage()  throws Exception{
        logger.info("Go into the wechat account page");
        sTools.click("a","class=demo-footer-global-link|text="+getProp("wechatPage"));
    }

    public void goToRnConnPage() throws Exception {
        sTools.click("a","class=demo-footer-global-link|text="+getProp("serviceCloudLink"));
    }

    public void goToSSOPage() throws Exception {
        sTools.click("a","class=demo-footer-global-link|text="+getProp("ssoPage"));
    }

    public void goToSMSPage() throws Exception {
        sTools.click("a","class=demo-footer-global-link|text="+getProp("smsConnection"));
    }

    public void gotoParameterPage() throws Exception {
        sTools.click("a", "class=demo-footer-global-link|text="+getProp("systemParametersPage"));
    }

    public void goToRoutingSettingPage() throws Exception {
        sTools.click("a","class=demo-footer-global-link|text="+getProp("routeMenuPage"));
    }

    public void goToLogPage() throws Exception {
        sTools.click("a","class=demo-footer-global-link|text="+getProp("logPage"));
    }

    public boolean checkIsHomePageDisplayed() throws Exception {
        String TitleMsg = sTools.find("span", "class=title").getText();
        return TitleMsg.equals(getProp("wechatHomeWelcome"));
    }
}
