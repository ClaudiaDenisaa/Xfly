/**
 * Clasa pentru persoana
 */
public class Persoana {
    private int id_pers;
    private String nume;
    private  Bilet bilet;
    private String cnp;
    private int telefon;
    private String email;
    private int tip; //0-administrator ; 1-pasager

    /**
     * Constructor Persoana
     * @param id id-ul persoanei
     * @param n numele
     * @param b bilet
     * @param cnp cnp
     * @param t telefon
     * @param e email
     * @param tip 0-administrator ; 1-pasager
     */
    public Persoana(int id,String n,Bilet b,String cnp,int t,String e,int tip){
        this.id_pers = id;
        this.nume = n;
        this.bilet=b;
        this.cnp=cnp;
        this.telefon=t;
        this.email=e;
        this.tip=tip;
    }

    public int getId_pers() {
        return id_pers;
    }

    public void setId_pers(int id_nou) {
        this.id_pers = id_nou;
    }

    public String getNume(){
        return nume;
    }

    public Bilet getBilet(){
        return bilet;
    }

    public String getCnp(){
        return cnp;
    }

    public int getTelefon(){
        return telefon;
    }

    public String getEmail(){
        return email;
    }

    public int getTip(){
        return tip;
    }
}
