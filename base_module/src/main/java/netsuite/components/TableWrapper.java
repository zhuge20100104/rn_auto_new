package netsuite.components;

import base.exceptions.JBaseException;
import base.exceptions.JTableParserFailedException;
import netsuite.utils.NodeUtilities;
import netsuite.utils.table.*;
import config.utils.StringUtils;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import java.util.*;

public class TableWrapper extends WebDocument {
    public TableWrapper(WebDriver webDriver) {
        super(webDriver);
    }

    public String findColumnName(Map<String, Integer> columnsOrder, int index) throws Exception {
        Iterator var3 = columnsOrder.entrySet().iterator();

        Map.Entry item;
        do {
            if (!var3.hasNext()) {
                throw new JTableParserFailedException("Unexpected error occurred!");
            }

            item = (Map.Entry)var3.next();
        } while(!((Integer)item.getValue()).equals(index));

        return (String)item.getKey();
    }

    public List<HashMap<String, String>> parseTable(String tableXPath, String headerItemsIteratorXPath, String rowsIteratorXPath) throws Exception{
        try {
            String text = this.webDriver.getPageSource();
            Document domDocument = this.readXmlSource(text);
            List<String> header = this.prepareTableParse(domDocument, tableXPath, headerItemsIteratorXPath);
            return parseSimpleTableBody(domDocument, this.combineXPaths(tableXPath, rowsIteratorXPath), header);
        } catch (Exception var7) {
            throw new JTableParserFailedException(var7.getMessage());
        }
    }

    public List<DataRowBase> parseTable(String tableXPath, String rowsIteratorXPath, ITableRowTypedParser rowParser) throws Exception{
        return this.parseTable(tableXPath, (String)null, rowsIteratorXPath, rowParser);
    }

    public List<DataRowBase> parseTable(String tableXPath, String headerItemsIteratorXPath, String rowsIteratorXPath, ITableRowTypedParser rowParser) throws Exception {
        try {
            String text = this.webDriver.getPageSource();
            Document domDocument = this.readXmlSource(text);
            List<String> header = this.prepareTableParse(domDocument, tableXPath, headerItemsIteratorXPath);
            Map<String, Integer> columnsOrder = null;
            if (headerItemsIteratorXPath != null && header != null) {
                columnsOrder = this.generateColumnsOrder(header);
            }

            return parseTableBody(domDocument, this.combineXPaths(tableXPath, rowsIteratorXPath), columnsOrder, rowParser);
        } catch (Exception var9) {
            throw new JTableParserFailedException(var9.getMessage());
        }
    }

    public List<ListRow> getTableHeader(String tableXPath, String headerItemsIteratorXPath) throws Exception {
        try {
            ListParser listParser = new ListParser(this.webDriver);
            List<ListRow> rows = listParser.parseList(XPathHelper.combineXPaths(new String[]{tableXPath, headerItemsIteratorXPath}));
            if (rows == null) {
                throw new JBaseException("Unable to parse table header!");
            } else {
                return rows;
            }
        } catch (Exception var5) {
            throw new JBaseException(var5.getMessage());
        }
    }

    public HashMap<String, Integer> getColumnsOrder(String tableXPath, String headerItemsIteratorXPath) throws Exception{
        String text = this.webDriver.getPageSource();
        Document domDocument = this.readXmlSource(text);
        List<String> header = parseHeader(domDocument, XPathHelper.combineXPaths(new String[]{tableXPath, headerItemsIteratorXPath}));
        return this.generateColumnsOrder(header);
    }

    private static List<String> parseHeader(Document domDocument, String headerXPath) throws Exception{
        ArrayList header = new ArrayList();

        try {
            xPathExpression = xPath.compile(headerXPath);
            Object result = xPathExpression.evaluate(domDocument, XPathConstants.NODESET);
            NodeList nodeList = (NodeList)result;

            for(int i = 0; i < nodeList.getLength(); ++i) {
                if (nodeList.item(i).getNodeType() == 1) {
                    String text = StringUtils.consolidateSpaces(NodeUtilities.getText(nodeList.item(i)));
                    text = StringUtils.trim(text);
                    header.add(text);
                }
            }

            return header;
        } catch (Exception var7) {
            throw new JTableParserFailedException(var7.getMessage());
        }
    }

    private static List<DataRowBase> parseTableBody(Document domDocument, String rowsIteratorXPath, Map<String, Integer> columnsOrder, ITableRowTypedParser rowParser) throws Exception {
        try {
            List<DataRowBase> rows = new ArrayList();
            xPathExpression = xPath.compile(rowsIteratorXPath);
            Object result = xPathExpression.evaluate(domDocument, XPathConstants.NODESET);
            NodeList nodeList = (NodeList)result;

            for(int i = 0; i < nodeList.getLength(); ++i) {
                if (nodeList.item(i).getNodeType() == 1) {
                    try {
                        DataRowBase dataRowBase = rowParser.parseRow(nodeList.item(i), columnsOrder, String.format("%s[%d]", rowsIteratorXPath, i + 1));
                        if (dataRowBase != null) {
                            rows.add(dataRowBase);
                        }
                    } catch (Exception var9) {
                        ;
                    }
                }
            }

            return rows;
        } catch (Exception var10) {
            throw new JTableParserFailedException(var10.getMessage());
        }
    }

    private static List<HashMap<String, String>> parseSimpleTableBody(Document domDocument, String rowsIteratorXPath, List<String> columnsOrder) throws Exception{
        try {
            List<HashMap<String, String>> rows = new ArrayList();
            xPathExpression = xPath.compile(rowsIteratorXPath);
            Object result = xPathExpression.evaluate(domDocument, XPathConstants.NODESET);
            NodeList nodeList = (NodeList)result;

            for(int i = 0; i < nodeList.getLength(); ++i) {
                if (nodeList.item(i).getNodeType() == 1) {
                    HashMap<String, String> columns = new HashMap();
                    NodeList childs = nodeList.item(i).getChildNodes();
                    if (childs != null) {
                        for(int j = 0; j < childs.getLength(); ++j) {
                            if (childs.item(j).getNodeType() == 1 && childs.item(j).getNodeName().equalsIgnoreCase("td")) {
                                if (columnsOrder != null) {
                                    columns.put(columnsOrder.get(j), NodeUtilities.getText(childs.item(j)));
                                } else {
                                    columns.put(Integer.toString(j), NodeUtilities.getText(childs.item(j)));
                                }
                            }
                        }
                    }

                    if (columns.size() > 0) {
                        rows.add(columns);
                    }
                }
            }

            return rows;
        } catch (Exception var10) {
            throw new JTableParserFailedException(var10.getMessage());
        }
    }

    private HashMap<String, Integer> generateColumnsOrder(List<String> header) {
        HashMap<String, Integer> order = new HashMap();

        for(int i = 0; i < header.size(); ++i) {
            order.put(StringUtils.consolidateSpaces((String)header.get(i)), i + 1);
        }

        return order;
    }

    private List<String> prepareTableParse(Document domDocument, String tableXPath, String headerItemsIteratorXPath) throws Exception {
        xPathExpression = XPathHelper.getXPathExpression(tableXPath);
        List<String> header = null;
        if (headerItemsIteratorXPath != null) {
            header = parseHeader(domDocument, XPathHelper.combineXPaths(new String[]{tableXPath, headerItemsIteratorXPath}));
        }

        return header;
    }
}
