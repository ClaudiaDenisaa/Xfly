import com.mysql.cj.x.protobuf.MysqlxPrepare;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class Zbor {
    private int id_zbor;
    private Plane avion;
    private double pret;
    private String destinatiePlecare;
    private String destinatieSosire;
    private LocalTime oraPlecare;
    private LocalTime oraSosire;
    private LocalDate data;
    private LocalTime durata;//localtime in db

    public static ArrayList<Zbor> listaZboruri = new ArrayList<>();




    /**
     * Constructor Zbor
     * @param avion avion
     * @param pret pret
     * @param dp data plecare
     * @param ds data sosire
     * @param op ora plecare
     * @param os ora sosire
     * @param data data
     * @param durata durata
     */
    public Zbor(Plane avion,double pret,String dp,String ds,LocalTime op,LocalTime os,LocalDate data,LocalTime durata) {
        this.avion = avion;
        this.pret = pret;
        this.destinatiePlecare = dp;
        this.destinatieSosire = ds;
        this.oraPlecare = op;
        this.oraSosire = os;
        this.data = data;
        this.durata = durata;
    }

    /**
     * Constructor gol
     */
    public Zbor(){};


    /**
     * Adaugare de zboruri in lista
     * @param zbor - tip Zbor
     */
    public static void addInListaZboruri(Zbor zbor) {
        listaZboruri.add(zbor);
    }

    /**
     * Returnarea listei de zboruri
     * @return lista de zboruri - tip Zbor
     */
    public static ArrayList<Zbor> getListaZboruri() {
        return listaZboruri;
    }

    /**
     * Sterge toata lista de zboruri. Va fi goala.
     */
    public static void stergeListaZboruri(){
        listaZboruri.clear();
    }

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
                stmt.setDate(8,Date.valueOf(zbor.getData().toString()));
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
     * Ia toate zborurile (care sunt asociate id ului. utilizatorului care s a logat) din baza de date si se creaza o lista de zboruri
     * @param idUser - tip int
     */
    public static void addZborDinBDinListaZboruri(int idUser){
        Zbor.stergeListaZboruri();
        try (Conn conexiune = new Conn()) {
            if (conexiune.MyConn() == 0) {
                throw new RuntimeException("Conexiunea la baza de date a esuat!");
            } else {
                String query2 = "SELECT f.id_flight, f.id_plane, f.classA, f.classB, f.price, f.departure, f.arrival, f.h_departure, f.h_arrival, f.date, f.duration FROM flight f INNER JOIN reservation r ON f.id_flight = r.id_flight WHERE r.id_user = ?;";
                //String query2 = "SELECT * FROM flight WHERE id_flight=?";
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
                                }//else{
                                    //System.out.println("Nu sunt avioane adaugate!");
                                    //SwingUtilities.invokeLater(Avion::new);
                                //}
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
     * Getter id zbor
     * @return id ul zborului - tip int
     */
    public int getId_zbor() {
        return id_zbor;
    }

    /**
     * Setter id zbor
     * @param id_zbor - tip int
     */
    public void setId_zbor(int id_zbor) {
        this.id_zbor = id_zbor;
    }

    /**
     * Getter avion
     * @return avion - tip Avion
     */
    public Plane getAvion() {
        return avion;
    }

    /**
     *Setter avion
     * @param avion - tip Avion
     */
    public void setAvion(Plane avion) {
        this.avion = avion;
    }

    /**
     * Getter pret
     * @return pret - tip double
     */
    public double getPret() {
        return pret;
    }

    /**
     * Setter pret
     * @param pret - tip double
     */
    public void setPret(double pret) {
        this.pret = pret;
    }

    /**
     * Getter destinatie plecare
     * @return destinatie plecare - tip String
     */
    public String getDestinatiePlecare() {
        return destinatiePlecare;
    }

    /**
     * Setter destinatie plecare
     * @param destinatiePlecare - tip String
     */
    public void setDestinatiePlecare(String destinatiePlecare) {
        this.destinatiePlecare = destinatiePlecare;
    }

    /**
     * Getter destinatie sosire
     * @return sosire - tip String
     */
    public String getDestinatieSosire() {
        return destinatieSosire;
    }

    /**
     * Setter destinatie sosire
     * @param destinatieSosire - tip String
     */
    public void setDestinatieSosire(String destinatieSosire) {
        this.destinatieSosire = destinatieSosire;
    }

    /**
     * Getter ora plecare
     * @return ora plecare - tip LocalTime
     */
    public LocalTime getOraPlecare() {
        return oraPlecare;
    }

    /**
     * Setter ora plecare
     * @param oraPlecare - tip LocalTime
     */
    public void setOraPlecare(LocalTime oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    /**
     * Getter ora sosire
     * @return ora sosire - tip LocalTime
     */
    public LocalTime getOraSosire() {
        return oraSosire;
    }

    /**
     * Setter ora sosire
     * @param oraSosire - tip. LocalTime
     */
    public void setOraSosire(LocalTime oraSosire) {
        this.oraSosire = oraSosire;
    }

    /**
     * Getter data
     * @return data - tip LocalDate
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Setter data
     * @param data - tip LocalDate
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Getter durata
     * @return durata - tip. LocalTime
     */
    public LocalTime getDurata() {
        return durata;
    }

    /**
     * Setter durata
     * @param durata - tip LocalTime
     */
    public void setDurata(LocalTime durata) {
        this.durata = durata;
    }

   @Override
    public String toString() {
        return avion +" " +pret + " "+destinatiePlecare+" "+destinatieSosire+" "+oraPlecare+" "+oraSosire+" "+data;
   }

}
