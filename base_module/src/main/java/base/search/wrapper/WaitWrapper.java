package base.search.wrapper;

import base.search.engine.AbsSearchEngine;
import base.search.utils.MobileUtil;
import base.search.engine.wait.base.CoreWait;
import base.search.engine.wait.utils.Condition;
import base.search.engine.wait.utils.Result;
import config.utils.ConfigUtil;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WaitWrapper extends BaseWrapper {
    private AbsSearchEngine engine;
    protected int defaultTimeout = 20;

    public WaitWrapper(WebDriver driver, WebElement ele, AbsSearchEngine engine) {
        super(driver,ele);
        this.engine = engine;
    }

    protected int getTimeout() throws Exception {
        String globalTimeout = ConfigUtil.getBrowserTimeout();
        defaultTimeout = globalTimeout == null ? defaultTimeout: Integer.parseInt(globalTimeout);
        return defaultTimeout;
    }



    public boolean waitForEnabled(String type,String value) throws Exception{
        CoreWait wait = new CoreWait();
        Result<Boolean> r = wait.waitForConditon(new Condition<Boolean>() {
            @Override
            public boolean check(Result<Boolean> r) {
                try {

                    WebElement webElement = engine.find(type,value);
                    if(webElement.isEnabled()) {
                        r.setCode(true);
                        return true;
                    }
                }catch (Exception ex) {
                    r.setCode(false);
                    return false;
                }

                r.setCode(false);
                return false;
            }
        },1,getTimeout());
        return r.Code();
    }


    public boolean waitForActivity(String activity, int interval,int timeout) {
        CoreWait wait = new CoreWait();
        Result<Boolean> r = wait.waitForConditon(new Condition<Boolean>() {
            @Override
            public boolean check(Result<Boolean> r) {
                if(MobileUtil.activity((AppiumDriver) driver).equals(activity)) {
                    r.setCode(true);
                    r.setMessage("Wait for activity success!!");
                    return  true;
                }
                r.setCode(false);
                return false;
            }
        },interval,timeout);
        return  r.Code();
    }
}
