package assignment2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SCellTest {

    @Test
    void getOrder() {
        SCell cell = new SCell("=A1+A2");
        cell.setOrder(2);
        assertEquals(2, cell.getOrder());
    }

    @Test
    void testToString() {
        SCell cell = new SCell("=A1+A2");
        assertEquals("=A1+A2", cell.toString());
    }

    @Test
    void setData() {
        SCell cell = new SCell("=A1+A2");
        cell.setData("=A1+A2");
        assertEquals("=A1+A2", cell.getData());
    }

    @Test
    void getData() {
        SCell cell = new SCell("=A1+A2");
        cell.setData("=A1+A2");
        assertEquals("=A1+A2", cell.getData());
    }

    @Test
    void getType() {
        SCell cell = new SCell("=A1+A2");
        setType();
        assertEquals(3, cell.getType());
    }

    @Test
    void setType() {
        SCell cell = new SCell("1+2");
        cell.setType(1);
        assertEquals(1, cell.getType());
    }

    @Test
    void setOrder() {
        SCell cell = new SCell("=A1+A2");
        cell.setOrder(2);
        assertEquals(2, cell.getOrder());
    }

    @Test
    void isNumber() {
        String ans[] = {"1", "-1.1" , "34567"};
        for (int i = 0; i < ans.length; i++) {
            assertTrue(SCell.isNumber(ans[i]));
        }
    }

    @Test
    void isText() {
        String ans[] = {"1", "-1.1"};
        for(int i =0 ; i < ans.length; i++){
            assertFalse(SCell.isText(ans[i]));
        }
        String check[] = {"{2}","2a","hi"};
        for (int i = 0; i < check.length; i++) {
            assertTrue(SCell.isText(check[i]));
        }
        String[] checkForms = {"=1","=1+2*2","=(2)" , "=(1+2*2)"};
        for (int i = 0; i < checkForms.length; i++) {
            assertFalse(SCell.isText(checkForms[i]));
        }
    }

    @Test
    void isForm() {

        String[] check = {"=1","=1+2*2","=(2)" , "=(1+2*2)" , "=(50)", "=(((1+2*2)))+1*2", "=(1+2*(2+3)/4+5/7)", "=1+2*(2+3)/4+5/7+1232211221/4+14.2+(17/4)"};//, "a1+2"};
        for (int i = 0; i < check.length; i++) {
            assertTrue(SCell.isForm(check[i]));
        }
        String[] falseVals = {"4+5)" ,"=(50))", "=)50)", "a", "AB", "@2", "2+)", "(3+1*2)-" , "()" , "(((1+2*2)))+1*2" , "=((1+2*3)", "=1+","","=1++2"};//,"=@2" ,};// "=a+b" , "=()"};
        for (int i = 0; i < falseVals.length; i++) {
            assertFalse(SCell.isForm(falseVals[i]));
        }



    }
    @Test
    void isValidBracket() {
        String[] trueVals = {"(1+2*2)" , "(1+2*(2+3)/4+5/7)" , "()"};
        String[] falseVals = {"((1+2*25)" , ")1+965+1221+(18/9)+2 , 2+3)" , "((1+2*25)" , "1+2*(2+3)/4+5/7)"};
        for(int i = 0; i < trueVals.length; i++){
            assertTrue(SCell.isValidBracket(trueVals[i]));
        }
        for(int i = 0; i < falseVals.length; i++){
            assertFalse(SCell.isValidBracket(falseVals[i]));
        }
    }

    @Test
    void placeOfOperator() {
        String text = "1+2*2";
        int indPlus = SCell.placeOfOperator(text , '+');
        int indMul = SCell.placeOfOperator(text , '*');
        assertEquals(3 , indMul);
        assertEquals(1 , indPlus);
    }

    @Test
    void numOfOperand() {
        String text = "=1+2*2";
        assertEquals(1 , SCell.numOfOperand(text , '='));
        assertEquals(1 , SCell.numOfOperand(text , '+'));
    }

    @Test
    void containsInvalidCharacters() {
        String[] falseVals = {"=@2" , "=#22245+1"};
        for(int i = 0; i < falseVals.length; i++){
            assertTrue(SCell.containsInvalidCharacters(falseVals[i]));
        }
        String[] trueVals = {"1+2*2" , "(1+2*2)" ,  "=a1+3"};
        for(int i = 0; i < trueVals.length; i++){
            assertFalse(SCell.containsInvalidCharacters(trueVals[i]));
        }
    }

    @Test
    void computeForm() {

        String[] arr = {"=2+3*6+(14)" , "=2+3+4/2"};
        double[] ans = {34.0 , 7.0};
        for(int i =0 ;i < arr.length; i++){
            assertEquals(ans[i], SCell.computeForm(arr[i]));
        }
    }

    @Test
    void findMainOperator() {
        // Test simple expressions
        assertEquals(1, SCell.findMainOperator("3+2")); // '+' is at index 1
        assertEquals(1, SCell.findMainOperator("3-2")); // '-' is at index 1
        assertEquals(1, SCell.findMainOperator("3*2")); // '*' is at index 1
        assertEquals(1, SCell.findMainOperator("3/2")); // '/' is at index 1
    }

    @Test
    void computeOperation() {
        assertEquals(5.0, SCell.computeOperation(2, 3, '+'));
        assertEquals(-1.0, SCell.computeOperation(2, 3, '-'));
        assertEquals(6.0, SCell.computeOperation(2, 3, '*'));
        assertEquals(2.0, SCell.computeOperation(6, 3, '/'));
        assertThrows(ArithmeticException.class, () -> SCell.computeOperation(1, 0, '/'));
    }

    @Test
    void testSingleNumber() {
        // Base case: single numbers
        assertEquals(5.0, SCell.evaluate("5"));
        assertEquals(123.456, SCell.evaluate("123.456"));
        assertEquals(-3.2, SCell.evaluate("-3.2"));
    }

    @Test
    void testSimpleAddition() {
        // Test basic addition
        assertEquals(7.0, SCell.evaluate("3+4"));
        assertEquals(-1.0, SCell.evaluate("-2+1"));
    }

    @Test
    void testSimpleSubtraction() {
        // Test basic subtraction
        assertEquals(5.0, SCell.evaluate("10-5"));
        assertEquals(-3.0, SCell.evaluate("-1-2"));
    }

    @Test
    void testSimpleMultiplication() {
        // Test basic multiplication
        assertEquals(15.0, SCell.evaluate("3*5"));
        assertEquals(-6.0, SCell.evaluate("-2*3"));
    }

    @Test
    void testSimpleDivision() {
        // Test basic division
        assertEquals(4.0, SCell.evaluate("12/3"));
        assertEquals(-2.0, SCell.evaluate("-6/3"));
        assertThrows(ArithmeticException.class, () -> SCell.evaluate("5/0"));
    }

    @Test
    void testIsOperator() {
        // Valid operators
        assertTrue(SCell.isOperator('+'));
        assertTrue(SCell.isOperator('-'));
        assertTrue(SCell.isOperator('*'));
        assertTrue(SCell.isOperator('/'));

        // Invalid operators
        assertFalse(SCell.isOperator('a'));
        assertFalse(SCell.isOperator('1'));
        assertFalse(SCell.isOperator('%')); // Assuming '%' is not a valid operator in your implementation
        assertFalse(SCell.isOperator(' ')); // Space
    }

    @Test
    void testGetPrecedence() {
        // High precedence for * and /
        assertEquals(2, SCell.getPrecedence('*'));
        assertEquals(2, SCell.getPrecedence('/'));

        // Low precedence for + and -
        assertEquals(1, SCell.getPrecedence('+'));
        assertEquals(1, SCell.getPrecedence('-'));

    }

    @Test
    void determineType() {
        SCell c = new SCell("=A1+A2");
        assertEquals(3 , c.getType());
    }

    @Test
    void extractReferences() {
        String s = "=A1";
        assertEquals("A1", SCell.extractReferences(s));
    }

    @Test
    void isValidExpression() {
    }
}