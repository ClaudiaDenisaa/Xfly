import java.time.LocalDate;


public class Bilet {
    private int id_bilet; //id_rezervation
    private String plecare;
    private LocalDate ora_plecare;
    private String sosire;
    private LocalDate ora_sosire;
    private String durata_calatorie;

    /**
     * Constructor fara parametrii
     */
    public Bilet(){};
    /**
     * Constructor bilet
     * @param id id-ul biletului
     * @param p destinatia
     * @param hp ora la care ajunge la destinatie
     * @param s locul de unde pleaca
     * @param hs ora la care pleaca
     * @param d durata calatoriei
     */
    public Bilet(int id,String p,LocalDate hp,String s,LocalDate hs,String d){
        this.id_bilet=id;
        this.plecare=p;
        this.ora_plecare=hp;
        this.sosire=s;
        this.ora_sosire=hs;
        this.durata_calatorie=d;
    }

    /**
     * Getter bilet
     * @return id bilet
     */
    public int getId_bilet() {
        return id_bilet;
    }

    /**
     * Getter plecare
     * @return de unde pleaca
     */
    public String getPlecare() {
        return plecare;
    }

    /**
     * Getter ora la care pleaca
     * @return ora de plecare
     */
    public LocalDate getOra_plecare() {
        return ora_plecare;
    }

    /**
     *Getter sosire destinatie
     * @return destinatia la care soseste
     */
    public String getSosire() {
        return sosire;
    }

    /**
     * Getter ora de sosire
     * @return ora de sosire
     */
    public LocalDate getOra_sosire() {
        return ora_sosire;
    }

    /**
     * Getter durata
     * @return durata calatoriei
     */
    public String getDurata_calatorie() {
        return durata_calatorie;
    }


}
