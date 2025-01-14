
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Mancare {
    private String numeMancare;
    private double pretMancare;

    public static ArrayList<Mancare> listaMancare = new ArrayList<>();

    /**
     * Constructor fara parametri
     */
    public Mancare() {};

    /**
     * Constructor Mancare
     * @param numeMancare - tip String
     * @param pretMancare - tip double
     */
    public Mancare(String numeMancare, double pretMancare) {
        this.numeMancare = numeMancare;
        this.pretMancare = pretMancare;
    }

    /**
     * Adauga mancare in baza de date , tabela food
     * @param m - tip Mancare
     */
    public static void addMancareDB(Mancare m){
        try(Conn conexiune = new Conn()){
            if (conexiune.MyConn() == 0) {
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            } else {
                String query = "INSERT INTO food (name, price) VALUES (?, ?)";
                PreparedStatement pstmt = conexiune.getDB().prepareStatement(query);
                pstmt.setString(1, m.getNumeMancare());
                pstmt.setDouble(2, m.getPretMancare());
                pstmt.executeUpdate();
                pstmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ia informatii din baza de date,creaza o lista si apoi initializeaza lista globala de mancare
     */
    public static void getMancareDB(){
        try(Conn conexiune = new Conn()){
            if (conexiune.MyConn() == 0) {
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                ArrayList<Mancare> lista = new ArrayList<>();
                String query = "SELECT * FROM food";
                PreparedStatement pstmt = conexiune.getDB().prepareStatement(query);
                ResultSet rs = pstmt.executeQuery(query);
                while (rs.next()) {
                    String numeMancare = rs.getString("name");
                    double pretMancare = rs.getDouble("price");
                    lista.add(new Mancare(numeMancare, pretMancare));
                }
                Mancare.setListaMancare(lista);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Seteaza lista globala de mancare
     * @param listaMancare - tip ArrayList<Mancare>
     */
    public static void setListaMancare(ArrayList<Mancare> listaMancare) {
        Mancare.listaMancare = listaMancare;
    }

    /**
     * Returneaza lista de mancare globala
     * @return lista de mancare - tip ArrayList<Mancare>
     */
    public static ArrayList<Mancare> getListaMancare() {
        return listaMancare;
    }


    /**
     * Getter mancare
     * @return nume mancare - tip  String
     */
    public String getNumeMancare() {
        return numeMancare;
    }

    /**
     * Setter mancare
     * @param numeMancare - tip String
     */
    public void setNumeMancare(String numeMancare) {
        this.numeMancare = numeMancare;
    }

    /**
     * Getter pret mancare
     * @return pret mancare - tip double
     */
    public double getPretMancare() {
        return pretMancare;
    }

    /**
     * Setter pret mancare
     * @param pretMancare - tip double
     */
    public void setPretMancare(double pretMancare) {
        this.pretMancare = pretMancare;
    }
}
