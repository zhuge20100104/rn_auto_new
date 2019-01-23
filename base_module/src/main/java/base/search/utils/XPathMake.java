package base.search.utils;

import config.utils.StringUtils;

/**
 * The Xpath make class
 * <br> Make xpath from property and value string
 */
public class XPathMake {

    /**
     * Make a contains property for xpath locator, eg. //span[contains(@text(),'hello')]
     * @param propertyType  Contains type, can be [contains], [starts-with] and [ends-with]
     * @param resultXpath   The xpath that return as result
     * @param propAndVal  Property and value array, such as   {"text","hello"}   {"class","oj-color-button"}
     * @return  Xpath as result
     */
    public static String makeContainsProperty(String propertyType, String resultXpath, String [] propAndVal) {
        resultXpath += "["+propertyType+"(";
        if(!propAndVal[0].equals("text")) {
            resultXpath += "@";
            resultXpath += propAndVal[0];
        }else {
            resultXpath += propAndVal[0];
            resultXpath += "()";
        }

        resultXpath += ",";
        resultXpath += "'";

        propAndVal[1] = propAndVal[1].replace("`","|");
        propAndVal[1] = propAndVal[1].replace("``","=");

        resultXpath += propAndVal[1];
        resultXpath += "'";
        resultXpath += ")]";

        return resultXpath;
    }

    /**
     * Make a equals property for xpath locator, eg. //span[@text()='hello']
     * @param resultXpath  The xpath to be returned
     * @param propAndVal  Property and value array, eg.  {"text","hello"}   {"class","oj-color-button"}
     * @return Xpath as result
     */
    public static String makeEqualsProperty(String resultXpath, String [] propAndVal) {
        resultXpath += "[";
        if(!propAndVal[0].equals("text")) {
            resultXpath += "@";
            resultXpath += propAndVal[0];
        }else {
            resultXpath += propAndVal[0];
            resultXpath += "()";
        }

        resultXpath += "=";
        resultXpath += "'";

        propAndVal[1] = propAndVal[1].replace("`","|");
        propAndVal[1] = propAndVal[1].replace("``","=");

        resultXpath += propAndVal[1];
        resultXpath += "'";
        resultXpath += "]";

        return resultXpath;
    }


    /**
     * Make a single xpath for an element, eg.  //input[@text()='hello']
     * @param eleType  The element type,can be [span],[input] and others
     * @param properties  Properties string, eg.  [class=oj-color-button|text=hello]
     * @return  The xpath as result
     */
    public static String makeOwnXpath(String eleType,String  properties) {

        String resultXpath = "//";

        if (StringUtils.isEmpty(eleType)) {
            eleType = "*";
        }

        resultXpath += eleType;


        if(!StringUtils.isEmpty(properties)) {
            //Special  delimiter , | will be encoded as ||
            properties = properties.replace("||", "`");
            properties = properties.replace("==", "``");
            String[] arrProperties = properties.split("\\|");


            for (String property : arrProperties) {
                if (property.contains("*=")) {
                    String[] propAndVal = property.split("\\*=");
                    resultXpath = makeContainsProperty("contains", resultXpath, propAndVal);
                } else if (property.contains("^=")) {
                    String[] propAndVal = property.split("\\^=");
                    resultXpath = makeContainsProperty("starts-with", resultXpath, propAndVal);
                } else if (property.contains("$=")) {
                    String[] propAndVal = property.split("\\$=");
                    resultXpath = makeContainsProperty("ends-with", resultXpath, propAndVal);
                } else if (property.contains("=")) {
                    String[] propAndVal = property.split("\\=");
                    resultXpath = makeEqualsProperty(resultXpath, propAndVal);
                }
            }
        }

        return resultXpath;
    }


    /**
     * Get the nth element path, eg.  (span[@text()='hello'])[1]
     * @param eleType  The element type,can be [span],[input] and others
     * @param properties  Properties string, eg.  [class=oj-color-button|text=hello]
     * @param nth  Get the nth element, index is counted from 1.
     * @return  The nth element's xpath
     */
    public static String makeNthXPath(String eleType,String properties,int nth) {
        String ownXPath = makeOwnXpath(eleType,properties);
        ownXPath = "("+ ownXPath+ ")";
        ownXPath +="[";
        ownXPath += nth;
        ownXPath += "]";
        return ownXPath;
    }


    /**
     * Make the child element's xpath, eg  //div[class*=oj-color-button]//span[@text()='hello']
     * @param parentType Parent element type, can be [span] [div] and others
     * @param parentProperties  Parent element properties, eg. [text=hello]
     * @param childType   Child element type, can be [span] [div] and others
     * @param childProperties   Child element properties, eg. [text=hello]
     * @param isDirectChild  Whether the child is directly child of the parent
     * @return  The child's xpath
     */
    public static String makeChildXPath(String parentType, String parentProperties, String childType,String childProperties, boolean isDirectChild)  {
        String resultXpath = "";
        String parentXpath = makeOwnXpath(parentType,parentProperties);
        resultXpath += parentXpath;
        if(isDirectChild) {
            resultXpath += "/";
        }else {
            resultXpath += "//";
        }

        resultXpath += makeOwnXpath(childType,childProperties).substring(2);
        return resultXpath;
    }

    /**
     * Make the child element's xpath, eg  //div[class*=oj-color-button]//span[@text()='hello']
     * <br>  The child is directly child.
     * @param parentType Parent element type, can be [span] [div] and others
     * @param parentProperties  Parent element properties, eg. [text=hello]
     * @param childType  Child element type, can be [span] [div] and others
     * @param childProperties Child element properties, eg. [text=hello]
     * @return The child's xpath
     */
    public static String makeChildXPath(String parentType, String parentProperties, String childType,String childProperties)  {
        return makeChildXPath(parentType,parentProperties,childType,childProperties,true);
    }
}
