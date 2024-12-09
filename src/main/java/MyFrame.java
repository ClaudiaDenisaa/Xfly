import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.Toolkit;


public class MyFrame extends JFrame {
    //constructor
    public MyFrame() {
        this.setTitle("XFly");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(800, 600);
        Image icon = Toolkit.getDefaultToolkit().getImage("resources/X.gif");
        this.setIconImage(icon);
        this.setVisible(true);
    }




}
