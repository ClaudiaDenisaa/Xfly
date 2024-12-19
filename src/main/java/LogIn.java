import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Clasa de logare
 */
public class LogIn extends MyFrame{
    private JPanel panel1;
    private JTextField text_username;
    private JTextField text_password;
    private JButton b_log;
    private JLabel log_name;
    private JLabel log_password;
    private JButton b_cont;


    /**
     * Constructor logare
     */
    public LogIn() {
        super();
        setTitle("Log In");
        setContentPane(panel1);
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(null);
        setVisible(true);
    }



}
