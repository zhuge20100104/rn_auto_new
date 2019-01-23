package osvc.pageobjects.web.WeChatConfig;

import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class WeChatChannelMarkPage extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    public WeChatChannelMarkPage(WebDriver driver,  Logger logger) throws Exception {
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
    }


    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","done",
                        "isFirstTime",
                        "save"
                },

                {"zh_CN","完成",
                        "请确保字段配置为对交谈可见",
                        "保存"

                },

                {"en_US","Done",
                        "Please make sure the field is visible for chat.",
                        "Save"
                }
        };
        return languages;
    }

    public void clickExpandSubListBtn() throws Exception {

        sTools.click("a", "aria-label=expand");
    }

    public void chooseSubList(String ChosenName) throws Exception {
        sTools.click("div", "text=" + ChosenName);
    }

    public void inputValueForWeChatCase(String Value) throws Exception {
        sTools.find("textarea", "aria-label=channelValue").clear();
        sTools.find("textarea", "aria-label=channelValue").sendKeys(Value);
    }

    public boolean isFirstTime() {
        try {
            return sTools.waitForTextWeb(getProp("isFirstTime").toString());
        }catch (Exception ex) {
            return false;
        }
    }

    public void clickDone() throws Exception {
        sTools.click("span", "text="+getProp("done"));
    }


    public void clickEditButton() throws Exception{
        sTools.click("button","title=Edit");
    }

    public void clickSave() throws Exception {
        sTools.click("span", "text="+getProp("save"));
    }
}
