package entitati;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Mancare {
    private String numeMancare;
    private double pretMancare;

    public static ArrayList<Mancare> listaMancare = new ArrayList<>();

    /**
     * Constructor fara parametri
     */
    public Mancare() {};

    /**
     * Constructor Mancare
     * @param numeMancare - tip String
     * @param pretMancare - tip double
     */
    public Mancare(String numeMancare, double pretMancare) {
        this.numeMancare = numeMancare;
        this.pretMancare = pretMancare;
    }


    /**
     * Seteaza lista globala de mancare
     * @param listaMancare - tip ArrayList<Mancare>
     */
    public static void setListaMancare(ArrayList<Mancare> listaMancare) {
        Mancare.listaMancare = listaMancare;
    }

    /**
     * Returneaza lista de mancare globala
     * @return lista de mancare - tip ArrayList<Mancare>
     */
    public static ArrayList<Mancare> getListaMancare() {
        return listaMancare;
    }





    /**
     * Getter mancare
     * @return nume mancare - tip  String
     */
    public String getNumeMancare() {
        return numeMancare;
    }

    /**
     * Setter mancare
     * @param numeMancare - tip String
     */
    public void setNumeMancare(String numeMancare) {
        this.numeMancare = numeMancare;
    }

    /**
     * Getter pret mancare
     * @return pret mancare - tip double
     */
    public double getPretMancare() {
        return pretMancare;
    }

    /**
     * Setter pret mancare
     * @param pretMancare - tip double
     */
    public void setPretMancare(double pretMancare) {
        this.pretMancare = pretMancare;
    }
}
