package base.search.engine.parser;

import base.exceptions.JNotSupportedLocatorException;
import org.openqa.selenium.By;

public class CommonByParser {
    public static By parseBy(String type,String value) throws Exception{
        switch (type) {
            case "id":
                return By.id(value);
            case "name":
                return By.name(value);
            case "css":
                return By.cssSelector(value);
            case "xpath":
                return By.xpath(value);
            case "class":
                return By.className(value);
            default:
                throw new JNotSupportedLocatorException("Not supported common locator!!",type,value);
        }
    }
}
