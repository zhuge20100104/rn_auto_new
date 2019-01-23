package osvc.pageobjects.web.WeChatConfig;

import base.BaseWebComponent;
import base.search.SearchTools;
import base.utils.ocr.beans.OneWord;
import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Java;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AutoReplyPage extends BaseWebComponent {
    private SearchTools sTools;
    private Logger logger;
    public AutoReplyPage(WebDriver driver, Logger logger) throws Exception {
        super(driver);
        this.sTools = new SearchTools(driver);
        this.logger = logger;
    }

    public Object[][] getLanguages() {
        Object [] [] languages = {
                {"keys","chatMsg"
                },

                {"zh_CN","聊天消息"

                },

                {"en_US","Chat Message"
                }
        };
        return languages;
    }

    public void getInSystemMessage() throws Exception {
        sTools.click("span", "text=System Message");
    }

    public void getInRegistrationMessage() throws Exception {
        sTools.click("span", "text=Registration Message");
    }

    public void getInChatMessage() throws Exception {
        sTools.click("span", "text="+getProp("chatMsg"));
    }

    public void switchLanguage() throws Exception {
        sTools.click("div", "role=switch checkbox");
    }

    private String checkLanguage() throws Exception {
        if ("false".equals(sTools.find("div", "role=switch checkbox").getAttribute("aria-checked"))) {
            return "Chinese";
        } else {
            return "English";
        }
    }

    public void switchToChinese() throws Exception {
        if ("English".equals(checkLanguage())) {
            switchLanguage();
        }
    }

    public void switchToEnglish() throws Exception {
        if ("Chinese".equals(checkLanguage())) {
            switchLanguage();
        }
    }

    private WebElement findCard(String Title) throws Exception {
        WebElement ret = null;
        List<WebElement> AllCards = sTools.findAll("template-message", "class=oj-complete");
        for (WebElement eachCard : AllCards) {
            if (Title.equals(eachCard.findElement(By.cssSelector("h4")).getText())) {
                ret = eachCard;
            }
        }
        int px = 400;
        if (ret == null) {
            JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
            String j = "var test_div = document.querySelector('div.message-list');" +
                    "return test_div.scrollTop.toString();";
            String wz = javascriptExecutor.executeScript(j).toString();
            int px_now = Integer.valueOf(wz);
            px = px + px_now;
            String js = "var test_div = document.querySelector(\"div.message-list\");test_div.scrollTop=" + px;
            javascriptExecutor.executeScript(js);
            findCard(Title);
        }
        return ret;
    }

    public String findCardContent(String Title, String ocrContent) throws Exception {
        WebElement Card = findCard(Title);
        Card.findElement(By.cssSelector("div.edit-icon")).click();
        OneWord oneWord = sTools.findWordsContainsByOcrWeb(ocrContent);
        return oneWord.getWords();
    }

    public void editCardContent(String Title, String Content) throws Exception {
        WebElement Card = findCard(Title);

        Card.findElement(By.cssSelector("div.edit-icon")).click();
        Card.findElement(By.cssSelector("textarea")).clear();
        Card.findElement(By.cssSelector("textarea")).sendKeys(Content);
        Card.findElement(By.cssSelector("button[data-bind*='button.save'")).click();
    }

    public void rollBackMsgToDefault(String Title) throws Exception {
        WebElement Card = findCard(Title);
        Card.findElement(By.cssSelector("div.reset-icon")).click();
    }

}
