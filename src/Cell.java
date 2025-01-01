import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Cell {

    public static boolean isNumber(String text) {
        boolean result = true;
        //determine if the number is negative
        try{
            double d = Double.parseDouble(text);
        }
        catch (NumberFormatException e){
            result = false;
        }
        return result;
    }
    public static boolean isText(String text) {
        //Can't convert from that String to number representation in DOUBLE
        //Dont have any Arithmetic sign => '-' '+' '*' '/'
        boolean result = true;
        if(isNumber(text)){
            return false;
        }
        if(text.contains("=")||text.contains("-") || text.contains("+") || text.contains("*") || text.contains("/") || text.contains("(") && text.contains(")")){
            result = false;
        }

        return result;
    }
    public static boolean isForm(String text){
        //add check that determines is after any arythmetic there is '(' if so
        if(text.contains("=") && numOfOperands(text ,'=' ) == 1 && hasNumberB4After(text , '+') && hasNumberB4After(text , '-') && hasNumberB4After(text , '*') && hasNumberB4After(text , '/')){
            if(!(isNumber(text) && isText(text)) && isValidBracket(text)){
                return true;
            }
        }
        return false;
    }
    //determines that every operand has a number before it
    public static boolean hasNumberB4After(String text, char c) {
        char[] chars = text.toCharArray();
        boolean result = true;
        for(int i = 0 ; i < chars.length ; i++){
            if(chars[i] == c){
                try{
                    double d = Double.parseDouble(String.valueOf(chars[i-1]));
                    double f = Double.parseDouble(String.valueOf(chars[i+1]));
                }
                catch(Exception e){
                    return false;
                }
            }
        }
        return result;
    }
    public static boolean isValidBracket(String text){
        boolean result = true;
        if(numOfOperands(text , '(') > numOfOperands(text , ')') || numOfOperands(text , '(') < numOfOperands(text , ')') && placeOfOperator(text , '(') - placeOfOperator(text , ')') <2 || text.contains("()"))
            return false;
        return result;
    }
    public static int placeOfOperator(String text , char c){
        int place = 0;
        for(int i = 0 ; i < text.length() ; i++){
            if(text.charAt(i) == c){
                place = i;
                break;
            }
        }
        return place;
    }
    public static int numOfOperands(String text , char operator){
        int result = 0;
        char[] chars = text.toCharArray();
        for(int i =0 ; i < text.length(); i++){
            if(chars[i] == operator)
                result++;
        }
        return result;
    }
    public static double computeForm(String text){
        double d = -1;
        if(Cell.isForm(text)){
            try{
                 d = Double.parseDouble(text);
            }
            catch (NumberFormatException e){
                 d = -1;
            }
        }
        return d;
    }
}
