package glenlib_table;

import java.util.List;

import glenlib.Str;
import glenlib.Style;
import glenlib.In;
import glenlib.Util;
import glenlib_menu.Menu;

public class PageTable extends Table {
    public int page;
    public int max_page;
    int page_row_counter;
    int table_row_counter;
    public int table_width;
    String saved_title;

    public PageTable(List<TableColumn<?>> columns) {
        super(columns);
    }

    public int getTable_width() {
        return table_width;
    }

    public void printPage(String title) {
        printPage(title, false, false);
    }

    public void printPage(String title, Boolean no_menu) {
        printPage(title, no_menu, false);
    }

    public void printPage(String title, Boolean no_menu, Boolean dont_reset) {
        saved_title = title;
        int num_columns = columns.size();
        table_width = num_columns + 1;

        for (TableColumn<?> column : columns) {
            table_width += Str.extractNumber(column.getFormat());
        }
        int content_size = columns.get(0).getData().size();

        if (dont_reset == false) {
        page = 0;
        max_page = (content_size / TABLE_PAGE_LENGTH) - 1;
        page_row_counter = 0; table_row_counter = 0;  
        }


        if (content_size % TABLE_PAGE_LENGTH != 0) {
            max_page++;
        }
        
        while(true) {
            Util.clear();
            // Print title
            Style.line(table_width);

            if (!title.isEmpty()) {
                Style.printCentered(table_width, title);
                System.out.println();
                Style.line(table_width);
            }

            // Print headers
            System.out.print("|");
            for (TableColumn<?> column : columns) {
                int column_width = Str.extractNumber(column.getFormat());
                Style.printCentered(column_width, column.getHeader());
                
                System.out.print("|");  
            }

            System.out.println();

            // Print content
            for (; page_row_counter < TABLE_PAGE_LENGTH && table_row_counter < content_size; page_row_counter++, table_row_counter++) {
                System.out.print("|");
                for (TableColumn<?> column : columns) {
                    List<?> column_data = column.getData();
                    Object value = invokeGetter(column_data, table_row_counter, column.getGetterMethod());

                    System.out.print(" " + Str.formatString(value, Str.extractNumber(column.getFormat())-1, Str.extractDecimal(column.getFormat())) + "|");
                }
                System.out.println();
            }
            page_row_counter = 0;
            Style.line(table_width);

            if (no_menu == false) {

                Style.printf(" Page %d of %d   ", page+1, max_page+1);
                if (page > 0) {
                    Style.print(" [8] Previous   ");
                }
                if (table_width < 31) {
                    Style.nl();
                }
                if (page != max_page) {
                    Style.print(" [9] Next   ");
                }
                Style.printf(" [0] Return%n");

                Style.line(table_width);

                int choice = In.getInt("Enter Choice: ");

                switch (choice) {
                    case 8:
                        if (page > 0) {
                            page--;
                            table_row_counter = page*TABLE_PAGE_LENGTH;
                            break;
                        }
                        else {
                            Util.invalid(Util.INVALID, table_width);
                            table_row_counter = page*TABLE_PAGE_LENGTH;
                            continue;
                        }
                    case 9:
                        if (page != max_page) {
                            page++;
                            table_row_counter = page*TABLE_PAGE_LENGTH;
                            break;
                        }
                        else {
                            Util.invalid(Util.INVALID, table_width);
                            table_row_counter = page*TABLE_PAGE_LENGTH;
                            continue;
                        }
                    case 0:
                        Style.line(table_width);
                        return;
                    default:
                        Util.invalid(Util.INVALID, table_width);
                        table_row_counter = page*TABLE_PAGE_LENGTH;
                        continue;
                    
                }
            }
            else {
                break;
            }
        }
    }

    public void nextPage() {
        if (page != max_page) {
            page++;
            table_row_counter = page*TABLE_PAGE_LENGTH;
            printPage(saved_title, true, true);
        }
        else {
            Util.invalid("Page does not exist", table_width);
            table_row_counter = page*TABLE_PAGE_LENGTH;
        }
        Menu.dontWait();
    }

    public void prevPage() {
        if (page > 0) {
            page--;
            table_row_counter = page*TABLE_PAGE_LENGTH;
            printPage(saved_title, true, true);
        }
        else {
            Util.invalid("Page does not exist", table_width);
            table_row_counter = page*TABLE_PAGE_LENGTH;
        }
        Menu.dontWait();
    }
}
