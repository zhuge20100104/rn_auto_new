package osvc.pageobjects.web.bui;

import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.JConfigUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.jar.JarEntry;

public class ContactPage extends BaseWebComponent {
    /**
     * Construct of base web component
     *
     * @param driver The selenium web driver
     * @throws Exception This method may throws exceptions
     */
    private SearchTools sTools;
    private Logger logger;
    public ContactPage(WebDriver driver, Logger logger) throws Exception {
        super(driver);
        this.logger = logger;
        this.sTools = new SearchTools(driver);
    }

    public void searchContactWithFirstName(String FirstName) throws Exception {
        WebElement input_firstname = sTools.find("input", "automationid=Contact First Name");
        input_firstname.clear();
        input_firstname.sendKeys(FirstName);
        sTools.clickByCss("button[data-bind*='title:searchButtonLabel']");
        sTools.click("span","class*=recordCommandLink|text=Open");
    }

    private WebElement findTab(String ExceptTabName) throws Exception {
        List<WebElement> tab_list = sTools.findAllCss("#primaryTabsetContainer li");
        for (WebElement tab : tab_list) {
            String text = tab.findElement(By.cssSelector("span.tab-text")).getText();
            if (ExceptTabName.equals(text)) {
                return tab;
            }
        }
        return null;
    }

    public String getContactIdFrom_AutomationA() throws Exception {
        String FirstName = JConfigUtil.getMergeContactFirstName();
        String LastName = JConfigUtil.getMergeContactLasttName();
        String Phone = JConfigUtil.getMergeContactPhone();
        String Email = JConfigUtil.getMergeContactEmail();

        sTools.waitAndSendKeys("input","automationid=Contact First Name",FirstName);
        sTools.waitAndSendKeys("input","automationid=Contact Last Name",LastName);
        sTools.waitAndSendKeys("input","automationid=Phone",Phone);
        sTools.waitAndSendKeys("input","automationid=Email",Email);

        sTools.click("span","class=oj-button-text|text=Search");
        sTools.click("span","class*=recordCommandLink|text=Open");

        WebElement contactPane = sTools.find("div","data-recordtype=Contact");

        String A_ContactID = contactPane.getAttribute("data-recordid");
        return A_ContactID;
    }

    public void inputPrimaryContactId(String PrimaryContactId) throws Exception {
        findTab("Contact Search").click();
        sTools.find("input","automationid=Contact Last Name").clear();
        sTools.find("input","automationid=Phone").clear();
        sTools.find("input","automationid=Email").clear();
        searchContactWithFirstName(JConfigUtil.getWechatUserOpenId1());

        sTools.find("input", "automationid$=PrimaryContactId").clear();
        sTools.find("input", "automationid$=PrimaryContactId").sendKeys(PrimaryContactId);

        List<WebElement> save_btns = sTools.findAll("button", "title=Save the current record. The record will remain open.");
        for (WebElement eachSaveBtn : save_btns) {
            try {
                eachSaveBtn.click();
            } catch (NoSuchElementException e) {
                logger.error(e);
            }
        }
    }


}
