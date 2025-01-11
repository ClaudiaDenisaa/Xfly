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

    private static ArrayList<Zbor> listaZboruri = new ArrayList<>();
    private Throwable e;


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
        this.destinatiePlecare = ds;
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
     * Adaugarea zborului in baza de date
     * @param zbor - tip Zbor
     */
    public  static void addZborDB(Zbor zbor) {
        try(Conn conexiune = new Conn()) {
            if(conexiune.MyConn() == 0){
                //JOptionPane.showMessageDialog(null, "Eroare: " + e.getMessage(),"Eroare ",JOptionPane.ERROR_MESSAGE);
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

    public static void addZborDinBDinListaZboruri(){
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
                        if (conexiune.MyConn() == 0) {
                            throw new RuntimeException("Conexiunea la baza de date a esuat!");
                        } else {
                            Plane plane = new Plane();
                            String query3 = "SELECT * FROM plane WHERE id_plane=?";
                            PreparedStatement stmt2 = conexiune2.getDB().prepareStatement(query3);
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
    public int getId_zbor() {
        return id_zbor;
    }

    public void setId_zbor(int id_zbor) {
        this.id_zbor = id_zbor;
    }

    public Plane getAvion() {
        return avion;
    }

    public void setAvion(Plane avion) {
        this.avion = avion;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public String getDestinatiePlecare() {
        return destinatiePlecare;
    }

    public void setDestinatiePlecare(String destinatiePlecare) {
        this.destinatiePlecare = destinatiePlecare;
    }

    public String getDestinatieSosire() {
        return destinatieSosire;
    }

    public void setDestinatieSosire(String destinatieSosire) {
        this.destinatieSosire = destinatieSosire;
    }

    public LocalTime getOraPlecare() {
        return oraPlecare;
    }

    public void setOraPlecare(LocalTime oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    public LocalTime getOraSosire() {
        return oraSosire;
    }

    public void setOraSosire(LocalTime oraSosire) {
        this.oraSosire = oraSosire;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getDurata() {
        return durata;
    }

    public void setDurata(LocalTime durata) {
        this.durata = durata;
    }

   @Override
    public String toString() {
        return avion +" " +pret + " "+destinatiePlecare+" "+destinatieSosire+" "+oraPlecare+" "+oraSosire+" "+data;
   }

}
