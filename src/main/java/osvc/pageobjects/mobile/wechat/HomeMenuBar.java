package osvc.pageobjects.mobile.wechat;

import base.BaseMobileComponent;
import base.search.SearchTools;
import base.search.engine.wait.utils.Sleeper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class HomeMenuBar extends BaseMobileComponent {
    private SearchTools sTools;
    private Logger logger;
    private WebDriver driver;
    public HomeMenuBar(WebDriver driver,Logger logger) throws Exception{
        sTools = new SearchTools(driver);
        this.driver = driver;
        this.logger = logger;
    }


    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","search",
                        "searchEdit",
                        "chatInfo",
                        "settings",
                        "clearHistory",
                        "confirmClearHistory",
                        "back"
                },

                {"zh_CN","搜索",
                        "搜索",
                        "聊天信息",
                        "设置",
                        "清空内容",
                        "清空内容",
                        "返回"
                },

                {"en_US","Search",
                        "Search",
                        "Chat Info",
                        "Settings",
                        "Clear History",
                        "Clear History",
                        "Back"

                }
        };
        return languages;
    }


    public void findWechatAccount(String account) throws Exception {
        Sleeper.sleep(4);
        boolean isLaunchActivity = sTools.waitForActivity("com.tencent.mm/.ui.LauncherUI",1000,6);
        if(!isLaunchActivity) {
            sTools.backToLastActivity();
        }

        Sleeper.sleep(3);

        sTools.clickByUi("desc="+getProp("search"));
        sTools.waitAndSendKeysUi("text="+getProp("searchEdit"),account);
        sTools.findAllElement("ui","text*="+account).get(1).click();
    }

    public void clearHistory() throws Exception {
        sTools.clickByUi("desc="+getProp("chatInfo"));
        sTools.clickByUi("res=com.tencent.mm:id/j1");
        sTools.clickByUi("text="+getProp("settings"));
        sTools.clickByUi("text="+getProp("clearHistory"));
        sTools.clickByUi("text="+getProp("confirmClearHistory"));

        sTools.clickByUi("desc="+getProp("back"));
        sTools.clickByUi("desc="+getProp("back"));
    }



}
