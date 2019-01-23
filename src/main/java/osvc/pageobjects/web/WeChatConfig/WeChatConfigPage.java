package osvc.pageobjects.web.WeChatConfig;

import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class WeChatConfigPage extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    private WebDriver driver;
    public WeChatConfigPage(WebDriver driver, Logger logger) throws Exception {
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
        this.driver = driver;
    }


    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","channelMark",
                        "credentials",
                        "menuSetting",
                        "autoReply"
                },

                {"zh_CN","渠道标识",
                        "服务号集成配置",
                        "菜单设置",
                        "自动回复"
                },

                {"en_US","Channel Mark",
                        "Credentials",
                        "Menu Setting",
                        "Auto Reply"
                }
        };
        return languages;
    }


    private List<String> getAllAccountCropIds() {
        List<WebElement> CropIdsEle = driver.findElements(By.cssSelector("div#crop-id"));
        List<String> CropIds  = new ArrayList<>();
        for (WebElement eachCropIdsEle : CropIdsEle) {
            CropIds.add(eachCropIdsEle.getText());
        }
        return CropIds;
    }

    private List<String> getAllAccountNames() {
        List<WebElement> AccountNamesEle = driver.findElements(By.cssSelector("div#account-name"));
        List<String> AccountNames  = new ArrayList<>();
        for (WebElement eachAccountNamesEle : AccountNamesEle) {
            AccountNames.add(eachAccountNamesEle.getText());
        }
        return AccountNames;
    }

    private boolean isAccountCropIdDisplayed(String ExpectCropId) {
        List<String> CropIds = getAllAccountCropIds();
        for (String eachCropId : CropIds) {
            if (eachCropId.equals(ExpectCropId))
                return true;
        }
        return false;
    }

    private boolean isAccountNameDisplayed(String ExpectAccountName) {
        List<String> AccountNames = getAllAccountNames();
        for (String eachAccountName : AccountNames) {
            if (eachAccountName.equals(ExpectAccountName))
                return true;
        }
        return false;
    }

    public void clickAddAccountBtn() throws Exception {
        sTools.click("div", "id=add-icon");
    }

    private void clickNextAccountBtn() throws Exception {
        sTools.clickChild("div", "class*=account-list", "span", "class*=oj-end", false);
    }

    private void clickPreviousAccountBtn() throws Exception {
        sTools.clickChild("div", "class*=account-list", "span", "class*=oj-start", false);
    }

    public void chooseAccountWithCropId(String CropId) throws Exception {
        sTools.find("div", "class=crop-id").click();
    }

    public void
    chooseAccountWithAccountName(String AccountName) throws Exception {

        String endIndicatorClass = "class*=oj-enabled|class*=oj-enabled|class*=oj-end|class*=oj-default";   // todo 1 多个
        String accountNameClass = "class=account-name|text=" + AccountName;

        while(sTools.find("div",endIndicatorClass)!=null) {
            if(sTools.find("div",accountNameClass) != null) {
                break;
            }else {
                sTools.click("div",endIndicatorClass);
            }
        }

        sTools.click("div",accountNameClass);
    }

    public void getInCredentialsTab() throws Exception {
        sTools.find("a", "class=oj-tabs-gen-a oj-tabs-anchor|text="+getProp("credentials")).click();
    }

    public void getInAutoReplyTab() throws Exception {
        sTools.find("a", "class=oj-tabs-gen-a oj-tabs-anchor|text="+getProp("autoReply")).click();
    }

    public void getInMenuSettingTab() throws Exception {
        sTools.find("a", "class=oj-tabs-gen-a oj-tabs-anchor|text="+getProp("menuSetting")).click();
    }

    public void getInChannelMarkTab() throws Exception {
        sTools.find("a", "class=oj-tabs-gen-a oj-tabs-anchor|text="+getProp("channelMark")).click();
    }
}
