package assignment2;// Add your documentation below:

public class CellEntry  implements Index2D {

    @Override
    public boolean isValid() {
        return false;
    }

    public static boolean isValidXCoordinate(String c){
        if (Character.isDigit(c.charAt(0)) || !(hasOneLetter(c)) || hasSpecialChar(c))
            return false;
        for(int i = 0; i < c.length(); i++){
            if(Character.isLetter(c.charAt(i))){
                if(Character.isLowerCase(c.charAt(i)))
                {
                    int val = c.charAt(i) - 'a';
                    if (val < 0 || val >= 26)
                        return false;
                }
                else {
                    int val = c.charAt(i) - 'A';
                    if (val < 0 || val >= 26)
                        return false;
                }

            }
        }
        return true;
    }
    public static boolean hasOneLetter(String c){
        int counter = 0;
        for(int i = 0; i < c.length(); i++){
            if(Character.isLetter(c.charAt(i)))
                counter++;
        }
        return counter == 1;
    }
    public static boolean hasSpecialChar(String c){
        return c.contains("!") || c.contains("@") || c.contains("#") || c.contains("$") || c.contains("%")
                || c.contains("^") || c.contains("&") || c.contains("_");
    }


    public static boolean isValidYCoordinate(String c){
        return true;
    }
    @Override
    public int getX() {return Ex2Utils.ERR;}

    @Override
    public int getY() {return Ex2Utils.ERR;}
}
