import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * Clasa pentru avion
 */
public class Avion extends MyFrame {
    private int id;
    private String model;
    private int locuriA;
    private int locuriB;
    private JTextField textDenumire;
    private JTextField textClasaA;
    private JTextField textClasaB;
    private JButton btnAddAvion;
    private JButton buttonSpreMain;
    private JLabel labelErori;
    private JPanel frameAddAvion;

    private ArrayList<Plane> listaAvioane;


    /**
     * Constructor fereastra de adaugare
     */
    public Avion() {
        super();
        setTitle("Cont nou");
        setContentPane(frameAddAvion);
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.listaAvioane = new ArrayList<>();

        btnAddAvion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAvionBD();
            }
        });

    }



    /**
     * Construieste avioanele adaugate cu informatii din baza de date.
     * Adauga avioanele intro lista de avioane;
     */
    public void makeAvion(){
        try {
            Conn conexiune = new Conn();
            if(conexiune.MyConn() ==  0){
                JOptionPane.showMessageDialog(labelErori, "Eroare la conectarea cu baza de date: " , "Eroare", JOptionPane.ERROR_MESSAGE);
                String takeSql = "SELECT * FROM plane";
                PreparedStatement stmt = conexiune.getDB().prepareStatement(takeSql);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id_plane");
                    String model = rs.getString("model");
                    int locuriA = rs.getInt("capacityA");
                    int locuriB = rs.getInt("capacityB");

                    Plane a = new Plane(id,model,locuriA,locuriB);
                    listaAvioane.add(a);
                }
                rs.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(labelErori, "Eroare la conectarea cu baza de date: " , "Eroare", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Adauga datele in baza de date.
     * Obtine date din campurile formularului de adaugare avioane.
     */
   public void addAvionBD(){

        String u_model = textDenumire.getText();
        String u_capacityA = textClasaA.getText();
        String u_capacityB = textClasaB.getText();
        int valid = 1;

        if(u_model.isEmpty()) {
            valid = 0;
            labelErori.setText("Trebuie sa dai un nume!");
        }

        int verif_capacityA = 0;
        int verif_capacityB = 0;

        try{
            verif_capacityA = Integer.parseInt(u_capacityA);
            if(verif_capacityA <= 0) {
                valid = 0;
                labelErori.setText("Capacitatea clasei A trebuie să fie un număr pozitiv!");
            }
        } catch (NumberFormatException ex) {
            valid = 0;
            labelErori.setText("Capacitatea clasei A trebuie să fie format din cifre!");
        }

       try{
           verif_capacityB = Integer.parseInt(u_capacityB);
           if(verif_capacityB <= 0) {
               valid = 0;
               labelErori.setText("Capacitatea clasei B trebuie să fie un număr pozitiv!");
           }
       } catch (NumberFormatException ex) {
           valid = 0;
           labelErori.setText("Capacitatea clasei B trebuie să fie format din cifre!!");
       }

        try{
           Conn conexiune = new Conn();
           if(conexiune.MyConn() == 0) {
               JOptionPane.showMessageDialog(labelErori, "Eroare la conectarea cu baza de date: " , "Eroare", JOptionPane.ERROR_MESSAGE);
           }else if(valid == 1) {
               String insertSql = "INSERT INTO plane (model,capacityA,capacityB) VALUES (?,?,?)";
               PreparedStatement stmt = conexiune.getDB().prepareStatement(insertSql);
               stmt.setString(1, u_model);
               stmt.setInt(2, verif_capacityA);
               stmt.setInt(3, verif_capacityB);
               stmt.executeUpdate();
               stmt.close();
               conexiune.CloseMyConn();
           }else {
               textDenumire.setText("");
               textClasaA.setText("");
               textClasaB.setText("");
               conexiune.CloseMyConn();
           }
       }catch (SQLException e){
            JOptionPane.showMessageDialog(labelErori, "Eroare la conectarea cu baza de date: " , "Eroare", JOptionPane.ERROR_MESSAGE);
        }
   }

}
