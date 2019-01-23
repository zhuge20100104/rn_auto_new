package osvc.pageobjects.web.bui;

import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class ChatNaviBar extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    public ChatNaviBar(WebDriver driver, Logger logger) throws Exception{
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
    }

    public void naviToIncidentsByContacts() throws Exception{
//        sTools.click(get("naviSetBtn"));
//        WebElement incidentEle = sTools.findElementAmongElements(get("incidentBtn"));
//        incidentEle.click();
//        sTools.click(get("incidentByContact"));
    }


    public void naviToContactSearch() throws Exception {
//        sTools.click(get("naviSetBtn"));
//        WebElement contactEle = sTools.find("div","class*=navigation-group-set-root|class*=navigation-group-set|text=Contacts");//sTools.findElementAmongElements(get("contactBtn"));
//        contactEle.click();
//        sTools.click(get("contactSearch"));
    }

    public void deleteLastIncident() throws Exception{
        //Wait for the current page to be loaded
//        sTools.waitForEnabled(get("deleteIncidentBtn"));
//        WebElement deleteBtn0 = sTools.findByIndex("span","title=Delete",0);//sTools.findByIndex(get("deleteIncidentBtn"),0);
//        deleteBtn0.click();
//        sTools.click(get("deleteConfirmBtn"));
    }

}
