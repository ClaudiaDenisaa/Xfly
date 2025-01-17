package database;
import entitati.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PasagerDB {

    /**
     * Primeste id ul utilizatorului care s a logat si va crea persoana pasager luand datele din baza de date
     * @param id - id ul persoanei care s a logat
     * @return un pasager de tip Pasager
     */
    public static void Utilizator(int id){

        Pasager pasager = null;
        try (Conn conn = new Conn()) {
            if (conn.MyConn() == 0) {
                throw new RuntimeException("Nu s-a putut conecta la baza de date.");
            }
            String interogare = "SELECT username,password,cnp,phone,email FROM user WHERE id_user=?";
            PreparedStatement stmt = conn.getDB().prepareStatement(interogare);
            stmt.setInt(1, id);
            ResultSet rez = stmt.executeQuery();

            if (rez.next()) {
                String n = rez.getString("username");
                String p = rez.getString("password");
                String cn = rez.getString("cnp");
                int tel = Integer.parseInt(rez.getString("phone"));
                String email = rez.getString("email");
                Bilet b = new Bilet();
                pasager = new Pasager(n, p, id, b, cn, tel, email);
                Pasager.setUtilizator(pasager);
            } else {
                throw new RuntimeException("Utilizatorul cu ID-ul " + id + " nu a fost găsit.");
            }
            rez.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
