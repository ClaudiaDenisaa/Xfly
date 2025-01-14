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

        Pasager utilizator = Pasager.getUtilizator();
        int id = utilizator.getId();
        Zbor.addZborDinBDinListaZboruri(id);
        Bilet.takeReservationBD(id);
        componenteInformatiiRezervare();


        btnMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                SwingUtilities.invokeLater(VizualizareZborPasager::new);
            }
        });
        //componenta va avea un buton de anulare (se va sterge si din db si componenta daca se anuleaza
        //ai grija ca atunci cand se stterge o rezervare sa nu se stearga alte info de la chei straine
    }

    public void componenteInformatiiRezervare() {
        componentaBilet.removeAll();
        componentaBilet.revalidate();
        componentaBilet.repaint();


        ArrayList<Zbor> listaZboruri = Zbor.getListaZboruri();
        if (listaZboruri == null || listaZboruri.isEmpty()) {
            componentaBilet.add(new JLabel("Nu există rezervari!"));
            return;
        }

        JPanel panelComponenteInformatii = new JPanel();
        panelComponenteInformatii.setLayout(new GridLayout(1,2,7,7));
        panelComponenteInformatii.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelComponenteInformatii.setBackground(Color.WHITE);

        for(int i=1;i<=listaZboruri.size();i++) {
            panelComponenteInformatii.add(getPanelComponenta1(i));
            JPanel panelComponenta2 = new JPanel();
            panelComponenta2.setLayout(new GridLayout(2,1,2,3));
            panelComponenta2.add(getComponenta3(i));
            JButton btnAnulare = new JButton("Anulare");
            int idRezervare = Bilet.listaBilete.get(i).getId_bilet();
            btnAnulare.addActionListener(e -> Bilet.deleteReservationBD(idRezervare));
            panelComponenta2.add(btnAnulare);
            panelComponenteInformatii.add(panelComponenta2);
            componentaBilet.add(panelComponenteInformatii);
        }

            componentaBilet.revalidate();
            componentaBilet.repaint();

    }
    public JPanel getPanelComponenta1(int i){
        JPanel panelComponenta1 = new JPanel();
        panelComponenta1.setLayout(new GridLayout(8,2,2,3));

        panelComponenta1.setBackground(new Color(0xBEC7FF));
        panelComponenta1.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panelComponenta1.add(new JLabel("Plecare: " + Zbor.listaZboruri.get(i).getDestinatiePlecare()));
        panelComponenta1.add(new JLabel("Sosire: " + Zbor.listaZboruri.get(i).getDestinatieSosire()));

        panelComponenta1.add(new JLabel("Ora plecare: " + Zbor.listaZboruri.get(i).getOraPlecare()));
        panelComponenta1.add(new JLabel("Ora sosire: " + Zbor.listaZboruri.get(i).getOraSosire()));

        panelComponenta1.add(new JLabel("Model avion: " + Zbor.listaZboruri.get(i).getAvion().getModel()));
        panelComponenta1.add(new JLabel());

        panelComponenta1.add(new JLabel("Clasa A: " + Zbor.listaZboruri.get(i).getAvion().getLocuri() + " locuri"));
        panelComponenta1.add(new JLabel());

        panelComponenta1.add(new JLabel("Clasa B: " + Zbor.listaZboruri.get(i).getAvion().getLocuriB() + " locuri"));
        panelComponenta1.add(new JLabel());

        panelComponenta1.add(new JLabel("Data: " + Zbor.listaZboruri.get(i).getData()));
        panelComponenta1.add(new JLabel());

        panelComponenta1.add(new JLabel("Durata: " + Zbor.listaZboruri.get(i).getDurata() + " ore"));
        JLabel pretLabel = new JLabel("Preț: " + Zbor.listaZboruri.get(i).getPret() + " euro");
        pretLabel.setFont(new Font(pretLabel.getFont().getName(), Font.BOLD, pretLabel.getFont().getSize()));
        panelComponenta1.add(pretLabel);

        return panelComponenta1;
    }

    public JPanel getComponenta3(int i) {
        JPanel panelComponenta3 = new JPanel();
        panelComponenta3.setLayout(new GridLayout(8,1,2,3));
        panelComponenta3.setBackground(new Color(0xBEC7FF));
        panelComponenta3.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        Mancare m = new Mancare();
        try(Conn conn = new Conn();){
            if(conn.MyConn() == 0){
                JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String query1 = "SELECT name,price FROM food WHERE id_food = ?";
                int idFood = Bilet.listaBilete.get(i).getIdMancare();
                PreparedStatement pstmt1 = conn.getDB().prepareStatement(query1);
                pstmt1.setInt(1, idFood);
                ResultSet rs1 = pstmt1.executeQuery();
                while(rs1.next()){
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

        Bautura b = new Bautura();
        try(Conn conn = new Conn();){
            if(conn.MyConn() == 0){
                JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String query2 = "SELECT name,price FROM drink WHERE id_food = ?";
                int idDrink = Bilet.listaBilete.get(i).getIdMancare();
                PreparedStatement pstmt2 = conn.getDB().prepareStatement(query2);
                pstmt2.setInt(1, idDrink);
                ResultSet rs2 = pstmt2.executeQuery();
                while(rs2.next()){
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

        panelComponenta3.add(new JLabel("Facilitati:"));
        panelComponenta3.add(new JLabel("Clasa: " + Bilet.listaBilete.get(i).getClasa()));
        panelComponenta3.add(new JLabel("Film selectat: " + Bilet.listaBilete.get(i).getFilm()));
        panelComponenta3.add(new JLabel("Servire:"));
        panelComponenta3.add(new JLabel(m.getNumeMancare() + "/" + m.getPretMancare()));
        panelComponenta3.add(new JLabel(b.getNumeBautura() + "/" + b.getPretBautura()));
        panelComponenta3.add(new JLabel());
        panelComponenta3.add(new JLabel("Pret total: " + Bilet.listaBilete.get(i).getPret()));


        return panelComponenta3;
    }
}
