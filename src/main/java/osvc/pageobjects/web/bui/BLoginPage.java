package osvc.pageobjects.web.bui;

import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utils.JConfigUtil;

public class BLoginPage extends BaseWebComponent{
    private SearchTools sTools;
    private Logger logger;
    public BLoginPage(WebDriver driver, Logger logger) throws Exception{
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
    }

    public void Login(String account) throws Exception{
        String userName = JConfigUtil.getBUIUserName(account);
        sTools.waitAndSendKeys("id=username",userName);
        logger.info("Enter keys in user name field");

        String password = JConfigUtil.getBUIPassword(account);
        sTools.waitAndSendKeys("id=password",password);

        logger.info("Enter keys in password field");

        sTools.click("id=loginbutton");

        logger.info("click the login button");
    }
}
