import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classa pentru avion
 */
public class Plane {
    private int id;
    private String model;
    private int locuriA;
    private int locuriB;

    /**
     * Constructor avion
     * @param id id ul avionului
     * @param model nume model avion
     * @param locuriA locuri pentru clasa A
     * @param locuriB locuri pentru clasa B
     */
    public Plane(int id,String model, int locuriA, int locuriB) {
        this.id = id;
        this.model = model;
        this.locuriA = locuriA;
        this.locuriB = locuriB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getLocuri() {
        return locuriA;
    }

    public void setLocuri(int locuri) {
        this.locuriA = locuri;
    }

    public int getLocuriB() {return locuriB;}

    public void setLocuriB(int locuriB) {this.locuriB = locuriB;}


}
