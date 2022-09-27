import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.List;

import javax.swing.JFrame;

/**

= = = = = = =
TP02 SDA-E
= = = = = = =
Nama : Givarrel Veivel Pattiwael
NPM  : 2106640341
Asdos: PDA

ACKNOWLEDGEMENTS: 
- https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/Shunting_yard.svg/1280px-Shunting_yard.svg.png

*/

public class TP2 {
    public static void main(String[] args) {
        JFrame frame = new CalculatorFrame();

        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Infix to Postfix Converter & Calculator");
        frame.setVisible(true);
    }

    // Untuk input-output dengan command line interface
    public static void cli() {
        Scanner sc = new Scanner(System.in);

        String inp = sc.nextLine().replace(" ", "") + " ";
        StringTokenizer tokens = new StringTokenizer(ExpressionConverter.standardizeInfix(inp));

        try {
            List<String> postfixExpression = ExpressionConverter.getPostfixExpression(tokens);
            System.out.println(PostfixCalculator.calculateFromPostfix(postfixExpression));
        } catch(Exception e) {
            System.out.println(e);
        }
        
        sc.close();
    }
}
