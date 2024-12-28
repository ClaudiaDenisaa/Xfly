import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.*;

/**
 * Clasa Conn pentru conectarea cu baza de date db_xfly
 */
public class Conn extends Component {
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String db = "jdbc:mysql://localhost:3306/db_xfly";
    final String db_user = "root";
    final String db_password = "root1234";
    private Connection connect = null;

    /**
     * Conectare baze de date
     * @return conexiunea la baza de date
     */
    public Connection getDB() {
        try {
            if (connect == null || connect.isClosed()) {
                connect = DriverManager.getConnection(db, db_user, db_password);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "!!Eroare la conectarea cu baza de date: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            return null;
        }
            return connect;
    }

    /**
     * Verifica conexiunea la baza de date
     * @return 1 daca exista conexiune,0 daca nu
     */
        public int MyConn () {
            try {
                Connection conn = getDB();
                if (conn != null) {
                    return 1;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Eroare la conexiunea cu baza de date: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            }
            return 0;
        }

    /**
     * Inchide conexiunea la baza de date daca exista
     */
    public void CloseMyConn () {
            try {
                if (connect != null && !connect.isClosed()) {
                    connect.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Eroare la închiderea conexiunii: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        }
}





