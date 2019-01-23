package base.utils.driver.create.web;

import base.utils.adb.CommandExecutor;
import base.utils.adb.OSVer;
import config.utils.ConfigUtil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class LocalChromeDriverCreate implements IWebDriverCreate {


    public WebDriver create(String url)  throws Exception{
        String lang = "";
        String locale = ConfigUtil.getCurrentLocale();
        if(locale.equals("en_US")) {
            lang = "--lang=en";
        }else {
            lang = "--lang=zh-CN";
        }

        String extension = ConfigUtil.getBrowserExtension();
        String test = ConfigUtil.getBrowserTestType();


        String driverKey = ConfigUtil.getBrowserDriverKey();
        String driverPath = ConfigUtil.getBrowserDriverPath();
        System.setProperty(driverKey,driverPath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments(lang);
        options.addArguments(extension);
        options.addArguments(test);

        if(CommandExecutor.getOsVer().equals(OSVer.WIN)) {
            options.addArguments("--start-maximized");
        }else {
            options.addArguments("--kiosk");
        }

        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        //region Profile related part
        String profile = ConfigUtil.getBrowserProfile();
        if(profile!=null) {
            options.addArguments(new String[]{"user-data-dir=" + (new File(profile)).getAbsolutePath()});
        }
        //endregion profile related part

        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setCapability(ChromeOptions.CAPABILITY, options);

        WebDriver driver = new ChromeDriver(options);
        //Bug existed in selenium window.maxinize
//        driver.manage().window().maximize();
        String implicitWaitTimeStr = ConfigUtil.getBrowserImplicitWaitTime();
        int implicitWaitTime = Integer.parseInt(implicitWaitTimeStr);

        driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        driver.get(url);
        Thread.sleep(4000);
        return driver;
    }
}
