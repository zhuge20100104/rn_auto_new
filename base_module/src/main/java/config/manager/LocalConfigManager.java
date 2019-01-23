package config.manager;

import config.utils.XMLUtil;

public class LocalConfigManager {
    public static void main(String[] args) throws Exception{
        //Generate the currentTest.xml file
        String packageName = args[0];
        XMLUtil.generateXML(packageName);
    }
}
