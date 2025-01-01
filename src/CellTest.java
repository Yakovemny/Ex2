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
        String[] check = {"=1","=1+2*2","=(2)" , "=(1+2*2)" , "=(50)", "=(((1+2*2)))+1*2", "=(1+2*(2+3)/4+5/7)"};
        for (int i = 0; i < check.length; i++) {
            assertTrue(Cell.isForm(check[i]));
        }
        String[] falseVals = {"4+5)" ,"=(50))", "=)50)", "a", "AB", "@2", "2+)", "(3+1*2)-" , "()" , "=()", "(((1+2*2)))+1*2" , "=((1+2*3)","=1++2", "=1+","","=@2"};// "=a+b"}; //, "=@2"};
        for (int i = 0; i < falseVals.length; i++) {
            assertFalse(Cell.isForm(falseVals[i]));
        }
    }

    @org.junit.jupiter.api.Test
    void computeForm() {
        /*
        String[] arr = {"2+3*6+(14)" , "2+3+4/2"};
        String[] ans = {"34" , "7"};

         */
        String a = "=1";
        assertEquals(1 , Cell.computeForm(a));
    }
}