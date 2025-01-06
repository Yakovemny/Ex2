package assignment2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class CellEntryTest {

    @Test
    void isValid() {
    }

    @Test
    void isValidXCoordinate() {
        assertTrue(CellEntry.isValidXCoordinate("A"));
        assertTrue(CellEntry.isValidXCoordinate("a"));
        assertTrue(CellEntry.isValidXCoordinate("b13"));
        assertTrue(CellEntry.isValidXCoordinate("A1"));
        assertTrue(CellEntry.isValidXCoordinate("B13"));
        assertFalse(CellEntry.isValidXCoordinate("13"));
        assertFalse(CellEntry.isValidXCoordinate("AA1"));
        assertFalse(CellEntry.isValidXCoordinate("B1B"));
        assertFalse(CellEntry.isValidXCoordinate("A!"));
        assertFalse(CellEntry.isValidXCoordinate("AA"));
        assertFalse(CellEntry.isValidXCoordinate("1A"));
        assertFalse(CellEntry.isValidXCoordinate("A_"));
        assertFalse(CellEntry.isValidXCoordinate("14A"));

    }

    @Test
    void isValidYCoordinate() {
    }

    @Test
    void getX() {
    }

    @Test
    void getY() {
    }
}