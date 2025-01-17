package database;
import entitati.*;
import database.*;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BauturaDB {
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

    public static int getIdDrinkWhere(String numeBautura, double pretBautura) {
        try (Conn conexiune = new Conn()) {
            if (conexiune.MyConn() == 0) {
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            } else {
                String query3 = "SELECT id_drink FROM drink WHERE name = ? and price = ?";
                PreparedStatement pstmt3 = conexiune.getDB().prepareStatement(query3);
                pstmt3.setString(1, numeBautura);
                pstmt3.setDouble(2, pretBautura);
                ResultSet rs3 = pstmt3.executeQuery();
                while (rs3.next()) {
                    return rs3.getInt("id_drink");
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
