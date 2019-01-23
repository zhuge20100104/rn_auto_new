package base.search.wrapper;

import base.search.engine.parser.JBy;
import base.exceptions.JBaseException;
import base.search.SearchTools;
import config.utils.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PageWrapper  {

    private  WebDriver webDriver;
    private SearchTools sTools;

    private  static final int  EXECUTE_SCRIPT_ALERT_TRY_COUNT = 8;

    public PageWrapper(WebDriver driver) {
        this.webDriver = driver;
        this.sTools = new SearchTools(webDriver);
    }


    public String getPageAsXml() {
        return this.webDriver == null ? null : this.webDriver.getPageSource();
    }

    private Document getPageDocument() {
        return this.webDriver == null ? null : Jsoup.parse(this.getPageAsXml());
    }

    private Document getPageBodyDocument() {
        if (this.webDriver == null) {
            return null;
        } else {
            Document doc = this.getPageDocument();
            if (doc == null) {
                return null;
            } else {
                doc.head().remove();
                return doc;
            }
        }
    }

    public String getVisiblePageContent() {
        if (this.webDriver == null) {
            return null;
        } else {
            Document doc = this.getPageBodyDocument();
            if (doc == null) {
                return "";
            } else {
                doc.select("*[style~=display: none]").remove();
                doc.select("*[style~=display:none]").remove();
                return doc.text();
            }
        }
    }

    public boolean isTextVisible(String text) {

        try {
            return StringUtils.simplifyText(this.getVisiblePageContent()).contains(text);
        } catch (Exception var3) {
            return false;
        }
    }



    public String getPageContent() {
        return this.webDriver == null ? null : this.getPageBodyDocument().text();
    }

    public boolean doesTextExist(String text) {
        try {
            return StringUtils.simplifyText(this.getPageContent()).contains(text);
        } catch (Exception var3) {
            return false;
        }
    }

    public String executeScript(String script) throws Exception {
        return this.executeScriptInternal(script, true);
    }

    public String executeScriptIgnoreAlert(String script) throws Exception {
        return this.executeScriptInternal(script, false);
    }

    private String executeScriptInternal(String script, boolean suppressAlerts) throws Exception{
        Object o = null;

        try {
            o = ((JavascriptExecutor)this.webDriver).executeScript(script, new Object[0]);
            if (suppressAlerts) {
                AlertWrapper alertWrapper = new AlertWrapper(webDriver,null);
                alertWrapper.handleAlert(EXECUTE_SCRIPT_ALERT_TRY_COUNT);
            }
        } catch (UnhandledAlertException ex) {
            return this.executeScriptInternal(script, suppressAlerts);
        } catch (Exception ex1) {
            throw new JBaseException("Error while executing javascript command ['" + script + "'],"+ex1.getMessage());
        }

        if (o == null) {
            return null;
        } else if (o instanceof WebElement) {
            return o.toString();
        } else {
            return o instanceof List ? StringUtils.arrayToString(((List)o).toArray()) : o.toString();
        }
    }


    public void waitForNSPageToLoad() {
        WebDriverWait wait = new WebDriverWait(this.webDriver, (long)60);

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//meta[@http-equiv='REFRESH']")));
        } catch (UnhandledAlertException var6) {
            AlertWrapper alertWrapper = new AlertWrapper(webDriver,null);
            alertWrapper.handleAlert(8);
        }
    }


    public  void mouseOver(JBy by) throws Exception{
        Actions builder = new Actions(webDriver);
        WebElement ele = this.sTools.findElement(by.getType(),by.getValue());
        builder.moveToElement(ele).build().perform();
    }
}
