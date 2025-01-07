package assignment2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class CellEntryTest {

    @Test
    void isValid() {
        CellEntry validCell1 = new CellEntry("A1");
        CellEntry invalidCell = new CellEntry("AA1");
        assertEquals(true , validCell1.isValid());
        assertEquals(false , invalidCell.isValid());
    }

    @Test
    void isValidXCoordinate() {
        String[] ltr = Ex2Utils.ABC;
        for (int i = 0; i < ltr.length; i++) {
            CellEntry validCell = new CellEntry(ltr[i] + "1");
            assertEquals(true , validCell.isValid());
        }
        CellEntry invalidCell = new CellEntry("AA1");
        assertEquals(false , invalidCell.isValid());
    }

    @Test
    void isValidYCoordinate() {
        for (int i = 1; i < 100; i++) {
            CellEntry validCell = new CellEntry("A" + i);
            assertEquals(true , validCell.isValid());
        }
        CellEntry invalidCell = new CellEntry("A100");
        assertEquals(false , invalidCell.isValid());
    }

    @Test
    void getX() {
        CellEntry validCell = new CellEntry("A1");
        assertEquals(0 , validCell.getX());
        CellEntry invalidCell = new CellEntry("AA1");
        assertEquals(-1 , invalidCell.getX());
    }

    @Test
    void getY() {
        CellEntry validCell = new CellEntry("A1");
        assertEquals(1 , validCell.getY());
        CellEntry invalidCell = new CellEntry("A100");
        assertEquals(-1 , invalidCell.getY());
    }

    @Test
    void findSplitIndex() {
        CellEntry validCell = new CellEntry("A1");
        assertEquals(1 , validCell.findSplitIndex());
    }
}