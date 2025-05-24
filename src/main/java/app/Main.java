package app;
import database.Conn;
import database.TabeleDB;
import entitati.*;
import gui.*;

import javax.swing.*;

public class Main {



    public static void main(String[] args) {

       try {
            Conn conexiune = new Conn();
            if (conexiune.MyConn() == 0) {
                System.out.println("Eroare la conectarea cu baza de date!");
                return;
            }

            TabeleDB t = new TabeleDB(conexiune.getDB());
            t.createTables();

            Admin admin = new Admin("admin","????????");

            SwingUtilities.invokeLater(() -> new LogIn());
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "A apărut o eroare: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }


    }
}
