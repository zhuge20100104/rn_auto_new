package base.utils;

import org.apache.log4j.PropertyConfigurator;

import java.io.File;

public class Log4JUtil {
    private static final String LOG4J_CONF_PATH = "config"+File.separator+"log4j"+File.separator+"log4j.properties";

    private static final String LOG_OUT_PATH = "logs"+File.separator+JDateUtils.getCurrentTimeStr()+".log";

    public static void config() {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
        System.setProperty("log4jFile",LOG_OUT_PATH);
        PropertyConfigurator.configure(LOG4J_CONF_PATH);
    }
}
