package osvc.pageobjects.web.bui;

import base.BaseWebComponent;
import base.search.SearchTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class IncidentPage extends BaseWebComponent {

    private SearchTools sTools;
    private Logger logger;

    /**
     * Construct of base web component
     *
     * @param driver The selenium web driver
     * @throws Exception This method may throws exceptions
     */
    public IncidentPage(WebDriver driver, Logger logger) throws Exception {
        super(driver);
        this.logger = logger;
        this.sTools = new SearchTools(driver);
    }

//    public WebElement findIncidentReference(String ExpectReference) throws Exception {
//        List<WebElement> References = sTools.findAllClass("span[title^='Reference']");
//        for (WebElement eachReference : References) {
//            if (eachReference.getAttribute("title").contains(ExpectReference)) {
//                return eachReference;
//            }
//        }
//        return null;
//    }

    public boolean isIncidentExist(String ExpectReference) throws Exception {
        List<WebElement> References = sTools.findAll("span", "title^=Reference");
        for (WebElement eachReference : References) {
            if (eachReference.getAttribute("title").contains(ExpectReference)) {
                return true;
            }
        }
        return false;
    }
}
