package osvc.pageobjects.web.integrator;


import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utils.JConfigUtil;

public class LoginPage extends BaseWebComponent {

    private SearchTools sTools;
    private Logger logger;
    public LoginPage(WebDriver driver, Logger logger) throws Exception{
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
    }

    public void Login() throws Exception{
        String userName = JConfigUtil.getAdminConsoleUsername();
        sTools.waitAndSendKeys("id=username",userName);
        logger.info("Enter keys in user name field");

        String password = JConfigUtil.getAdminConsolePassword();
        sTools.waitAndSendKeys("id=password",password);
        logger.info("Enter keys in password field");

        sTools.click("button","id=signin");
        logger.info("click the login button");
        // refresh the current page to ensure full load of elements
        logger.info("Refresh the current page");
        sTools.refreshPage();
    }
}
