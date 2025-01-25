package database;

import entitati.Mancare;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MancareDB {
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
     * Cauta id ul care are numele si pretul dat ca parametru
     * @param numeMancare - String
     * @param pretMancare - double
     * @return id ul pentru food; 0 daca nu
     */
    public static int getIdFoodWhere(String numeMancare, double pretMancare){
        try (Conn conexiune = new Conn()) {
            if (conexiune.MyConn() == 0) {
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            } else {
                String query3 = "SELECT id_food FROM food WHERE name = ? and price = ?";
                PreparedStatement pstmt3 = conexiune.getDB().prepareStatement(query3);
                pstmt3.setString(1, numeMancare);
                pstmt3.setDouble(2, pretMancare);
                ResultSet rs3 = pstmt3.executeQuery();
                while (rs3.next()) {
                    return rs3.getInt("id_food");
                }
                rs3.close();
                pstmt3.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }
}
