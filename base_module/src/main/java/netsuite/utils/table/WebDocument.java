package netsuite.utils.table;


import base.exceptions.JTableParserFailedException;
import netsuite.utils.NodeUtilities;
import config.utils.StringUtils;
import org.apache.xpath.XPathAPI;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebDocument {
    protected static XPathFactory xPathFactory;
    protected static XPath xPath;
    protected static XPathExpression xPathExpression;
    protected static Pattern pattern;
    protected static final String xPathPrefix = "\"(/+)$\"";
    private static Map<String, XPathExpression> xPathExpressionCache = new HashMap();
    protected final WebDriver webDriver;

    public WebDocument(WebDriver webDriver) {
        this.initXPathFactory();
        this.webDriver = webDriver;
    }

    public NodeList selectNodes(WebDriver webdriver, String xPath) throws Exception {
        String html = webdriver.getPageSource();
        return this.selectNodes(html, xPath);
    }

    public Node selectSingleNode(WebDriver webdriver, String xPath) throws Exception {
        String html = webdriver.getPageSource();
        NodeList nodeList = this.selectNodes(html, xPath);
        return nodeList != null && nodeList.getLength() > 0 ? nodeList.item(0) : null;
    }

    public NodeList selectNodes(String html, String xPathIterator) throws Exception {
        try {
            Document domDocument = this.readXmlSource(html);
            XPathExpression labelsXPath = this.getXPathExpression(xPathIterator);
            NodeList nodeList = (NodeList)labelsXPath.evaluate(domDocument, XPathConstants.NODESET);
            return nodeList;
        } catch (XPathExpressionException var6) {
            throw new JTableParserFailedException(String.format("Invalid xPath: '%s'", xPathIterator));
        }
    }

    public NodeList selectNodes(Node contextNode, String xPath) {
        try {
            return XPathAPI.selectNodeList(contextNode, xPath);
        } catch (TransformerException var4) {
            return null;
        }
    }

    public Node selectSingleNode(Node contextNode, String xPath) throws Exception {
        if (contextNode == null) {
            throw new JTableParserFailedException("ContextNode is not defined!");
        } else if (xPath == null) {
            throw new JTableParserFailedException("xPath is not defined!");
        } else {
            try {
                Node node = XPathAPI.selectSingleNode(contextNode, xPath);
                return node;
            } catch (TransformerException var4) {
                return null;
            }
        }
    }

    public Node getDocumentNode(String string) throws Exception {
        Document domDocument = this.readXmlSource(string);
        return domDocument.getDocumentElement();
    }

    public String getPageBody(String html) throws Exception{
        Document document = this.readXmlSource(html);
        NodeList nodes = document.getElementsByTagName("body");
        if (nodes != null && nodes.getLength() > 0) {
            return NodeUtilities.getText(nodes.item(0));
        } else {
            throw new JTableParserFailedException("Page does not have 'body' element!");
        }
    }

    public Document readXmlSource(String text) throws Exception{
        Tidy tidy = TidyManager.getTidy();

        try {
            ByteArrayInputStream is = new ByteArrayInputStream(text.getBytes("UTF-8"));
            tidy.setInputEncoding("UTF-8");
            Document domDocument = tidy.parseDOM(is, (OutputStream)null);
            return domDocument;
        } catch (Exception var6) {
            throw new JTableParserFailedException(var6.getMessage() + StringUtils.getStackTrace(var6));
        }
    }

    protected XPathExpression getXPathExpression(String xPathString) throws XPathExpressionException {
        if (xPathExpressionCache.containsKey(xPathString)) {
            return (XPathExpression)xPathExpressionCache.get(xPathString);
        } else {
            xPathExpressionCache.put(xPathString, xPath.compile(xPathString));
            return (XPathExpression)xPathExpressionCache.get(xPathString);
        }
    }

    private void initXPathFactory() {
        xPathFactory = XPathFactory.newInstance();
        xPath = xPathFactory.newXPath();
        pattern = Pattern.compile("\"(/+)$\"");
    }

    protected String combineXPaths(String xPath1, String xPath2) {
        Matcher matcher = pattern.matcher(xPath1);
        if (matcher.find()) {
            xPath1 = xPath1.replaceAll("\"(/+)$\"", "");
        }

        return String.format("%s%s", xPath1, xPath2);
    }
}
