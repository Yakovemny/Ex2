import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @org.junit.jupiter.api.Test
    void isNumber() {
        String ans[] = {"1", "-1.1" , "34567"};
        for (int i = 0; i < ans.length; i++) {
            assertTrue(Cell.isNumber(ans[i]));
        }
    }
    @org.junit.jupiter.api.Test
    void isText() {
        String ans[] = {"1", "-1.1"};
        for(int i =0 ; i < ans.length; i++){
            assertFalse(Cell.isText(ans[i]));
        }
        String check[] = {"{2}","2a","hi"};
        for (int i = 0; i < check.length; i++) {
            assertTrue(Cell.isText(check[i]));
        }
        String[] checkForms = {"=1","=1+2*2","=(2)" , "=(1+2*2)"};
        for (int i = 0; i < checkForms.length; i++) {
            assertFalse(Cell.isText(checkForms[i]));
        }
    }
    @org.junit.jupiter.api.Test
    void isForm() {
        String[] check = {"=1","=1+2*2","=(2)" , "=(1+2*2)" , "=(50)", "=(((1+2*2)))+1*2", "=(1+2*(2+3)/4+5/7)", "=1+2*(2+3)/4+5/7+1232211221/4+14.2+(17/4)"};
        for (int i = 0; i < check.length; i++) {
            assertTrue(Cell.isForm(check[i]));
        }
        String[] falseVals = {"4+5)" ,"=(50))", "=)50)", "a", "AB", "@2", "2+)", "(3+1*2)-" , "()" , "(((1+2*2)))+1*2" , "=((1+2*3)","=1++2", "=1+","","=@2" , "=a+b" , "=()"};
        for (int i = 0; i < falseVals.length; i++) {
            assertFalse(Cell.isForm(falseVals[i]));
        }
    }
    @Test
    void isValidBracket() {
        String[] trueVals = {"(1+2*2)" , "(1+2*(2+3)/4+5/7)" , "()"};
        String[] falseVals = {"((1+2*25)" , ")1+965+1221+(18/9)+2 , 2+3)" , "((1+2*25)" , "1+2*(2+3)/4+5/7)"};
        for(int i = 0; i < trueVals.length; i++){
            assertTrue(Cell.isValidBracket(trueVals[i]));
        }
        for(int i = 0; i < falseVals.length; i++){
            assertFalse(Cell.isValidBracket(falseVals[i]));
        }
    }
    @Test
    void placeOfOperator() {
        String text = "1+2*2";
        int indPlus = Cell.placeOfOperator(text , '+');
        int indMul = Cell.placeOfOperator(text , '*');
        assertEquals(3 , indMul);
        assertEquals(1 , indPlus);
    }
    @Test
    void numOfOperand() {
        String text = "=1+2*2";
        assertEquals(1 , Cell.numOfOperand(text , '='));
    }
    @Test
    void containsInvalidCharacters() {
        String[] falseVals = {"=@2" , "=#22245+1"};
        for(int i = 0; i < falseVals.length; i++){
            assertTrue(Cell.containsInvalidCharacters(falseVals[i]));
        }
        String[] trueVals = {"1+2*2" , "(1+2*2)"};
        for(int i = 0; i < trueVals.length; i++){
            assertFalse(Cell.containsInvalidCharacters(trueVals[i]));
        }
    }
    @Test
    void computeForm() {

        String[] arr = {"=2+3*6+(14)" , "=2+3+4/2"};
        double[] ans = {34.0 , 7.0};
        for(int i =0 ;i < arr.length; i++){
            assertEquals(ans[i], Cell.computeForm(arr[i]));
        }
    }


}