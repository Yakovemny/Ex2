import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @org.junit.jupiter.api.Test
    void isNumber() {
        String ans[] = {"1", "-1.1"};
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
    }

    @org.junit.jupiter.api.Test
    void isForm() {
        String[] check = {"=1","=1+2*2","=(2)" , "=(1+2*2)"};
        for (int i = 0; i < check.length; i++) {
            assertTrue(Cell.isForm(check[i]));
        }
    }

    @org.junit.jupiter.api.Test
    void computeForm() {
    }
}