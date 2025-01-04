import javax.swing.*;
import java.awt.*;

public class VizualizareZborPasager extends JFrame {

    private int id;

    private JPanel panelPrincipal;

    public VizualizareZborPasager(int id) {
        super();
        this.id = id;
        setTitle("Vizualizarea zborurilor");
        setContentPane(panelPrincipal);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public int getId() {
        return id;
    }


}
