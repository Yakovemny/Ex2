package assignment2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ex2SheetTest {

    @Test
    void value() {

    }

    @Test
    void get() {
    }

    @Test
    void testGet() {
    }

    @Test
    void xCell(){
        assertEquals(5 , Ex2Sheet.xCell("F13"));
        assertEquals(-1 , Ex2Sheet.xCell("AA1"));
        assertEquals(-1 , Ex2Sheet.xCell("BB1"));
        assertEquals(-1 , Ex2Sheet.xCell("CC1"));
    }
    @Test
    void yCell(){
        assertEquals(13 , Ex2Sheet.yCell("F13")); //returns -1
        assertEquals(7 , Ex2Sheet.yCell("A7"));
    }
    @Test
    void width() {

    }

    @Test
    void height() {
    }

    @Test
    void set() {
    }

    @Test
    void depth() {
    }

    @Test
    void canBeComputedNow() {
    }

    @Test
    void extractCellReferences() {
    }

    @Test
    void initializeArr() {
        int[][] arr = new int[10][10];
        Ex2Sheet.initializeArr(arr);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                assertEquals(-1 , arr[i][j]);
            }
        }
    }

    @Test
    void load() {
    }

    @Test
    void save() {
    }

    @Test
    void testEval() {
    }
}