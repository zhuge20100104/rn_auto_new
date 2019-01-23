package base.search.wrapper;

import base.search.SearchTools;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.touch.TouchActions;

import java.time.Duration;


public class MobileScrollWrapper {

    protected  WebDriver driver;
    protected  SearchTools sTools;

    public MobileScrollWrapper(WebDriver driver) {
        this.driver = driver;
        this.sTools = new SearchTools(driver);
    }




    public boolean scroll(int startX, int startY, int endX, int endY,float durationSecs) {
        try {
            int nanos = (int)(durationSecs * 1000);
            Duration duration = Duration.ofNanos(nanos);

            new TouchAction((MobileDriver)driver).press(PointOption.point(startX,startY))
                    .waitAction(WaitOptions.waitOptions(duration))
                    .moveTo(PointOption.point(endX,endY))
                    .release().perform();

            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }


    public boolean scrollLeft() {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        int scrollY = height/2;
        int startX = (int)(width*0.9f);
        int endX = (int) (width*0.1f);
        return scroll(startX,scrollY,endX,scrollY,0.6f);
    }

    public boolean scrollRight() {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        int scrollY = height/2;
        int startX = (int)(width*0.1f);
        int endX = (int) (width*0.9f);
        return scroll(startX,scrollY,endX,scrollY,0.6f);
    }


    public boolean scrollUp() {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        int scrollX = width/2;
        int startY = (int)(height*0.9f);
        int endY = (int) (height*0.1f);
        return scroll(scrollX,startY,scrollX,endY,0.6f);
    }


    public boolean scrollDown() {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        int scrollX = width/2;
        int startY = (int)(height*0.1f);
        int endY = (int) (height*0.9f);
        return scroll(scrollX,startY,scrollX,endY,0.6f);
    }
}
