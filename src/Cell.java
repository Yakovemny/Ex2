public class Cell {

    public boolean isNumber(String text) {
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
    public boolean isText(String text) {
        return true;
    }
    public boolean isForm(String text){
        return false;
    }
    public double computeForm(String form){
        return 0;
    }
}
