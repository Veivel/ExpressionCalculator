import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.EmptyStackException;
import java.util.List;

public class CalculatorFrame extends JFrame{
    // Komponen JFrame
    private JLabel labelInfix = new JLabel("Enter your Infix Expression:");
    private JTextField textfieldInfix = new JTextField();
    private JLabel labelPostfix = new JLabel("Postfix Conversion:");
    private JTextField textfieldPostfix = new JTextField();
    private JButton btnCalculate = new JButton("Calculate!");
    private JTextField textfieldResult = new JTextField("<Result>");

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
                    textfieldResult.setText("Error: Invalid Infix Expression");
                } else {
                    try {
                        StringTokenizer tokens = new StringTokenizer(ExpressionConverter.standardizeInfix(infix));
                        List<String> postfixExpression = ExpressionConverter.getPostfixExpression(tokens);
                        textfieldPostfix.setText(ExpressionConverter.cleanPostfix(postfixExpression));
                        
                        long res = PostfixCalculator.calculateFromPostfix(postfixExpression);
                        textfieldResult.setText(String.valueOf(res));

                    } catch(IOException e) {
                        textfieldResult.setText(e.getMessage());
                    } catch(EmptyStackException e) {
                        textfieldResult.setText("Missing open parenthesis.");
                    } catch(Exception e) {
                        textfieldResult.setText(e.toString());
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

        labelInfix.setAlignmentX(CENTER_ALIGNMENT);
        textfieldInfix.setAlignmentX(CENTER_ALIGNMENT);
        labelPostfix.setAlignmentX(CENTER_ALIGNMENT);
        textfieldPostfix.setAlignmentX(CENTER_ALIGNMENT);
        textfieldPostfix.setEditable(false);
        btnCalculate.setAlignmentX(CENTER_ALIGNMENT);
        textfieldResult.setAlignmentX(CENTER_ALIGNMENT);
        textfieldResult.setEditable(false);

        this.add(Box.createRigidArea(
            new Dimension(100, 50)
        ));

        this.add(labelInfix);
        this.add(textfieldInfix);
        this.add(labelPostfix);
        this.add(textfieldPostfix);
        this.add(btnCalculate);
        this.add(textfieldResult);
    }

}
