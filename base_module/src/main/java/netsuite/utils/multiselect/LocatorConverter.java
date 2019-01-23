package netsuite.utils.multiselect;

import base.search.engine.parser.JBy;
import netsuite.utils.table.LocatorType;
import netsuite.utils.table.NSLocator;

public class LocatorConverter {
    public static NSLocator convertToNSLocator(JBy by) {

        switch (by.getType()) {
            case "id":
                return new NSLocator(LocatorType.ID, by.getValue());
            case "name":
                return new NSLocator(LocatorType.NAME,  by.getValue());
            case "xpath":
                return new NSLocator(LocatorType.XPATH,  by.getValue());
            case "css":
                return new NSLocator(LocatorType.CSS,  by.getValue());
            case "class":
                return new NSLocator(LocatorType.CLASS,  by.getValue());
            default:
                return null;
        }
    }


    public  static JBy convertToJBy(NSLocator locator) {
        String locatorStr = "";
        JBy by = null;
        if (locator.getLocatorType().equals(LocatorType.ID)) {
            by = new JBy();
            by.setType("id");
            by.setValue(locator.getLocatorExpression());
            return  by;
         } else if (locator.getLocatorType().equals(LocatorType.NAME)) {
            by = new JBy();
            by.setType("name");
            by.setValue(locator.getLocatorExpression());
            return  by;
        } else if (locator.getLocatorType().equals(LocatorType.XPATH)) {
            by = new JBy();
            by.setType("xpath");
            by.setValue(locator.getLocatorExpression());
            return  by;
        } else if (locator.getLocatorType().equals(LocatorType.CSS)) {
            locatorStr = "css="+locator.getLocatorExpression();
            by = new JBy();
            by.setType("css");
            by.setValue(locator.getLocatorExpression());
            return  by;
        } else if (locator.getLocatorType().equals(LocatorType.CLASS)) {
            by = new JBy();
            by.setType("class");
            by.setValue(locator.getLocatorExpression());
            return  by;
        }

        return null;
    }
}
