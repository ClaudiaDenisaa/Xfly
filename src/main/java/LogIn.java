import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;


public class LogIn extends MyFrame{
    private JPanel panel1;
    private JTextField text_username;
    private JPasswordField text_password;
    private JButton b_log;
    private JLabel log_name;
    private JLabel log_password;
    private JButton b_cont;

    private boolean validAdmin;

    Admin admin = new Admin("admin","????????");

    /**
     * Constructor logare
     */
    public LogIn() {
        super();
        setTitle("Log In");
        setContentPane(panel1);
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b_cont.addActionListener(e -> new Inregistrare());

        b_log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logare();
            }
        });

    }

    /**
     * Validare campuri si functia de logare pentru butonul de logare
     */
    public void logare(){
        boolean valid = true;
        AtomicBoolean validAdmin = new AtomicBoolean(false);
        String username = text_username.getText();
        String password = String.valueOf(text_password.getPassword());

        if (username.isEmpty()) {
            text_username.setText("Scrie numele!");
            text_username.setForeground(Color.GRAY);
            Border brNume = text_username.getBorder();
            text_username.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (text_username.getText().equals("Scrie numele!")) {
                        text_username.setText("");
                        text_username.setForeground(Color.BLACK);
                        text_username.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                    }else {
                        text_username.setBorder(brNume);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (text_username.getText().trim().isEmpty() || text_username.getText().equals("Scrie numele!")) {
                        text_username.setText("Scrie numele!");
                        text_username.setForeground(Color.GRAY);
                        text_username.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                    }
                    text_username.setBorder(brNume);

                }
            });
            valid = false;
        }


        if(Pattern.matches(("^\\?{8}$"),password) && text_username.getText().equals("admin")) {
            validAdmin.set(true);
        } else{
            if (!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", password)) {
                text_password.setEchoChar((char) 0);
                text_password.setText("!! 1 literă mare, 1 literă mică, 1 cifră, 1 caracter special și 8 caractere!");
                text_password.setForeground(Color.GRAY);
                Border brParola = text_password.getBorder();

                text_password.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        String pass1 = String.valueOf(text_password.getPassword());
                        if (Pattern.matches(("^\\?{8}$"), pass1) && text_username.getText().equals("admin")) {
                            validAdmin.set(true);
                        } else if (!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", pass1)) {
                            text_password.setText("");
                            text_password.setEchoChar('*');
                            text_password.setForeground(Color.BLACK);
                            text_password.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                        } else {
                            text_password.setBorder(brParola);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        String passs = String.valueOf(text_password.getPassword());
                        if (Pattern.matches(("^\\?{8}$"), passs) && text_username.getText().equals("admin")) {
                            validAdmin.set(true);
                        } else if (!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",passs) ){
                            text_password.setEchoChar((char) 0);
                            text_password.setText("!! 1 literă mare, 1 literă mică, 1 cifră, 1 caracter special și 8 caractere!");
                            text_password.setForeground(Color.GRAY);
                        }
                            text_password.setBorder(brParola);
                        

                    }
                });
                valid = false;
            }
        }


        if(validAdmin.get() == true){
            SwingUtilities.invokeLater(() -> new VizualizareZborAdmin());
        }else if(valid == true) {
            try(Conn conn = new Conn()){
                if(conn.MyConn() == 0){
                    JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
                }else{
                String numeAp = text_username.getText();
                String parolaAp = String.valueOf(text_password.getPassword());

                String s1 = "SELECT id_user,user_name,passworld FROM user WHERE user_name = ? and passworld = ?";
                PreparedStatement stmt = conn.getDB().prepareStatement(s1);
                stmt.setString(1,numeAp);
                stmt.setString(2,parolaAp);

                ResultSet rs = stmt.executeQuery();
                boolean found = false;

                        while (rs.next()) {
                                int idUser = rs.getInt("id_user");
                                found = true;
                                Pasager.Utilizator(idUser);
                                SwingUtilities.invokeLater(() -> new VizualizareZborPasager(idUser));
                        }

                        if (!found) {
                            JOptionPane.showMessageDialog(this, "Utilizator sau parolă nu au fost gasite in baza de date.", "Eroare", JOptionPane.ERROR_MESSAGE);
                            SwingUtilities.invokeLater(Inregistrare::new);
                        }

                        rs.close();
                        stmt.close();
                        conn.close();
                    }
                }catch (Exception e) {
                JOptionPane.showMessageDialog(this,"Eroare la conectarea cu baza de date1: " + e.getMessage(),"EROARE",JOptionPane.ERROR_MESSAGE);
            }

        }


    }




}
