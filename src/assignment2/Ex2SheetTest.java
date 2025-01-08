package assignment2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

class Ex2SheetTest {
    private Ex2Sheet sheet;

    @BeforeEach
    void setUp() {
        sheet = new Ex2Sheet(5, 5); // Create a 5x5 spreadsheet
    }

    @Test
    void testSetAndGetCell() {
        sheet.set(0, 0, "10");
        assertEquals("10", sheet.value(0, 0));

        sheet.set(1, 1, "=5+5");
        assertEquals("10.0", sheet.value(1, 1));

        sheet.set(2, 2, "=A1 + B2");
        assertEquals(Ex2Utils.ERR_FORM, sheet.value(2, 2)); // Invalid cell references
    }

    @Test
    void testIsIn() {
        assertTrue(sheet.isIn(0, 0));
        assertTrue(sheet.isIn(4, 4));
        assertFalse(sheet.isIn(-1, 0));
        assertFalse(sheet.isIn(5, 5));
    }

    @Test
    void testEvaluateFormula() {
        Set<String> visited = new HashSet<>();

        assertEquals(10.0, sheet.evaluateFormula("10", visited));
        assertEquals(8.0, sheet.evaluateFormula("10-2", visited));
        assertEquals(50.0, sheet.evaluateFormula("10*5", visited));
        assertEquals(2.0, sheet.evaluateFormula("10/5", visited));

        Exception exception = assertThrows(ArithmeticException.class, () -> {
            sheet.evaluateFormula("10/0", visited);
        });
        assertEquals("Division by zero", exception.getMessage());
    }

    @Test
    void testCalculateFormula() {
        sheet.set(0, 0, "10");
        sheet.set(1, 1, "=A1+5");

        assertEquals(15.0, sheet.calculateFormula("A1+5", new HashSet<>()));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sheet.calculateFormula("Z1+5", new HashSet<>());
        });
        assertEquals(Ex2Utils.ERR_FORM, exception.getMessage());
    }

    @Test
    void testEvalWithFormulas() {
        sheet.set(0, 1, "10");
        sheet.set(1, 1, "=A1+5");

        assertEquals("10", sheet.eval(0, 1));
        assertEquals("15.0", sheet.eval(1, 1));

        sheet.set(3, 3, "=C3");
        sheet.set(4, 4, "=D4");
        sheet.set(2, 2, "=E5");

        assertEquals(Ex2Utils.ERR_FORM, sheet.eval(2, 2)); // Invalid cell
    }

    @Test
    void testDepthCalculation() {
        sheet.set(0, 0, "10");
        sheet.set(1, 1, "=A1+5");
        sheet.set(2, 2, "=B2+5");

        int[][] depths = sheet.depth();

        assertEquals(0, depths[0][0]); // Direct value
        assertEquals(1, depths[1][1]); // Depends on A1
        assertEquals(2, depths[2][2]); // Depends on B2
    }

    @Test
    void testCircularReference() {
        sheet.set(0, 0, "=A1"); // Direct circular reference
        assertEquals(Ex2Utils.ERR_CYCLE, sheet.value(0, 0));

        sheet.set(1, 1, "=B2");
        sheet.set(2, 2, "=A1");
        sheet.set(0, 0, "=C3"); // Indirect circular reference

        assertEquals(Ex2Utils.ERR_CYCLE, sheet.value(0, 0));
    }

    @Test
    void testSaveAndLoad() throws IOException {
        String fileName = "test_sheet.txt";

        sheet.set(0, 0, "10");
        sheet.set(1, 1, "=A1+5");
        sheet.set(2, 2, "=B2*2");

        sheet.save(fileName);

        Ex2Sheet newSheet = new Ex2Sheet(5, 5);
        newSheet.load(fileName);

        assertEquals("10", newSheet.value(0, 0));
        assertEquals("15.0", newSheet.value(1, 1));
        assertEquals("30.0", newSheet.value(2, 2));

        new File(fileName).delete(); // Cleanup after test
    }
}
