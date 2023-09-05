//==================================================================================================================        
//  ██████╗ ██╗     ███████╗███╗   ██╗██╗     ██╗██████╗      █  █  █▀▀█  █▀▀█
// ██╔════╝ ██║     ██╔════╝████╗  ██║██║     ██║██╔══██╗ ▄▄  █▀▀█  █▄▄█  █▄▄█
// ██║  ███╗██║     █████╗  ██╔██╗ ██║██║     ██║██████╔╝ ▀▀  █  █  █     █   
// ██║   ██║██║     ██╔══╝  ██║╚██╗██║██║     ██║██╔══██╗
// ╚██████╔╝███████╗███████╗██║ ╚████║███████╗██║██████╔╝  v1.0
//  ╚═════╝ ╚══════╝╚══════╝╚═╝  ╚═══╝╚══════╝╚═╝╚═════╝   by Glen Angelo Bautista                                       
//==================================================================================================================
// This is my header file containing a bunch of useful functions that I made to make my life easier. The features
// available aren't limited to encryption though. Please explore the file to learn more.
//==================================================================================================================
// Latest Changes (v1.0)
//  - Now converted to C++ (from glencrypt v0.6.1c)
//  - Redesigned the following functions:
//      > printCentered
//      > convertString
//      > formatString
//      > input
//          - Added value returning way to call function (getInt, getFloat, etc.)
//          - Added float
//          - Added min-max functionality for strings
//          - Use function overloading to allow multiple input types without specifying type
//      > inputCustom
//          - Now merged with input
//      > runProgram
//      > compileRunProgram
//      > showMenu
//      > showPageMenu
//      > printTableFull
//      > printTable
//  - Added the following functions:
//      > truncate
//      > trim
//      > trimZeros
//      > getFileName
//      > getFileExtension
//      > run (for handling runProgram() and compileRunProgram())
//      > extractDecimal
//  - Removed the following functions:
//      > openFile (no longer efficient, use ifstream/ofstream instead)
//  - Use std::string instead of char*
//  - Added and change many default configurations
// Upcoming Changes
//	- Line macros for std::cout
//  - Change the aesthetics of menus and tables
//==================================================================================================================

#ifndef GLENLIB_HPP
#define GLENLIB_HPP

#include <iostream>
#include <string>
#include <cstdlib>
#include <cctype>
#include <ctime>
#include <cstdarg>
#include <Windows.h>  //
#include <unistd.h> //
#include <cstddef>
#include <cmath>
#include <cstring>
#include <iomanip>
#include <sstream>
#include <cstdio>
#include <algorithm>
#include <functional>
#include <vector>
#include <fstream>
#include <cstdint>

namespace gc {

//==================================================================================================================
// Max Defaults (Can be configured)
#define MAX_STRING_LENGTH 256
#define MAX_KEY_LENGTH 256
#define MAX_COMMAND_LENGTH 128
#define MAX_ELEMENTS 256

//Default Messages
#define INVALID_CHOICE "Invalid Choice.\n"

//Line Defaults
#define LINE_WIDTH 31
#define LINE_WIDTH_LONG 63

//Input Defaults
#define INPUT_PROMPT "Input: "
#define STRING_INPUT_MIN 0
#define STRING_INPUT_MAX 256

//Menu Defaults
#define MENU_WIDTH 31
#define MENU_PAGE_LENGTH 7

//Table Defaults
#define TABLE_PAGE_LENGTH 10

//==================================================================================================================
// Character list definitions, used for inputCustom()
#define ALPHANUM "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
#define NUMERIC "0123456789."
#define ALPHABET "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

// Character list definitions, used for encryption/decryption
#define CHAR_LIB "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ "
#define DEFAULT_KEY "}Pj? THrxT_{Dh^Q.UdrG&s-X'?zaPBJ-2]dyFD23IV9:'&tT%]:}$m@<~[,oT$"
#define KEYGEN "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!#$%&'()*+,-./:;<=>?@[]^_`{|}~ "

//==================================================================================================================
// Function Prototypes (structure based are commented out)
void printLine(int type);

//==================================================================================================================
// Print Line Functions



// Sample: printLineWidth(10); 
void printLineWidth(int width) { //print line with custom width
    for (int i = 0; i < width; i++) {
        printf("-");
    }
    printf("\n");
}

// Sample: printLine(0);
void printLine(int type = 0) { //print lines in different styles
	switch(type) {
		case 0: //normal (length: 31)
			printLineWidth(LINE_WIDTH);
			break;
		case 1: //fancy
			printf("->->-~-~-~-=-=-=-=-=-~-~-~-<-<-\n");
			break;
		case 2: //long (length: 63)
			printLineWidth(LINE_WIDTH_LONG);
			break;
		default:
			printLineWidth(LINE_WIDTH);
			break;
	}
}

//==================================================================================================================
// Color Macros & Print Functions
#define BLACK 0
#define RED FOREGROUND_RED | FOREGROUND_INTENSITY
#define GREEN FOREGROUND_GREEN | FOREGROUND_INTENSITY
#define BLUE FOREGROUND_BLUE | FOREGROUND_INTENSITY
#define YELLOW FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_INTENSITY
#define MAGENTA FOREGROUND_RED | FOREGROUND_BLUE | FOREGROUND_INTENSITY
#define CYAN FOREGROUND_GREEN | FOREGROUND_BLUE | FOREGROUND_INTENSITY
#define WHITE FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE | FOREGROUND_INTENSITY

// Sample: printColor(RED, "Error %d", number);
void printColor(int text_color, const std::string& format, va_list args) {
    // Get the current console handle
    HANDLE h_console = GetStdHandle(STD_OUTPUT_HANDLE);

    // Store the current console attributes
    CONSOLE_SCREEN_BUFFER_INFO console_info;
    GetConsoleScreenBufferInfo(h_console, &console_info);
    WORD original_attr = console_info.wAttributes;

    // Set the text color
    SetConsoleTextAttribute(h_console, static_cast<WORD>(text_color));
    
    // Print the formatted text
    vprintf(format.c_str(), args);
    
    // Reset the color
    SetConsoleTextAttribute(h_console, original_attr);
}

void printColor(int text_color, const std::string& format, ...) {
    va_list args;
    va_start(args, format);
    printColor(text_color, format, args);
    va_end(args);
}


// Sample: printCentered("Hello", 15); | Result: "     Hello     "
void printCentered(int field_width, const std::string& format, ...) {
    va_list args;
    va_start(args, format);

    // Convert the C++ string to a C-style string
    const char* c_format = format.c_str();

    // Calculate the length of the formatted string
    int length = vsnprintf(nullptr, 0, c_format, args);

    va_end(args);

    va_start(args, format);

    int total_padding = field_width - length;



    // Calculate padding based on field_width and the formatted string length
    int left_padding = total_padding / 2;
    int right_padding = total_padding / 2;

    if (total_padding % 2 != 0) {
        right_padding++;
    }

    // std::cout << field_width << std::endl;
    // std::cout << length << std::endl;

    // std::cout << total_padding << std::endl;
    // std::cout << left_padding << std::endl;
    // std::cout << right_padding << std::endl;

    // Print the left padding
    for (int i = 0; i < left_padding; ++i) {
        std::cout << ' ';
    }

    // Print the formatted string
    vprintf(c_format, args);

    // Print the right padding
    for (int i = 0; i < right_padding; ++i) {
        std::cout << ' ';
    }

    va_end(args);
}

//==================================================================================================================
// Miscelaneous Functions

// Sample: getCharPos("Hello", 'e'); | Result: 1
int getCharPos(const std::string& str, char ch) {
    auto pos = str.find(ch);
    if (pos == std::string::npos) {
        return -1;
    } else {
        return static_cast<int>(pos);
    }
}


void waitEnter() { //waits for user to press enter
    std::cout << "Press Enter to continue...";
    std::cout.flush();
    while (std::cin.get() != '\n') {
        
    }
}

void invalidChoice() {
    printColor(RED, "Invalid choice.\n"); //returns invalid choice
    printLine();
    waitEnter();
}

// Sample: invalidCustom("Invalid choice", 10);
void invalidCustom(const std::string& message, int width) { //returns invalid choice with custom parameters
    printColor(RED, message);
    printLineWidth(width);
    waitEnter();
}


void exitProgram() { //exits program
    system("cls");
    printLine();
    std::cout << "Exiting Program..." << std::endl;
    printLine();
    std::exit(0);
}

//Sample: exitCustom("BYE!", 10);
void exitCustom(const std::string& message, int width) { //exits program with custom parameters
    system("cls");
    printLineWidth(width);
    std::cout << message << std::endl;
    printLineWidth(width);
    exit(0);
}

//==================================================================================================================
// Data Type & String Formatting Functions
		
// Sample: convertString(100, "int", output); | Result: "100"
template <typename T>
std::string convertString(const T& value) {
    std::stringstream ss;
    ss << value;
    return ss.str();
}

std::string trim(const std::string& str) {
    size_t start = str.find_first_not_of(" \t\n\r");
    size_t end = str.find_last_not_of(" \t\n\r");

    if (start == std::string::npos || end == std::string::npos) {
        // Handle the case where the string is all whitespace
        return "";
    }

    return str.substr(start, end - start + 1);
}

std::string trimZeros(const std::string& numStr) {
    size_t decimalPointPos = numStr.find('.');
    
    if (decimalPointPos != std::string::npos) {
        size_t lastNonZeroDigit = numStr.find_last_not_of('0');
        
        if (lastNonZeroDigit == decimalPointPos) {
            // If there are no non-zero digits after the decimal point, remove it too.
            return numStr.substr(0, decimalPointPos);
        } else {
            // Remove trailing zeros after the decimal point.
            return numStr.substr(0, lastNonZeroDigit + 1);
        }
    }

    // If there's no decimal point, no action is needed.
    return numStr;
}

std::string truncate(const std::string& inputString, int width) {

    if (width == 0) {
        return inputString;
    }

    std::string input_buffer = trimZeros(inputString);

    if (static_cast<int>(input_buffer.length()) > width) {
        std::string truncated = input_buffer.substr(0, static_cast<std::string::size_type>(width - 2)) + "..";
        return truncated;
    }
    if (static_cast<int>(input_buffer.length()) < width) {
        std::string truncated = input_buffer;
        for (int i = 0; i < (width - static_cast<int>(input_buffer.length())); i++) {
            truncated += " ";
        }
        return truncated;
    }
    return input_buffer;
}

template <typename T>
std::string formatString(const T& value, int width = 0, int precision = -1) {
    std::stringstream ss;
    
    if ((precision >= 0 && typeid(T) == typeid(float)) || typeid(T) == typeid(double)) {
        ss << std::fixed << std::setprecision(precision);
    }

    ss << value;
    
    return truncate(ss.str(), width);
}

// Sample: typeFromFormat("%s"); | Result: "string"
std::string typeFromFormat(const std::string& format) {
    if (format.find("s") != std::string::npos) {
        return "string";
    }
    else if (format.find("lf") != std::string::npos){
        return "double";
    }
    else if (format.find("f") != std::string::npos) {
        return "float";
    }
    else if (format.find("c") != std::string::npos) {
        return "char";
    }
    else if (format.find("d") != std::string::npos) {
        return "int";
    }
    else if (format.find("b") != std::string::npos) {
        return "bool";
    }
    else {
        return "unknown";
    }
}

// Sample: extractNumber("abc123.45"); | Result: 123
int extractNumber(const std::string& str) {
    // Skip leading whitespaces and non-digit characters
    size_t pos = 0;
    while (pos < str.length() && !isdigit(str[pos])) {
        pos++;
    }
    
    // Extract the numeric part (integral part only)
    int result = 0;
    while (pos < str.length() && isdigit(str[pos])) {
        result = result * 10 + (str[pos] - '0');
        pos++;
    }
    
    return result; // Return the extracted number
}

int extractDecimal(const std::string& str) {
    // Find the position of the decimal point
    size_t decimalPos = str.find('.');
    
    if (decimalPos == std::string::npos) {
        // No decimal point found, return 0
        return 0;
    }
    
    // Extract the digits after the decimal point
    int result = 0;
    
    for (size_t i = decimalPos + 1; i < str.length(); i++) {
        if (isdigit(str[i])) {
            result = result * 10 + (str[i] - '0');
        } else {
            // If a non-digit character is encountered, stop extracting
            break;
        }
    }
    
    return result; // Return the extracted decimal value
}

std::string getFileExtension(const std::string& filename) {
    // Find the position of the last dot in the filename
    size_t dotPosition = filename.find_last_of(".");
    
    // Check if a dot was found and it's not the first or last character
    if (dotPosition != std::string::npos && dotPosition != 0 && dotPosition != filename.length() - 1) {
        // Extract and return the substring after the last dot
        return filename.substr(dotPosition + 1);
    } else {
        // No valid extension found
        return "";
    }
}

std::string getFileName(const std::string& filename) {
    // Find the position of the last dot in the filename
    size_t dotPosition = filename.find_last_of(".");
    
    // Check if a dot was found and it's not the first or last character
    if (dotPosition != std::string::npos && dotPosition != 0 && dotPosition != filename.length() - 1) {
        // Extract and return the substring before the last dot
        return filename.substr(0, dotPosition);
    } else {
        // No valid extension found, return the entire filename
        return filename;
    }
}

//==================================================================================================================
// Cryptography Functions (glencrypt)

// Sample: keyEncrypt("Hello", "testkey"); | Result: "+sNOI"
std::string keyEncrypt(const std::string& input, const std::string& key) {
    std::string output;
    std::string::size_type n = input.length(); // Use std::string::size_type
    std::string::size_type m = key.length();   // Use std::string::size_type
    
    const std::string lib = CHAR_LIB;
    
    for (std::string::size_type i = 0; i < n; i++) { // Use std::string::size_type
        std::string::size_type converted_input = lib.find(input[i]); // Use std::string::size_type
        std::string::size_type converted_key = lib.find(key[i % m]); // Use std::string::size_type
        
        std::string::size_type a = (converted_input + converted_key) % lib.length(); // Use std::string::size_type
        output += lib[a];
    }
    
    return output;
}

// Sample: keyDecrypt("+sNOI", "testkey"); | Result: "Hello"
std::string keyDecrypt(const std::string& input, const std::string& key) {
    std::string output;
    std::string::size_type n = input.length(); // Use std::string::size_type
    std::string::size_type m = key.length();   // Use std::string::size_type
    
    const std::string lib = CHAR_LIB;
    
    for (std::string::size_type i = 0; i < n; i++) { // Use std::string::size_type
        std::string::size_type converted_input = lib.find(input[i]); // Use std::string::size_type
        std::string::size_type converted_key = lib.find(key[i % m]); // Use std::string::size_type
        
        std::string::size_type a = (converted_input - converted_key + lib.length()) % lib.length(); // Use std::string::size_type
        output += lib[a];
    }
    
    return output;
}

// Sample: encrypt("Hello"); | Result: "E$E8n"
std::string encrypt(const std::string& input) {
    return keyEncrypt(input.c_str(), DEFAULT_KEY);
}

// Sample: decrypt("E$E8n"); | Result: "Hello"
std::string decrypt(const std::string& input) {
    return keyDecrypt(input.c_str(), DEFAULT_KEY);
}

// Sample: generateKey(10); | Result: "**********" (10 character random key)
std::string generateKey(int length) { 
    static int initialized = 0;
    if (!initialized) {
        std::srand(static_cast<unsigned int>(std::time(nullptr))); // Explicit cast to unsigned int
        initialized = 1;
    }
    
    std::string key;
    for (int i = 0; i < length; i++) {
        key += KEYGEN[static_cast<size_t>(std::rand()) % std::strlen(KEYGEN)]; // Explicit cast to size_t
    }
    
    return key;
}

//==================================================================================================================
// Check Functions

// Sample: IsNumericChar('a'); | Result: 0
bool isNumericChar(char ch) {
    return std::isdigit(ch) || ch == '.' || ch == '-' || ch == '+';
}

// Sample: isEmpty(""); | Result: 1
bool isEmpty(const std::string& input) {
    for (char ch : input) {
        if (!std::isspace(ch)) {
            return false;
        }
    }
    return true;
}

//==================================================================================================================
// Improved Input with error handling and specifiers

// Sample: input(int_var, "Enter int: ");
// Sample: input(string_var, "Enter string: ", "abcd1234", 2, 6);
// Sample: input(bool_var, "Enter bool: ", 'T', 'F') | the last 2 parameters control the error message, 0/1 by default

// Function for integer input
void input(int& output, const std::string& prompt) {
    while (true) {
        std::cout << prompt;
        std::string input;
        std::getline(std::cin, input);
        
        if (input.empty()) {
            printColor(RED,"Invalid input. Input must not be empty.\n");
            printLine();
        }
        else {
            try {
                int temp = std::stoi(input);
                output = temp;
                return;
            }
            catch (const std::invalid_argument&) {
                printColor(RED,"Invalid input. Input must be an integer.\n");
                printLine();
            }
        }
    }
}

// Function for float input
void input(float& output, const std::string& prompt) {
    while (true) {
        std::cout << prompt;
        std::string input;
        std::getline(std::cin, input);
        
        if (input.empty()) {
            printColor(RED,"Invalid input. Input must not be empty.\n");
            printLine();
        }
        else {
            try {
                float temp = std::stof(input);
                output = temp;
                return;
            }
            catch (const std::invalid_argument&) {
                printColor(RED,"Invalid input. Please enter a valid float.\n");
                printLine();
            }
        }
    }
}


// Function for double input
void input(double& output, const std::string& prompt) {
    while (true) {
        std::cout << prompt;
        std::string input;
        std::getline(std::cin, input);
        
        if (input.empty()) {
            printColor(RED,"Invalid input. Input must not be empty.\n");
            printLine();
        }
        else {
            try {
                double temp = std::stod(input);
                output = temp;
                return;
            }
            catch (const std::invalid_argument&) {
                printColor(RED,"Invalid input. Please enter a valid double.\n");
                printLine();
            }
        }
    }
}


// Function for character input
void input(char& output, const std::string& prompt) {
    while (true) {
        std::cout << prompt;
        std::string input;
        std::getline(std::cin, input);
        
        if (input.empty()) {
            printColor(RED,"Invalid input. Input must not be empty.\n");
            printLine();
        }
        else if (input.length() != 1) {
            printColor(RED,"Invalid input. Input must be a single character.\n");
            printLine();
        }
        else {
            output = input[0];
            return;
        }
    }
}

// Function for string input
void input(std::string& output, const std::string& prompt, const std::string& accepted = CHAR_LIB, std::string::size_type min_length = STRING_INPUT_MIN, std::string::size_type max_length = STRING_INPUT_MAX) {
    while (true) {
        std::cout << prompt;
        std::string input;
        std::getline(std::cin, input);

        if (input.empty()) {
            printColor(RED,"Invalid input. Input must not be empty.\n");
            printLine();
        } else if (input.length() < min_length || input.length() > max_length) {
            printColor(RED,"Invalid input. Input must be between %d and %d characters.\n", min_length, max_length);
            printLine();
        } else {
            bool invalid = false;
            std::string invalid_chars;

            for (char c : input) {
                if (accepted.find(c) == std::string::npos) {
                    invalid = true;
                    if (invalid_chars.find(c) == std::string::npos) {
                        invalid_chars += c;
                    }
                }
            }

            if (!invalid) {
                output = input;
                return;
            } else {
                printColor(RED,"Invalid input. Input cannot contain '%s'.\n", invalid_chars.c_str());
                printLine();
            }
        }
    }
}

// Function for boolean input
void input(bool& output, const std::string& prompt, const char default_choice_0 = '0', const char default_choice_1 = '1') {
    while (true) {
        std::cout << prompt;
        std::string input;
        std::getline(std::cin, input);
        
        if (input.length() == 1) {
            char ch = input[0];
            if (ch == 'y' || ch == 'Y' || ch == '1' || ch == 't' || ch == 'T' || ch == default_choice_1) {
                output = true;
                return;
            }
            else if (ch == 'n' || ch == 'N' || ch == '0' || ch == 'f' || ch == 'F' || ch == default_choice_0) {
                output = false;
                return;
            }
        }
        
        printColor(RED, "Invalid input. Input must be '%c' or '%c'.\n", default_choice_0, default_choice_1);
        printLine();
    }
}

int getInt(const std::string& prompt) {
    int buffer;
    input(buffer, prompt);
    return buffer;
}

float getFloat(const std::string& prompt) {
    float buffer;
    input(buffer, prompt);
    return buffer;
}

double getDouble(const std::string& prompt) {
    double buffer;
    input(buffer, prompt);
    return buffer;
}

char getChar(const std::string& prompt) {
    char buffer;
    input(buffer, prompt);
    return buffer;
}

std::string getString(const std::string& prompt, const std::string& accepted = CHAR_LIB, std::string::size_type min_length = STRING_INPUT_MIN, std::string::size_type max_length = STRING_INPUT_MAX) {
    std::string buffer;
    input(buffer, prompt, accepted, min_length, max_length);
    return buffer;
}

bool getBool(const std::string& prompt, const char default_choice_0 = '0', const char default_choice_1 = '1') {
    bool buffer;
    input(buffer, prompt, default_choice_0, default_choice_1);
    return buffer;
}

//==================================================================================================================
// Program Run & Compile Functions
char original_dir[MAX_STRING_LENGTH];

// Sample: runProgram("LRT/", "Bautista_LRT.exe");
void runProgram(const std::string& program, const std::string& path = ".") {
    if (getcwd(original_dir, sizeof(original_dir)) == nullptr) {
        printColor(RED, "Error: Could not get current directory.\n");
        printLine();
        waitEnter();
        return;
    }

    if (chdir(path.c_str()) != 0) {
        printColor(RED, "Error: Could not find directory at %s.\n", path.c_str());
        printLine();
        waitEnter();
        return;
    }

    system("cls");
    system(program.c_str());
    system("cls");

    chdir(original_dir);
}

// Sample: compileRunProgram("LRT/", "Bautista_LRT");
void compileRunProgram(const std::string& program, const std::string& path = ".") {
    if (getcwd(original_dir, sizeof(original_dir)) == nullptr) {
        printColor(RED, "Error: Could not get current directory.\n");
        printLine();
        waitEnter();
        return;
    }

    if (chdir(path.c_str()) != 0) {
        printColor(RED, "Error: Could not find directory at %s.\n", path.c_str());
        printLine();
        waitEnter();
        return;
    }
    std::string compile_command = "g++ " + program + " -o " + program;
    std::cout << std::endl << "Compiling... Please Wait." << std::endl << std::endl;
    system(compile_command.c_str());
    system("cls");
    std::string run_command = getFileName(program) +".exe";
    system(run_command.c_str());
    system("cls");
    chdir(original_dir);
}

void run(const std::string& program, const std::string& path = ".") {
    if (getFileExtension(program) == "cpp") {
        compileRunProgram(program, path);
    }
    else if (getFileExtension(program) == "exe") {
        runProgram(program, path);
    }
    else {
        printColor(RED, "Error: Invalid file type '%s'.\n", getFileExtension(program).c_str());
        printLine();
        waitEnter();
        return;
    }
}



//==================================================================================================================
// Menu Functions

struct menu {
    const char* text;
    void (*function)();
};

void showMenu_title() {}
void showMenu_subtitle() {}
void showMenu_line() {}
void showMenu_end() {}

bool showMenu_functionCheck(const menu& options, void (*comparisonFunction)()) {
    return options.function == comparisonFunction;
}

int showMenu_findPosition(menu* options, int option) {
    int result = 0;
    int valid_options = 0;
    while(valid_options != option) {
        if (showMenu_functionCheck(options[result], showMenu_subtitle)) {
            result++;
        }
        else if (showMenu_functionCheck(options[result], showMenu_line)) {
            result++;
        }
        else {
            result++; valid_options++;
        }
    }
    return result-1;
}

#define TITLE showMenu_title
#define SUBTITLE showMenu_subtitle
#define LINE {nullptr, showMenu_line}
#define END_MENU {nullptr, showMenu_end}

int menu_return = 0;

void showMenu(const std::string& title, menu* options, int menu_width = MENU_WIDTH) {
    while (true) {
        system("cls");
        printLineWidth(menu_width);
        printCentered(menu_width, title);
        std::cout << std::endl;
        printLineWidth(menu_width);
        int i = 0; int excess = 0;
        while (true) {
            if (showMenu_functionCheck(options[i + excess], showMenu_end)) {
                break;
            }
            if (showMenu_functionCheck(options[i + excess], showMenu_title)) {
                printLineWidth(menu_width);
                printCentered(menu_width, options[i + excess].text);
                std::cout << std::endl;
                printLineWidth(menu_width);
                excess++;
                continue;
            }
            if (showMenu_functionCheck(options[i + excess], showMenu_subtitle)) {
                std::cout << options[i+excess].text << std::endl;
                excess++;
                continue;
            }
            if (showMenu_functionCheck(options[i + excess], showMenu_line)) {
                printLineWidth(menu_width);
                excess++;
                continue;
            }

            std::cout << "[" << i+1 << "] " << options[i + excess].text << std::endl;
            i++;
        }

        std::cout << "[0] Return" << std::endl;
        printLineWidth(menu_width);
        int choice;
        input(choice, "Enter Choice: ");

        if (choice == 0) {
            menu_return = 1;
            return;
        }

        if (choice > i || choice < 0) {
            invalidCustom(INVALID_CHOICE, menu_width);
            continue;
        }

        system("cls");
        options[showMenu_findPosition(options, choice)].function();
        if (menu_return != 1) {
            waitEnter();
            system("cls");
        }

        menu_return = 0;
    }
}

/* Sample for showMenu()
    menu test[] = {
        {"Option 1", function1},
        LINE,
        {"Test subtitle", SUBTITLE},
        {"Option 2", function2},
        END_MENU
    };
    showMenu("Menu", test)
*/

#define END_PAGE_MENU nullptr

struct page_menu {
    menu* options;
};

void showPageMenu(const std::string& title, page_menu* page, int menu_width = MENU_WIDTH) {
    int current_page = 0;
    int page_count = 0;

    while (page[page_count].options != nullptr) {
        page_count++;
    }
    
    while(true) {
        system("cls");
        printLineWidth(menu_width);
        printCentered(menu_width, title + " [" + std::to_string(current_page+1) + "/" + std::to_string(page_count) + "]");
        std::cout << std::endl;
        printLineWidth(menu_width);
        int i = 0; int excess = 0;

        while (true) {
            if(i >= MENU_PAGE_LENGTH) {
                printColor(RED, "ERROR: Page Overflow.\n");
                return;
            }

            if (showMenu_functionCheck(page[current_page].options[i+excess], showMenu_end)) {
                break;
            }
            if (showMenu_functionCheck(page[current_page].options[i+excess], showMenu_title)) {
                printLineWidth(menu_width);
                printCentered(menu_width, page[current_page].options[i + excess].text);
                std::cout << std::endl;
                printLineWidth(menu_width);
                excess++;
                continue;
            }
            if (showMenu_functionCheck(page[current_page].options[i+excess], showMenu_subtitle)) {
                std::cout << page[current_page].options[i+excess].text << std::endl;
                excess++;
                continue;
            }
            if (showMenu_functionCheck(page[current_page].options[i+excess], showMenu_line)) {
                printLineWidth(menu_width);
                excess++;
                continue;
            }

            std::cout << "[" << i+1 << "] " << page[current_page].options[i + excess].text << std::endl;
            i++;
        }
        
        printLineWidth(menu_width);
		if (current_page > 0) {
			std::cout << "[8] Previous Page" << std::endl;
		}
		if (current_page != page_count-1) {
			std::cout << "[9] Next Page" << std::endl;
		}
        std::cout << "[0] Return" << std::endl;
        printLineWidth(menu_width);
        int choice;
        input(choice, "Enter Choice: ");
        
        switch(choice) {
            case 8: 
                if (current_page > 0) {	
                    current_page--;				
                    continue;
                }
                else {
                    invalidChoice();
                    continue;
                }
            case 0: menu_return = 1; return;
            case 9: 
                if (current_page != page_count-1) {	
                    current_page++;
                    continue;
                }
                else {
                    invalidChoice();
                    continue;
                }
            default: break;
        }

        if ((choice > i && choice != 8 && choice != 9) || choice < 0) {
            invalidCustom(INVALID_CHOICE, menu_width);
            continue;
        }

        system("cls");

        page[current_page].options[showMenu_findPosition(page[current_page].options, choice)].function();


        if (menu_return != 1) {
            waitEnter();
            system("cls");
        }

        menu_return = 0;
    }
}
/*
menu page1[] = {
    {"Option 1", function1},
    LINE,
    {"Test subtitle", SUBTITLE},
    {"Option 2", function2},
    END_MENU
};

menu page2[] = {
    {"Option 3", function3},
    {"Option 4", function4},
    END_MENU
};

page_menu test2[] = {
    page1,
    page2,
    END_PAGE_MENU
};

showPageMenu("Page Menu", test2);
*/

//==================================================================================================================
// Table Functions

struct table {
    std::string header;
    void* sub_struct;
    size_t content_size;
    size_t struct_size;
    std::string format; 
    size_t offset;
};

#define END_TABLE {"end_table", NULL, 0, 0, "", 0}

void printTableFull(const std::string& title, table* data) {
    int num_columns = 0;
    while(data[num_columns].header != "end_table") {
        num_columns++;
    }

    // std::cout << num_columns;
    
    int table_width = num_columns + 1;
    for (int i = 0; i < num_columns; i++) {
        table_width += extractNumber(data[i].format);
    }
    // std::cout << table_width;

    //Print title
    printLineWidth(table_width);
    printCentered(table_width, title);
    std::cout << std::endl;
    printLineWidth(table_width);

    //Print headers
    std::cout << "|";
    for (int i = 0; i < num_columns; i++) {
        int column_width = extractNumber(data[i].format);
        printCentered(column_width, data[i].header);
        std::cout << "|";
    }
    std::cout << std::endl;

    //Print content
    for (size_t i = 0; i < data->content_size; i++) {
        std::cout << "|";
        for (int j = 0; j < num_columns; j++) {
            uintptr_t address = ((uintptr_t)data[0].sub_struct + data[j].offset) + (i * data[j].struct_size);

            std::string buffer;

            if (typeFromFormat(data[j].format) == "int") {
                buffer = convertString(*reinterpret_cast<int*>(address));
            } 
            else if (typeFromFormat(data[j].format) == "string") {
                buffer = convertString(*reinterpret_cast<std::string*>(address));
            } 
            else if (typeFromFormat(data[j].format) == "float") {
                buffer = convertString(*reinterpret_cast<float*>(address));
            }
            else if (typeFromFormat(data[j].format) == "double") {
                buffer = convertString(*reinterpret_cast<double*>(address));
            }
            else if (typeFromFormat(data[j].format) == "char") {
                buffer = convertString(*reinterpret_cast<char*>(address));
            }
            else if (typeFromFormat(data[j].format) == "bool") {
                buffer = convertString((*reinterpret_cast<bool*>(address) ? "True" : "False"));
            }
            else {
                buffer = "Unknown";
            }

            std::cout << " " << formatString(buffer, extractNumber(data[j].format) - 1) << "|";

        }
            
        std::cout << std::endl;
    }
    printLineWidth(table_width);
}

void printTable(const std::string& title, table* data) {
    int num_columns = 0;
    while(data[num_columns].header != "end_table") {
        num_columns++;
    }

    size_t page = 0;
    size_t max_page = ((data->content_size) / TABLE_PAGE_LENGTH) - 1 ;
    size_t page_row_counter = 0; size_t table_row_counter = 0;

    if ((data->content_size) % TABLE_PAGE_LENGTH != 0) {
        max_page++;
    }
    

    while(1) {
        system("cls");
        int table_width = num_columns + 1;
        for (int i = 0; i < num_columns; i++) {
            table_width += extractNumber(data[i].format);
        }
        
        std::cout << (data->content_size) << "/" << TABLE_PAGE_LENGTH << "=" << max_page << std::endl;
        
        //Print title
        printLineWidth(table_width);
        printCentered(table_width, title);
        std::cout << std::endl;
        printLineWidth(table_width);

        //Print headers
        std::cout << "|";
        for (int i = 0; i < num_columns; i++) {
            int column_width = extractNumber(data[i].format);
            printCentered(column_width, data[i].header);
            std::cout << "|";
        }
        std::cout << std::endl;

        //Print content
        for (; page_row_counter < TABLE_PAGE_LENGTH && table_row_counter < data->content_size; page_row_counter++, table_row_counter++) {
            std::cout << "|";
            for (int j = 0; j < num_columns; j++) {
                uintptr_t address = ((uintptr_t)data[0].sub_struct + data[j].offset) + (table_row_counter * data[j].struct_size);

                std::string buffer;

                if (typeFromFormat(data[j].format) == "int") {
                    buffer = convertString(*reinterpret_cast<int*>(address));
                } 
                else if (typeFromFormat(data[j].format) == "string") {
                    buffer = convertString(*reinterpret_cast<std::string*>(address));
                } 
                else if (typeFromFormat(data[j].format) == "float") {
                    buffer = convertString(*reinterpret_cast<float*>(address));
                }
                else if (typeFromFormat(data[j].format) == "double") {
                    buffer = convertString(*reinterpret_cast<double*>(address));
                }
                else if (typeFromFormat(data[j].format) == "char") {
                    buffer = convertString(*reinterpret_cast<char*>(address));
                }
                else if (typeFromFormat(data[j].format) == "bool") {
                    buffer = convertString((*reinterpret_cast<bool*>(address) ? "True" : "False"));
                }
                else {
                    buffer = "Unknown";
                }

                std::cout << " " << formatString(buffer, extractNumber(data[j].format) - 1) << "|";

            }
                
            std::cout << std::endl;
        }
        page_row_counter = 0;
        printLineWidth(table_width);

        std::cout << " Page " << page+1 << " of " << max_page+1 << "   ";
        if (page > 0) {
            std::cout << " [8] Previous   ";
        }
        if (table_width < 31) {
            std::cout << std::endl;
        }
        if (page != max_page) {
            std::cout << " [9] Next   ";
        }
        std::cout << " [0] Return" << std::endl;

        printLineWidth(table_width);

        int choice;
        input(choice, "Enter Choice: ");
        
        switch (choice) {
            case 8:
                if (page > 0) {	
                    page--;
                    table_row_counter = page*TABLE_PAGE_LENGTH;
                    break;
                }
                else {
                    invalidCustom("Invalid choice.", table_width);
                    continue;
                }
                
            case 9:
                if (page != max_page) {	
                    page++;
                    table_row_counter = page*TABLE_PAGE_LENGTH;
                    break;
                }
                else {
                    invalidCustom("Invalid choice.", table_width);
                    continue;
                }
            case 0:
                printLine(2);
                return;
                break;
            default:
                invalidCustom("Invalid choice.", table_width);
                continue; 
        }

    }


}



}
#endif // GLENLIB_HPP