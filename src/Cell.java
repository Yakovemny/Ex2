public class Cell {

    public boolean isNumber(String text) {
        boolean result = true;
        //determine if the number is negative
        char[] chars = text.toCharArray();
        if(text.contains("-")) {
            for (int i = 1; i < text.length(); i++) {
                if (!(Integer.valueOf(text.charAt(i)) >= 48 && Integer.valueOf(text.charAt(i)) <= 57)) {
                    return false;
                }
            }
        }
        else {
            //0 - > 9
            for (int i = 0; i < text.length(); i++) {
                if (!(Integer.valueOf(text.charAt(i)) >= 48 && Integer.valueOf(text.charAt(i)) <= 57)) {
                    return false;
                }
            }
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
