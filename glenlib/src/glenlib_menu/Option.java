package glenlib_menu;

public class Option implements MenuItem {
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
