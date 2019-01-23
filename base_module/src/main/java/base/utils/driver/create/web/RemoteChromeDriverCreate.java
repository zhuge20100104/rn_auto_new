package base.utils.driver.create.web;


import base.utils.adb.CommandExecutor;
import base.utils.adb.OSVer;
import config.utils.ConfigUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class RemoteChromeDriverCreate implements IWebDriverCreate {
    public WebDriver create(String url) throws Exception {

        String lang = "";
        String locale = ConfigUtil.getCurrentLocale();
        if(locale.equals("en_US")) {
            lang = "--lang=en";
        }else {
            lang = "--lang=zh-CN";
        }

        String extension = ConfigUtil.getBrowserExtension();
        String test = ConfigUtil.getBrowserTestType();
        String browser = ConfigUtil.getBrowserType();
        String host = ConfigUtil.getBrowserRemoteHost();
        String port = ConfigUtil.getBrowserRemotePort();


        ChromeOptions options = new ChromeOptions();
        options.addArguments(lang);
        options.addArguments(extension);
        options.addArguments(test);
        options.addArguments("--enable-popup-blocking");

        if(CommandExecutor.getOsVer().equals(OSVer.WIN)) {
            options.addArguments("--start-maximized");
        }else {
            options.addArguments("--kiosk");
        }

        options.addArguments("--no-sandbox");

        //region Profile related part
        String profile = ConfigUtil.getBrowserProfile();
        if(profile!=null) {
            options.addArguments(new String[]{"user-data-dir=" + (new File(profile)).getAbsolutePath()});
        }
        //endregion profile related part

        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setCapability(ChromeOptions.CAPABILITY, options);
        capability.setBrowserName(browser);
        WebDriver driver=null;
        try {
            driver = new RemoteWebDriver(new URL("http://"+host+":"+port+"/wd/hub"), capability);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //Bug existed in selenium window.maximized
        //driver.manage().window().maximize();
        String implicitWaitTimeStr = ConfigUtil.getBrowserImplicitWaitTime();
        int implicitWaitTime = Integer.parseInt(implicitWaitTimeStr);
        driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        driver.get(url);
        Thread.sleep(4000);

        return driver;
    }
}
