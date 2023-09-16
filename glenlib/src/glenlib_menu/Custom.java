package glenlib_menu;

public class Custom implements MenuItem {
    String text;
    MenuFunction function;

    public Custom(String text, MenuFunction function) {
        this.text = text;
        this.function = function;
    }
    public Custom(MenuFunction function) {
        this.text = "";
        this.function = function;
    }

    public String getText() {
        return text;
    }
}
