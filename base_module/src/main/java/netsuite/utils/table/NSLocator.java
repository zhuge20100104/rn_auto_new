package netsuite.utils.table;

import base.exceptions.JBaseException;

public class NSLocator {
    private final LocatorType locatorType;
    private final String expression;
    private String fuzzyText = null;

    public NSLocator(LocatorType locatorType, String expression) {
        this.locatorType = locatorType;
        this.expression = expression;
    }

    public LocatorType getLocatorType() {
        return this.locatorType;
    }

    public String getLocatorExpression() {
        return this.expression;
    }

    public static NSLocator parse(String s) throws Exception {
        if (s == null) {
            return null;
        } else {
            String[] split = s.split("->");
            if (split.length != 2) {
                throw new JBaseException("Locator " + s + " is not in correct format.");
            } else {
                LocatorType type = LocatorType.parse(split[0]);
                if (type == LocatorType.AMBIGUOUS) {
                    throw new JBaseException(split[0] + " is not a supported locator type");
                } else {
                    String expr = split[1].replace("\\,", ",");
                    if (expr != null && !expr.equals("")) {
                        if (type == LocatorType.ID) {
                            type = LocatorType.XPATH;
                            expr = "//.[@id='" + expr + "']";
                        }

                        return new NSLocator(type, expr);
                    } else {
                        throw new JBaseException("Locator expression cannot be empty.");
                    }
                }
            }
        }
    }

    public boolean locateByFuzzyText() {
        return this.fuzzyText != null;
    }

    public String getFuzzyText() {
        return this.fuzzyText;
    }

    public void setFuzzyText(String fuzzyText) {
        this.fuzzyText = fuzzyText;
    }

    public String toString() {
        return this.locatorType + ":" + this.expression;
    }

    public static NSLocator css(String expression) {
        return new NSLocator(LocatorType.CSS, expression);
    }

    public static NSLocator xpath(String expression) {
        return new NSLocator(LocatorType.XPATH, expression);
    }

    public static NSLocator id(String id) {
        return new NSLocator(LocatorType.ID, id);
    }

    public static NSLocator name(String name) {
        return new NSLocator(LocatorType.NAME, name);
    }

    public static NSLocator className(String className) {
        return new NSLocator(LocatorType.CLASS, className);
    }

    public static NSLocator text(String text) {
        return new NSLocator(LocatorType.TEXT, text);
    }
}
