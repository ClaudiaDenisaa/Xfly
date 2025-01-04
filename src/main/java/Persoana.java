/**
 * Clasa pentru persoana
 */
public class Persoana {

    private String nume;
    private String parola;

    //0-administrator ; 1-pasager


    public Persoana(String n, String p) {
        this.nume = n;
        this.parola = p;
    }


    public String getNume(){
        return nume;
    }

    public String getParola(){
        return parola;
    }

}
