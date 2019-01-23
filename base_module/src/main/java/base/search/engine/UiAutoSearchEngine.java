package base.search.engine;

import base.search.engine.parser.UiAutoParser;
import base.search.engine.wait.UiAutoWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class UiAutoSearchEngine extends AbsSearchEngine {
    public UiAutoSearchEngine(WebDriver driver) throws Exception{
        super(driver);
    }


    @Override
    public WebElement find(String type,String value) throws Exception {

        UiAutoParser uiParser = new UiAutoParser("ui",value);
        String uiAutoSelector =  uiParser.parseUiAutoString();
        
        UiAutoWait uWait = new UiAutoWait(driver);
        return uWait.waitAndGetCommonElement(uiAutoSelector,defaultInterval,getTimeout());
    }


    @Override
    public boolean waitForText(String value) throws Exception {
        UiAutoWait uWait = new UiAutoWait(driver);
        return uWait.waitForText(value,defaultInterval,getTimeout());
    }

    @Override
    public boolean waitForDisappear(String type,String value) throws Exception {
        UiAutoWait uWait = new UiAutoWait(driver);

        UiAutoParser uiParser = new UiAutoParser("ui",value);
        String uiAutoSelector =  uiParser.parseUiAutoString();
        uWait.waitForCommonElementDispear(uiAutoSelector,defaultInterval,getTimeout());
        return false;
    }

    @Override
    public boolean waitForTextDisappear(String text) throws Exception {

        UiAutoWait uWait = new UiAutoWait(driver);
        return uWait.waitForTextDisappear(text,defaultInterval,getTimeout());
    }


    @Override
    public WebElement findByIndex(String type,String value, int index) throws Exception{
        List<WebElement> eles = this.findAll(type,value);
        if(eles == null || eles.size() == 0 || eles.size() <= index) {
            return null;
        }
        return eles.get(index);
    }


    @Override
    public List<WebElement> findAll(String type,String value) throws Exception {
        UiAutoParser uiParser = new UiAutoParser("ui",value);
        String uiAutoSelector =  uiParser.parseUiAutoString();
        UiAutoWait uWait = new UiAutoWait(driver);
        return uWait.waitAndGetElements(uiAutoSelector,defaultInterval,getTimeout());
    }
}
