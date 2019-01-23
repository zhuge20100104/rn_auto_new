package base.search.engine.wait;

import base.search.engine.wait.base.ElementWait;
import base.search.engine.wait.utils.Condition;
import base.search.engine.wait.utils.Result;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class UiAutoWait extends ElementWait{

    public UiAutoWait(WebDriver driver) {
        super(driver);
    }

    public boolean waitForCommonElementDispear(String value, int interval, int timeout) {

        String textToWait = this.getTextToWait(value);

        //Wait for text to be disappear
        if(this.waitForTextDisappear(value,interval,timeout)){
            return true;
        }

        return waitForElementDisappear(value, interval, timeout);
    }


    public boolean waitForText(String text,int interval,int timeout) {

        Result<String> result =  this.waitForConditon(new Condition<String>() {
            public boolean check(Result<String> r) {
                if(driver.getPageSource().contains(text)) {
                    r.setCode(true);
                    return true;
                }
                return false;
            }
        },interval,timeout);
        return result.Code();
    }


    public boolean waitForTextDisappear(String text,int interval,int timeout) {
        //Sleep for the element appear

        //Then check if the element is disappear
        Result<String> result =  this.waitForConditon(new Condition<String>() {
            public boolean check(Result<String> r) {
                if(!driver.getPageSource().contains(text)) {
                    r.setCode(true);
                    return true;
                }
                return false;
            }
        },interval,timeout);
        return result.Code();
    }

    public boolean waitForElementDisappear(String selectorStr ,int interval ,int timeout) {

        Result<WebElement> result = this.waitForConditon(new Condition<WebElement>() {
            @Override
            public boolean check(Result<WebElement> r) {

                WebElement ele = null;
                try {
                    ele = ((AndroidDriver)driver).findElementByAndroidUIAutomator(selectorStr);
                    if(ele == null  || !ele.isDisplayed()){
                        r.setCode(true);
                        return true;
                    }
                }catch (Exception ex) {
                    r.setCode(true);
                    return true;
                }
                return false;
            }
        },interval,timeout);
        return result.Code();
    }

    private String getTextToWait(String value) {
        String textToWait = "";
        //ui automator selector, like this, new UiSelector().textStartsWith("text1")
        String [] valueArr = value.split("\"");
        int len = valueArr.length;
        Assert.assertTrue(len>2,"xpath locator parse failed!!");
        textToWait = valueArr[len-2].trim();
        return textToWait;
    }


    public WebElement waitAndGetCommonElement(String value,int interval, int timeout) {
        String textToWait = getTextToWait(value);

        boolean textInPage = this.waitForText(textToWait,interval,timeout);

        if(!textInPage){
            return  null;
        }
        // text already in this page, try to locate it
        return this.waitAndGetElement(value,interval,3);
    }


    public List<WebElement> waitAndGetElements(String uiSelector , int interval, int timeout) {
        Result<List<WebElement>> result = this.waitForConditon(new Condition<List<WebElement>>() {
            List<WebElement> eles = null;
            @Override
            public boolean check(Result<List<WebElement>> r) {
                try {
                    eles = ((AndroidDriver)driver).findElementsByAndroidUIAutomator(uiSelector);
                }catch (Exception ex) {
                    return false;
                }
                if(eles !=null && eles.size() >0 && eles.get(0).isDisplayed()){
                    r.setCode(true);
                    r.setMessage("Find elements success!!");
                    r.setObj(eles);
                    return true;
                }
                return false;
            }
        },interval,timeout);
        return result.getObj();
    }




    public WebElement waitAndGetElement(String selectorStr,int interval, int timeout) {
        Result<WebElement> result = this.waitForConditon(new Condition<WebElement>() {
            WebElement ele = null;

            @Override
            public boolean check(Result<WebElement> r) {
                try {
                    ele = ((AndroidDriver)driver).findElementByAndroidUIAutomator(selectorStr);
                }catch (Exception ex) {
                    return false;
                }
                if(ele!=null && ele.isDisplayed()){
                    r.setCode(true);
                    r.setMessage("Get element success!!");
                    r.setObj(ele);
                    return true;
                }
                return false;
            }
        },interval,timeout);
        return result.getObj();
    }
}
