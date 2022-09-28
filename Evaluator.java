import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/*
 * Kelas yang mengandung semua keperluan evaluator postfix expression.
 */

public class Evaluator{

    /**
     * @param postfix
     * @return Hasil perhitungan (long) dari postfix
     * @throws EmptyStackException
     */
    public static long calculateFromPostfix(List<String> postfix) throws CalculationException{
        Stack<Long> resStack = new Stack<Long>();
        long a = 0, b = 0;

        try {
            while (postfix.size() > 0) {
                String next = postfix.remove(0);
    
                if (Converter.isOperator(next)) {
                    a = resStack.pop();
                    b = resStack.pop();
                    resStack.push(calculate(a, b, next));
                } else {
                    resStack.push((long) Long.parseLong(next));
                }
            }
        } catch(EmptyStackException e) {
            throw new CalculationException("Missing operand/operator", (long) a);
        }

        return resStack.pop();
    }

    /**
     * @param b (long)
     * @param a (long)
     * @param operator (String berupa $ * / + -)
     * @return hasil dari a [operator] b
     */
    public static long calculate(long b, long a, String operator) {
        switch(operator){
            case "$":
                return (long) Math.pow(a, b);
            case "*":
                return a * b;
            case "/":
                return a / b;
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "–": // BERBEDA DENGAN - BIASA
                return a - b;
        }
        return 999999l; // if wrong operator
    }

    /**
     * @param postfixList
     * @return Bentuk string dari Postfix
     */
    public static String cleanPostfix(List<String> postfixList) {
        StringBuilder sb = new StringBuilder();
    
        for (String item : postfixList) {
            sb.append(item);
            sb.append(" ");
        }
    
        return sb.toString();
    }
}