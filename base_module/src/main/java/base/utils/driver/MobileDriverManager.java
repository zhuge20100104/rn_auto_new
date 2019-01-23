package base.utils.driver;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import base.utils.adb.Device;
import base.utils.driver.create.mobile.MobileDriverCreate;

public class MobileDriverManager {
    private static ThreadLocal<AppiumDriver> driverThreadLocal = new ThreadLocal();
    private static ThreadLocal<AppiumDriverLocalService> serverThreadLocal = new ThreadLocal<>();


    public  static AppiumDriver getDriver(Device device) throws Exception {

        MobileDriverCreate mobileDriverCreate = new MobileDriverCreate();
        if(serverThreadLocal.get() == null) {
            serverThreadLocal.set(mobileDriverCreate.createAServer(device));
        }

        if(driverThreadLocal.get() == null) {
            driverThreadLocal.set(mobileDriverCreate.create(device));
        }

        return driverThreadLocal.get();
    }

    public static void removeDriver() throws Exception {
        driverThreadLocal.set(null);
    }

    public static void stopServer() {
        if(serverThreadLocal.get() != null){
            serverThreadLocal.get().stop();
            serverThreadLocal.set(null);
        }
    }

    public static void stop() throws Exception {
        removeDriver();
        stopServer();
    }





}
