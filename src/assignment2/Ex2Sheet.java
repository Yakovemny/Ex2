package assignment2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

public class Ex2Sheet implements Sheet {
    private Cell[][] table;

    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                table[i][j] = new SCell("");
            }
        }
        eval();
    }

    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    /**
     * returns the value of the Cell in the X,Y coordinate of the cell
     * @param x integer, x-coordinate of the cell.
     * @param y integer, y-coordinate of the cell.
     * @return
     */
    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;
        Cell c = get(x, y);
        if (c != null) {
            ans = eval(x, y);
        }
        return ans;
    }

    /**
     * Return the Cell in the x,y, position (or null if not in).
     * @param x integer, x-coordinate of the cell.
     * @param y integer, y-coordinate of the cell.
     * @return
     */
    @Override
    public Cell get(int x, int y) {
        if(isIn(x,y)){
            return table[x][y];
        }
        return null;
    }

    /**
     * returns the cell at the X.
     * Y coordinate, or null if cords is an illegal coordinate or is out of this SprayedSheet.
     * @param cords
     * @return
     */
    @Override
    public Cell get(String cords) {
        if (cords == null || cords.length() < 2) return null;

        cords = cords.toUpperCase(); // Convert to uppercase
        char col = cords.charAt(0);
        int row;

        try {
            row = Integer.parseInt(cords.substring(1));
        } catch (NumberFormatException e) {
            return null;
        }

        int x = col - 'A';
        int y = row;

        return isIn(x, y) ? table[x][y] : null;
    }

    /**
     *
     returns the dimension (length) of the x-coordinate of this spreadsheet.
     * @return
     */
    @Override
    public int width() {
        return table.length;
    }
    /**
     *
     returns the dimension (height) of the y-coordinate of this spreadsheet.
     * @return
     */
    @Override
    public int height() {
        return table[0].length;
    }

    /**
     * This method changes the x,y cell to a cell with the data c.
     * @param x integer, x-coordinate of the cell.
     * @param y integer, y-coordinate of the cell.
     * @param s - the string representation of the cell.
     */
    @Override
    public void set(int x, int y, String s) {
        Cell c = new SCell(s);
        table[x][y] = c;
        eval();
    }

    /**
     * Computes all the values of all the cells in the spreadsheet.
     */
    @Override
    public void eval() {
        int[][] dd = depth();
        int maxDepth = 0;
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                if (dd[i][j] > maxDepth) {
                    maxDepth = dd[i][j];
                }
            }
        }

        for (int d = 0; d <= maxDepth; d++) {
            for (int i = 0; i < width(); i++) {
                for (int j = 0; j < height(); j++) {
                    if (dd[i][j] == d) {
                        eval(i, j);
                    }
                }
            }
        }
    }

    /**
     * Check is the x,y coordinate is with in this table
     * @param xx - integer, x-coordinate of the table (starts with 0).
     * @param yy - integer, y-coordinate of the table (starts with 0).
     * @return
     */
    @Override
    public boolean isIn(int xx, int yy) {
        return xx >= 0 && xx < width() && yy < height() && yy >= 0;
    }

    /**
     * Computes a 2D array of the same dimension as this SpreadSheet that has the dependency rates of all the Cells in each
     * original X Y coordinates of the cells
     * @return
     */
    @Override
    public int[][] depth() {
        int[][] depths = new int[width()][height()];
        boolean[][] visited = new boolean[width()][height()];

        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                calculateDepth(i, j, depths, visited);
            }
        }
        return depths;
    }
    // Helper function that calculates the dependency of each Cell
    public void calculateDepth(int x, int y, int[][] depths, boolean[][] visited) {
        if (visited[x][y]) return;
        visited[x][y] = true;

        String content = get(x, y).getData().trim();
        if (!content.startsWith("=")) {
            depths[x][y] = 0;
            return;
        }

        int maxDepth = 0;
        String formula = content.substring(1); //will start the formula after the '=' sign
        for (int i = 0; i < formula.length(); i++) {
            if (Character.isLetter(formula.charAt(i))) {
                int j = i + 1;
                while (j < formula.length() && Character.isDigit(formula.charAt(j))) {
                    j++;
                }

                if (j > i + 1) {
                    int col = formula.charAt(i) - 'A';
                    int row = Integer.parseInt(formula.substring(i + 1, j));

                    if (isIn(col, row)) {
                        calculateDepth(col, row, depths, visited);
                        maxDepth = Math.max(maxDepth, depths[col][row] + 1);
                    }
                }
            }
        }


        depths[x][y] = maxDepth;
    }

    /**
     *
      Load the content of a saved SpreadSheet into this SpreadSheet
     * @param fileName a String representing the full (an absolute or relative path to the loaded file).
     * @throws IOException
     */
    @Override
    public void load(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                try {
                    int x = Integer.parseInt(parts[0].trim());
                    int y = Integer.parseInt(parts[1].trim());
                    String value = parts[2].trim();
                    if (isIn(x, y)) {
                        set(x, y, value);
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        }
        reader.close();
    }

    /**
     *
     Sheet Saves this SpreadSheet into a text file
     * @param fileName a String representing the full (an absolute or relative path tp the saved file).
     * @throws IOException
     */
    @Override
    public void save(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write("I2CS ArielU: SpreadSheet (Ex2) assignment - this line should be ignored in the load method\n");

        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                String cellData = value(x, y);
                if (!cellData.equals(Ex2Utils.EMPTY_CELL)) {
                    writer.write(x + "," + y + "," + cellData + "\n");
                }
            }
        }
        writer.close();
    }

    @Override
    public String eval(int x, int y) {
        try {
            return eval(x, y, new HashSet<>());
        } catch (IllegalArgumentException e) {
            if (e.getMessage() != null && e.getMessage().equals(Ex2Utils.ERR_CYCLE)) {

                return Ex2Utils.ERR_CYCLE;
            }
            return Ex2Utils.ERR_FORM;
        }
    }
    //An helper function for eval it checks if the Cell was already in use
    public String eval(int x, int y, Set<String> visited) {
        Cell cell = get(x, y);
        String cellKey = x + "," + y;

        if (visited.contains(cellKey)) {
            throw new IllegalArgumentException(Ex2Utils.ERR_CYCLE);
        }
        visited.add(cellKey);

        if (cell == null || cell.getData().isEmpty()) {
            return Ex2Utils.EMPTY_CELL;
        }

        String cellData = cell.getData().trim();

        if (cellData.startsWith("=")) {
            String formula = cellData.substring(1).trim();
            try {
                double result = calculateFormula(formula, new HashSet<>(visited));
                return String.valueOf(result);
            } catch (IllegalArgumentException e) {
                if (e.getMessage() != null && e.getMessage().equals(Ex2Utils.ERR_CYCLE)) {
                    throw e;
                }
                return Ex2Utils.ERR_FORM;
            } catch (Exception e) {
                return Ex2Utils.ERR_FORM;
            }
        }

        return cellData;
    }

    /**
     * this method is being used as Helper method for the depth method, it stores the "visited" cells
     * and when some Cell is being called it will help to detect if the cell is being called into infinite loop
     * with HashMap.
     * @param formula
     * @param visited
     * @return
     */
    public double calculateFormula(String formula, Set<String> visited) {
        formula = formula.replaceAll(" ", "").toUpperCase(); // Convert to uppercase

        String cellPattern = "[A-Z]\\d+";
        if (formula.matches(cellPattern)) {
            char col = formula.charAt(0);
            int row = Integer.parseInt(formula.substring(1));
            int x = col - 'A';
            int y = row;

            if (isIn(x, y)) {
                String cellValue;
                try {
                    cellValue = eval(x, y, visited);
                    if (cellValue.equals(Ex2Utils.ERR_CYCLE)) {
                        throw new IllegalArgumentException(Ex2Utils.ERR_CYCLE);
                    }
                    return Double.parseDouble(cellValue);
                } catch (IllegalArgumentException e) {
                    if (e.getMessage() != null && e.getMessage().equals(Ex2Utils.ERR_CYCLE)) {
                        throw e;
                    }
                    throw new IllegalArgumentException(Ex2Utils.ERR_FORM);
                }
            }
        }

        for (int i = 0; i < formula.length() - 1; i++) {
            if (Character.isLetter(formula.charAt(i)) &&
                    Character.isDigit(formula.charAt(i + 1))) {

                int j = i + 1;
                while (j < formula.length() && Character.isDigit(formula.charAt(j))) {
                    j++;
                }

                String cellRef = formula.substring(i, j).toUpperCase(); // Convert to uppercase
                char col = cellRef.charAt(0);
                int row = Integer.parseInt(cellRef.substring(1));
                int x = col - 'A';
                int y = row;

                if (isIn(x, y)) {
                    String cellValue;
                    try {
                        cellValue = eval(x, y, visited);
                        if (cellValue.equals(Ex2Utils.ERR_CYCLE)) {
                            throw new IllegalArgumentException(Ex2Utils.ERR_CYCLE);
                        }
                        formula = formula.replace(cellRef, cellValue);
                    } catch (IllegalArgumentException e) {
                        if (e.getMessage() != null && e.getMessage().equals(Ex2Utils.ERR_CYCLE)) {
                            throw e;
                        }
                        throw new IllegalArgumentException(Ex2Utils.ERR_FORM);
                    }
                }
            }
        }

        return evaluateFormula(formula, visited);
    }

    /**
     * evalutes the formula with formatted XY coordinates aka.E2+A5
     * @param formula
     * @param visited
     * @return
     */
    public double evaluateFormula(String formula, Set<String> visited) {
        formula = formula.replaceAll(" ", ""); // Remove spaces

        // If it's a single cell reference (e.g., "A1"), evaluate it
        if (formula.matches("[A-Z][0-9]+")) {
            CellEntry cellEntry = new CellEntry(formula);
            if (!cellEntry.isValid()) {
                throw new IllegalArgumentException(Ex2Utils.ERR_FORM);
            }

            int x = cellEntry.getX();
            int y = cellEntry.getY();

            if (isIn(x, y)) {
                String cellValue = eval(x, y, visited);
                if (cellValue.equals(Ex2Utils.ERR_CYCLE)) {
                    throw new IllegalArgumentException(Ex2Utils.ERR_CYCLE);
                }
                return Double.parseDouble(cellValue);
            } else {
                throw new IllegalArgumentException(Ex2Utils.ERR_FORM);
            }
        }
        
        // If it's a numeric value, return it
        try {
            return Double.parseDouble(formula);
        } catch (NumberFormatException ignored) {}

        // Handle expressions with operations (e.g., "A1 + B2")
        int bracketCount = 0;
        int lastOpIndex = -1;

        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);
            if (c == '(') bracketCount++;
            if (c == ')') bracketCount--;

            // Find the last operator (outside of brackets)
            if (bracketCount == 0 && "+-*/".indexOf(c) >= 0) {
                if (c == '-' && (i == 0 || "+-*/(".indexOf(formula.charAt(i - 1)) >= 0)) {
                    continue; // Handle negative numbers correctly
                }
                lastOpIndex = i;
            }
        }

        if (lastOpIndex != -1) {
            String leftPart = formula.substring(0, lastOpIndex);
            String rightPart = formula.substring(lastOpIndex + 1);
            char op = formula.charAt(lastOpIndex);

            switch (op) {
                case '+': return evaluateFormula(leftPart, visited) + evaluateFormula(rightPart, visited);
                case '-': return evaluateFormula(leftPart, visited) - evaluateFormula(rightPart, visited);
                case '*': return evaluateFormula(leftPart, visited) * evaluateFormula(rightPart, visited);
                case '/':
                    double divisor = evaluateFormula(rightPart, visited);
                    if (divisor == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    return evaluateFormula(leftPart, visited) / divisor;
            }
        }

        // Handle parentheses
        if (formula.startsWith("(") && formula.endsWith(")")) {
            return evaluateFormula(formula.substring(1, formula.length() - 1), visited);
        }
        throw new IllegalArgumentException(Ex2Utils.ERR_FORM);
    }

}