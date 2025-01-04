import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Pasager extends Persoana{
    private int id;
    private Bilet bilet;
    private String cnp;
    private int telefon;
    private String email;

    private Pasager utilizator;

    /**
     * Constructor Pasager
     * @param id id-ul persoanei din baza de date
     * @param n numele
     * @param p parola
     * @param b bilet
     * @param cnp cnp
     * @param t telefon
     * @param e email
     */
    public Pasager(String n,String p,int id,Bilet b,String cnp,int t,String e){
        super(n,p);
        this.id = id;
        this.cnp = cnp;
        this.telefon = t;
        this.email = e;
    }

    /**
     * Primeste id ul utilizatorului care s a logat si va crea persoana pasager luand datele din baza de date
     * @param id - id ul persoanei care s a logat
     * @return un pasager de tip Pasager
     */
   public static Pasager Utilizator(int id){

        Pasager pasager = null;
        try (Conn conn = new Conn()) {
            if (conn.MyConn() == 0) {
                throw new RuntimeException("Nu s-a putut conecta la baza de date.");
            }
            String interogare = "SELECT user_name,passworld,cnp,phone,email FROM user WHERE id_user=?";
            PreparedStatement stmt = conn.getDB().prepareStatement(interogare);
            stmt.setInt(1, id);
            ResultSet rez = stmt.executeQuery();

            if (rez.next()) {
                String n = rez.getString("user_name");
                String p = rez.getString("passworld");
                String cn = rez.getString("cnp");
                int tel = Integer.parseInt(rez.getString("phone"));
                String email = rez.getString("email");
                Bilet b = new Bilet();
                pasager = new Pasager(n, p, id, b, cn, tel, email);
            } else {
                throw new RuntimeException("Utilizatorul cu ID-ul " + id + " nu a fost găsit.");
            }
                rez.close();
                stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pasager;
  }

    /**
     * Getter pentru utilizator
     * @return un pasager
     */
    public Pasager getUtilizator(){
       if(utilizator == null){
           throw new RuntimeException("Utilizatorul nu a fost setat. Folosește metoda Utilizator(int id).");
       }
       return utilizator;
    }


    /**
     * Getter id pasager
     * @return id - int
     */
    public int getId() {
        return id;
    }

    /**
     * Seteer id pasager
     * @param id_nou - int
     */
    public void setId(int id_nou) {
        this.id = id_nou;
    }

    /**
     * Geeter bilet pasager
     * @return bilet - tip Bilet
     */
    public Bilet getBilet(){
        return bilet;
    }

    /**
     * Getter cnp pasager
     * @return cnp - string
     */
    public String getCnp(){
        return cnp;
    }

    /**
     * Getter telefon pasager
     * @return telefon - int
     */
    public int getTelefon(){
        return telefon;
    }

    /**
     * Getter email pasager
     * @return email - string
     */
    public String getEmail(){
        return email;
    }

}
