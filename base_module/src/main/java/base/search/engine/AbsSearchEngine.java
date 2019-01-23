package base.search.engine;


import base.exceptions.JElementNotFoundException;
import config.utils.ConfigUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class AbsSearchEngine {
    protected int defaultInterval = 1000;
    protected int defaultTimeout = 20;

    protected WebDriver driver;

    protected int getTimeout() throws Exception {
        String globalTimeout = ConfigUtil.getBrowserTimeout();
        defaultTimeout = globalTimeout == null ? defaultTimeout: Integer.parseInt(globalTimeout);
        return defaultTimeout;
    }

    public AbsSearchEngine(WebDriver driver) throws Exception {
        this.driver = driver;
    }

    public abstract WebElement find(String type,String value) throws Exception;
    public void click(String type,String value) throws Exception{
        try {
            this.find(type,value).click();
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new JElementNotFoundException("Element not found!!", type, value);
        }
    }

    public boolean wait(String type,String value) throws Exception{
        return this.find(type,value) == null? false:true;
    }



    public abstract boolean waitForText(String value) throws Exception;
    public abstract boolean waitForDisappear(String type,String value) throws Exception;
    public abstract boolean waitForTextDisappear(String text) throws Exception;
    public void waitAndSendKeys(String type,String value, String keys) throws Exception{
        try {
            WebElement ele = this.find(type,value);
            ele.clear();
            ele.sendKeys(keys);
        }catch (Exception ex) {
            throw new JElementNotFoundException("Element not found!!",type,value);
        }
    }
    public abstract WebElement findByIndex(String type, String value, int index) throws Exception;
    public abstract List<WebElement> findAll(String type,String value) throws Exception;
}
