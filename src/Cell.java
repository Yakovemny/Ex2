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
        if(text.contains("-") || text.contains("+") || text.contains("*") || text.contains("/") || text.contains("(") && text.contains(")")){
            result = false;
        }

        return result;
    }
    public static boolean isForm(String text){
        if(text.contains("=")){
            if(!(isNumber(text) && isText(text))){
                return true;
            }
        }
        return false;
    }
    public static double computeForm(String text){
        if(Cell.isForm(text)){

        }
        return 0;
    }
}
