package base.utils.driver.create.web;


import config.utils.ConfigUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class RemoteFirefoxDriverCreate implements IWebDriverCreate {
    @Override
    public WebDriver create(String url) throws Exception {
        String host = ConfigUtil.getBrowserRemoteHost();
        String port = ConfigUtil.getBrowserRemotePort();

        //Firefox lang setting is a bit different from chrome browser, should be en


        String lang = "";
        String locale = ConfigUtil.getCurrentLocale();
        if(locale.equals("en_US")) {
            lang = "--lang=en";
        }else {
            lang = "--lang=zh-CN";
        }

        String [] langArr = lang.split("=");
        lang = langArr[langArr.length-1];

//        System.out.println("LANG:"+lang);


        DesiredCapabilities capability = DesiredCapabilities.firefox();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("intl.accept_languages",lang);
        profile.setPreference("dom.popup_maximum", 0);
        profile.setPreference("privacy.popups.showBrowserMessage", false);

        capability.setCapability(FirefoxDriver.PROFILE, profile);
        WebDriver driver = new RemoteWebDriver(new URL("http://"+host+":"+port+"/wd/hub/"), capability);

        driver.manage().window().maximize();
        String implicitWaitTimeStr = ConfigUtil.getBrowserImplicitWaitTime();
        int implicitWaitTime = Integer.parseInt(implicitWaitTimeStr);
        driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        driver.get(url);
        Thread.sleep(4000);

        return driver;
    }
}
