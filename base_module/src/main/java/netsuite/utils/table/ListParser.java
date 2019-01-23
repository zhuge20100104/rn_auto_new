package netsuite.utils.table;

import base.exceptions.JBaseException;
import netsuite.utils.NodeUtilities;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import java.util.ArrayList;
import java.util.List;

public class ListParser extends WebDocument {
    public ListParser(WebDriver webDriver) {
        super(webDriver);
    }

    public List<ListRow> parseList(String itemsXPath, IListItemTypedParser rowParser) throws Exception{
        try {
            xPathExpression = xPath.compile(itemsXPath);
            String text = this.webDriver.getPageSource();
            Document domDocument = this.readXmlSource(text);
            return this.parseListBody(domDocument, itemsXPath, rowParser);
        } catch (Exception var5) {
            throw new JBaseException(var5.getMessage());
        }
    }

    public List<ListRow> parseList(String itemsXPath) throws Exception {
        try {
            xPathExpression = xPath.compile(itemsXPath);
            String text = this.webDriver.getPageSource();
            Document domDocument = this.readXmlSource(text);
            return this.parseListBody(domDocument, itemsXPath, (IListItemTypedParser)null);
        } catch (Exception var4) {
            throw new JBaseException(var4.getMessage());
        }
    }

    private List<ListRow> parseListBody(Document domDocument, String rowsIteratorXPath, IListItemTypedParser rowParser) throws Exception{
        try {
            List<ListRow> rows = new ArrayList();
            xPathExpression = xPath.compile(rowsIteratorXPath);
            Object result = xPathExpression.evaluate(domDocument, XPathConstants.NODESET);
            NodeList nodeList = (NodeList)result;

            for(int i = 0; i < nodeList.getLength(); ++i) {
                if (nodeList.item(i).getNodeType() == 1) {
                    try {
                        String name = NodeUtilities.getText(nodeList.item(i)).trim();
                        String xPath = String.format("%s[%d]", rowsIteratorXPath, i + 1);
                        if (rowParser != null) {
                            rows.add(rowParser.parseRow(nodeList.item(i), name, xPath));
                        } else {
                            rows.add(new ListRow(NSLocator.xpath(xPath), name));
                        }
                    } catch (Exception var10) {
                        ;
                    }
                }
            }

            return rows;
        } catch (Exception var11) {
            throw new JBaseException(var11.getMessage());
        }
    }
}

