package base.utils.driver.create.mobile;


import base.utils.JDateUtils;
import base.utils.JFileUtils;
import base.utils.adb.Device;
import base.search.engine.wait.utils.Sleeper;
import config.utils.ConfigUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public  class MobileDriverCreate {


    // Start configure items
    private  String nodePath;
    private  String appiumJSPath;
    private  String appServerIP;
    private  String baseServerPort;
    private  String automationName;
    private  String platformName;
    private  String platformVersion;
    private  boolean noReset;
    private  boolean fullReset;
    private  boolean sessionOverride;
    private  boolean unicodeKeyboard;
    private  boolean resetKeyboard;
    private  String appPackage;
    private  String appActivity;
    private  String chromeAndroidProcess;
    private  int appStartTimeout;
    private  int newCommandTimeout;
    // End configure items

    public MobileDriverCreate() throws Exception {
        getConfigItems();
    }

    private  void getConfigItems() throws Exception {
        nodePath = ConfigUtil.getMobileNodePath();
        appiumJSPath = ConfigUtil.getMobileAppiumJSPath();
        appServerIP = ConfigUtil.getMobileAppServerIP();
        baseServerPort = ConfigUtil.getMobileBaseServerPort();
        automationName = ConfigUtil.getMobileAutomationName();
        platformName = ConfigUtil.getMobilePlatformName();
        platformVersion = ConfigUtil.getMobilePlatformVersion();
        noReset = Boolean.parseBoolean(ConfigUtil.getMobileNoRest());
        fullReset = Boolean.parseBoolean(ConfigUtil.getMobileFullReset());
        sessionOverride = Boolean.parseBoolean(ConfigUtil.getMobileSessionOverride());
        unicodeKeyboard = Boolean.parseBoolean(ConfigUtil.getMobileUnicodeKeyboard());
        resetKeyboard = Boolean.parseBoolean(ConfigUtil.getMobileResetKeyboard());
        appPackage = ConfigUtil.getMobileAppPackage();
        appActivity = ConfigUtil.getMobileAppActivity();
//        chromeAndroidProcess = ConfigUtil.getConfig(ConfigItem.MOBILE_CAP,"chromeAndroidProcess");
        appStartTimeout = Integer.parseInt(ConfigUtil.getMobileAppStartTimeout());
        newCommandTimeout = Integer.parseInt(ConfigUtil.getMobileAppNewCommandTimeout());
    }



    public AppiumDriver  create(Device device) throws Exception {
        return startAMobileDriver(device);
    }

    private String getLogFileName() {
        return "appium_" + JDateUtils.getCurrentTimeStr()+".log";
    }

    public AppiumDriver startAMobileDriver(Device device) throws Exception{
        AndroidDriver appiumDriver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName",device.getDeviceName());
        capabilities.setCapability("udid", device.getDeviceName());
        capabilities.setCapability("automationName", automationName);
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("noReset", noReset);
        capabilities.setCapability("fullReset", fullReset);
        capabilities.setCapability("sessionOverride", sessionOverride);
        capabilities.setCapability("unicodeKeyboard", unicodeKeyboard);
        capabilities.setCapability("resetKeyboard", resetKeyboard);
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("appActivity", appActivity);
        capabilities.setCapability("newCommandTimeout",newCommandTimeout);

//        ChromeOptions options = new ChromeOptions();
//        options.setExperimentalOption("androidPackage", "com.tencent.wework");
//        options.setExperimentalOption("androidUseRunningApp", true);
//        options.setExperimentalOption("androidActivity", ".common.web.JsWebActivity");
//        options.setExperimentalOption("androidProcess", "com.tencent.wework:support");
//
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        // init driver
        try {
            appiumDriver = new AndroidDriver(new URL("http://"+appServerIP+":"+device.getPort()+"/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException" + e);
        } catch (SessionNotCreatedException e) {
            System.out.println("Session cannot be created" + e);
        }
        Sleeper.sleep(3);
        appiumDriver.manage().timeouts().implicitlyWait(appStartTimeout, TimeUnit.SECONDS);
        Sleeper.sleep(10);
        return appiumDriver;
    }

    public  AppiumDriverLocalService createAServer(Device device) throws Exception{
        String relativelyPath= JFileUtils.getProjRootDir();

        String appiumLogFileDir = relativelyPath + File.separator + "logs";
        File logFileDir = FileUtils.getFile(appiumLogFileDir);
        if(!logFileDir.exists()) {
            FileUtils.forceMkdir(logFileDir);
        }

        String appiumLogFilePath = relativelyPath + File.separator + "logs" + File.separator + getLogFileName();
        File logFile = FileUtils.getFile(appiumLogFilePath);
        if(logFile.exists()) {
            logFile.createNewFile();
        }

        System.out.println(appiumLogFilePath);
        //usingDriverExecutable(new File(nodePath))
        AppiumDriverLocalService appiumNode = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .withIPAddress(appServerIP).usingDriverExecutable(new File(nodePath)).withAppiumJS(new File(appiumJSPath))
                .usingPort(device.getPort())
                .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, String.valueOf(device.getBp()))
                .withLogFile(logFile));
        avoidOutputStream(appiumNode);
        appiumNode.start();
        return appiumNode;
    }

    private  void avoidOutputStream(AppiumDriverLocalService service) {
        Field streamField = null;
        Field streamsField = null;
        try {
            streamField = AppiumDriverLocalService.class.getDeclaredField("stream");
            streamField.setAccessible(true);
            streamsField = Class.forName("io.appium.java_client.service.local.ListOutputStream")
                    .getDeclaredField("streams");
            streamsField.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            ((ArrayList<OutputStream>) streamsField.get(streamField.get(service))).clear(); // remove System.out logging
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
