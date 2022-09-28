import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.StringTokenizer;
import java.util.List;

/*
 * Class yang mengandung window utama untuk program.
 */

public class CalculatorFrame extends JFrame{
    // Komponen JFrame
    private JLabel labelInfix = new JLabel("Enter your Infix Expression:");
    private JTextField textfieldInfix = new JTextField();
    private JLabel labelPostfix = new JLabel("Postfix Conversion:");
    private JTextField textfieldPostfix = new JTextField();
    private JButton btnCalculate = new JButton("Calculate!");
    private JTextField textfieldResult = new JTextField("<Result>");
    private JTextField textfieldStatus = new JTextField("Status");

    public CalculatorFrame() {
        this.setLayout( // Set panel layout
            new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS)
        );

        initListener();
        initUserInterface();
    }

    /**
     * Menghidupkan button listener (untuk menghitung hasil)
     */
    public void initListener() {
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String infix = textfieldInfix.getText().replace(" ", "") + " ";

                if (infix.equals(" ")) {
                    textfieldStatus.setText("Input cannot be empty!");
                } else {
                    List<String> postfixExpression;
                    try {
                        infix = Converter.standardizeInfix(infix);
                        StringTokenizer tokens = new StringTokenizer(infix);
                        postfixExpression = Converter.getPostfixExpression(tokens);
                        textfieldStatus.setText("Conversion & calculation successful!");
                    } catch (CalculationException e) {
                        textfieldStatus.setText(e.getMessage());
                        postfixExpression = e.getPostfix();
                    }
                        
                    try {
                        textfieldPostfix.setText(Evaluator.cleanPostfix(postfixExpression));
                        long res = Evaluator.calculateFromPostfix(postfixExpression);
                        textfieldResult.setText(String.valueOf(res));
                    } catch (CalculationException e) {
                        textfieldStatus.setText(e.getMessage());
                        textfieldResult.setText(String.valueOf(e.getResult()));
                    } catch (ArithmeticException e) {
                        textfieldStatus.setText("Division by zero: undefined");
                        textfieldResult.setText("-");
                    }
                }
            }
        });
    }

    /**
     * Menghidupkan GUI dan semua komponennya.
     */
    public void initUserInterface() {
        Dimension dimensionBtn = new Dimension(300, 50);
        textfieldInfix.setMaximumSize(dimensionBtn);
        textfieldPostfix.setMaximumSize(dimensionBtn);
        textfieldResult.setMaximumSize(dimensionBtn);
        textfieldStatus.setMaximumSize(
            new Dimension(400, 30)
        );

        labelInfix.setAlignmentX(CENTER_ALIGNMENT);
        textfieldInfix.setAlignmentX(CENTER_ALIGNMENT);

        labelPostfix.setAlignmentX(CENTER_ALIGNMENT);
        textfieldPostfix.setAlignmentX(CENTER_ALIGNMENT);
        textfieldPostfix.setEditable(false);

        btnCalculate.setAlignmentX(CENTER_ALIGNMENT);
        textfieldResult.setAlignmentX(CENTER_ALIGNMENT);
        textfieldResult.setEditable(false);

        textfieldStatus.setAlignmentX(CENTER_ALIGNMENT);
        textfieldStatus.setEditable(false);

        this.add(Box.createRigidArea(
            new Dimension(100, 50)
        ));

        this.add(labelInfix);
        this.add(textfieldInfix);
        this.add(labelPostfix);
        this.add(textfieldPostfix);
        this.add(btnCalculate);
        this.add(textfieldResult);
        this.add(textfieldStatus);
    }

}
