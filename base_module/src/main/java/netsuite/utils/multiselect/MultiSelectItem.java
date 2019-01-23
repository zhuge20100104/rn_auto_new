package netsuite.utils.multiselect;

import netsuite.utils.table.ListRow;
import netsuite.utils.table.NSLocator;


public class MultiSelectItem extends ListRow {
    private boolean isSelected;

    public MultiSelectItem(String label, NSLocator itemLocator, boolean isSelected) {
        super(itemLocator, label);
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return this.isSelected;
    }
}
