package base.search.engine;

import base.search.engine.parser.CommonByParser;
import base.search.wrapper.WebWrapper;
import base.search.engine.wait.utils.Sleeper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.search.engine.wait.CommonWait;

import java.util.List;

public class CommonSearchEngine extends AbsSearchEngine {

    public CommonSearchEngine(WebDriver driver) throws Exception{
        super(driver);
    }


    @Override
    public WebElement find(String type,String value) throws Exception {
        //Wait for page element to be ready
        Sleeper.sleep(0.5f);
        By appBy= CommonByParser.parseBy(type,value);
        CommonWait cWait = new CommonWait(driver);
        cWait.waitForPageReady();
        WebElement ele =  cWait.waitAndGetElement(appBy,defaultInterval,getTimeout());
        WebWrapper webWrapper = new WebWrapper(driver,ele);
        webWrapper.scrollToView();
        cWait.waitForPageReady();
        return ele;
    }


    @Override
    public boolean waitForText(String value) throws Exception {
        CommonWait cWait = new CommonWait(driver);
        return cWait.waitForText(value,defaultInterval,getTimeout());
    }

    @Override
    public boolean waitForDisappear(String type,String value) throws Exception {


        By appBy= CommonByParser.parseBy(type,value);
        CommonWait cWait = new CommonWait(driver);

        cWait.waitForPageReady();
        // xpath selector is handled in MobileWait
        return cWait.waitForElementDisappear(appBy, defaultInterval, getTimeout());
    }

    @Override
    public boolean waitForTextDisappear(String text) throws Exception {
        CommonWait cWait = new CommonWait(driver);
        return cWait.waitForTextDisappear(text,defaultInterval,getTimeout());
    }

    @Override
    public WebElement findByIndex(String type,String value, int index) throws Exception {
        List<WebElement> eles = this.findAll(type,value);
        if(eles == null || eles.size() == 0 || eles.size() <= index) {
            return null;
        }
        return eles.get(index);
    }

    @Override
    public List<WebElement> findAll(String type,String value) throws Exception {
        By appBy= CommonByParser.parseBy(type,value);
        CommonWait cWait = new CommonWait(driver);
        cWait.waitForPageReady();
        return cWait.waitAndGetElements(appBy,defaultInterval,getTimeout());
    }



}
