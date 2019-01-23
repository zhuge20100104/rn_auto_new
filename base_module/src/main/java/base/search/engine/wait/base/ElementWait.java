package base.search.engine.wait.base;

import base.utils.CSJSUtil;
import base.search.engine.wait.utils.Condition;
import base.search.engine.wait.utils.Result;
import config.utils.ConfigUtil;
import org.openqa.selenium.WebDriver;

/**
 * The class to wait for web element ready
 */
public class ElementWait {

    /** Selenium web driver instance*/
    protected WebDriver driver;

    /** Core wait class
     * <br> See also {@link base.search.engine.wait.base.CoreWait}*/
    private CoreWait coreWait;

    /**
     * Construct of element wait
     * @param driver  The selenium web driver instance
     */
    public ElementWait(WebDriver driver) {
        this.driver = driver;
        coreWait = new CoreWait();
    }

    /**
     * Wait for a specific condition to be true
     * @param con  The condition interface
     *             <br> You should implement an Anonymous class for the interface and describe your intention
     *             <br> See also {@link  base.search.engine.wait.utils.Condition}
     * @param interval wait internal
     * @param tryCount  wait counts
     * @param <T> A type parameter to specify what type of element you want to return
     *           <br> See also {@link base.search.engine.wait.utils.Result}
     * @return  Your required result
     */
    public <T> Result<T> waitForConditon(Condition<T> con, int interval, int tryCount) {
        return coreWait.waitForConditon(con,interval,tryCount);
    }


    /**
     * Wait for a web page ready
     * @return  Whether the web page is ready at last
     */
    public boolean waitForPageReady() {
        int timeout = ConfigUtil.getBrowserPageReadyTimeout() == null ? 20: Integer.parseInt(ConfigUtil.getBrowserPageReadyTimeout());

        Result<String> result = this.waitForConditon(new Condition<String>() {
            @Override
            public boolean check(Result<String> r) {

                if(CSJSUtil.documentReady(driver)) {
                    r.setCode(true);
                    return true;
                }

                return false;
            }
        },500,timeout);
        return result.Code();
    }
}
