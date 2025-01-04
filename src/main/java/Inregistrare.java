import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;


public class Inregistrare extends MyFrame{
    private JPanel panelContNou;
    private JTextField nume;
    private JPasswordField parola;
    private JTextField cnp;
    private JFormattedTextField telefon;
    private JTextField email;
    private JButton b_inregistrare;
    private JLabel textMesaj;

    /**
     * Constructor Inregistrare
     */
    public Inregistrare() {
        super();
        setTitle("Cont nou");
        setContentPane(panelContNou);
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b_inregistrare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPersoana();
            }
        });
    }


    /**
     * Adauga persoana in baza de date.
     * Face verificari pentru campurile din formular.
     */
    private void addPersoana() {
        String u_nume = nume.getText().trim();
        String u_parola = String.valueOf(parola.getPassword()).trim();
        String u_cnp = cnp.getText();
        String u_telefon = telefon.getText().trim();
        String u_email = email.getText().trim();
        boolean valid = true;

        if (u_nume.isEmpty()) {
            nume.setText("Scrie numele!");
            nume.setForeground(Color.GRAY);
            Border brNume = nume.getBorder();
            nume.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (nume.getText().equals("Scrie numele!")) {
                        nume.setText("");
                        nume.setForeground(Color.BLACK);
                        nume.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                    }
                    else {
                        nume.setBorder(brNume);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (nume.getText().trim().isEmpty() || nume.getText().equals("Scrie numele!")) {
                        nume.setText("Scrie numele!");
                        nume.setForeground(Color.GRAY);
                        nume.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                    }
                    nume.setBorder(brNume);

                }
            });
            valid = false;
        }


        if(!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",u_parola)) {
            parola.setEchoChar((char) 0);
            parola.setText("!! 1 literă mare, 1 literă mică, 1 cifră, 1 caracter special și 8 caractere!");
            parola.setForeground(Color.GRAY);
            Border brParola = parola.getBorder();
            parola.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String pass1 = String.valueOf(parola.getPassword());
                if(!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",pass1)) {
                    parola.setText("");
                    parola.setEchoChar('*');
                    parola.setForeground(Color.BLACK);
                    parola.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }else {
                    parola.setBorder(brParola);
                }
            }

            @Override
                public void focusLost(FocusEvent e) {
                String passs = String.valueOf(parola.getPassword());
                if(!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",passs))
                {
                    parola.setEchoChar((char) 0);
                    parola.setText("!! 1 literă mare, 1 literă mică, 1 cifră, 1 caracter special și 8 caractere!");
                    parola.setForeground(Color.GRAY);
                }
                    parola.setBorder(brParola);

              }
            });
            valid = false;
        }


        if(!u_cnp.matches("\\d{13}")){
            cnp.setText("CNP-ul trebuie sa fie formata din 13 cifre!");
            cnp.setForeground(Color.GRAY);
            Border brCnp = cnp.getBorder();
            cnp.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if(!cnp.getText().trim().matches("\\d{13}"))
                    {    cnp.setText("");
                        cnp.setForeground(Color.BLACK);
                        cnp.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    }
                    else {
                        cnp.setBorder(brCnp);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if(!cnp.getText().trim().matches("\\d{13}") || cnp.getText().trim().isEmpty()){
                        cnp.setText("CNP-ul trebuie sa fie formata din 13 cifre!");
                        cnp.setForeground(Color.GRAY);
                    }
                    cnp.setBorder(brCnp);
                }
            });
            valid = false;
        }


        if(!u_telefon.matches("\\d{10}")){
            telefon.setText("Numarul de telefon trebuie sa contina 10 cifre!");
            telefon.setForeground(Color.GRAY);
            Border brTelefon = telefon.getBorder();
            telefon.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if(!telefon.getText().matches("\\d{10}") || telefon.getText().isEmpty()){
                        telefon.setText("");
                        telefon.setForeground(Color.BLACK);
                        telefon.setBorder(BorderFactory.createLineBorder(Color.red, 2));
                }
                    else {
                        telefon.setBorder(brTelefon);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if(!telefon.getText().matches("\\d{10}") || telefon.getText().isEmpty()){
                        telefon.setText("Numarul de telefon trebuie sa contina 10 cifre!");
                        telefon.setForeground(Color.GRAY);
                    }

                        telefon.setBorder(brTelefon);

                }
            });
            valid = false;
        }


        if(!u_email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,4}$")) {
            email.setText("Adresa de email nu e valida!");
            email.setForeground(Color.GRAY);
            Border brEmail = email.getBorder();
            email.addFocusListener(new FocusListener() {
               @Override
               public void focusGained(FocusEvent e) {
                   if (!email.getText().matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,4}$")) {
                       email.setText("");
                       email.setBorder(BorderFactory.createLineBorder(Color.red, 2));
                   }
                   else {
                       email.setBorder(brEmail);
                   }
               }
               @Override
                public void focusLost(FocusEvent e) {
                   if(!email.getText().matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,4}$")){
                       email.setText("Adresa de email nu e valida!");
                       email.setForeground(Color.GRAY);

                   }
                       email.setBorder(brEmail);
               }
            });
            valid = false;
        }


        if (!valid) {
            textMesaj.setText("Toate câmpurile trebuie completate!");
        }
        else {
            try {
                Conn conexiune = new Conn();
                if (conexiune.MyConn() == 0) {
                    JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
                } else {
                    String sql = "INSERT INTO user (id_ticket,user_name,nr_phone,cnp,type,email,passworld) VALUES (?,?,?,?,?,?,?)";


                    PreparedStatement stmt = conexiune.getDB().prepareStatement(sql);
                    stmt.setObject(1, null);
                    stmt.setString(2, u_nume);
                    stmt.setInt(3, Integer.parseInt(u_telefon));
                    stmt.setLong(4, Long.parseLong(u_cnp));
                    stmt.setString(5, String.valueOf(1));
                    stmt.setString(6, u_email);
                    stmt.setString(7, u_parola);
                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Utilizator înregistrat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);

                    stmt.close();
                    conexiune.CloseMyConn();
                    SwingUtilities.invokeLater(() -> new LogIn());
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}



