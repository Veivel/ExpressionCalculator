import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class ExpressionConverter {

    /**
     * @param expression (Infix) in string form
     * @return Standardized infix expression in string form - can be tokenized
     */
    public static String standardizeInfix(String expression) {
        char[] charArr = expression.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < charArr.length - 1; i++) {
            if (isParenthesis(charArr[i])) {
                sb.append(" ");
                sb.append(charArr[i]);
                sb.append(" ");
            } else if (isOperator(charArr[i]) != isOperator(charArr[i+1])) {
                sb.append(charArr[i]);
                sb.append(" ");
            } else {
                sb.append(charArr[i]);
            }
        }

        return sb.toString();

    }

    /**
     * @param chr - token
     * @return integer - tingkat prioritas operator <p>
     * $: 4, *: 3, /:3, +:2, -:2, ():1, non-operator:-1
     */
    public static Integer operatorPriority(char chr) {
        switch(chr) {
            case '$':
                return 4;
            case '*':
                return 3;
            case '/':
                return 3;
            case '+':
                return 2;
            case '-':
                return 2;
            case '(':
                return 1;
            case ')':
                return 1;
        }
        return -1;
    }
    
    /**
     * @param chr - token
     * @return boolean - True jika chr adalah operator, false jika bukan.
     */
    public static Boolean isOperator(char chr) {
        if (operatorPriority(chr) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param str - token multi-character
     * @return boolean - false jika ada satu atau lebih karakter non-operator
     */
    public static Boolean isOperator(String str) {
        for (char chr : str.toCharArray()) {
            if (!isOperator(chr)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param chr - token
     * @return integer 1 untuk kurung awal, 2 untuk kurung akhir
     */
    public static int parenthesisPriority (char chr) {
        switch(chr) {
            case '(':
                return 1;
            case ')':
                return 2;
        }
        return -1;
    }

    /**
     * @param chr - token
     * @return boolean - True jika chr adalah kurung awal/akhir, false jika bukan.
     */
    public static Boolean isParenthesis(char chr) {
        if (parenthesisPriority(chr) > 0) return true;
        return false;
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

    /**
     * Mengkonversi infix yang sudah tokenized menjadi postfix.
     * @param tokens of Infix expression (StringTokenizer)
     * @return Postfix expression (List<String>)
     * @throws IOException
     */
    public static List<String> getPostfixExpression(StringTokenizer tokens) throws IOException {
        List<String> postfixExpression = new LinkedList<String>();
        Stack<Character> operatorStack = new Stack<Character>();
        int operatorCount = 0, operandCount = 0;
    
        // Meng-iterasi setiap token pada tokenized infix expression
        while (tokens.hasMoreTokens()) {
            String next = tokens.nextToken();
            
            if (isOperator(next)) { 
                // jika token selanjutnya adalah operator
                char nextOperator = next.charAt(0);

                if (operandCount + operatorCount == 0 && next.equals("-")) {
                    // jika expression dimulai dengan bilangan negatif, perbaiki (tetap valid)
                    postfixExpression.add("0");
                    operandCount++;

                } else if (operatorStack.empty()) { 
                    // jangan peek jika kosong. soalnya pasti di-push juga.
                    operatorStack.push(nextOperator);
                    continue;
    
                } else if (parenthesisPriority(nextOperator) == 1) {
                    // jika kurungnya adalah kurung awal.
                    operatorStack.push(nextOperator);
    
                } else if (parenthesisPriority(nextOperator) == 2){ 
                    // jika kurungnya kurung akhir, pop operator sampai ketemu kurung awal.
                    while (parenthesisPriority(operatorStack.peek()) < 0) {
                        postfixExpression.add(operatorStack.pop().toString());
                        operatorCount++;
                    }
                } else while (operatorPriority(nextOperator) < 4 && 
                operatorPriority(nextOperator) <= operatorPriority(operatorStack.peek())) {
                    // jika operator selanjutnya bukan pangkat, namun prioritas lebih tinggi dari yang di top of stack
                    postfixExpression.add(operatorStack.pop().toString());
                    operatorCount++;
                    if (operatorStack.empty()) break;
                }
                operatorStack.push(nextOperator);
    
            } else { 
                // jika token selanjutnya operand
                postfixExpression.add(next);
                operandCount++;
            }
        }
        
        // Ketika infix sudah habis/selesai, pop semua operator yang tersisa pada stack.
        while (!operatorStack.empty()) {
            if (isParenthesis(operatorStack.peek())) {
                operatorStack.pop(); // jangan masukkan tanda kurung ke postfix
            } else {
                postfixExpression.add(operatorStack.pop().toString());
                operatorCount++;
            }
        }
    
        // Error-handling (TODO)
        if (operandCount != operatorCount + 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unbalanced operator/operands: ");
            sb.append(operandCount).append("&").append(operatorCount);
            String msg = sb.toString();
    
            throw new IOException(msg);
        }
    
        return postfixExpression;
    }
}
