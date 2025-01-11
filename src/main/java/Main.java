import javax.swing.*;
import java.sql.*;
import java.awt.Image;
import java.awt.Toolkit;

public class Main {



    public static void main(String[] args) {

        //new LogIn();
       //new Inregistrare();
      new VizualizareZborAdmin();
        //new Avion();
        //new AddZborAdmin();


       // Avion.takeAvionDB();
       // Zbor.addZborDinBDinListaZboruri();
       // System.out.println(Zbor.getListaZboruri());
       // System.out.println(Plane.getAvioane());

//ok//continua cu cnp si restul
//ok// conectare baza de date si verificare daca se potriveste numele si parola si daca da atunci se va merge in pagina //nu ai creat o inca
//ok//pentru admin creezi ceva special,daca acesta are numele admin si parola data de tine creezi de dinainte in aplicatie si setezi acestea
//ok//deci daca e admin intri in pagina VizualizareZborAdmin

     /*   try {
            Conn conexiune = new Conn();
            if (conexiune.MyConn() == 0) {
                System.out.println("Eroare la conectarea cu baza de date!");
                return;
            }

            TabeleDB t = new TabeleDB(conexiune.getDB());
            t.createTables();

            Admin admin - new Admin("admin","????????");

            SwingUtilities.invokeLater(() -> new LogIn());
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "A apărut o eroare: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
*/

    }
}
