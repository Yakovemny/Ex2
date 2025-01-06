package assignment2;

import java.io.IOException;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    // Add your code here

    // ///////////////////
    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for(int i=0;i<x;i=i+1) {
            for(int j=0;j<y;j=j+1) {
                table[i][j] = new SCell("");
            }
        }
        eval();
    }
    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;
        // Add your code here

        Cell c = get(x,y);
        if(c!=null) {ans = c.toString();}

        /////////////////////
        return ans;
    }

    @Override
    public Cell get(int x, int y) {
        return table[x][y];
    }

    @Override
    public Cell get(String cords) {
        Cell ans = null;
        // Add your code here
        int x = xCell(cords);
        int y = yCell(cords);
        if(isIn(x,y)) {ans = table[x][y];}
        else {ans = new SCell(Ex2Utils.EMPTY_CELL);}
        return ans;
    }
    // Convert string for column, e.g., "F13" → ****index Of 'X'**** (A→0, B→1...Z→25; invalid for AA, BB)
    public int xCell(String c) {
        int len = c.length();
        int colIndex = -1;

        for (int i = 0; i < len; i++) {
            char ch = c.charAt(i);
            if (Character.isLetter(ch)) {
                if (colIndex == -1)
                    colIndex = 0;
                int val = ch - 'A';
                if (val < 0 || val >= 26) return -1; // Invalid if out of A-Z
                colIndex = colIndex * 26 + val;
            } else {
                break; // Encountered a number
            }
        }
        return colIndex >= Ex2Utils.WIDTH ? -1 : colIndex; //check
    }

    // Extract and return the numerical part of a cell reference, e.g., "F13" → 13
    public int yCell(String c) {
        StringBuilder numPart = new StringBuilder();
        int num = 0;
        for(int i = 0; i < c.length(); i++){
            if(Character.isDigit(c.charAt(i))){
                numPart.append(c.charAt(i));
            }
        }
        for(int i = 0; i < numPart.length(); i++){
            num = num * 10 + (numPart.charAt(i) - '0');
        }
        return num;
    }
    @Override
    public int width() {
        return table.length;
    }
    @Override
    public int height() {
        return table[0].length;
    }
    @Override
    public void set(int x, int y, String s) {
        Cell c = new SCell(s);
        table[x][y] = c;
    }
    @Override
    public void eval() {
        int[][] dd = depth();
        // Add your code here
        for(int i=0;i<width();i++) {
            for (int j = 0; j < height(); j++) {
                if(dd[i][j] == 0) {
                    table[i][j] = new SCell(eval(i,j));
                }
            }
        }
    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx>=0 && xx <= Ex2Utils.WIDTH && yy>=0 && yy <= Ex2Utils.HEIGHT;
        return ans;
    }

    @Override
    public int[][] depth() {
        int[][] ans = new int[width()][height()];
        int w = width(), h = height();
        // Add your code here
        initializeArr(ans);
        int depth = 0, counter = 0, max = w*h;
        boolean flagC = true;
        while(counter <max && flagC) {
            flagC = false;
            for(int x = 0;x<w;x++) {
                for(int y = 0;y<h;y++) {
                    if(canBeComputedNow(x,y)) { // DIY
                        ans[x][y] = depth;
                        counter+=1;
                        flagC=true;
                    }
                }
            }
            depth++;
        }
        return ans;
    }

    public boolean canBeComputedNow(int x, int y) {
        if
        return true;
    }

    /**
     * gets an 2-dimensional array
     * initialize all the cells within it to have the value -1.
     * @param arr
     */
    public static void initializeArr(int[][] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                arr[i][j] = -1;
            }
        }
    }
    @Override
    public void load(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public void save(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public String eval(int x, int y) {
        String ans = null;
        if(get(x,y)!=null) {ans = get(x,y).toString();}
        // Add your code here

        /////////////////////
        return ans;
        }
}
