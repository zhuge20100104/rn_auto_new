package base.search.engine.parser;

public class UiLocator {

    // The match attribute name,such as text, type, html and others
    private String attrName;
    // Determine how to match the tag value, startwith, endwith and equals
    private String matchType;
    // The value of the tag to match
    private String uiValue;


    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getUiValue() {
        return uiValue;
    }

    public void setUiValue(String uiValue) {
        this.uiValue = uiValue;
    }
}
