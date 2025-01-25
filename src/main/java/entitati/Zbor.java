package entitati;
import database.*;
import gui.*;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class Zbor {
    private int id_zbor;
    private Plane avion;
    private double pret;
    private String destinatiePlecare;
    private String destinatieSosire;
    private LocalTime oraPlecare;
    private LocalTime oraSosire;
    private LocalDate data;
    private LocalTime durata;//localtime in db

    public static ArrayList<Zbor> listaZboruri = new ArrayList<>();




    /**
     * Constructor Zbor
     * @param avion avion
     * @param pret pret
     * @param dp data plecare
     * @param ds data sosire
     * @param op ora plecare
     * @param os ora sosire
     * @param data data
     * @param durata durata
     */
    public Zbor(Plane avion,double pret,String dp,String ds,LocalTime op,LocalTime os,LocalDate data,LocalTime durata) {
        this.avion = avion;
        this.pret = pret;
        this.destinatiePlecare = dp;
        this.destinatieSosire = ds;
        this.oraPlecare = op;
        this.oraSosire = os;
        this.data = data;
        this.durata = durata;
    }

    /**
     * Constructor gol
     */
    public Zbor(){};


    /**
     * Adaugare de zboruri in lista
     * @param zbor - tip Zbor
     */
    public static void addInListaZboruri(Zbor zbor) {
        listaZboruri.add(zbor);
    }

    /**
     * Returnarea listei de zboruri
     * @return lista de zboruri - tip Zbor
     */
    public static ArrayList<Zbor> getListaZboruri() {
        return listaZboruri;
    }

    /**
     * Sterge toata lista de zboruri. Va fi goala.
     */
    public static void stergeListaZboruri(){
        listaZboruri.clear();
    }


    /**
     * Getter id zbor
     * @return id ul zborului - tip int
     */
    public int getId_zbor() {
        return id_zbor;
    }

    /**
     * Setter id zbor
     * @param id_zbor - tip int
     */
    public void setId_zbor(int id_zbor) {
        this.id_zbor = id_zbor;
    }

    /**
     * Getter avion
     * @return avion - tip Avion
     */
    public Plane getAvion() {
        return avion;
    }

    /**
     *Setter avion
     * @param avion - tip Avion
     */
    public void setAvion(Plane avion) {
        this.avion = avion;
    }

    /**
     * Getter pret
     * @return pret - tip double
     */
    public double getPret() {
        return pret;
    }

    /**
     * Setter pret
     * @param pret - tip double
     */
    public void setPret(double pret) {
        this.pret = pret;
    }

    /**
     * Getter destinatie plecare
     * @return destinatie plecare - tip String
     */
    public String getDestinatiePlecare() {
        return destinatiePlecare;
    }

    /**
     * Setter destinatie plecare
     * @param destinatiePlecare - tip String
     */
    public void setDestinatiePlecare(String destinatiePlecare) {
        this.destinatiePlecare = destinatiePlecare;
    }

    /**
     * Getter destinatie sosire
     * @return sosire - tip String
     */
    public String getDestinatieSosire() {
        return destinatieSosire;
    }

    /**
     * Setter destinatie sosire
     * @param destinatieSosire - tip String
     */
    public void setDestinatieSosire(String destinatieSosire) {
        this.destinatieSosire = destinatieSosire;
    }

    /**
     * Getter ora plecare
     * @return ora plecare - tip LocalTime
     */
    public LocalTime getOraPlecare() {
        return oraPlecare;
    }

    /**
     * Setter ora plecare
     * @param oraPlecare - tip LocalTime
     */
    public void setOraPlecare(LocalTime oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    /**
     * Getter ora sosire
     * @return ora sosire - tip LocalTime
     */
    public LocalTime getOraSosire() {
        return oraSosire;
    }

    /**
     * Setter ora sosire
     * @param oraSosire - tip. LocalTime
     */
    public void setOraSosire(LocalTime oraSosire) {
        this.oraSosire = oraSosire;
    }

    /**
     * Getter data
     * @return data - tip LocalDate
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Setter data
     * @param data - tip LocalDate
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Getter durata
     * @return durata - tip. LocalTime
     */
    public LocalTime getDurata() {
        return durata;
    }

    /**
     * Setter durata
     * @param durata - tip LocalTime
     */
    public void setDurata(LocalTime durata) {
        this.durata = durata;
    }

    /**
     * Detalile unui zbor
     * @return info zbor
     */
   @Override
    public String toString() {
        return avion +" " +pret + " "+destinatiePlecare+" "+destinatieSosire+" "+oraPlecare+" "+oraSosire+" "+data;
   }

}
