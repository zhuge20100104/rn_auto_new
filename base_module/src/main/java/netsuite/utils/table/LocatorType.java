package netsuite.utils.table;

import base.exceptions.JBaseException;

public enum LocatorType {
    XPATH,
    ID,
    NAME,
    CLASS,
    TEXT,
    PARTIAL_TEXT,
    FUZZY_TEXT,
    TEXT_PATTERN,
    CSS,
    DOM,
    AMBIGUOUS;

    private LocatorType() {
    }

    public static LocatorType parse(String loc)  throws Exception{
        if (loc.equalsIgnoreCase("xpath")) {
            return XPATH;
        } else if (loc.equalsIgnoreCase("id")) {
            return ID;
        } else if (loc.equalsIgnoreCase("name")) {
            return NAME;
        } else if (loc.equalsIgnoreCase("class")) {
            return CLASS;
        } else if (loc.equalsIgnoreCase("css")) {
            return CSS;
        } else if (loc.equalsIgnoreCase("dom")) {
            return DOM;
        } else if (loc.equalsIgnoreCase("text")) {
            return TEXT;
        } else if (loc.equalsIgnoreCase("partial")) {
            return PARTIAL_TEXT;
        } else if (loc.equalsIgnoreCase("contains")) {
            return PARTIAL_TEXT;
        } else if (loc.equalsIgnoreCase("fuzzy")) {
            return FUZZY_TEXT;
        } else if (loc.equalsIgnoreCase("pattern")) {
            return TEXT_PATTERN;
        } else {
            throw new JBaseException("Unknown locator type!");
        }
    }

    public String toString() {
        return super.toString().toLowerCase();
    }
}
