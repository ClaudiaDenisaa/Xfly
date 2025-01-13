
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Bautura {
    private String numeBautura;
    private double pretBautura;

    public static ArrayList<Bautura> listaBautura = new ArrayList<>();

    /**
     * Constructor Bautura
     * @param numeBautura - tip String
     * @param pretBautura - tip double
     */
    public Bautura(String numeBautura, double pretBautura) {
        this.numeBautura = numeBautura;
        this.pretBautura = pretBautura;
    }

    /**
     * Adaugarea in baza de date a unei bauturi
     * @param b - tip Bautura
     */
    public static void addBauturaDB(Bautura b){
        try(Conn conexiune = new Conn()){
            if (conexiune.MyConn() == 0) {
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            } else {
                String query = "INSERT INTO drink (name, price) VALUES (?, ?)";
                PreparedStatement pstmt = conexiune.getDB().prepareStatement(query);
                pstmt.setString(1, b.getNumeBautura());
                pstmt.setDouble(2, b.getPretBautura());
                pstmt.executeUpdate();
                pstmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Luarea din baza de date a tuturor bauturilor si adaugarea lor in lista de bauturi
     */
    public static void getBauturaDB(){
        try(Conn conexiune = new Conn()){
            if (conexiune.MyConn() == 0) {
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                ArrayList<Bautura> lista = new ArrayList<>();
                String query = "SELECT * FROM drink";
                PreparedStatement pstmt = conexiune.getDB().prepareStatement(query);
                ResultSet rs = pstmt.executeQuery(query);
                while (rs.next()) {
                    String numeBautura = rs.getString("name");
                    double pretBautura = rs.getDouble("price");
                    lista.add(new Bautura(numeBautura, pretBautura));
                }
                Bautura.setListaBautura(lista);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Primeste o lista de bauturi si este adaugata in lista de bauturi globala
     * @param listaBautura - tip ArrayList<Bautura>
     */
    public static void setListaBautura(ArrayList<Bautura> listaBautura) {
        Bautura.listaBautura = listaBautura;
    }

    /**
     * Returneaza lista de bauturi
     * @return lista de bauturi - tip ArrayList<Bautura>
     */
    public static ArrayList<Bautura> getListaBautura() {
        return listaBautura;
    }


    /**
     * Getter Bautura
     * @return nume bautura - tip  String
     */
    public String getNumeBautura() {
        return numeBautura;
    }

    /**
     * Setter Bautura
     * @param numeBautura - tip String
     */
    public void setNumeBautura(String numeBautura) {
        this.numeBautura = numeBautura;
    }

    /**
     * Getter pret mancare
     * @return pret mancare - tip double
     */
    public double getPretBautura() {
        return pretBautura;
    }

    /**
     * Setter pret Bautura
     * @param pretBautura - tip double
     */
    public void setPretBautura(double pretBautura) {
        this.pretBautura = pretBautura;
    }
}
