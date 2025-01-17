package entitati;


import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Bautura {
    private String numeBautura;
    private double pretBautura;

    public static ArrayList<Bautura> listaBautura = new ArrayList<>();

    /**
     * Constructor fara parametrii
     */
    public Bautura(){};

    /**
     * Constructor Bautura
     * @param numeBautura - tip String
     * @param pretBautura - tip double
     */
    public Bautura(String numeBautura, double pretBautura) {
        this.numeBautura = numeBautura;
        this.pretBautura = pretBautura;
    }



    /**
     * Primeste o lista de bauturi si este adaugata in lista de bauturi globala
     * @param listaBautura - tip ArrayList<Bautura>
     */
    public static void setListaBautura(ArrayList<Bautura> listaBautura) {
        Bautura.listaBautura = listaBautura;
    }

    /**
     * Returneaza lista de bauturi
     * @return lista de bauturi - tip ArrayList<Bautura>
     */
    public static ArrayList<Bautura> getListaBautura() {
        return listaBautura;
    }


    /**
     * Getter Bautura
     * @return nume bautura - tip  String
     */
    public String getNumeBautura() {
        return numeBautura;
    }

    /**
     * Setter Bautura
     * @param numeBautura - tip String
     */
    public void setNumeBautura(String numeBautura) {
        this.numeBautura = numeBautura;
    }

    /**
     * Getter pret mancare
     * @return pret mancare - tip double
     */
    public double getPretBautura() {
        return pretBautura;
    }

    /**
     * Setter pret Bautura
     * @param pretBautura - tip double
     */
    public void setPretBautura(double pretBautura) {
        this.pretBautura = pretBautura;
    }
}
