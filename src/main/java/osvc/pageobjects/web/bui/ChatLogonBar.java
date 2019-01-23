package osvc.pageobjects.web.bui;

import base.BaseWebComponent;
import base.search.SearchTools;

import base.search.engine.wait.utils.Sleeper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class ChatLogonBar extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    public ChatLogonBar(WebDriver driver, Logger logger) throws Exception{
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
    }

    private final String Menu_Contacts = "Contacts";
    private final String Menu_Incidents = "Incidents";

    public void logonChat() throws Exception {
//        WebElement element = sTools.find("button","id=chatMediaBar_requestBtn");
//        if(element.getAttribute("value").equals("Request Chat")) {
//            element.click();
//        }


        sTools.click("button","id=chatMediaBar_requestBtn");


//        sTools.wait("button","id=chatMediaBar_requestBtn");
//        while(driver.findElement(By.id("chatMediaBar_requestBtn")).getAttribute("value").equals("Request Chat")) {
//            driver.findElement(By.id("chatMediaBar_requestBtn")).click();
//            Sleeper.sleep(0.3f);
//        }

    }

    public void logonOutChat() throws Exception {
        WebElement element = sTools.find("button","id=chatMediaBar_requestBtn");
        if(element.getAttribute("value").equals("Cancel Request")) {
            element.click();
        }
    }

    public void acceptAlert()  throws  Exception{
        sTools.handleAlert(25);
    }

    public void clickMenuBtn() throws Exception {
        sTools.click("button", "id=navigationSetBtn");
    }

    private boolean isMenuExtend(String MenuItem) throws Exception {

        WebElement ele = sTools.findChild("div","class*=navigation-heading|class*=oj-collapsible-header|text="+MenuItem,
                "a","role=button");
        return Boolean.valueOf(ele.getAttribute("aria-expanded"));
    }

    public void getInContactPage() throws Exception {
        clickMenuBtn();
        if (!isMenuExtend(Menu_Contacts)) {
            sTools.click("div", "class*=navigation-heading|class*=oj-collapsible-header|text="+Menu_Contacts);
            sTools.click("span","text=Contact Search");
        } else {
            sTools.click("span","text=Contact Search");
        }
    }

    public void getInIncidentPage() throws Exception {
        clickMenuBtn();
        if (!isMenuExtend(Menu_Incidents)) {
            sTools.click("div", "id=" + "oj-collapsible-7-header");
            sTools.click("span", "text=Incidents by Contact");
        } else {
            sTools.click("span", "text=Incidents by Contact");
        }
    }
}
