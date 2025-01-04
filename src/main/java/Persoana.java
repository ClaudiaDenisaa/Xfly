
public class Persoana {

    private String nume;
    private String parola;

    //0-administrator ; 1-pasager


    /**
     * Constructor Persoana
     * @param n - numele
     * @param p - parola
     */
    public Persoana(String n, String p) {
        this.nume = n;
        this.parola = p;
    }


    /**
     * Getter nume persoana
     * @return nume - string
     */
    public String getNume(){
        return nume;
    }

    /**
     * Getter parola persoana
     * @return parola - string
     */
    public String getParola(){
        return parola;
    }

}
