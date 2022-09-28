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

public class App {
    public static void main(String[] args) {
        gui(500, 350);
    }

    // Untuk input-output dengan graphical user interface
    public static void gui(int w, int h) {
        JFrame frame = new CalculatorFrame();

        frame.setSize(w, h);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Infix to Postfix Converter & Calculator");
        frame.setVisible(true);
    }
}
