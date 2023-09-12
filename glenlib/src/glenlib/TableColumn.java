package glenlib;

import java.util.List;


public class TableColumn<T> {
    private String header;
    private List<T> data;
    private String format;
    private String getterMethod;

    public TableColumn(String header, List<T> data, String format, String getterMethod) {
        this.header = header;
        this.data = data;
        this.format = format;
        this.getterMethod = getterMethod;
    }

    public String getHeader() {
        return header;
    }

    public List<T> getData() {
        return data;
    }

    public String getFormat() {
        return format;
    }

    public String getGetterMethod() {
        return getterMethod;
    }
}
