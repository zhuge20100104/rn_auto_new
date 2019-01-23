package osvc.pageobjects.web.bui;

import base.BaseWebComponent;
import base.search.SearchTools;
import base.search.engine.wait.utils.Sleeper;
import base.utils.http.OkHttpUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.JConfigUtil;

public class BUnRegisterPage extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    private ChatNaviBar chatNaviBar;
    public BUnRegisterPage(WebDriver driver, Logger logger) throws Exception{
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
        chatNaviBar = new ChatNaviBar(driver,logger);
    }

    public void unRegisterUserInBuiByPhone(String wechatId, String phoneNumber) throws Exception{
        chatNaviBar.naviToContactSearch();
        phoneNumber = "+86" + phoneNumber;
        sTools.waitAndSendKeys("input","automationid=Phone",phoneNumber);
        sTools.clickByIndex("button","text=Search",0);

        Sleeper.sleep(6);

        WebElement deleteRegBtn = null;
        try{
            deleteRegBtn = sTools.findChild(null,"class*=oj-datagrid-databody","span","text=Delete",false);//sTools.getElementInParent(get("regDeleteBtn"));
        }catch (Exception ex) {

        }

        if(deleteRegBtn != null) { ;
            deleteRegBtn.click();
            sTools.click("button","automationid=Yes");
            //Confirm delete dialog was handle in WebDivAlertWatcher
            String unRegistrationURL = JConfigUtil.getUserRegisterUnregistrationURL();
            OkHttpUtil.httpDelete(unRegistrationURL+wechatId);
        }
    }


    public void unRegisterUserInBuiByContactName(String wechatId, String contactName) throws Exception{
//        chatNaviBar.naviToContactSearch();
//
//        sTools.waitAndSendKeys("input","automationid=Contact First Name",contactName);
//        sTools.click(get("regSearchBtn"));
//
//        Sleeper.sleep(6);
//
//        WebElement deleteRegBtn = null;
//        try{
//            deleteRegBtn = sTools.getElementInParent(get("regDeleteBtn"));
//        }catch (Exception ex) {
//
//        }
//
//        if(deleteRegBtn != null ) {
//            deleteRegBtn.click();
//            sTools.click(get("deleteYesBtn"));
//            //Confirm delete dialog was handle in WebDivAlertWatcher
//            String unRegistrationURL = JConfigUtil.getUserRegisterUnregistrationURL();
//            OkHttpUtil.httpDelete(unRegistrationURL+wechatId);
//        }

    }
}
