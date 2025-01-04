import javax.swing.*;
import java.awt.*;

/**
 * Clasa de VizualizareZborAdmin
 */
public class VizualizareZborAdmin extends JFrame {
    private JButton BtnMeniuAdmin;
    private JLabel MainView;
    private JPanel z1;
    private JPanel z2;
    private JPanel z3;
    private JPanel z4;
    private JTextPane textPlecare;
    private JTextPane textSosire;
    private JTextPane textOraSosire;
    private JTextPane textOraPlecare;
    private JButton btnModificare;
    private JButton button1;
    private JButton btnExitAdmin;
    private JPanel panel;

    /**
     * Constructor VizualizareZborAdmin
     */
    public VizualizareZborAdmin() {
        super();
        setTitle("Vizualizarea zborurilor");
        setContentPane(panel);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
