package backupCell;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SpreadsheetTest {
    static Cell c = new Cell();
    static Spreadsheet spreadsheet = new Spreadsheet(9 , 10);

    @Test
    void width() {
        assertEquals(9, spreadsheet.getWidth());
    }

    @Test
    void height() {
        assertEquals(10, spreadsheet.getHeight());
    }
    @Test
    void xCell(){
        assertEquals(5 , spreadsheet.xCell("F13"));
        assertEquals(-1 , spreadsheet.xCell("AA1"));
    }
    @Test
    void yCell(){
        assertEquals(13 , spreadsheet.yCell("F13")); //returns -1

    }
    @Test
    void isValidCoordinates(){
        assertEquals(false , spreadsheet.isValidCoordinate(12 , 10));
        assertEquals(true , spreadsheet.isValidCoordinate(5 , 10));
    }
}