package glenlib;

public class Subtitle implements MenuItem {
    String text;

    public Subtitle(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}