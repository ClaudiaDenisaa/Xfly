package database;

import database.*;
import entitati.*;
import gui.*;
import javax.swing.*;
import java.sql.*;

import static entitati.Zbor.addInListaZboruri;
import static entitati.Zbor.listaZboruri;

public class ZborDB {

    /**
     * Se sterge din baza de date si din lista de zboruri un zbor cu acel id primit
     * @param idZbor - tip int
     */
    public static void stergereZbor(int idZbor){
        try(Conn conexiune = new Conn()){
            if(conexiune.MyConn() == 0) {
                throw new RuntimeException("Conexiunea la baza de date a eșuat!");
            }else{
                String query = "DELETE FROM flight WHERE id_flight = ?";
                PreparedStatement stmt = conexiune.getDB().prepareStatement(query);
                stmt.setInt(1,idZbor);
                int liniiAfectate = stmt.executeUpdate();
                stmt.close();

                if(liniiAfectate > 0){
                    listaZboruri.removeIf(zbor -> zbor.getId_zbor() == idZbor);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Adaugarea zborului in baza de date
     * @param zbor - tip Zbor
     */
    public  static void addZborDB(Zbor zbor) {
        try(Conn conexiune = new Conn()) {
            if(conexiune.MyConn() == 0){
                System.out.println("Eroare la conectarea cu baza de date!");
            }
            else{
                String query = "INSERT INTO flight (id_plane,classA,price,departure,arrival,h_departure,h_arrival,date,duration,classB) VALUES (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement stmt = conexiune.getDB().prepareStatement(query);

                stmt.setInt(1,zbor.getAvion().getId());
                stmt.setInt(2,zbor.getAvion().getLocuri());
                stmt.setDouble(3,zbor.getPret());
                stmt.setString(4,zbor.getDestinatiePlecare());
                stmt.setString(5,zbor.getDestinatieSosire());
                String formPlecare = zbor.getOraPlecare() + ":00";
                stmt.setTime(6, Time.valueOf(formPlecare));
                String formSosire = zbor.getOraSosire() + ":00";
                stmt.setTime(7, Time.valueOf(formSosire));
                stmt.setDate(8, Date.valueOf(zbor.getData().toString()));
                String formDurata = zbor.getDurata() + ":00";
                stmt.setTime(9,Time.valueOf(formDurata));
                stmt.setInt(10,zbor.getAvion().getLocuriB());
                stmt.executeUpdate();
                stmt.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eroare: " + e.getMessage(),"Eroare ",JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Se iau toate zborurile din baza de date si se creaza lista de zboruri cu informatii
     */
    public static void addZborDinBDinListaZboruri(){
        Zbor.stergeListaZboruri();
        try (Conn conexiune = new Conn()) {
            if (conexiune.MyConn() == 0) {
                throw new RuntimeException("Conexiunea la baza de date a esuat!");
            } else {
                String query2 = "SELECT * FROM flight";
                PreparedStatement stmt = conexiune.getDB().prepareStatement(query2);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    Zbor zborNou = new Zbor();
                    zborNou.setId_zbor(rs.getInt("id_flight"));

                    try(Conn conexiune2 = new Conn()) {
                        if (conexiune2.MyConn() == 0) {
                            throw new RuntimeException("Conexiunea la baza de date a esuat!");
                        } else {
                            Plane plane = new Plane();
                            String query3 = "SELECT * FROM plane WHERE id_plane=?";
                            PreparedStatement stmt2 = conexiune2.getDB().prepareStatement(query3);
                            stmt2.setInt(1, rs.getInt("id_plane"));
                            ResultSet rs2 = stmt2.executeQuery();
                            while (rs2.next()) {
                                if (!rs2.wasNull()) {
                                    plane.setId(rs2.getInt(1));
                                    plane.setModel(rs2.getString(2));
                                    plane.setLocuri(rs2.getInt(3));
                                    plane.setLocuriB(rs2.getInt(4));
                                }else{
                                    System.out.println("Nu sunt avioane adaugate!");
                                    SwingUtilities.invokeLater(Avion::new);
                                }
                            }
                            zborNou.setAvion(plane);
                            rs2.close();
                            stmt2.close();
                        }
                    }
                    zborNou.setDestinatiePlecare(rs.getString("departure"));
                    zborNou.setDestinatieSosire(rs.getString("arrival"));
                    zborNou.setOraPlecare(rs.getTime("h_departure").toLocalTime());
                    zborNou.setOraSosire(rs.getTime("h_arrival").toLocalTime());
                    zborNou.setData(rs.getDate("date").toLocalDate());
                    zborNou.setDurata(rs.getTime("duration").toLocalTime());
                    zborNou.setPret(rs.getDouble("price"));

                    addInListaZboruri(zborNou);
                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eroare: " + e.getMessage(), "Eroare ", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Ia toate zborurile (care sunt asociate id ului. utilizatorului care s a logat) din baza de date si se creaza o lista de zboruri
     * @param idUser - tip int
     */
    public static void addZborDinBDinListaZboruri(int idUser){
        Zbor.stergeListaZboruri();
        try (Conn conexiune = new Conn()) {
            if (conexiune.MyConn() == 0) {
                JOptionPane.showMessageDialog(null, "Eroare! " , "Eroare ", JOptionPane.ERROR_MESSAGE);
            } else {
                String query2 = "SELECT f.id_flight, f.id_plane, f.classA, f.classB, f.price, f.departure, f.arrival, f.h_departure, f.h_arrival, f.date, f.duration FROM flight f INNER JOIN reservation r ON f.id_flight = r.id_flight WHERE r.id_user = ?;";
                PreparedStatement stmt = conexiune.getDB().prepareStatement(query2);
                stmt.setInt(1,idUser);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    Zbor zborNou = new Zbor();
                    zborNou.setId_zbor(rs.getInt("id_flight"));


                    Plane plane = new Plane();
                    String query3 = "SELECT * FROM plane WHERE id_plane=?";
                    PreparedStatement stmt2 = conexiune.getDB().prepareStatement(query3);
                    stmt2.setInt(1,rs.getInt("id_plane"));
                    ResultSet rs2 = stmt2.executeQuery();
                    while(rs2.next()){
                        if(!rs2.wasNull()){
                            plane.setId(rs2.getInt(1));
                            plane.setModel(rs2.getString(2));
                            plane.setLocuri(rs2.getInt(3));
                            plane.setLocuriB(rs2.getInt(4));
                        }else{
                        System.out.println("Nu sunt avioane adaugate!");
                        SwingUtilities.invokeLater(Avion::new);
                        }
                    }
                    zborNou.setAvion(plane);
                    rs2.close();
                    stmt2.close();


                    zborNou.setDestinatiePlecare(rs.getString("departure"));
                    zborNou.setDestinatieSosire(rs.getString("arrival"));
                    zborNou.setOraPlecare(rs.getTime("h_departure").toLocalTime());
                    zborNou.setOraSosire(rs.getTime("h_arrival").toLocalTime());
                    zborNou.setData(rs.getDate("date").toLocalDate());
                    zborNou.setDurata(rs.getTime("duration").toLocalTime());
                    zborNou.setPret(rs.getDouble("price"));

                    addInListaZboruri(zborNou);
                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eroare: " + e.getMessage(), "Eroare ", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Returneaza pretul unui zbor
     * @param id - idl zborului
     * @return pretul(double) sau -1 daca nu s a putut obtine
     */
    public static double getPrice(int id){
        try(Conn conexiune = new Conn()) {
            if(conexiune.MyConn() == 0){
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String query1 = "SELECT price FROM flight WHERE id_flight = ?";
                PreparedStatement pstm1 = conexiune.getDB().prepareStatement(query1);
                pstm1.setInt(1, id);
                ResultSet result1 = pstm1.executeQuery();
                while(result1.next()){
                    return result1.getDouble("price");
                }
                result1.close();
                pstm1.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Eroare la conectarea cu baza de date1: " + e.getMessage(),"EROARE",JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }

    /**
     * Returneaza nr de locuri de la clasa A pentru un zbor
     * @param id - id ul zborului
     * @return nr locuri de la clasa A(int) sau -1 daca nu s a putut obtine
     */
    public static int getNrLocuriClasaA(int id){
        try(Conn conexiune = new Conn()) {
            if(conexiune.MyConn() == 0){
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String query1 = "SELECT classA,classB,price FROM flight WHERE id_flight = ?";
                PreparedStatement pstm1 = conexiune.getDB().prepareStatement(query1);
                pstm1.setInt(1, id);
                ResultSet result1 = pstm1.executeQuery();
                while(result1.next()){
                    return result1.getInt("classA");

                }
                result1.close();
                pstm1.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Eroare la conectarea cu baza de date1: " + e.getMessage(),"EROARE",JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }

    /**
     * Returneaza nr de locuri de la clasa B pentru un zbor
     * @param id - id ul zborului
     * @return nr locuri de la clasa B(int) sau -1 daca nu s a putut obtine
     */
    public static int getNrLocuriClasaB(int id){
        try(Conn conexiune = new Conn()) {
            if(conexiune.MyConn() == 0){
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String query1 = "SELECT classA,classB,price FROM flight WHERE id_flight = ?";
                PreparedStatement pstm1 = conexiune.getDB().prepareStatement(query1);
                pstm1.setInt(1, id);
                ResultSet result1 = pstm1.executeQuery();
                while(result1.next()){
                    return result1.getInt("classB");
                }
                result1.close();
                pstm1.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Eroare la conectarea cu baza de date1: " + e.getMessage(),"EROARE",JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }

    /**
     * Update numar de locuri la clasa B
     * @param id - id ul unui zbor
     * @param b - nr de locuri
     */
    public static void setNrLocuriClasaB(int id, int b){
        try(Conn conexiune = new Conn()) {
            if(conexiune.MyConn() == 0){
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String updateSQL = "UPDATE flight SET classB = ? WHERE id_flight = ?";
                PreparedStatement pstmt2 = conexiune.getDB().prepareStatement(updateSQL);
                pstmt2.setInt(1,b);
                pstmt2.setInt(2,id);
                pstmt2.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update numar de locuri la clasa A
     * @param id - id ul unui zbor
     * @param a - nr de locuri
     */
    public static void setNrLocuriClasaA(int id, int a){
        try(Conn conexiune = new Conn()) {
            if(conexiune.MyConn() == 0){
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String updateSQL = "UPDATE flight SET classA = ? WHERE id_flight = ?";
                PreparedStatement pstmt2 = conexiune.getDB().prepareStatement(updateSQL);
                pstmt2.setInt(1,a);
                pstmt2.setInt(2,id);
                pstmt2.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
