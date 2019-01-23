package config.manager;

import config.utils.ConfigUtil;
import config.utils.XMLUtil;

public class ConfigManager {
    public static void main(String[] args) throws Exception{

        //Generate the currentTest.xml file
        String suiteNameStr = System.getenv("suiteStr");
        XMLUtil.generateXML(suiteNameStr);
    }
}
