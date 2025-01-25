package gui;

import entitati.*;
import database.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VizualizareZborPasager extends MyFrame {

    private JPanel panelPrincipal;
    private JScrollPane scrollPanel;
    private JPanel panelComponente;
    private JButton btnIesire;
    //private JTextField baraCautare;
    //private JButton button1;
    private JButton btnIstoricRezervari;

    /**
     * Constructor VizualizareZborPasager
     */
    public VizualizareZborPasager() {
        super();
        setTitle("Vizualizarea zborurilor");
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        setVisible(true);
        panelComponente.setLayout(new BoxLayout(panelComponente,BoxLayout.Y_AXIS));

        Avion.takeAvionDB();
        ZborDB.addZborDinBDinListaZboruri();
        componenteInformatiiZbor();

        btnIstoricRezervari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(IstoricRezervari::new);
            }
        });

        btnIesire.addActionListener(e -> System.exit(0));

    }

    /**
     * Se adauga toate zborurile pentru a fi vizualizate si pentru a rezerva utilizatorul ce zbor doreste
     */
    public void componenteInformatiiZbor() {
        panelComponente.removeAll();
        panelComponente.revalidate();
        panelComponente.repaint();

        ArrayList<Zbor> listaZboruri = Zbor.getListaZboruri();
        if (listaZboruri == null || listaZboruri.isEmpty()) {
            panelComponente.add(new JLabel("Nu există zboruri disponibile."));
            return;
        }

        for(Zbor z : listaZboruri) {
            JPanel zborPanel = new JPanel();
            zborPanel.setLayout(new GridLayout(8, 2, 5, 5));
            zborPanel.setBackground(new Color(0xBEC7FF));
            zborPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));


            zborPanel.add(new JLabel("Plecare: " + z.getDestinatiePlecare()));
            zborPanel.add(new JLabel("Sosire: " + z.getDestinatieSosire()));

            zborPanel.add(new JLabel("Ora plecare: " + z.getOraPlecare()));
            zborPanel.add(new JLabel("Ora sosire: " + z.getOraSosire()));

            zborPanel.add(new JLabel("Model avion: " + z.getAvion().getModel()));
            zborPanel.add(new JLabel());

            zborPanel.add(new JLabel("Clasa A: " + z.getAvion().getLocuri() + " locuri"));
            zborPanel.add(new JLabel());

            zborPanel.add(new JLabel("Clasa B: " + z.getAvion().getLocuriB() + " locuri"));
            zborPanel.add(new JLabel());

            zborPanel.add(new JLabel("Data: " + z.getData()));
            zborPanel.add(new JLabel());

            zborPanel.add(new JLabel("Durata: " + z.getDurata() + " ore"));
            JLabel pretLabel = new JLabel("Preț: " + z.getPret() + " euro");
            pretLabel.setFont(new Font(pretLabel.getFont().getName(), Font.BOLD, pretLabel.getFont().getSize()));
            zborPanel.add(pretLabel);

            zborPanel.add(new JLabel());
            JButton btnRezervare = new JButton("Rezervare");
            zborPanel.add(btnRezervare);

            int idUtilizator = Pasager.getUtilizator().getId();
            int idZbor = z.getId_zbor();
            btnRezervare.addActionListener(e -> formularRezervare(idZbor,idUtilizator));

            panelComponente.add(zborPanel);
        }
    }



    /**
     * O pagina formular in care utilizatorul selecteaza ce preferinte are si se adauga in baza de date o "rezervare"
     * @param idZbor - id ul zborului selectat de utilizator
     * @param idUser - id ul utilizatorului acre a selectat zborul
     */
    public void formularRezervare(int idZbor,int idUser){
        JFrame thisFrame = this;
        JDialog dialog = new JDialog(thisFrame, "Formular Rezervare", true);

        int width = thisFrame.getWidth() - 50;
        int height = thisFrame.getHeight() - 50;
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(thisFrame);

         JPanel mainPanel = new JPanel();
         mainPanel.setAlignmentY(CENTER_ALIGNMENT);
         mainPanel.setBackground(new Color(0xFFFFFF));


        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(10,1,30,30));
        formPanel.setBackground(Color.LIGHT_GRAY);



        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentY(CENTER_ALIGNMENT);
        formPanel.setBackground(new Color(0xFFFFFF));



        JLabel textJlabel = new JLabel();
        textJlabel.setText("Selecteaza urmatoarele facilități:");
        textJlabel.setAlignmentX(CENTER_ALIGNMENT);
        textJlabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(textJlabel);



        Bilet bilet = new Bilet();
        bilet.setIdZbor(idZbor);
        bilet.setIdUser(idUser);

        int nrLocuriClasaA = ZborDB.getNrLocuriClasaA(idZbor);
        int nrLocuriClasaB = ZborDB.getNrLocuriClasaB(idZbor);
        double price = ZborDB.getPrice(idZbor);
        bilet.setPret(price);//pret zbor


        JPanel clasaPanel = new JPanel();
        clasaPanel.setLayout(new GridLayout(1, 2, 50, 5));
        clasaPanel.add(new JLabel("Clasa:"));
        JComboBox<String> comboBoxClass = new JComboBox<String>();
        if(nrLocuriClasaA > 0){
            comboBoxClass.addItem("Clasa A");
        }
        if(nrLocuriClasaB > 0){
            comboBoxClass.addItem("Clasa B");
        }
        clasaPanel.add(comboBoxClass);
        formPanel.add(clasaPanel);



        JPanel mancarePanel = new JPanel();
        mancarePanel.setLayout(new GridLayout(1, 2, 50, 5));
        mancarePanel.add(new JLabel("Mancare:"));
        JComboBox<String> comboBoxMancare = new JComboBox<>();
        MancareDB.getMancareDB();
        if(Mancare.listaMancare.isEmpty()){
            comboBoxMancare.addItem("-");
            mancarePanel.add(comboBoxMancare);
        }else {
            for (Mancare m : Mancare.listaMancare) {
                String str = m.getNumeMancare() + " - " + m.getPretMancare();
                comboBoxMancare.addItem(str);
            }
        mancarePanel.add(comboBoxMancare);
        formPanel.add(mancarePanel);



        JPanel bauturaPanel = new JPanel();
        bauturaPanel.setLayout(new GridLayout(1, 2, 50, 5));
        bauturaPanel.add(new JLabel("Bautura:"));
        JComboBox<String> comboBoxBautura = new JComboBox<>();
        BauturaDB.getBauturaDB();
        if(Bautura.listaBautura.isEmpty()){
            comboBoxBautura.addItem("-");
            bauturaPanel.add(comboBoxBautura);
        }else {
            for (Bautura b : Bautura.listaBautura) {
                String str2 = b.getNumeBautura() + " - " + b.getPretBautura();
                comboBoxBautura.addItem(str2);
            }
        bauturaPanel.add(comboBoxBautura);
        formPanel.add(bauturaPanel);



        JPanel filmPanel = new JPanel();
        filmPanel.setLayout(new GridLayout(1, 2, 50, 5));
        filmPanel.add(new JLabel("Film:"));
        JTextField film = new JTextField(10);
        filmPanel.add(film);
        formPanel.add(filmPanel);


        JButton btnConfirm = new JButton("Confirmare");
        btnConfirm.addActionListener(e -> {

            String tipMancare = comboBoxMancare.getSelectedItem().toString();
            if(!tipMancare.equals("-")){
                String[] v = tipMancare.split(" - ");
                String numeMancare = v[0];
                double pretMancare = Double.parseDouble(v[1]);

                int getIdMancare = MancareDB.getIdFoodWhere(numeMancare, pretMancare);
                bilet.setIdMancare(getIdMancare);//daca nu reuseste interactiunea cu baza de date va returna 0 si nu se va pune nimic in baza de date,va fi null default
                if(getIdMancare != 0){
                    bilet.setPret(bilet.getPret() + pretMancare);//pret mancare
                }
            }else{
                bilet.setIdMancare(0);
            }



            String tipBautura = comboBoxBautura.getSelectedItem().toString();
            if(!tipBautura.equals("-")){
                String[] vect = tipBautura.split(" - ");
                String numeBautura = vect[0];
                double pretBautura = Double.parseDouble(vect[1]);

                int getIdBautura = BauturaDB.getIdDrinkWhere(numeBautura, pretBautura);
                bilet.setIdBautura(getIdBautura);
                if(getIdBautura != 0){
                    bilet.setPret(bilet.getPret() + pretBautura);//pret bautura
                }
            }else{
                bilet.setIdBautura(0);
            }




            String getClassSelected = comboBoxClass.getSelectedItem().toString();
            String[] classSelected = getClassSelected.split(" ");
            bilet.setClasa(classSelected[1]);
            if(getClassSelected.equals("A")){
                bilet.setPret(bilet.getPret() + 50);
                bilet.setNrClasaA(bilet.getNrClasaA() - 1);
                ZborDB.setNrLocuriClasaA(idZbor,bilet.getNrClasaA());
            }else{
                bilet.setNrClasaB(bilet.getNrClasaA() - 1);
                ZborDB.setNrLocuriClasaB(idZbor,bilet.getNrClasaB());
            }


            String tipFilm = film.getText();
            bilet.setFilm(tipFilm);


            if (bilet.getIdMancare() == 0 && bilet.getIdBautura() == 0) {
                System.out.println("Administratorul nu a adaugat mancare si bautura!");
                BiletDB.addReservationBDwithoutDrinkAndFood(bilet);
            } else if (bilet.getIdMancare() == 0) {
                System.out.println("Administratorul nu a adaugat mancare!");
                BiletDB.addReservationBDwithoutFood(bilet);
            } else if (bilet.getIdBautura() == 0) {
                System.out.println("Administratorul nu a adaugat bautura!");
                BiletDB.addReservationBDwithoutDrink(bilet);
            } else {
                BiletDB.addReservationBD(bilet);
            }
            JOptionPane.showMessageDialog(dialog, "Rezervarea pentru zborul cu ID " + idZbor + " a fost realizată!\nMULTUMIM!");

            dialog.dispose();
        });



        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnConfirm);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buttonPanel);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

}}}
