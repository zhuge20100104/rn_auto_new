package netsuite.components;


import base.search.NSSearchTools;
import base.search.engine.parser.JBy;
import com.google.common.base.Predicate;
import netsuite.utils.NodeUtilities;
import netsuite.utils.multiselect.LocatorConverter;
import netsuite.utils.multiselect.MultiSelectItem;
import netsuite.utils.table.IListItemTypedParser;
import netsuite.utils.table.ListParser;
import netsuite.utils.table.ListRow;
import netsuite.utils.table.NSLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.w3c.dom.Node;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class MultiSelectWrapper {
    private static String newLabel = "- New -";
    private String pairingAttribute = "nlmultidropdown";
    private String identityLocator = "./descendant-or-self::table[@nlmultidropdown]";
    private String tableRowsXPathIteratorTemplate = "//div[@class='dropdownDiv' and @nlmultidropdown='%s']//table//tr";
    private String disabledXPath = "//a[contains(@style,'color: rgb(170, 170, 170')]";
    protected ListParser listParser;
    protected MultiSelectWrapper.RowParser rowParser = new MultiSelectWrapper.RowParser();

    protected JBy parentBy;
    protected WebDriver driver;
    protected NSSearchTools sTools;

    protected WebElement widgetElement = null;
    protected Predicate<WebElement> isItMePredicate;


    public MultiSelectWrapper(WebDriver webDriver,String fieldId) {
        String xpath ="//input[@name='" + fieldId + "']/ancestor::*[@class='uir-field']";
        JBy by = new JBy();
        by.setType("xpath");
        by.setValue(xpath);

        this.driver = webDriver;
        this.sTools = new NSSearchTools(webDriver);
        this.listParser = new ListParser(webDriver);
        this.parentBy = by;

    }

    public MultiSelectWrapper(WebDriver webDriver, JBy parentBy) {
        this.driver = webDriver;
        this.sTools = new NSSearchTools(webDriver);
        this.listParser = new ListParser(webDriver);
        this.parentBy = parentBy;
    }

    public void clear() throws Exception {
        List<String> selectedItems = this.getValues();
        List<MultiSelectItem> availableOptions = this.parseOptions();
        Iterator var3 = selectedItems.iterator();

        while (true) {
            while (var3.hasNext()) {
                String selectedItem = (String) var3.next();
                Iterator var5 = availableOptions.iterator();

                while (var5.hasNext()) {
                    MultiSelectItem item = (MultiSelectItem) var5.next();
                    if (item.getName().equals(selectedItem)) {
                        NSLocator rowLocator = item.getRowLocator();

                        JBy jRowBy = LocatorConverter.convertToJBy(rowLocator);

                        WebElement itemElement = this.sTools.findElement(jRowBy.getType(),jRowBy.getValue());
                        Actions actions = new Actions(driver);
                        actions.keyDown(Keys.CONTROL).clickAndHold(itemElement).release(itemElement).keyUp(Keys.CONTROL).build().perform();
                        break;
                    }
                }
            }

            return;
        }
    }

    public void setValue(String value) throws Exception{
        List<String> values = new ArrayList();
        values.add(value);
        this.setValues(values);
    }

    public void setValues(List<String> values) throws Exception {
        List<String> visitedOptions = new ArrayList();
        List<MultiSelectItem> availableOptions = this.parseOptions();

        for (int i = 0; i < availableOptions.size(); ++i) {
            MultiSelectItem availableItem = (MultiSelectItem) availableOptions.get(i);
            Iterator var6 = values.iterator();

            while (var6.hasNext()) {
                String value = (String) var6.next();
                if (availableItem.getName().equals(value) && !availableItem.isSelected()) {
                    NSLocator locator = availableItem.getRowLocator();
                    JBy rowBy = LocatorConverter.convertToJBy(locator);
                    WebElement itemElement = this.sTools.findElement(rowBy.getType(),rowBy.getValue());
                    Actions actions = new Actions(driver);
                    actions.keyDown(Keys.CONTROL).clickAndHold(itemElement).release(itemElement).keyUp(Keys.CONTROL).build().perform();
                    visitedOptions.add(value);
                    break;
                }
            }

            if (visitedOptions.size() == values.size()) {
                break;
            }
        }

    }

    public boolean isDisabled() throws Exception{
        NSLocator disableLocator = NSLocator.xpath(this.getTableRowXPath() + this.disabledXPath);
        JBy disableBy = LocatorConverter.convertToJBy(disableLocator);

        WebElement handle = this.sTools.findElement(disableBy.getType(),disableBy.getValue());
        return handle == null;
    }

    public String getValue() throws Exception{
        List<String> options = this.getValues();
        if (options != null && options.size() > 0) {
            StringBuilder buffer = new StringBuilder();
            Iterator var3 = options.iterator();

            while (var3.hasNext()) {
                String item = (String) var3.next();
                buffer.append(item).append("\n");
            }

            return buffer.toString().replaceAll("\n$", "");
        } else {
            return "";
        }
    }

    public void blur() {
    }

    public List<String> getValues() throws Exception {
        List<MultiSelectItem> list = this.parseOptions();
        ArrayList<String> selected = new ArrayList();
        Iterator var3 = list.iterator();

        while (var3.hasNext()) {
            MultiSelectItem item = (MultiSelectItem) var3.next();
            if (item.isSelected()) {
                selected.add(item.getName());
            }
        }

        return selected;
    }

    public List<String> getAllValues()  throws Exception{
        List<MultiSelectItem> list = this.parseOptions();
        ArrayList<String> allItems = new ArrayList();
        Iterator var3 = list.iterator();

        while (var3.hasNext()) {
            MultiSelectItem item = (MultiSelectItem) var3.next();
            allItems.add(item.getName());
        }

        return allItems;
    }

    private List<MultiSelectItem> parseOptions() throws Exception {
        String tableRowsXPathIterator = this.getTableRowXPath();
        List<MultiSelectItem> list = new ArrayList();
        List<ListRow> rows = this.listParser.parseList(tableRowsXPathIterator, this.rowParser);
        Iterator var4 = rows.iterator();

        while (var4.hasNext()) {
            ListRow row = (ListRow) var4.next();
            list.add((MultiSelectItem) row);
        }

        return list;
    }

    protected WebElement getContainerElement() throws Exception{

        this.widgetElement = this.sTools.findElement(this.parentBy.getType(),this.parentBy.getValue());
        return this.widgetElement;
    }

    private String getTableRowXPath() throws Exception {
        String pairingAttributeValue = this.getContainerElement().findElement(By.xpath(this.identityLocator)).getAttribute("nlmultidropdown");
        String tableRowsXPathIterator = String.format(this.tableRowsXPathIteratorTemplate, pairingAttributeValue);
        return tableRowsXPathIterator;
    }

    public void selectItem(int itemIndex) throws Exception {
        List<MultiSelectItem> items = this.parseOptions();
        MultiSelectItem item = (MultiSelectItem) items.get(itemIndex);
        if (item != null) {
            NSLocator rowLocator = ((MultiSelectItem) items.get(itemIndex)).getRowLocator();
            JBy rowBy = LocatorConverter.convertToJBy(rowLocator);
            this.sTools.clickElement(rowBy.getType(),rowBy.getValue());
        }
    }

    public void initDetectionPredicate() {
        this.isItMePredicate = new Predicate<WebElement>() {
            public boolean apply(@Nullable WebElement input) {
                if (input == null) {
                    return false;
                } else {
                    WebElement handle = input.findElement(By.xpath(MultiSelectWrapper.this.identityLocator));
                    return handle != null;
                }
            }
        };
    }

    private class RowParser implements IListItemTypedParser {
        private RowParser() {
        }

        public ListRow parseRow(Node node, String nodeText, String rowXPath) throws Exception {
            Node tdNode = MultiSelectWrapper.this.listParser.selectSingleNode(node, ".//td");
            String nodeClass = NodeUtilities.getAttribute(tdNode, "class");
            boolean isSelected = nodeClass.contains("dropdownSelected");
            String itemXPath = String.format("%s/td//a", rowXPath);
            String mainText = NodeUtilities.getText(tdNode);
            MultiSelectItem item = new MultiSelectItem(mainText, NSLocator.xpath(itemXPath), isSelected);
            return item;
        }
    }
}
