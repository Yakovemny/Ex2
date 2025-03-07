package assignment2;// Add your documentation below:

public class SCell implements Cell {
    private String line;
    private int type;
    private int order;

    public SCell(String s) {
        setData(s);
        order = 0;
        determineType();
    }

    @Override
    public String getData() {
        return line;
    }

    @Override
    public void setData(String s) {
        line = s;
        determineType();
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        type = t;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int t) {
        order = t;
    }

    @Override
    public String toString() {
        return getData();
    }

    /**
     * Determines the type of the cell based on its content
     */

    public void determineType() {
        if (isNumber(line)) {
            setType(Ex2Utils.NUMBER);
        }
        else if (isText(line)) {
            setType(Ex2Utils.TEXT);
        }
        else if (isForm(line)) {
            setType(Ex2Utils.FORM);
        } else {
            setType(Ex2Utils.ERR_FORM_FORMAT);
        }

    }

    /**
     * A helper method that Extract references from a formula string
      */
    public static String[] extractReferences(String formula) {
        return formula.split("[^A-Z0-99]");
    }
    /**
     * determines if the String is a number
      */
    public static boolean isNumber(String text) {
        boolean result = true;
        try {
            double d = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }

    /**
     *  gets a String and determines if the String is a text.
     * @param text
     * @return
     */
    public static boolean isText(String text) {
        if (text == null || text.isEmpty()) return false; // Empty strings are not text
        if (isNumber(text) || text.startsWith("=")) return false; // Numbers and formulas are not text

        // Ensure it only contains valid text characters (letters, spaces, and digits)
        return text.matches("[A-Za-z0-9 ]+");
    }
    /**
     *  gets a String and determines if the String is a form.
     * @param text
     * @return
     */
    public static boolean isForm(String text) {

        if (text.contains("=") && numOfOperand(text, '=') == 1 && isValidBracket(text)) {
            if (!(isValidExpression(text.substring(1)))) {
                try {
                    if (text.contains("+"))
                        if (text.charAt(placeOfOperator(text, '+')) == text.charAt(placeOfOperator(text, '+') + 1))
                            return false;
                    if (text.contains("-"))
                        if (text.charAt(placeOfOperator(text, '-')) == text.charAt(placeOfOperator(text, '-') + 1))
                            return false;
                    if (text.contains("*"))
                        if (text.charAt(placeOfOperator(text, '*')) == text.charAt(placeOfOperator(text, '*') + 1))
                            return false;
                    if (text.contains("/"))
                        return text.charAt(placeOfOperator(text, '/')) != text.charAt(placeOfOperator(text, '/') + 1);
                } catch (IndexOutOfBoundsException e) {
                    return false;
                }
            }
            if(isValidBracket(text)) {
                if (placeOfOperator(text, '(') + 1 == placeOfOperator(text, ')') || placeOfOperator(text, ')') + 1 == placeOfOperator(text, ')'))
                    return false;

            }
            return true;
        }
        return false;
    }
    /**
     *  gets a String and determines if the expression inside is valid.
     * @param expr
     * @return
     */
    public static boolean isValidExpression(String expr) {
        if (expr == null || expr.isEmpty()) return false;
        if(expr.contains("+") || expr.contains("-") || expr.contains("*") || expr.contains("/")) {
            if(expr.charAt(placeOfOperator(expr, '+')) == expr.charAt(placeOfOperator(expr, '-') + 1))
                return false;
        }
        return expr.matches("[0-9A-Z+\\-*/().]+");
    }

    public static boolean isValidBracket(String text) {
        int balance = 0; // Keeps track of the balance between '(' and ')'

        for (char ch : text.toCharArray()) {
            if (ch == '(') {
                balance++;
            } else if (ch == ')') {
                balance--;
            }
            // If balance goes negative, there's an unmatched ')'
            if (balance < 0) {
                return false;
            }
        }
        // At the end, balance must be zero for all brackets to be matched
        return balance == 0;
    }

    public static int placeOfOperator(String text, char c) {
        int place = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == c) {
                place = i;
                break;
            }
        }
        return place;
    }

    public static int numOfOperand(String text, char operator) {
        int result = 0;
        char[] chars = text.toCharArray();
        for (int i = 0; i < text.length(); i++) {
            if (chars[i] == operator)
                result++;
        }
        return result;
    }

    public static boolean containsInvalidCharacters(String text) {
        // Define a regex for valid characters (e.g., numbers, operators, parentheses)
        String validChars = "[0-9=+\\-*/().A-Z]";
        for (int i = 1; i < text.length(); i++) {
            text = text.toUpperCase();
            char c = text.charAt(i);
            // Check if the character does not match the valid set
            if (!String.valueOf(c).matches(validChars)) {
                return true; // Invalid character found
            }
        }
        return false; // No invalid characters
    }

    public static double computeForm(String form) {
        if (form == null || form.isEmpty() || !form.startsWith("=")) {
            throw new IllegalArgumentException("Invalid formula");
        }

        // Strip the leading '='
        String expression = form.substring(1).replaceAll("\\s+", ""); // Remove whitespace
        return evaluate(expression);
    }

    public static double evaluate(String expression) {
        // Base case: if the expression is a single number
        if (isNumber(expression)) {
            return Double.parseDouble(expression);
        }

        // Remove outer parentheses if they enclose the entire expression
        if (expression.startsWith("(") && expression.endsWith(")") && isValidBracket(expression.substring(1, expression.length() - 1))) {
            return evaluate(expression.substring(1, expression.length() - 1));
        }

        // Find the main operator
        int operatorIndex = findMainOperator(expression);
        if (operatorIndex == -1) {
            throw new IllegalArgumentException("Invalid formula: " + expression);
        }

        char operator = expression.charAt(operatorIndex);
        String left = expression.substring(0, operatorIndex);
        String right = expression.substring(operatorIndex + 1);

        // Recursively evaluate left and right parts
        double leftValue = evaluate(left);
        double rightValue = evaluate(right);

        // Compute the result based on the operator
        return computeOperation(leftValue, rightValue, operator);
    }

    public static int findMainOperator(String expression) {
        int parenthesesCount = 0;
        int lowestPrecedence = Integer.MAX_VALUE;
        int mainOperatorIndex = -1;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Track parentheses
            if (c == '(') {
                parenthesesCount++;
            } else if (c == ')') {
                parenthesesCount--;
            }

            // Skip characters inside parentheses
            if (parenthesesCount != 0) {
                continue;
            }

            // Handle operators
            if (isOperator(c)) {
                // Check for unary '-' or '+' (e.g., '-3', '+4')
                if (i == 0 || isOperator(expression.charAt(i - 1)) || expression.charAt(i - 1) == '(') {
                    // Skip unary '-' and '+'
                    continue;
                }

                // Determine precedence
                int precedence = getPrecedence(c);
                if (precedence <= lowestPrecedence) {
                    lowestPrecedence = precedence;
                    mainOperatorIndex = i;
                }
            }
        }

        return mainOperatorIndex;
    }

    public static boolean isOperator(char c) {
        return "+-*/".indexOf(c) != -1;
    }

    public static int getPrecedence(char operator) {
        if (operator == '+' || operator == '-') return 1;
        if (operator == '*' || operator == '/') return 2;
        return Integer.MAX_VALUE;
    }

    public static double computeOperation(double a, double b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}
