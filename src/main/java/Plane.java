import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Plane {
    private int id;
    private String model;
    private int locuriA;
    private int locuriB;

    /**
     * Constructor avion
     * @param id - id ul avionului
     * @param model - nume model avion
     * @param locuriA - locuri pentru clasa A
     * @param locuriB - locuri pentru clasa B
     */
    public Plane(int id,String model, int locuriA, int locuriB) {
        this.id = id;
        this.model = model;
        this.locuriA = locuriA;
        this.locuriB = locuriB;
    }

    /**
     * Getter id avion
     * @return id - int
     */
    public int getId() {
        return id;
    }

    /**
     * Setter id avion
     * @param id - int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter model avion
     * @return model - string
     */
    public String getModel() {
        return model;
    }

    /**
     * Setter model avion
     * @param model - string
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Getter loc clasa A avion
     * @return locuriA - nr locuri
     */
    public int getLocuri() {
        return locuriA;
    }

    /**
     * Setter locuri clasa A avion
     * @param locuri - nr locuri (int)
     */
    public void setLocuri(int locuri) {
        this.locuriA = locuri;
    }

    /**
     * Getter loc clasa B avion
     * @return locuriB - nr locuri
     */
    public int getLocuriB() {return locuriB;}

    /**
     * Setter locuri clasa B avion
     * @param locuriB - nr locuri (int)
     */
    public void setLocuriB(int locuriB) {this.locuriB = locuriB;}


}
