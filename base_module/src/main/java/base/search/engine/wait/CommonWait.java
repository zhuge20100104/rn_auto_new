package base.search.engine.wait;

import base.search.engine.wait.base.ElementWait;
import base.search.engine.wait.utils.Condition;
import base.search.engine.wait.utils.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CommonWait extends ElementWait {

    public CommonWait(WebDriver driver) {
        super(driver);
    }


    public boolean waitForElementDisappear(By by,int interval ,int timeout) {

        Result<WebElement> result = this.waitForConditon(new Condition<WebElement>() {
            @Override
            public boolean check(Result<WebElement> r) {

                WebElement ele = null;
                try {
                    ele = driver.findElement(by);
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


    public boolean waitForText(String text,int interval,int timeout) {
        this.waitForPageReady();

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
        this.waitForPageReady();

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

    public List<WebElement> waitAndGetElements(By by, int interval, int timeout) {
        Result<List<WebElement>> result = this.waitForConditon(new Condition<List<WebElement>>() {
            List<WebElement> eles = null;
            @Override
            public boolean check(Result<List<WebElement>> r) {
                try {
                    eles = driver.findElements(by);
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




    public WebElement waitAndGetElement(By by,int interval, int timeout) {
        Result<WebElement> result = this.waitForConditon(new Condition<WebElement>() {
            WebElement ele = null;
            @Override
            public boolean check(Result<WebElement> r) {
                try {
                    ele = driver.findElement(by);
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
