import javax.swing.*;

public class AddZborAdmin extends MyFrame{
    private JPanel panel1;
    private JTextField pretulZborului;
    private JTextField sosireAleasa;
    private JComboBox avionulSelectat;
    private JComboBox clasaSelectata;
    private JTextField dataZborului;
    private JButton adaugaButton;
    private JButton button2;
    private JTextField plecareAleasa;
    private JComboBox oraPlecare;
    private JComboBox oraSosire;
    private JComboBox comboBox1;
    private JComboBox comboBox2;

    //private Zbor[] listaZboruri;

    public AddZborAdmin() {
        super();
        setTitle("Adaugare de zboruri");
        setContentPane(panel1);
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
