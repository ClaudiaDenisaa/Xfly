import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class Bilet {
    private int id_bilet; //id_rezervation
    private String plecare;
    private LocalDate ora_plecare;
    private String sosire;
    private LocalDate ora_sosire;
    private String durata_calatorie;

    private int nrClasaA;
    private int nrClasaB;
    private int idZbor;
    private int idUser;
    private double pret;
    private String clasa;
    private String film;
    private int idMancare;
    private int idBautura;

   public static ArrayList<Bilet> listaBilete =  new ArrayList<>();

    /**
     * Constructor fara parametrii
     */
    public Bilet(){};
    /**
     * Constructor bilet
     * @param id id-ul biletului
     * @param p destinatia
     * @param hp ora la care ajunge la destinatie
     * @param s locul de unde pleaca
     * @param hs ora la care pleaca
     * @param d durata calatoriei
     */
   public Bilet(int id,String p,LocalDate hp,String s,LocalDate hs,String d,int nrClasaA,int nrClasaB,double pr){
        this.id_bilet=id;
        this.plecare=p;
        this.ora_plecare=hp;
        this.sosire=s;
        this.ora_sosire=hs;
        this.durata_calatorie=d;
        this.nrClasaA=nrClasaA;
        this.nrClasaB=nrClasaB;
        this.pret=pr;
    }

    /**
     * Constructor Bilet
     * @param idZbor - id_reservation
     * @param idUser - id_user
     * @param pretTotal - pretul total
     * @param clasa - clasa A  sau B
     * @param film - tip String
     * @param idMancare - id_food
     * @param idBautura - id_drink
     */
    public Bilet(int idZbor,int idUser,double pretTotal,String clasa,String film,int idMancare,int idBautura){
       this.idZbor=idZbor;
       this.idUser=idUser;
       this.pret=pretTotal;
       this.clasa=clasa;
       this.film=film;
       this.idMancare=idMancare;
       this.idBautura=idBautura;
    }

    /**
     * Constructor Bilet - lista de bilete va avea acest constructor pentru bilet
     * @param idBilet - tip int
     * @param pretTotal - tip double
     * @param clasa - tip String
     * @param film - tip String
     * @param idMancare - tip int
     * @param idBautura - tip int
     */
    public Bilet(int idBilet,double pretTotal,String clasa,String film,int idMancare,int idBautura){
        this.id_bilet=idBilet;
        this.pret=pretTotal;
        this.clasa=clasa;
        this.film=film;
        this.idMancare=idMancare;
        this.idBautura=idBautura;
    }

    /**
     * Adauga in baza de date un bilet, in tabela reservation
     * @param bilet - tip bilet
     */
    public static void addReservationBD(Bilet bilet){
        try(Conn conn = new Conn()){
            if(conn.MyConn() == 0){
                JOptionPane.showMessageDialog(null, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String addSQL = "INSERT INTO reservation (id_flight,id_user,price_reservation,class,movie,id_food,id_drink) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement pstmt = conn.getDB().prepareStatement(addSQL);
                pstmt.setInt(1, bilet.idZbor);
                pstmt.setInt(2, bilet.idUser);
                pstmt.setDouble(3, bilet.pret);
                pstmt.setString(4, bilet.clasa);
                pstmt.setString(5, bilet.film);
                pstmt.setInt(6, bilet.idMancare);
                pstmt.setInt(7, bilet.idBautura);
                pstmt.executeUpdate();
                pstmt.close();
            }
        }catch(Exception e){
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

    /**
     * Getter bilet
     * @return id bilet
     */
    public int getId_bilet() {
        return id_bilet;
    }

    /**
     * Getter plecare
     * @return de unde pleaca
     */
    public String getPlecare() {
        return plecare;
    }

    /**
     * Getter ora la care pleaca
     * @return ora de plecare
     */
    public LocalDate getOra_plecare() {
        return ora_plecare;
    }

    /**
     *Getter sosire destinatie
     * @return destinatia la care soseste
     */
    public String getSosire() {
        return sosire;
    }

    /**
     * Getter ora de sosire
     * @return ora de sosire
     */
    public LocalDate getOra_sosire() {
        return ora_sosire;
    }

    /**
     * Getter durata
     * @return durata calatoriei
     */
    public String getDurata_calatorie() {
        return durata_calatorie;
    }

    /**
     * Getter ntr locuri clasa A
     * @return - nr locuri - tip int
     */
    public int getNrClasaA() {
        return nrClasaA;
    }

    /**
     * Getter nr locuri clasa B
     * @return nr locuri - tip int
     */
    public int getNrClasaB() {
        return nrClasaB;
    }

    /**
     * Setter nr locuri clasa A
     * @param nrClasaA - tip int
     */
    public void setNrClasaA(int nrClasaA) {
        this.nrClasaA = nrClasaA;
    }

    /**
     * Setter nr locuri clasa B
     * @param nrClasaB - TIP INT
     */
    public void setNrClasaB(int nrClasaB) {
        this.nrClasaB = nrClasaB;
    }

    /**
     * Getter pret bilet
     * @return pret total bilet - tip double
     */
    public double getPret() {
        return pret;
    }

    /**
     * Setter pret bilet
     * @param pret - tip double
     */
    public void setPret(double pret) {
        this.pret = pret;
    }

    public String getClasa() {
        return clasa;
    }
    public void setClasa(String clasa) {
        this.clasa = clasa;
    }
    public String getFilm() {
        return film;
    }
    public void setFilm(String film) {
        this.film = film;
    }
    public int getIdZbor() {
        return idZbor;
    }
    public void setIdZbor(int idZbor) {
        this.idZbor = idZbor;
    }
    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public int getIdMancare() {
        return idMancare;
    }
    public void setIdMancare(int idMancare) {
        this.idMancare = idMancare;
    }
    public int getIdBautura() {
        return idBautura;
    }
    public void setIdBautura(int idBautura) {
        this.idBautura = idBautura;
    }

}
