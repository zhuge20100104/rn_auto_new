package config.utils;

import java.io.IOException;

public class ConfigUtil {
    private static JProperties jProperties = null;

    static {
        try {
            jProperties = new JProperties();
        }catch (IOException ex) {
            System.err.println("Read properties file failed: ["+ex.getMessage()+"]!!!");
        }
    }


    //region common methods for properties operation
    public static String getPropertyValue(String propertyName) {
        return jProperties.getPropertyValue(propertyName);
    }


    public  static synchronized void setPropertyValue(String propertyName, String propertyValue) {
        jProperties.setPropertyValue(propertyName,propertyValue);
    }

    public static String getReportTemplateFolder() {
        return getPropertyValue("testautomation.report.template_folder");
    }
    //endregion



    //region report related methods
    public static String getReportTemplateName() {
        return getPropertyValue("testautomation.report.template_name");
    }

    public static String getHtmlOutputFolder() {
        return getPropertyValue("testautomation.report.html_output_folder");
    }

    public static String getHtmlOutputPrefix() {
        return getPropertyValue("testautomation.report.html_output_prefix");
    }
    //endregion



    //region ZK related methods
    public static String getZKTimeout() {
        return getPropertyValue("testautomation.zk.timeout");
    }

    public static String getZKConnectionString() {
        return getPropertyValue("testautomation.zk.connect_string");
    }

    public static String getZKSessionTimeout() {
        return getPropertyValue("testautomation.zk.session_timeout");
    }

    public static String getZKIsLocal() {
        return getPropertyValue("testautomation.zk.is_local");
    }
    //endregion


    //region browser related methods
    public static String getBrowserExtension(){
        return getPropertyValue("testautomation.browser.extension");
    }

    public static String getBrowserTestType() {
        return getPropertyValue("testautomation.browser.test");
    }

    public static String getBrowserMode() {
        return getPropertyValue("testautomation.browser.mode");
    }

    public static String getBrowserDriverKey() {
        return getPropertyValue("testautomation.browser.driver_key");
    }

    public static String getBrowserDriverPath() {
        return getPropertyValue("testautomation.browser.driver_path");
    }

    public static String getBrowserImplicitWaitTime() {
        return getPropertyValue("testautomation.browser.implicit_wait_time");
    }

    public static String getBrowserTimeout() {
        return getPropertyValue("testautomation.browser.timeout");
    }

    public static String getBrowserPageReadyTimeout() {
        return getPropertyValue("testautomation.browser.page.timeout");
    }

    public static String getBrowserType() {
        return getPropertyValue("testautomation.browser.type");
    }

    public static String getBrowserRemoteHost() {
        return getPropertyValue("testautomation.browser.remote_host");
    }


    public static String getBrowserRemotePort() {
        return getPropertyValue("testautomation.browser.remote_port");
    }


    public static String getBrowserProfile() {
        return getPropertyValue("testautomation.browser.profile");
    }
    //endregion




    //region mobile related methods
    public static String getMobileNodePath() {
        return getPropertyValue("testautomation.mobile.node_path");
    }

    public static String getMobileAppiumJSPath() {
        return getPropertyValue("testautomation.mobile.appium_js_path");
    }

    public static String getMobileAppServerIP() {
        return getPropertyValue("testautomation.mobile.app_server_ip");
    }

    public static String getMobileBaseServerPort() {
        return getPropertyValue("testautomation.mobile.base_server_port");
    }

    public static String getMobileAutomationName() {
        return getPropertyValue("testautomation.mobile.automation_name");
    }

    public static String getMobilePlatformName() {
        return getPropertyValue("testautomation.mobile.platform_name");
    }


    public static String getMobilePlatformVersion() {
        return getPropertyValue("testautomation.mobile.platform_version");
    }

    public static String getMobileNoRest() {
        return getPropertyValue("testautomation.mobile.no_reset");
    }

    public static String getMobileFullReset() {
        return getPropertyValue("testautomation.mobile.full_reset");
    }

    public static String getMobileSessionOverride() {
        return getPropertyValue("testautomation.mobile.session_override");
    }

    public static String getMobileUnicodeKeyboard() {
        return getPropertyValue("testautomation.mobile.unicode_keyboard");
    }

    public static String getMobileResetKeyboard() {
        return getPropertyValue("testautomation.mobile.reset_keyboard");
    }


    public static String getMobileAppPackage() {
        return getPropertyValue("testautomation.mobile.app_package");
    }

    public static String getMobileAppActivity() {
        return getPropertyValue("testautomation.mobile.app_activity");
    }


    public static String getMobileChromeAndroidProcess() {
        return getPropertyValue("testautomation.mobile.chrome_android_process");
    }

    public static String getMobileAppStartTimeout() {
        return getPropertyValue("testautomation.mobile.app_start_timeout");
    }

    public static String getMobileAppNewCommandTimeout() {
        return getPropertyValue("testautomation.mobile.new_command_timeout");
    }


    public static String getMobileTimeout() {
        return getPropertyValue("testautomation.mobile.timeout");
    }
    //endregion


    //region Language and locale related configuration
    public static String getCurrentLocale() {
        return getPropertyValue("testautomation.app.language");
    }
    //endregion


    //region Default host related configuration
    public static String getAppHost() {
        return getPropertyValue("testautomation.app.default.host");
    }
    //endregion

    //region Get proxy related configuration
    public static String getProxyHost() {
        return getPropertyValue("testautomation.config.proxy.host");
    }

    public static Integer getProxyPort() {
        return Integer.parseInt(getPropertyValue("testautomation.config.proxy.port"));
    }
    //endregion


    //region Admin console related configurations
    public static String getAdminConsoleUsername() {
        return getPropertyValue("testautomation.admin_console.username");
    }

    public static String getAdminConsolePassword() {
        return getPropertyValue("testautomation.admin_console.password");
    }

    public static String getAdminConsoleIdentityDomain() {
        return getPropertyValue("testautomation.admin_console.identity_domain");
    }

    public static String getAdminConsoleServiceName() {
        return getPropertyValue("testautomation.admin_console.service_name");
    }

    //endregion


    //region  account related configuration
    public static String getAccount1AppId() {
        return getPropertyValue("testautomation.test_account.account1.app_id");
    }

    public static String getAccount1AppSecret() {
        return getPropertyValue("testautomation.test_account.account1.app_secret");
    }

    public static String getAccount1AppToken() {
        return getPropertyValue("testautomation.test_account.account1.token");
    }

    public static String getFirstWechatMessageId() {
        return getPropertyValue("testautomation.wechat.first_message_id");
    }

    //endregion





    public static void main(String[] args) throws Exception{
        System.out.println(getMobileTimeout());
//        System.out.println(getProxyHost());
//        System.out.println(getReportTemplateFolder());
//        System.out.println(getBrowserLang());
//        System.out.println(ConfigUtil.getConfig(BrowserConfig.class,"timeout"));
//        System.out.println(ConfigUtil.getConfig(ConfigItem.BROWSER_CAP,"timeout"));
    }
}
