package base.search.agent;

import base.search.engine.AbsSearchEngine;
import base.search.engine.CommonSearchEngine;
import base.search.engine.UiAutoSearchEngine;
import base.search.wrapper.WaitWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CommonSearchAgent {

    protected WebDriver driver;

    public CommonSearchAgent(WebDriver driver) {
        this.driver = driver;
    }
    public AbsSearchEngine prepareEngine(String type) throws Exception {
        AbsSearchEngine searchEngine;
        searchEngine = type.equals("ui")? new UiAutoSearchEngine(driver): new CommonSearchEngine(driver);
        return searchEngine;
    }

    public WebElement findElement(String type, String value) throws Exception{
        AbsSearchEngine searchEngine = this.prepareEngine(type);
        return searchEngine.find(type,value);
    }

    public void clickElement(String type,String value) throws Exception{
        AbsSearchEngine searchEngine = this.prepareEngine(type);
        searchEngine.click(type,value);
    }

    public void waitAndSendKeysElement(String type,String value,String keys) throws Exception{
        AbsSearchEngine searchEngine = this.prepareEngine(type);
        searchEngine.waitAndSendKeys(type,value,keys);
    }

    public List<WebElement> findAllElement(String type, String value) throws Exception {
        AbsSearchEngine searchEngine = this.prepareEngine(type);
        return searchEngine.findAll(type,value);
    }

    public WebElement findByIndexElement(String type,String value, int index) throws Exception {
        AbsSearchEngine searchEngine = this.prepareEngine(type);
        return searchEngine.findByIndex(type,value,index);
    }

    public boolean waitElement(String type,String value) throws Exception{
        AbsSearchEngine searchEngine = this.prepareEngine(type);
        return searchEngine.wait(type,value);
    }


    public boolean waitForText(String value) throws Exception{
        AbsSearchEngine searchEngine = this.prepareEngine("id");
        return searchEngine.waitForText(value);
    }


    public boolean waitForDisappear(String type,String value) throws Exception{
        AbsSearchEngine searchEngine = this.prepareEngine(type);
        return searchEngine.waitForDisappear(type,value);
    }

    public boolean waitForEnabled(String type,String value) throws Exception {
        AbsSearchEngine searchEngine = this.prepareEngine(type);
        WaitWrapper wWrapper = new WaitWrapper(driver,null,searchEngine);
        return wWrapper.waitForEnabled(type,value);
    }

}
