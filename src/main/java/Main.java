import javax.swing.*;
import java.sql.*;
import java.awt.Image;
import java.awt.Toolkit;

public class Main {



    public static void main(String[] args) {

        new LogIn();
       //new Inregistrare();
      //new VizualizareZborAdmin();
        //new Avion();
        //new AddZborAdmin();
        //new VizualizareZborPasager();
        //new AddFood();
        //new AddDrink();
        //new IstoricRezervari();


       // Avion.takeAvionDB();
       // Zbor.addZborDinBDinListaZboruri();
       // System.out.println(Zbor.getListaZboruri());
       // System.out.println(Plane.getAvioane());


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
