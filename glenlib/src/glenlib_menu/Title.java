package glenlib_menu;

public class Title implements MenuItem {
    String text;

    public Title(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
