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

public class IstoricRezervari extends MyFrame{
    private JPanel componentaBilet;
    private JScrollPane scrolRezervare;
    private JPanel panelMain;
    private JButton btnMain;

    public IstoricRezervari() {
        super();
        setTitle("Vizualizarea zborurilor rezervate!");
        setContentPane(panelMain);
        setLocationRelativeTo(null);
        setVisible(true);
        componentaBilet.setLayout(new BoxLayout(componentaBilet,BoxLayout.Y_AXIS));
        componentaBilet.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        componentaBilet.setBackground(Color.WHITE);



        Pasager utilizator = Pasager.getUtilizator();
        int id = utilizator.getId();
        ZborDB.addZborDinBDinListaZboruri(id); //se umple listaZboruri
        BiletDB.takeReservationBD(id); //se umple listaBilete
        componenteInformatiiRezervare(id);


        btnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                SwingUtilities.invokeLater(VizualizareZborPasager::new);
            }
        });

    }

    public void componenteInformatiiRezervare(int id) {
        componentaBilet.removeAll();
        componentaBilet.revalidate();
        componentaBilet.repaint();


        ArrayList<Zbor> listaZboruri = Zbor.getListaZboruri();
        if (listaZboruri == null || listaZboruri.isEmpty()) {
            componentaBilet.add(new JLabel("Nu există rezervari!"));
            return;
        }


        for(int i=0;i<listaZboruri.size();i++) {
            JPanel panelComponenteInformatii = new JPanel();
            panelComponenteInformatii.setLayout(new GridLayout(1,2,20,20));
            panelComponenteInformatii.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panelComponenteInformatii.setBackground(new Color(0x96C2FF));
            panelComponenteInformatii.add(getPanelComponenta1(i));
            JPanel panelComponenta2 = new JPanel();
            panelComponenta2.setLayout(new GridLayout(1,1,20,20));
            panelComponenta2.setBorder(null);
            panelComponenta2.setBackground(new Color(0x96C2FF));
            panelComponenta2.add(getComponenta3(i));
            JButton btnAnulare = new JButton("Anulare");
            //btnAnulare.setBackground(new Color(0xACD3FF));
            btnAnulare.setAlignmentX(Component.CENTER_ALIGNMENT);//
            int idRezervare = Bilet.listaBilete.get(i).getId_bilet();
            btnAnulare.addActionListener(e -> {
                BiletDB.deleteReservationBD(idRezervare);//sterge rezervarea
                Zbor.stergeListaZboruri();
                Bilet.listaBilete.clear();
                SwingUtilities.invokeLater(IstoricRezervari::new);
            });
            //panelComponenta2.add(btnAnulare);
            panelComponenta2.add(getComponenta3(i).add(btnAnulare));
            panelComponenteInformatii.add(panelComponenta2);
            componentaBilet.add(panelComponenteInformatii);
            componentaBilet.add(Box.createVerticalStrut(30));
        }

            componentaBilet.revalidate();
            componentaBilet.repaint();

    }
    public JPanel getPanelComponenta1(int i){
        JPanel panelComponenta1 = new JPanel();
        panelComponenta1.setLayout(new GridLayout(10,1,2,3));
        panelComponenta1.setBorder(null);
        panelComponenta1.setBackground(new Color(0x96C2FF));


        panelComponenta1.add(new JLabel("Plecare: " + Zbor.listaZboruri.get(i).getDestinatiePlecare()));
        panelComponenta1.add(new JLabel("Sosire: " + Zbor.listaZboruri.get(i).getDestinatieSosire()));

        panelComponenta1.add(new JLabel("Ora plecare: " + Zbor.listaZboruri.get(i).getOraPlecare()));
        panelComponenta1.add(new JLabel("Ora sosire: " + Zbor.listaZboruri.get(i).getOraSosire()));

        panelComponenta1.add(new JLabel("Model avion: " + Zbor.listaZboruri.get(i).getAvion().getModel()));
        panelComponenta1.add(new JLabel("Clasa A: " + Zbor.listaZboruri.get(i).getAvion().getLocuri() + " locuri"));

        panelComponenta1.add(new JLabel("Clasa B: " + Zbor.listaZboruri.get(i).getAvion().getLocuriB() + " locuri"));
        panelComponenta1.add(new JLabel("Data: " + Zbor.listaZboruri.get(i).getData()));

        panelComponenta1.add(new JLabel("Durata: " + Zbor.listaZboruri.get(i).getDurata() + " ore"));
        JLabel pretLabel = new JLabel("Preț: " + Zbor.listaZboruri.get(i).getPret() + " euro");
        pretLabel.setFont(new Font(pretLabel.getFont().getName(), Font.BOLD, pretLabel.getFont().getSize()));
        panelComponenta1.add(pretLabel);

        return panelComponenta1;
    }

    public JPanel getComponenta3(int i) {
        JPanel panelComponenta3 = new JPanel();
        panelComponenta3.setLayout(new GridLayout(17,2,2,3));
        panelComponenta3.setBackground(new Color(0x96C2FF));
        panelComponenta3.setBorder(null);

        int existaMancare = 1;
        int existaBautura = 1;
        Mancare m = new Mancare();
        Bautura b = new Bautura();

        if(Bilet.listaBilete.get(i).getIdMancare() != 0) {
            try (Conn conn = new Conn();) {
                if (conn.MyConn() == 0) {
                    JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
                } else {
                    String query1 = "SELECT name,price FROM food WHERE id_food = ?";
                    int idFood = Bilet.listaBilete.get(i).getIdMancare();
                    PreparedStatement pstmt1 = conn.getDB().prepareStatement(query1);
                    pstmt1.setInt(1, idFood);
                    ResultSet rs1 = pstmt1.executeQuery();
                    while (rs1.next()) {
                        String numeMancare = rs1.getString("name");
                        double pretMancare = rs1.getDouble("price");
                        m.setNumeMancare(numeMancare);
                        m.setPretMancare(pretMancare);
                    }
                    rs1.close();
                    pstmt1.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            existaMancare = 0;
        }

        if(Bilet.listaBilete.get(i).getIdBautura() != 0) {
            try (Conn conn = new Conn();) {
                if (conn.MyConn() == 0) {
                    JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
                } else {
                    String query2 = "SELECT name,price FROM drink WHERE id_drink= ?";
                    int idDrink = Bilet.listaBilete.get(i).getIdBautura();
                    PreparedStatement pstmt2 = conn.getDB().prepareStatement(query2);
                    pstmt2.setInt(1, idDrink);
                    ResultSet rs2 = pstmt2.executeQuery();
                    while (rs2.next()) {
                        String numeBautura = rs2.getString("name");
                        double pretBautura = rs2.getDouble("price");
                        b.setNumeBautura(numeBautura);
                        b.setPretBautura(pretBautura);
                    }
                    rs2.close();
                    pstmt2.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            existaBautura = 0;
        }

        panelComponenta3.add(new JLabel());
        JLabel facilitati = new JLabel("Facilitati:");
        facilitati.setFont(new Font(facilitati.getFont().getName(), Font.BOLD, facilitati.getFont().getSize()));
        panelComponenta3.add(facilitati);
        panelComponenta3.add(new JLabel());
        panelComponenta3.add(new JLabel("Clasa: " + Bilet.listaBilete.get(i).getClasa()));
        panelComponenta3.add(new JLabel());
        panelComponenta3.add(new JLabel("Film selectat: " + Bilet.listaBilete.get(i).getFilm()));
        panelComponenta3.add(new JLabel());
        panelComponenta3.add(new JLabel());

        JLabel servire = new JLabel("Servire:");
        servire.setFont(new Font(servire.getFont().getName(), Font.BOLD, servire.getFont().getSize()));
        panelComponenta3.add(servire);
        panelComponenta3.add(new JLabel(" "));
        if(existaMancare == 1) {
            panelComponenta3.add(new JLabel(m.getNumeMancare() + "/" + m.getPretMancare()));}
        else {
            panelComponenta3.add(new JLabel("-"));
        }
        if(existaBautura == 1){
            panelComponenta3.add(new JLabel(b.getNumeBautura() + "/" + b.getPretBautura()));
        }
        else{
            panelComponenta3.add(new JLabel("-"));
        }
        panelComponenta3.add(new JLabel());
        panelComponenta3.add(new JLabel());
        JLabel pretLabel = new JLabel("Preț TOTAL: " + Bilet.listaBilete.get(i).getPret() + " euro");
        pretLabel.setFont(new Font(pretLabel.getFont().getName(), Font.BOLD, pretLabel.getFont().getSize()));
        panelComponenta3.add(pretLabel);
        panelComponenta3.add(new JLabel());
        panelComponenta3.add(new JLabel());


        return panelComponenta3;
    }
}
