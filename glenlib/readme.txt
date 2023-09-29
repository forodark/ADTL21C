==================================================================================================================        
  ██████╗ ██╗     ███████╗███╗   ██╗██╗     ██╗██████╗         █  █▀▀█  █▀▀█ 
 ██╔════╝ ██║     ██╔════╝████╗  ██║██║     ██║██╔══██╗ ▄▄  ▄  █  █▄▄█  █▄▄▀ 
 ██║  ███╗██║     █████╗  ██╔██╗ ██║██║     ██║██████╔╝ ▀▀  █▄▄█  █  █  █  █
 ██║   ██║██║     ██╔══╝  ██║╚██╗██║██║     ██║██╔══██╗
 ╚██████╔╝███████╗███████╗██║ ╚████║███████╗██║██████╔╝  v1.4
  ╚═════╝ ╚══════╝╚══════╝╚═╝  ╚═══╝╚══════╝╚═╝╚═════╝   by Glen Angelo Bautista          
==================================================================================================================
 This is my header file containing a bunch of useful functions that I made to make my life easier.
 Please explore the file to learn more.
==================================================================================================================
Latest changes (v1.4)
    - added search function for multiple getter methods
    - reorganized classes in glenlib_objects
    - new Obj class under glenlib_objects
        > moved some of the Sort methods header
        > append and remove methods
        > select function (like search but single object return and query must be exact)
            - case-sensitive and non-case sensitive variations
        > exists function
        > extractCol function
    - new functions under Sort class
        > shuffle
        > filter & exclude
    - fixed bug where private members cannot be accessed by invokeGetter
    - improved compare function to be able to handle numeric strings and filter special characters
    - fixed bug in compare function where number variables would get sorted incorrectly
    - new Calc class under new package glenlib_math
        > rand
        > mean
        > variance
        > sd
        > median
        > mode
    todo:
        - expression class that can store mathematical expressions
Changes (v1.3)
    - added overloaded function for printTitle
    - added overloaded function for exit
    - fixed bug regarding input buffer in truncate
    - added new Sort class under new package glenlib_objects
        > selection
        > insertion
        > bubble
        > invert
        > other supporting functions
            - swap
            - compare
            - isAscending
            - isDescending
        > search
Latest changes (v1.2)
    - revamped invalid inputs for In class
        > now clears the error message after enter
    - unified invalid and exit methods
    - added accepted chars parameter to getChar
    - added support for adding title to auto table
    - added default width attribute to the Util class
        > use Util.setWidth to change the width of the line in invalid and exit messages

Changes (v1.1)
    - auto() for table columns
    - fixed extractDecimal to return -1 if none found

Changes (v1.0)
    - Separated menus and tables into their own packages
        > glenlib_menu (needs glenlib)
        > glenlib_table (needs glenlib)
        > glenlib_table_page (needs glenlib, glenlib_table, and glenlib_menu)
    - Moved glenlib.File to glenlib.Str
    
Changes (pre3)
    - fixed default precision for formatWidth
    - table now shows N/A if getter method doesnt exist
    - table now truncates header if it exceeds the column width

Changes (pre2)
    - Added new way to build tables with Tbl class
    - Add handling to print empty if null

Changes (pre1)
    - Now remade for Java (from glenlib.hpp v1.1)
    - Style functions (glenlib.style)
        > printColor
        > color
        > line
        > printCentered
        > nl (newLine)
        > Macros for System.out functions
            - print
            - printf
            - println
    - Utility functions (glenlib.util)
        > clear
        > sleep
        > invalid
        > exit
    - String related functions (glenlib.str)
        > convertString
        > trim
        > trimZeros
        > truncate
        > formatString
        > extractNumber
        > extractDecimal
        > isNumericChar
        > isEmpty
        > paragraph
        > getFileExtension
        > getFileName
    - Input functions (glenlib.in)
        > getInt
        > getFloat
        > getDouble
        > getChar
        > getString
        > getBool
        > waitEnter
    - Menu functions (glenlib.Menu)
        > showMenu
        > returnFromMenu
    - Table functions (glenlib.Table)
        > printFull
        > printPage
            - nextPage
            - prevPage
            - dontWait
Upcoming changes
    - File processing
==================================================================================================================
 
