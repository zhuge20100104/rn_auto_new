package netsuite.utils.table;

import base.exceptions.JBaseException;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XPathHelper {
    private static XPathFactory xPathFactory = XPathFactory.newInstance();
    private static XPath xPath;
    private static final String xPathPrefix = "\"(/+)$\"";
    private static final String dotPrefix = "^\\.";
    private static Pattern slashPattern;
    private static Pattern dotPattern;
    private static final Pattern XPATH_DOUBLE_QUOTE_SPLIT_PATTERN;
    private static final Pattern XPATH_SINGLE_QUOTE_SPLIT_PATTERN;

    public XPathHelper() {
    }

    public static String combineXPaths(String... xPath) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < xPath.length; ++i) {
            Matcher matcher;
            if (i == 0) {
                matcher = slashPattern.matcher(xPath[i]);
                if (matcher.find()) {
                    xPath[i] = xPath[i].replaceAll("\"(/+)$\"", "");
                }

                builder.append(xPath[i]);
            } else {
                matcher = dotPattern.matcher(xPath[i]);
                if (matcher.find()) {
                    xPath[i] = xPath[i].replaceAll("^\\.", "");
                }

                builder.append(xPath[i]);
            }
        }

        return builder.toString();
    }

    public static String combineXPaths(NSLocator locator1, NSLocator locator2) throws Exception{
        if (locator1.getLocatorType() == locator2.getLocatorType() && locator1.getLocatorType().equals(LocatorType.XPATH)) {
            return combineXPaths(locator1.getLocatorExpression(), locator2.getLocatorExpression());
        } else {
            throw new JBaseException("Just xPath locators are supported!");
        }
    }

    public static String combineXPaths(NSLocator locator1, String xPath)  throws Exception{
        if (locator1.getLocatorType().equals(LocatorType.XPATH)) {
            return combineXPaths(locator1.getLocatorExpression(), xPath);
        } else {
            throw new JBaseException("Just xPath locators are supported!");
        }
    }

    public static NSLocator combineLocators(NSLocator locator1, NSLocator locator2) throws Exception {
        String xPath = combineXPaths(locator1, locator2);
        return NSLocator.xpath(xPath);
    }

    public static NSLocator combineLocators(NSLocator locator1, String xPath) throws Exception{
        String result = combineXPaths(locator1, xPath);
        return NSLocator.xpath(result);
    }

    public static XPathExpression getXPathExpression(String xPathStr) throws Exception {
        try {
            return xPath.compile(xPathStr);
        } catch (XPathExpressionException var2) {
            throw new JBaseException(var2.getMessage());
        }
    }

    @Nonnull
    public static String hasCssClass(@Nonnull String cls) {
        return "contains(concat(' ', normalize-space(@class), ' '), " + getQuoteSafeXPathString(" " + StringUtils.strip(cls, " ") + " ") + ")";
    }

    @Nonnull
    public static String getQuoteSafeXPathString(@Nonnull String str) {
        if (!str.contains("'")) {
            return "'" + str + "'";
        } else if (!str.contains("\"")) {
            return "\"" + str + "\"";
        } else {
            int singleCount = StringUtils.countMatches(str, "'");
            int doubleCount = StringUtils.countMatches(str, "\"");
            String quote;
            String quotedQuote;
            Pattern splitPattern;
            if (singleCount > doubleCount) {
                quote = "\"";
                quotedQuote = "'\"'";
                splitPattern = XPATH_DOUBLE_QUOTE_SPLIT_PATTERN;
            } else {
                quote = "'";
                quotedQuote = "\"'\"";
                splitPattern = XPATH_SINGLE_QUOTE_SPLIT_PATTERN;
            }

            List<String> xpathConcatList = new ArrayList();
            String[] substrings = splitPattern.split(str, -1);

            for(int i = 0; i < substrings.length; ++i) {
                String chunk = substrings[i];
                if (!chunk.equals("")) {
                    xpathConcatList.add(quote + chunk + quote);
                }

                if (i < substrings.length - 1) {
                    xpathConcatList.add(quotedQuote);
                }
            }

            return "concat(" + StringUtils.join(xpathConcatList, ", ") + ")";
        }
    }

    static {
        xPath = xPathFactory.newXPath();
        slashPattern = Pattern.compile("\"(/+)$\"");
        dotPattern = Pattern.compile("^\\.");
        XPATH_DOUBLE_QUOTE_SPLIT_PATTERN = Pattern.compile("\"");
        XPATH_SINGLE_QUOTE_SPLIT_PATTERN = Pattern.compile("'");
    }
}

