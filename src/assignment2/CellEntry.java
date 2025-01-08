package assignment2;

public class CellEntry implements Index2D {
    private String cellIndex; // represents the cell (e.g., "B3")

    public CellEntry(String cellIndex) {
        this.cellIndex = cellIndex;
    }

    public CellEntry(int xx, int yy) {
        this.cellIndex = getXRepresentation(xx) + "" + yy;
    }
    /**
     * return the String representation of 'X' coordinate
     */
    public static String getXRepresentation(int xx) {
        String[] abc = Ex2Utils.ABC;
        return abc[xx];
    }

    /**
     * checks of the string representation of this index is valid "XY" as X is a letter "A-Z" (or "a-z"), and Y is an integer [0-99].
     * @return
     */
    @Override
    public boolean isValid() {
        if (cellIndex == null || cellIndex.isEmpty()) {
            return false;
        }

        // Split the string into X and Y parts
        int splitIndex = findSplitIndex();
        if (splitIndex == -1) {
            return false; // Unable to find a valid split index
        }

        String xPart = cellIndex.substring(0, splitIndex);
        String yPart = cellIndex.substring(splitIndex);

        // Validate both parts
        return isValidXCoordinate(xPart) && isValidYCoordinate(yPart);
    }

    /**
     * returns The first digit marks the start of the Y coordinate
     * @return
     */
    public int findSplitIndex() {
        for (int i = 0; i < cellIndex.length(); i++) {
            if (Character.isDigit(cellIndex.charAt(i))) {
                return i; // The first digit marks the start of the Y coordinate
            }
        }
        return -1; // No digit found
    }
    /**
     * returns true if the x value (integer) of this index is within the bounds of the spreadsheet (within A-Z)
     * @return
     */
    public static boolean isValidXCoordinate(String x) {
        // Must be exactly one letter (A-Z or a-z)
        if (x == null || x.length() != 1 || hasSpecialChar(x)) {
            return false;
        }
        char c = x.charAt(0);
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }
    /**
     * returns true if the y value (integer) of this index is within the bounds of the spreadsheet
     * @return
     */
    public static boolean isValidYCoordinate(String y) {
        // Must be an integer between 0 and 99
        if (y == null || y.isEmpty() || hasSpecialChar(y)) {
            return false;
        }

        try {
            int value = Integer.parseInt(y);
            return value >= 0 && value <= 99;
        } catch (NumberFormatException e) {
            return false; // Not a valid number
        }
    }
    /**
     * returns true if the string contains some special char
     * @return
     */
    public static boolean hasSpecialChar(String c) {
        // Defines a set of special characters to check for
        return c.matches(".*[!@#$%^&*_+=<>?/].*");
    }
    /**
     * returns the x value (integer) of this index
     * @return
     */
    @Override
    public int getX() {
        if (!isValid()) {
            return Ex2Utils.ERR; // Return error constant if the index is invalid
        }

        char xChar = cellIndex.charAt(0); // The X coordinate is the first letter
        if (xChar >= 'a' && xChar <= 'z') {
            return xChar - 'a'; // Convert lower-case letter to 0-based index
        } else {
            return xChar - 'A'; // Convert upper-case letter to 0-based index
        }
    }

    /**
     * returns the y value (integer) of this index
     * @return
     */
    @Override
    public int getY() {
        if (!isValid()) {
            return Ex2Utils.ERR; // Return error constant if the index is invalid
        }

        int splitIndex = findSplitIndex();
        String yPart = cellIndex.substring(splitIndex); // Extract Y coordinate
        try {
            return Integer.parseInt(yPart); // Convert Y part to integer
        } catch (NumberFormatException e) {
            return Ex2Utils.ERR; // Handle unexpected format issues
        }
    }

    /**
     * returns the ToString representation of the Cell (e.g., "B3")
     * @return
     */
    @Override
    public String toString() {
        return cellIndex;
    }
}