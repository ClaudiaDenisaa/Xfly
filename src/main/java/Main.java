import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.Image;
import java.awt.Toolkit;

public class Main {
    final String db = "jdbc:mysql://localhost:330";
    final String db_user = "root";
    final String db_password = "root1234";

    public void MyConn(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(db,db_user,db_password);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {


        //new LogIn();
      //  new Inregistrare();

    }
}
