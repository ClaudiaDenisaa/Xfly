package gui;

import entitati.*;
import database.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.time.format.DateTimeFormatter;

public class AddZborAdmin extends MyFrame{
    private JPanel panel1;
    private JTextField pretulZborului;
    private JTextField sosireAleasa;
    private JComboBox<String> avionulSelectat;
    private JComboBox<String> clasaSelectata;
    private JTextField dataZborului;
    private JButton adaugaButton;
    private JButton btnVizualizare;
    private JTextField plecareAleasa;
    private JComboBox<Integer> minPlecare;
    private JComboBox<Integer> minSosire;
    private JComboBox<Integer> oraPlecare;
    private JComboBox<Integer> oraSosire;



    public AddZborAdmin() {
        super();
        setTitle("Adaugare de zboruri");
        setContentPane(panel1);
        setLocationRelativeTo(null);
        setVisible(true);

        extragereAvioaneBD();

        adaugaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    addZbor();
            }
        });

        btnVizualizare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(VizualizareZborAdmin::new);
            }
        });
//validare:
        //avioanele cand merge pe pagina sau cand apasa butonul de selectare sa se incarce lista direct cu ce model este in baza de date-lista de modele
        //pret sa fie decimal xx.xx
        //plecare si sosire sa fie string

        //cand se apasa butonul de adaugare in Vizualizare zboruri sa apara zborul si datele despre el,
        //va trebui sa poti modifica un zbor ca admin si sa l stergi ... gandestete la logica pentru astea
        //sa schimbi layoutul si sa faci separat componenta(chenarul cu informatii de zbor) apoi sa o adaugi
    }

    public void extragereAvioaneBD(){
        try(Conn conexiune = new Conn()){
            if (conexiune.MyConn() == 0) {
                JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            } else {
                String query = "SELECT model FROM plane";
                PreparedStatement stmt = conexiune.getDB().prepareStatement(query);
                ResultSet result = stmt.executeQuery();
                while (result.next()) {
                    avionulSelectat.addItem(result.getString("model"));
                }
                result.close();
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addZbor() {
        int valid = 1;
        String plecare = plecareAleasa.getText();
        String sosire = sosireAleasa.getText();
        String pret = pretulZborului.getText();
        String data = dataZborului.getText();

        if(!(Pattern.matches("^[a-zA-Z\\s\"'-]+$", plecare))){
            plecareAleasa.setText("Campul format din litere!");
            plecareAleasa.setForeground(Color.GRAY);
            Border borderPlecare = plecareAleasa.getBorder();
            plecareAleasa.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if(!(Pattern.matches("^[a-zA-Z\\s\"'-]+$", plecareAleasa.getText()))){
                        plecareAleasa.setText("Campul format din litere!");
                        plecareAleasa.setText("");
                        plecareAleasa.setForeground(Color.BLACK);
                        plecareAleasa.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                    }
                    else{
                        plecareAleasa.setBorder(borderPlecare);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if(!(Pattern.matches("^[a-zA-Z\\s\"'-]+$", plecareAleasa.getText()))){
                        plecareAleasa.setText("Campul format din litere!");
                        plecareAleasa.setForeground(Color.GRAY);
                        plecareAleasa.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                    }

                        plecareAleasa.setBorder(borderPlecare);

                }

            });
            valid = 0;
        }
        if(!(Pattern.matches("^[a-zA-Z\\s\"'-]+$", sosire))){
            sosireAleasa.setText("Campul format din litere!");
            sosireAleasa.setForeground(Color.GRAY);
            Border borderSosire = sosireAleasa.getBorder();
            sosireAleasa.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if(!(Pattern.matches("^[a-zA-Z\\s\"'-]+$", sosireAleasa.getText()))){
                        sosireAleasa.setText("Campul format din litere!");
                        sosireAleasa.setText("");
                        sosireAleasa.setForeground(Color.BLACK);
                        sosireAleasa.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                    }
                    else{
                       sosireAleasa.setBorder(borderSosire);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if(!(Pattern.matches("^[a-zA-Z\\s\"'-]+$", sosireAleasa.getText()))){
                        sosireAleasa.setText("Campul format din litere!");
                        sosireAleasa.setForeground(Color.GRAY);
                        sosireAleasa.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                    }
                        sosireAleasa.setBorder(borderSosire);
                }
            });
            valid = 0;
        }

        if(!(Pattern.matches("^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.(\\d{4})$",data))){
            dataZborului.setText("Formatul datei: DD.MM.YYYY!");
            dataZborului.setForeground(Color.GRAY);
            Border borderData = dataZborului.getBorder();
            dataZborului.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if(!(Pattern.matches("^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.(\\d{4})$",dataZborului.getText()))){
                       dataZborului.setText("");
                       dataZborului.setForeground(Color.BLACK);
                       dataZborului.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                    }else{
                        dataZborului.setBorder(borderData);
                    }
                }

                @Override
                public void focusLost(FocusEvent e){
                    if(!(Pattern.matches("^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.(\\d{4})$",dataZborului.getText()))){
                        dataZborului.setText("Formatul datei: DD-MM-YYYY!");
                        dataZborului.setForeground(Color.GRAY);
                        dataZborului.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                    }

                        dataZborului.setBorder(borderData);
                }
            });
            valid = 0;
        }

        if(!Pattern.matches("^[0-9]{2,4}\\.[0-9]{2}",pret)){
            pretulZborului.setText("Pretul de forma x.00");
            pretulZborului.setForeground(Color.GRAY);
            Border borderPret = pretulZborului.getBorder();
            pretulZborului.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if(!Pattern.matches("^[0-9]{2,4}\\.[0-9]{2}",pretulZborului.getText())){
                        pretulZborului.setText("");
                        pretulZborului.setForeground(Color.BLACK);
                        pretulZborului.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                    }else{
                        pretulZborului.setBorder(borderPret);
                    }
                }

                @Override
                public void focusLost(FocusEvent e){
                    if(!Pattern.matches("^[0-9]{2,4}\\.[0-9]{2}",pretulZborului.getText())){
                        pretulZborului.setText("Pretul de forma x.00");
                        pretulZborului.setForeground(Color.GRAY);
                        pretulZborului.setBorder(BorderFactory.createLineBorder(Color.RED,2));
                    }

                        pretulZborului.setBorder(borderPret);

                }
            });
            valid = 0;
        }
        if(valid == 1){
            //se creaza un zbor cu datele preluate si se pune in listaZboruri
            String numeAvion = (String) avionulSelectat.getSelectedItem();
            double pretul = Double.parseDouble(pretulZborului.getText());
            String plecarea =plecareAleasa.getText().trim();
            String sosirea = sosireAleasa.getText().trim();
            DateTimeFormatter formatata = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate d = LocalDate.parse(dataZborului.getText(), formatata);

            Object oraPlecarii = oraPlecare.getSelectedItem();
            String oraPlecariiString = (String) oraPlecarii;
            int oPlecarii = Integer.parseInt(oraPlecariiString);
            Object minPlecarii = minPlecare.getSelectedItem();
            String minPlecariiString = (String) minPlecarii;
            int mPlecarii = Integer.parseInt(minPlecariiString);
            LocalTime oraDePlecare = LocalTime.of(oPlecarii, mPlecarii);

            Object oraSosirii = oraSosire.getSelectedItem();
            String oraSosiriiString = (String) oraSosirii;
            int oSosirii = Integer.parseInt(oraSosiriiString);
            Object minSosirii = minSosire.getSelectedItem();
            String minSosiriiString = (String) minSosirii;
            int mSosirii = Integer.parseInt(minSosiriiString);
            LocalTime oraDeSosire = LocalTime.of(oSosirii, mSosirii);

            int durataMinute = oraDeSosire.toSecondOfDay() / 60 - oraDePlecare.toSecondOfDay() / 60;
            if(durataMinute < 0) {
                durataMinute += 24 * 60;
            }
            LocalTime durata = LocalTime.of(durataMinute / 60,durataMinute % 60);

            Plane.stergereListaAvioane();
            Avion.takeAvionDB();
            ArrayList<Plane> listaAvioane = Plane.getListaAvioane();
            for(Plane a : listaAvioane){
                if(a.getModel().equals(numeAvion)){
                    Zbor zborul = new Zbor(a,pretul,plecarea,sosirea,oraDePlecare,oraDeSosire,d,durata);
                    ZborDB.addZborDB(zborul);
                }
            }

            //SE VA CREA ZVORUL CARE APARE LA VIZUALIZARE ZBOR ADMIN CAT SI LA CLIENT

            plecareAleasa.setText("");
            sosireAleasa.setText("");
            pretulZborului.setText("");
            dataZborului.setText("");
            oraPlecare.setSelectedIndex(0);
            minPlecare.setSelectedIndex(0);
            oraSosire.setSelectedIndex(0);
            minSosire.setSelectedIndex(0);
            avionulSelectat.setSelectedIndex(0);
        }
    }
}
