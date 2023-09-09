package glenlib;


public class file {
    public static String getFileExtension(String filename) {
        // Find the position of the last dot in the filename
        int dotPosition = filename.lastIndexOf(".");
        
        // Check if a dot was found and it's not the first or last character
        if (dotPosition != -1 && dotPosition != 0 && dotPosition != filename.length() - 1) {
            // Extract and return the substring after the last dot
            return filename.substring(dotPosition + 1);
        } else {
            // No valid extension found
            return "";
        }
    }

    public static String getFileName(String filename) {
        // Find the position of the last dot in the filename
        int dotPosition = filename.lastIndexOf(".");
        
        // Check if a dot was found and it's not the first or last character
        if (dotPosition != -1 && dotPosition != 0 && dotPosition != filename.length() - 1) {
            // Extract and return the substring before the last dot
            return filename.substring(0, dotPosition);
        } else {
            // No valid extension found, return the entire filename
            return filename;
        }
    }

    
}
