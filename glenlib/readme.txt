==================================================================================================================        
  ██████╗ ██╗     ███████╗███╗   ██╗██╗     ██╗██████╗         █  █▀▀█  █▀▀█ 
 ██╔════╝ ██║     ██╔════╝████╗  ██║██║     ██║██╔══██╗ ▄▄  ▄  █  █▄▄█  █▄▄▀ 
 ██║  ███╗██║     █████╗  ██╔██╗ ██║██║     ██║██████╔╝ ▀▀  █▄▄█  █  █  █  █
 ██║   ██║██║     ██╔══╝  ██║╚██╗██║██║     ██║██╔══██╗
 ╚██████╔╝███████╗███████╗██║ ╚████║███████╗██║██████╔╝  v1.0
  ╚═════╝ ╚══════╝╚══════╝╚═╝  ╚═══╝╚══════╝╚═╝╚═════╝   by Glen Angelo Bautista          
==================================================================================================================
 This is my header file containing a bunch of useful functions that I made to make my life easier. The features
 available aren't limited to encryption though. Please explore the file to learn more.
==================================================================================================================
Latest changes (pre3)
    - fixed default precision for formatWidth
    - table now shows N/A if getter method doesnt exist

Latest changes (pre2)
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
        > waitEnter
        > invalid
        > exit
    - File related functions (glenlib.file)
        > getFileExtension
        > getFileName
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
    - Input functions (glenlib.in)
        > getInt
        > getFloat
        > getDouble
        > getChar
        > getString
        > getBool
    - Menu functions (glenlib.Menu)
        > showMenu
        > returnFromMenu
    - Table functions (glenlib.Table)
        > printFull
        > printPage
            - nextPage
            - prevPage
            - dontWait()
Upcoming changes
    - Change printCentered to accept formatting args
    - separate into separate packages like a core lib, then for menus, tables.

==================================================================================================================
 
