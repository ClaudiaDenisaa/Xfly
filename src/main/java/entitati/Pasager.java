package entitati;
import database.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Pasager extends Persoana{
    private int id;
    private Bilet bilet;
    private String cnp;
    private int telefon;
    private String email;

    private static Pasager utilizator;

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
     * Setarea utilizatorului (folosim pentru persoana care s a logat)
     * @param p - tip Pasager
     */
  public static void setUtilizator(Pasager p){
       utilizator = p;
  }
  
    /**
     * Getter pentru utilizator (datele persoanei care  s a logat in aplicaie)
     * @return un pasager - tip Pasager
     */
    public static Pasager getUtilizator(){
       if(utilizator == null){
           throw new RuntimeException("Utilizatorul nu a fost setat. Folosește metoda setUtilizator(Pasager pasager).");
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
     * Setter cnp pasager
     * @param cnp - tip String
     */
    public void setCnp(String cnp){this.cnp = cnp;}

    /**
     * Getter telefon pasager
     * @return telefon - int
     */
    public int getTelefon(){
        return telefon;
    }

    /**
     * Setter telefon pasager
     * @param telefon - tip int
     */
    public void setTelefon(int telefon){this.telefon = telefon;}

    /**
     * Getter email pasager
     * @return email - string
     */
    public String getEmail(){
        return email;
    }

    /**
     * Setter email
     * @param email - tip String
     */
    public void setEmail(String email){this.email = email;}

}
