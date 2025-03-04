# BS"D
## Ex2 - Spreadsheet Solution
### Description
This project is a solution for the Ex2 assignment in Computer Science at Ariel University, Semester A, Winter 2024.
It implements a spreadsheet system that supports basic arithmetic operations, formula evaluation, cell referencing, and dependency tracking.

## Implemented Classes & Methods
### Ex2Sheet (Implements Sheet)
The main class that manages the spreadsheet grid, evaluates formulas, tracks dependencies, and handles file operations.

set(int x, int y, String s) – Sets a value in a cell.
get(int x, int y) – Retrieves the cell at given coordinates.
get(String cords) – Gets a cell using a reference ("A1", "B2").
value(int x, int y) – Returns the evaluated value of a cell.
eval(int x, int y) – Computes and returns the evaluated result of a cell.
eval() – Evaluates all cells in the spreadsheet.
depth() – Calculates the dependency depth for all cells.
calculateDepth(int x, int y, int[][] depths, boolean[][] visited) – Recursively determines the depth of dependencies for a cell.
isIn(int x, int y) – Checks if a given coordinate is within the spreadsheet bounds.
save(String fileName) – Saves the spreadsheet data to a file.
load(String fileName) – Loads a spreadsheet from a saved file.
### SCell (Implements Cell)
Represents a single cell in the spreadsheet, storing its value, type, and evaluation order.

getData() – Returns the raw string stored in the cell.
setData(String s) – Updates the value of the cell and determines its type.
getType() – Returns whether the cell is a number, text, or formula.
setType(int t) – Manually sets the type of the cell.
getOrder() – Returns the evaluation order for formulas.
setOrder(int t) – Sets the order of formula evaluation.
calculateOrder(SCell[][] cells) – Determines the correct evaluation order for formulas, preventing dependency issues.
extractReferences(String formula) – Extracts cell references from a given formula.
### CellEntry (Implements Index2D)
Handles cell referencing (e.g., "A1", "B2") and ensures that references are properly formatted.

isValid() – Checks if a reference is well-formed ("A1", "B10").
getX() – Converts "A1" to a 0-based column index (A → 0).
getY() – Extracts the row index from a reference ("A1" → 1).
### Ex2GUI
A graphical user interface (GUI) for interacting with the spreadsheet.

drawFrame() – Draws the grid structure of the spreadsheet.
drawCells() – Renders spreadsheet content, displaying values.
inputCell(int x, int y) – Allows users to manually input values into the spreadsheet.
### Testing - JUnit
This project includes unit tests to ensure correctness.

### SCellTest
✔ isNumber(String s) – Verifies if a given string represents a number.
✔ isText(String s) – Checks if the string is considered text.
✔ isForm(String s) – Validates if a string is a properly formatted formula.
✔ computeForm(String form) – Evaluates arithmetic expressions.
✔ findMainOperator(String expression) – Identifies the main operator in an expression.

### Ex2SheetTest
✔ set() and get() – Verifies correct assignment and retrieval of values.
✔ depth() – Ensures correct dependency calculation.
✔ eval() – Checks formula evaluation with simple and complex expressions.
✔ cycle detection – Tests prevention of circular dependencies in formulas.

### File Format - Save & Load
The spreadsheet supports saving and loading from a .txt-like format, storing only non-empty cells.

I2CS ArielU: Spreadsheet (Ex2) Assignment
### Formula Evaluation
This spreadsheet has the ability to compute a String that also has some refernces to existing cells inside of it and calculate its value.

### Cycle Detection
The spreadsheet can detect if a given coordinates creates Cyclic routes between two cells.

picture:



![‏‏(The program)](https://github.com/user-attachments/assets/bc62c146-d2e6-4877-84e0-9bf8da0a6d98)
