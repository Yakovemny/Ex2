package backupCell;

public class Spreadsheet {
    private final Cell[][] cells;  // 2D array of Cell objects
    private final int x;
    private final int y;

    // Constructor to generate a spreadsheet with given dimensions (width x height)
    public Spreadsheet(int x, int y) {
        this.x = x;
        this.y = y;
        this.cells = new Cell[x][y];

        // Initialize the spreadsheet with empty cells
        for (int i = 0; i < this.x; x++) {
            for (int j = 0; j < this.y; y++) {
                cells[i][j] = new Cell();  // Set empty data
            }
        }
    }

    // Get the cell at the coordinate (x, y)
    public Cell get(int x, int y) {
        if (isValidCoordinate(x, y)) {
            return cells[x][y];
        }
        throw new IllegalArgumentException("Invalid cell coordinates (" + x + "," + y + ")");
    }

    // Set the cell at the coordinate (x, y)
    public void set(int x, int y, Cell c) {
        if (isValidCoordinate(x, y)) {
            cells[x][y] = c;
        } else {
            throw new IllegalArgumentException("Invalid cell coordinates (" + x + "," + y + ")");
        }
    }

    // Return the width of the spreadsheet
    public int width() {
        return this.x;
    }

    // Return the height of the spreadsheet
    public int height() {
        return this.y;
    }

    // Convert string for column, e.g., "F13" → index (A→0, B→1...Z→25; invalid for AA, BB)
    public int xCell(String c) {
        int len = c.length();
        int colIndex = -1;

        for (int i = 0; i < len; i++) {
            char ch = c.charAt(i);
            if (Character.isLetter(ch)) {
                if (colIndex == -1) colIndex = 0;
                int val = ch - 'A';
                if (val < 0 || val >= 26) return -1; // Invalid if out of A-Z
                colIndex = colIndex * 26 + val;
            } else {
                break; // Encountered a number
            }
        }
        return colIndex >= this.x ? -1 : colIndex;
    }

    // Extract and return the numerical part of a cell reference, e.g., "F13" → 13
    public int yCell(String c) {
        StringBuilder numPart = new StringBuilder();
        for (char ch : c.toCharArray()) {
            if (Character.isDigit(ch)) {
                numPart.append(ch);
            }
        }
        try {
            int y = Integer.parseInt(numPart.toString()) - 1; // Convert to 0-based index
            return (y >= 0 && y < this.y) ? y : -1; // Ensure it's within bounds
        } catch (Exception e) {
            return -1; // Invalid number
        }
    }

    // Helper: Check if a cell coordinate is valid
    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < this.x && y >= 0 && y < this.y;
    }
}


