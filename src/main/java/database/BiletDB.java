package database;
import entitati.*;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static entitati.Bilet.listaBilete;


public class BiletDB {

    /**
     * Adauga in baza de date un bilet, in tabela reservation
     * @param bilet - tip bilet
     */
    public static void addReservationBD(Bilet bilet){
        try(Conn conexiune = new Conn()){
            if(conexiune.MyConn() == 0){
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String addSQL = "INSERT INTO reservation (id_flight,id_user,price_reservation,class,movie,id_food,id_drink) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement pstmt = conexiune.getDB().prepareStatement(addSQL);
                pstmt.setInt(1, bilet.getIdZbor());
                pstmt.setInt(2, bilet.getIdUser());
                pstmt.setDouble(3, bilet.getPret());
                pstmt.setString(4, bilet.getClasa());
                pstmt.setString(5, bilet.getFilm());
                pstmt.setInt(6, bilet.getIdMancare());
                pstmt.setInt(7, bilet.getIdBautura());
                pstmt.executeUpdate();
                pstmt.close();
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Rezervare fara mancare si bautura pentru ca nu exista
     * @param bilet
     */
    public static void addReservationBDwithoutDrinkAndFood(Bilet bilet){
        try(Conn conexiune = new Conn()){
            if(conexiune.MyConn() == 0){
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String addSQL = "INSERT INTO reservation (id_flight,id_user,price_reservation,class,movie) VALUES (?,?,?,?,?)";
                PreparedStatement pstmt = conexiune.getDB().prepareStatement(addSQL);
                pstmt.setInt(1, bilet.getIdZbor());
                pstmt.setInt(2, bilet.getIdUser());
                pstmt.setDouble(3, bilet.getPret());
                pstmt.setString(4, bilet.getClasa());
                pstmt.setString(5, bilet.getFilm());
                pstmt.executeUpdate();
                pstmt.close();
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Rezervare fara mancare pentru ca nu exista
     * @param bilet
     */
    public static void addReservationBDwithoutFood(Bilet bilet){
        try(Conn conexiune = new Conn()){
            if(conexiune.MyConn() == 0){
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String addSQL = "INSERT INTO reservation (id_flight,id_user,price_reservation,class,movie,id_drink) VALUES (?,?,?,?,?,?)";
                PreparedStatement pstmt = conexiune.getDB().prepareStatement(addSQL);
                pstmt.setInt(1, bilet.getIdZbor());
                pstmt.setInt(2, bilet.getIdUser());
                pstmt.setDouble(3, bilet.getPret());
                pstmt.setString(4, bilet.getClasa());
                pstmt.setString(5, bilet.getFilm());
                pstmt.setInt(6, bilet.getIdBautura());
                pstmt.executeUpdate();
                pstmt.close();
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Rezervare fara bautura pentru ca nu a fost adaugata inca de admin
     * @param bilet
     */
    public static void addReservationBDwithoutDrink(Bilet bilet){
        try(Conn conexiune = new Conn()){
            if(conexiune.MyConn() == 0){
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String addSQL = "INSERT INTO reservation (id_flight,id_user,price_reservation,class,movie,id_food) VALUES (?,?,?,?,?,?)";
                PreparedStatement pstmt = conexiune.getDB().prepareStatement(addSQL);
                pstmt.setInt(1, bilet.getIdZbor());
                pstmt.setInt(2, bilet.getIdUser());
                pstmt.setDouble(3, bilet.getPret());
                pstmt.setString(4, bilet.getClasa());
                pstmt.setString(5, bilet.getFilm());
                pstmt.setInt(6, bilet.getIdMancare());
                pstmt.executeUpdate();
                pstmt.close();
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void deleteReservationBD(int idZReservation){
        try(Conn conn = new Conn()){
            if(conn.MyConn() == 0){
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String deleteSQL = "DELETE FROM reservation WHERE id_reservation=?";
                PreparedStatement pstmt = conn.getDB().prepareStatement(deleteSQL);
                pstmt.setInt(1, idZReservation);
                pstmt.executeUpdate();
                pstmt.close();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void takeReservationBD(int idUser){
        try(Conn conn = new Conn()){
            if(conn.MyConn() == 0){
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String query = "SELECT id_reservation,price_reservation,class,movie,id_food,id_drink FROM reservation WHERE id_user = ?";
                PreparedStatement stmt = conn.getDB().prepareStatement(query);
                stmt.setInt(1, idUser);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int idReservation = rs.getInt("id_reservation");
                    double pret = rs.getDouble("price_reservation");
                    String clasa = rs.getString("class");
                    String film = rs.getString("movie");
                    int idFood = rs.getInt("id_food");
                    int idDrink = rs.getInt("id_drink");
                    Bilet bilet = new Bilet(idReservation,pret,clasa,film,idFood,idDrink);
                    listaBilete.add(bilet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
