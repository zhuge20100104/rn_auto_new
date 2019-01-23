package netsuite.utils.table;

import org.w3c.dom.Node;

import java.util.Map;

public interface ITableRowTypedParser {
    DataRowBase parseRow(Node var1, Map<String, Integer> var2, String var3);
}
