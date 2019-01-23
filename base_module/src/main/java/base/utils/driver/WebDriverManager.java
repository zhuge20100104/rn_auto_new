package base.utils.driver;


import base.utils.driver.create.web.IWebDriverCreate;
import base.utils.driver.create.web.LocalChromeDriverCreate;
import base.utils.driver.create.web.RemoteChromeDriverCreate;
import base.utils.driver.create.web.RemoteFirefoxDriverCreate;
import config.utils.ConfigUtil;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class WebDriverManager {
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal();

    private static Map<String,IWebDriverCreate> allChromeDriverCreateMap() {
        Map<String,IWebDriverCreate> webDriverCreateMap = new HashMap<>();
        webDriverCreateMap.put("local",new LocalChromeDriverCreate());
        webDriverCreateMap.put("remote", new RemoteChromeDriverCreate());
        return webDriverCreateMap;
    }

    private static Map<String,IWebDriverCreate> allFirefoxDriverCreateMap(){
        Map<String,IWebDriverCreate> firefoxDriverCreateMap = new HashMap<>();
        firefoxDriverCreateMap.put("remote",new RemoteFirefoxDriverCreate());
        return firefoxDriverCreateMap;
    }


    public  static WebDriver getDriver(String url) throws Exception {
        if(driverThreadLocal.get() == null) {
            driverThreadLocal.set(create(url));
        }
        return driverThreadLocal.get();
    }

    public static void removeDriver() throws Exception {
        driverThreadLocal.set(null);
    }

    private  static WebDriver create(String url) throws Exception{
        String browser = ConfigUtil.getBrowserType();
        Map<String, IWebDriverCreate> webDriverCreateMap = browser.equals("chrome") ? allChromeDriverCreateMap(): allFirefoxDriverCreateMap();
        String model = ConfigUtil.getBrowserMode();

        try{
            return webDriverCreateMap.get(model).create(url);
        }catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
