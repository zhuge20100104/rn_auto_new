package netsuite.utils.table;


public class ListRow {
    private NSLocator rowLocator;
    private String name;

    public ListRow(NSLocator rowLocator, String name) {
        this.name = name;
        this.rowLocator = rowLocator;
    }

    public NSLocator getRowLocator() {
        return this.rowLocator;
    }

    public String getName() {
        return this.name;
    }
}