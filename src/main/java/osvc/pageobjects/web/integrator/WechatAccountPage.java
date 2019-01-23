package osvc.pageobjects.web.integrator;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import base.BaseWebComponent;
import base.search.SearchTools;

public class WechatAccountPage extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    public WechatAccountPage(WebDriver driver, Logger logger) throws Exception{
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
    }

    public void editAccount() throws Exception {
//        logger.info("Start edit the wechat account");
//        sTools.click(get("account_edit"));
//
//        logger.info("Edit the account name");
//        sTools.waitAndSendKeys(get("official_account_name"),"Fredric_And_Chenguang");
//
//        logger.info("Test the current official account");
//        sTools.click(get("test_button"));
//        //Sleep for the save button to be clickable
//        Sleeper.sleep(10);
//
//        logger.info("Save changes");
//        sTools.click(get("save_button"));
//        //Sleep for integrator to save the changes
//        Sleeper.sleep(10);
    }


}
