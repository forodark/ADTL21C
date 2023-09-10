package glenlib;

// Define a functional interface for menu functions
interface MenuFunction {
    void execute();
}

interface MenuItem {
    String getText();
}

// Define a menu item class
class Option implements MenuItem {
    String text;
    MenuFunction function;

    public Option(String text, MenuFunction function) {
        this.text = text;
        this.function = function;
    }
    public Option(MenuFunction function) {
        this.text = "";
        this.function = function;
    }

    public String getText() {
        return text;
    }
}

class Title implements MenuItem {
    String text;

    public Title(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

class Subtitle implements MenuItem {
    String text;

    public Subtitle(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

class Line implements MenuItem {
    public Line() {}
    
    public String getText() {
        return "";
    }
}

// Create a Menu class for handling menu interactions
public class Menu {

    //Defaults
    public static final int MENU_WIDTH = 31;

    public static int getPosition(MenuItem[] items, int choice) {
        int result = 0;
        int valid_options = 0;
        while(valid_options != choice) {
            if (items[result] instanceof Title) {
                result++;
            }
            else if (items[result] instanceof Subtitle) {
                result++;
            }
            else if (items[result] instanceof Line) {
                result++;
            }
            else if (items[result] instanceof Option) {
                result++; valid_options++;
            }
        }
        return result-1;
    }

    public static void showMenu(String title, MenuItem[] items) {
        showMenu(title, items, MENU_WIDTH);
    }
    
    public static int menu_return = 0;
    
    public static void showMenu(String title, MenuItem[] items, int menu_width) {

        while (true) {
            Util.clear();

            Style.line(menu_width);
            Style.printCentered(menu_width, title);
            Style.nl();
            Style.line(menu_width);

            
            int i = 0; int excess = 0;
            for (; i+excess < items.length;) {
                if (items[i+excess] instanceof Title) {
                    Style.line(menu_width);
                    Style.printCentered(menu_width, items[i+excess].getText());
                    Style.nl();
                    Style.line(menu_width);
                    excess++;
                } else if (items[i+excess] instanceof Subtitle) {
                    System.out.println(items[i+excess].getText());
                    excess++;
                } else if (items[i+excess] instanceof Line) {
                    Style.line(menu_width);
                    excess++;
                } else {
                    System.out.println("[" + (i + 1) + "] " + items[i+excess].getText());
                    i++;
                }
            }

            System.out.println("[0] Return");
            Style.line();

            int choice = In.getInt("Enter Choice: ");

            if (choice == 0) {
                menu_return = 1;
                return;
            }

            if (choice < 0 || choice > i) {
                Util.invalid();
                continue;
            } 

            Option selected = (Option) items[getPosition(items, choice)];
            selected.function.execute();

            if (menu_return != 1) {
                In.waitEnter();
                Util.clear();
            }
    
            menu_return = 0;

        }
        
    }

}

/* Sample Usage:
    MenuItem[] items = {
        new Option("Option 1", () -> function1()),
        new Title("test title"),
        new Subtitle("test subtitle"),
        new Line(),
        new Option("Option 2", () -> function2()),
    };

    Menu.showMenu("Menu", items);
 */