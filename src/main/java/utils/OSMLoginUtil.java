package utils;


import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class OSMLoginUtil {
    WebDriver driver;
    Logger logger;
    private SearchTools sTools;

    public OSMLoginUtil(WebDriver driver, Logger logger) {
        this.driver = driver;
        this.logger = logger;
        sTools = new SearchTools(driver);
    }

    public void login() throws Exception {
        String username = JConfigUtil.getOSMAdminUIUsername();
        String password = JConfigUtil.getOSMAdminUIPassword();

//        JBy OracleLogo = new JBy.Builder().setLocator("id=logo").setTimeout(60).build();

//        sTools.find(new JBy.Builder().setLocator("id=logo").build());

        sTools.find("id=username").clear();
        sTools.find("id=username").sendKeys(username);
        sTools.find("id=password").clear();
        sTools.find("id=password").sendKeys(password);
        sTools.click("id=loginbutton");

//        sTools.findElementInIFrame(new JBy.Builder().setLocator("css=iframe#oms1[src='about:blank']").setTimeout(60).build(), new JBy.Builder().setLocator("css=h2").build()).isDisplayed();

//        if ("Channels".equals(sTools.find(new JBy.Builder().setLocator("css=h2").build()).getText())){
//            driver.navigate().refresh();
//        }

//        sTools.wait(OracleLogo);

//        sTools.waitAndSendKeys(new JBy.Builder().setLocator("id=username").setTimeout(60).build(), username);
//        sTools.waitAndSendKeys(new JBy.Builder().setLocator("id=password").setTimeout(60).build(), password);
//        sTools.click(new JBy.Builder().setLocator("id=loginbutton").setTimeout(60).build());
    }
}
