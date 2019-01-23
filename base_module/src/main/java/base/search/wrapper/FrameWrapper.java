package base.search.wrapper;

import base.search.engine.AbsSearchEngine;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FrameWrapper extends BaseWrapper {
    private AbsSearchEngine sEngine;
    public FrameWrapper(AbsSearchEngine sEngine, WebDriver driver, WebElement ele) {
        super(driver,ele);
        this.sEngine = sEngine;
    }


    public WebElement findElementInIFrame(String type,String value) throws Exception{
        driver.switchTo().frame(this.element);
        WebElement eleInFrame = sEngine.find(type,value);
        driver.switchTo().defaultContent();
        return eleInFrame;
    }

    public void waitAndSendKeysInIFrame(String type,String value,String message) throws Exception {
        driver.switchTo().frame(this.element);
        sEngine.waitAndSendKeys(type,value,message);
        driver.switchTo().defaultContent();
    }

    public void clickElementInIFrame(String type,String value) throws Exception {
        driver.switchTo().frame(this.element);
        sEngine.click(type,value);
        driver.switchTo().defaultContent();
    }
}
