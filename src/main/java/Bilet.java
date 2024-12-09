import java.time.LocalDate;

public class Bilet {
    private int id_bilet; //id_rezervation
    private String plecare;
    private LocalDate ora_plecare;
    private String sosire;
    private LocalDate ora_sosire;
    private String durata_calatorie;

    public Bilet(int id,String p,LocalDate hp,String s,LocalDate hs,String d){
        this.id_bilet=id;
        this.plecare=p;
        this.ora_plecare=hp;
        this.sosire=s;
        this.ora_sosire=hs;
        this.durata_calatorie=d;
    }

}
