package osvc.pageobjects.web.bui;

import base.BaseWebComponent;
import base.search.SearchTools;
import base.search.engine.wait.base.ElementWait;
import base.search.engine.wait.utils.Sleeper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.xml.bind.Element;

public class ChatAcceptDialog extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    public ChatAcceptDialog(WebDriver driver, Logger logger) throws Exception{
        super(driver);
        sTools = new SearchTools(driver);
        this.logger = logger;
    }


    private void clickLogonButton() throws  Exception{
        sTools.click("button","id=chatMediaBar_requestBtn");
//        int i = 0;
//
//        sTools.wait("button","id=chatMediaBar_requestBtn");

//        while(!driver.getPageSource().contains("\"Cancel Request") && i < 4) {
//            driver.findElement(By.id("chatMediaBar_requestBtn")).click();
//            Sleeper.sleep(0.2f);
//            i++;
//        }
    }


    public void acceptChat() throws Exception {

        Sleeper.sleep(5);

        int i = 0;

        while(!driver.getPageSource().contains("OracleChatAcceptButton") && i<5) {
            sTools.refreshPage();
            Sleeper.sleep(10);
            clickLogonButton();
            Sleeper.sleep(6);
            i++;
        }

        driver.findElement(By.className("OracleChatAcceptButton")).click();

       // sTools.click("input","value=Accept|class=OracleChatAcceptButton");
    }


    public void acceptChatInTransfer() throws Exception {


        Sleeper.sleep(1);

        int i = 0;

        while(!driver.getPageSource().contains("OracleChatAcceptButton") && i<8) {
            sTools.refreshPage();
            Sleeper.sleep(10);
            clickLogonButton();
            Sleeper.sleep(6);
            i++;
        }

        driver.findElement(By.className("OracleChatAcceptButton")).click();
    }
}
