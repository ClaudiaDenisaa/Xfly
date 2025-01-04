import java.time.LocalDate;
import java.time.LocalTime;


public class Zbor {
    private int id_zbor;
    private Avion avion;
    private String clasa;
    private double pret;
    private String destinatiePlecare;
    private String destinatieSosire;
    private LocalTime oraPlecare;
    private LocalTime oraSosire;
    private LocalDate data;
    private String durata;//localtime in db

    /**
     * Constructor Zbor
     * @param avion avion
     * @param clasa clasa
     * @param pret pret
     * @param dp data plecare
     * @param ds data sosire
     * @param op ora plecare
     * @param os ora sosire
     * @param data data
     * @param durata durata
     */
    public Zbor(Avion avion,String clasa,double pret,String dp,String ds,LocalTime op,LocalTime os,LocalDate data,String durata) {
        this.avion = avion;
        this.clasa = clasa;
        this.pret = pret;
        this.destinatiePlecare = ds;
        this.destinatieSosire = ds;
        this.oraPlecare = op;
        this.oraSosire = os;
        this.data = data;
        this.durata = durata;
    }

    public int getId_zbor() {
        return id_zbor;
    }

    public void setId_zbor(int id_zbor) {
        this.id_zbor = id_zbor;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public String getClasa() {
        return clasa;
    }

    public void setClasa(String clasa) {
        this.clasa = clasa;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public String getDestinatiePlecare() {
        return destinatiePlecare;
    }

    public void setDestinatiePlecare(String destinatiePlecare) {
        this.destinatiePlecare = destinatiePlecare;
    }

    public String getDestinatieSosire() {
        return destinatieSosire;
    }

    public void setDestinatieSosire(String destinatieSosire) {
        this.destinatieSosire = destinatieSosire;
    }

    public LocalTime getOraPlecare() {
        return oraPlecare;
    }

    public void setOraPlecare(LocalTime oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    public LocalTime getOraSosire() {
        return oraSosire;
    }

    public void setOraSosire(LocalTime oraSosire) {
        this.oraSosire = oraSosire;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }


}
