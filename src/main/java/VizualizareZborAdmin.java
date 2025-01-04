import javax.swing.*;
import java.awt.*;


public class VizualizareZborAdmin extends MyFrame {
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
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
