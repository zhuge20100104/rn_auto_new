package base.search.utils;

import io.appium.java_client.AppiumDriver;
import base.utils.adb.CommandExecutor;
import base.search.engine.wait.utils.Result;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

public class MobileUtil {
    public static String activity(AppiumDriver driver){
        String devName = deviceName(driver);
        String getActivityCmd = "adb -s "+devName+" shell dumpsys activity | "+CommandExecutor.getGrepCmd()+" \"mFocusedActivity\"";

        Result<String> result = CommandExecutor.execCommand(getActivityCmd);

        //Command execute success
        if(result.Code()){
            String resultMessage = result.getMessage();
            //These lines are for debug only
//            System.out.println("RESULT MESSAGE");
//            System.out.println(resultMessage);
//            System.out.println("END RESULT MESSAGE");
            String [] resultArr = resultMessage.split(" ");
            return resultArr[resultArr.length-2].trim();
        }else {
            return "ERROR";
        }
    }

    public static void back(AppiumDriver driver) {
        ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.BACK);
    }

    public static void home(AppiumDriver driver) {
        ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.HOME);
    }


    public static String deviceName(AppiumDriver driver) {
        return driver.getCapabilities().getCapability("deviceName").toString();
    }

}
