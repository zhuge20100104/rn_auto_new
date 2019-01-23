package base.search.engine.parser;

import base.exceptions.JNotSupportedLocatorException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UiAutoParser {


    private String type;
    private String value;

    public UiAutoParser(String type,String value) {
        this.type = type;
        this.value = value;
    }

    private UiLocator uiLocator;

    private  String makeUiSelectorString(String attrName,String attrValue){
        String uiSelectorStr = "new UiSelector().";
        String leftBracket = "(\"";
        String rightBracket = "\");";
        uiSelectorStr+=attrName;
        uiSelectorStr+=leftBracket;
        uiSelectorStr+=attrValue;
        uiSelectorStr+=rightBracket;
        return uiSelectorStr;
    }

    private List<String> getUiAutoLocators() {
        List<String> uiAutoLocators = new ArrayList<String>();
        uiAutoLocators.add("text");
        uiAutoLocators.add("desc");
        uiAutoLocators.add("res");
        return uiAutoLocators;
    }

    private String parseTextLocatorString() throws Exception{
        //Start with locator  "^="
        if(uiLocator.getMatchType().equals("^=")) {
            return makeUiSelectorString("textStartsWith",uiLocator.getUiValue());
            // contains locator  "*="
        }else if(uiLocator.getMatchType().equals("*=")) {
            return makeUiSelectorString("textContains",uiLocator.getUiValue());
            // equals locator  "="
        }else if(uiLocator.getMatchType().equals("=")){
            return makeUiSelectorString("text",uiLocator.getUiValue());
        }else{
            throw new JNotSupportedLocatorException("Not supported locator match type: ["+uiLocator.getMatchType()+"]",type,value);
        }
    }

    private String parseDescString() throws Exception {
        if(uiLocator.getMatchType().equals("^=")) {
            return makeUiSelectorString("descriptionStartsWith",uiLocator.getUiValue());
        }else if(uiLocator.getMatchType().equals("*=")) {
            return makeUiSelectorString("descriptionContains",uiLocator.getUiValue());
        }else if(uiLocator.getMatchType().equals("=")){
            return makeUiSelectorString("description",uiLocator.getUiValue());
        }else{
            throw new JNotSupportedLocatorException("Not supported locator match type: ["+uiLocator.getMatchType()+"]",type,value);
        }
    }


    public UiLocator parseUiAutoLocator() {
        uiLocator = this.parseUiLocatorValueToLocator(this.value);
        return uiLocator;
    }


    public static void main(String[] args) {
        UiAutoParser parser = new UiAutoParser("ui","text*=com.tencent.mm:id/hu");
        UiLocator uiLocator = parser.parseUiAutoLocator();
        System.out.println("hello");

    }

    public UiLocator parseUiLocatorValueToLocator(String uiLocatorValue) {

        UiLocator uiLocator = null;
        // text=Send
        String pattern = "^([a-z]+)(.*\\=)([\\s\\S]+?)$";
        // Create a pattern object
        Pattern r = Pattern.compile(pattern);
        // Create a matcher object
        Matcher m = r.matcher(uiLocatorValue);

        if(m.find()) {
            uiLocator = new UiLocator();
            uiLocator.setAttrName(m.group(1));
            uiLocator.setMatchType(m.group(2));
            uiLocator.setUiValue(m.group(3));

        }

        return uiLocator;
    }


    public  String parseUiAutoString() throws Exception{
        //Use ui-automator locator to locate the element on the screen
        List<String> uiAutoLocators = getUiAutoLocators();
        uiLocator = this.parseUiAutoLocator();
        boolean inUiAutoLocators = uiAutoLocators.contains(uiLocator.getAttrName());
        if (inUiAutoLocators) {
            if (uiLocator.getAttrName().equals("text")) {
                return this.parseTextLocatorString();
            } else if (uiLocator.getAttrName().equals("desc")) {
                return this.parseDescString();
            } else if (uiLocator.getAttrName().equals("res")) {
                return makeUiSelectorString("resourceId", uiLocator.getUiValue());
            }
        } else {
            throw new JNotSupportedLocatorException("Unsupported selector exception:[" + uiLocator.getAttrName() + "]",type,value);
        }

        return null;
    }
}
