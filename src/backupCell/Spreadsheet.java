package backupCell;

public class Spreadsheet {
    private Cell[][] cells; // 2D array of Cells

    // Constructor: Creates a spreadsheet with x columns and y rows
    public Spreadsheet(int x, int y) {
        cells = new Cell[y][x]; // Rows first, then columns
    }

    public Cell get(int x, int y) {
        if (isValidIndex(x, y)) {
            return cells[y][x]; // Access in row-major order
        }
        return null; // Invalid index
    }

    public void set(int x, int y, Cell c) {
        if (isValidIndex(x, y)) {
            cells[y][x] = c;
        }
    }

    // Returns the number of columns (width)
    public int width() {
        return cells.length > 0 ? cells[0].length : 0;
    }

    // Returns the number of rows (height)
    public int height() {
        return cells.length;
    }

    // Convert column label (e.g., "F13", "AA13") to column index
    public int xCell(String c) {
        String columnPart = c.replaceAll("[^A-Z]", ""); // Extract letters
        int colIndex = 0;
        for (char ch : columnPart.toCharArray()) {
            colIndex = colIndex * 26 + (ch - 'A' + 1);
        }
        return colIndex - 1; // Convert to 0-based index
    }

    // Extracts row index from a cell reference (e.g., "F13" → 13)
    public int yCell(String c) {
        String rowPart = c.replaceAll("[^0-9]", ""); // Extract numbers
        try {
            int rowIndex = Integer.parseInt(rowPart) - 1; // Convert to 0-based index
            return (rowIndex >= 0 && rowIndex < 100) ? rowIndex : -1;
        } catch (NumberFormatException e) {
            return -1; // Invalid row reference
        }
    }

    private boolean isValidIndex(int x, int y) {
        return x >= 0 && x < width() && y >= 0 && y < height();
    }

    public String eval(int x , int y){
        if(isValidIndex(x,y)) {
            return cells[y][x].toString();
        }
        return "Wrong Input Format";
    }

    public static void main(String[] args) {
        Spreadsheet s = new Spreadsheet(10, 10);
        System.out.println( s.eval(1,1));
    }
}


