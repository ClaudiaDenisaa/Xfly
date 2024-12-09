import javax.swing.*;
import java.awt.*;

public class Inregistrare extends MyFrame{
    private JPanel panelContNou;
    private JTextField textField1;
    private JLabel nume_nou;
    private JPasswordField passwordField1;
    private JLabel cnp;
    private JTextField textField2;
    private JLabel nr_telefon;
    private JFormattedTextField formattedTextField1;
    private JLabel email;
    private JTextField textField3;
    private JButton b_inregistrare;

    public Inregistrare() {
        super();
        setTitle("Cont nou");
        setContentPane(panelContNou);
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
