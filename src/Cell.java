
public class Cell {

    public static boolean isNumber(String text) {
        boolean result = true;
        try {
            double d = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }

    public static boolean isText(String text) {
        boolean result = true;
        if (isNumber(text)) {
            return false;
        }
        if (text.contains("=")) {
            result = false;
        }

        return result;
    }

    public static boolean isForm(String text) {
        if(containsInvalidCharacters(text))
            return false;
        //add check that determines is after any arithmetic there is '(' if so true
        if (text.contains("=") && numOfOperand(text, '=') == 1 && isValidBracket(text)) {
            if (!(isNumber(text) && isText(text))) {
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

    //determines that every operand has a number before it --not being used
    /*
    public static boolean hasNumberB4After(String text, char c) {
        char[] chars = text.toCharArray();
        boolean result = true;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == c) {
                try {
                    double d = Double.parseDouble(String.valueOf(chars[i - 1]));
                    double f = Double.parseDouble(String.valueOf(chars[i + 1]));
                } catch (Exception e) {
                    result = false;
                }
                if (chars[i + 1] == '(' || chars[i + 1] == ')') {
                    result = true;
                }
            }
        }
        return result;
    }

     */

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
        for (int i = 0; i < text.length(); i++) {
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
            if (c == '(') parenthesesCount++;
            else if (c == ')') parenthesesCount--;
            else if (parenthesesCount == 0 && isOperator(c)) {
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

