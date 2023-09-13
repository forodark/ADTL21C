package glenlib;


public class File {
    public static String getFileExtension(String filename) {
        // Find the position of the last dot in the filename
        int dot_position = filename.lastIndexOf(".");
        
        // Check if a dot was found and it's not the first or last character
        if (dot_position != -1 && dot_position != 0 && dot_position != filename.length() - 1) {
            // Extract and return the substring after the last dot
            return filename.substring(dot_position + 1);
        } else {
            // No valid extension found
            return "";
        }
    }

    public static String getFileName(String filename) {
        // Find the position of the last dot in the filename
        int dot_position = filename.lastIndexOf(".");
        
        // Check if a dot was found and it's not the first or last character
        if (dot_position != -1 && dot_position != 0 && dot_position != filename.length() - 1) {
            // Extract and return the substring before the last dot
            return filename.substring(0, dot_position);
        } else {
            // No valid extension found, return the entire filename
            return filename;
        }
    }
    
}
