
public class Persoana {
    private int id_pers;
    private String nume;
    private  Bilet bilet;
    private String cnp;
    private int telefon;
    private String email;
    private int tip; //0-administrator ; 1-pasager

    public Persoana(int id,String n,Bilet b,String cnp,int t,String e,int tip){
        this.id_pers = id;
        this.nume = n;
        this.bilet=b;
        this.cnp=cnp;
        this.telefon=t;
        this.email=e;
        this.tip=tip;
    }

}
