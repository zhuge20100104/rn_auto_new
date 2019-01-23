package netsuite.utils.table;

import org.w3c.dom.Node;

public interface IListItemTypedParser {
    ListRow parseRow(Node var1, String var2, String var3) throws Exception;
}
