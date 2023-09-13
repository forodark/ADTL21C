package glenlib;


public class Test {
    public static void main(String[] args) {
        String longString = "This is a long string that we want to format into a paragraph with a specified width parameter.";
        int width = 20;
        String formattedParagraph = Str.paragraph(longString, width);
        System.out.println(formattedParagraph);
    }
}