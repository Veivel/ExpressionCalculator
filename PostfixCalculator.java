import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import ExpressionConverter;

public class PostfixCalculator{

    /**
     * @param postfixExpression
     * @return Hasil perhitungan (long) dari postfixExpression
     * @throws EmptyStackException
     */
    public static long calculateFromPostfix(List<String> postfixExpression) throws EmptyStackException {
        Stack<Long> resStack = new Stack<Long>();
        try {
            while (postfixExpression.size() > 0) {
                String next = postfixExpression.remove(0);
    
                if (ExpressionConverter.isOperator(next)) {
                    resStack.push(calculate(resStack.pop(), resStack.pop(), next));
                } else {
                    resStack.push((long) Long.parseLong(next));
                }
            }
        } catch(EmptyStackException e) {
            throw new EmptyStackException();
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
        }
        return 999999l; // if wrong operator
    }
}