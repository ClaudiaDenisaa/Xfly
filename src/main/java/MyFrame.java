import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.Toolkit;


public class MyFrame extends JFrame {
    /**
     * Constructor MyFrame
     */
    public MyFrame() {
        this.setTitle("XFly");
        Image icon = Toolkit.getDefaultToolkit().getImage("X.gif");
        this.setIconImage(icon);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(800, 600);
        this.setVisible(true);
    }





}
