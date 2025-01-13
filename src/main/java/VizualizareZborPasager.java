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
    private JTextField baraCautare;
    private JButton button1;
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
        Zbor.addZborDinBDinListaZboruri();
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

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout (formPanel,BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(0xFFFFFF));
        formPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        double priceTotal = 0;
        Bilet bilet = new Bilet();
        bilet.setIdZbor(idZbor);
        bilet.setIdUser(idUser);

        try(Conn conexiune = new Conn()) {
            if(conexiune.MyConn() == 0){
                JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String query1 = "SELECT classA,classB,price FROM flight WHERE id_flight = ?";
                PreparedStatement pstm1 = conexiune.getDB().prepareStatement(query1);
                pstm1.setInt(1, idZbor);
                ResultSet result1 = pstm1.executeQuery();
                while(result1.next()){
                    int nrLocuriClasaA = result1.getInt("classA");
                    bilet.setNrClasaA(nrLocuriClasaA);
                    int nrLocuriClasaB = result1.getInt("classB");
                    bilet.setNrClasaB(nrLocuriClasaB);
                    double price = result1.getDouble("price");
                    bilet.setPret(price);
                }
                result1.close();
                pstm1.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Eroare la conectarea cu baza de date1: " + e.getMessage(),"EROARE",JOptionPane.ERROR_MESSAGE);
        }



        formPanel.add(new JLabel("Clasa:"));
        JComboBox<String> comboBoxClass = new JComboBox<String>();
        if(bilet.getNrClasaA() > 0){
            comboBoxClass.addItem("Clasa A");
        }
        if(bilet.getNrClasaB() > 0){
            comboBoxClass.addItem("Clasa B");
        }
        formPanel.add(comboBoxClass);
        bilet.setClasa(comboBoxClass.getSelectedItem().toString());



        if(comboBoxClass.getName() == "Clasa A"){
            priceTotal = priceTotal + bilet.getPret() + 50;
            bilet.setPret(priceTotal);
            bilet.setNrClasaA(bilet.getNrClasaA() - 1);
            //actualizez si in baza de date nr de locuri pentru acel zbor
            try(Conn conexiune = new Conn()) {
                if(conexiune.MyConn() == 0){
                    JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
                }else{
                    String updateSQL = "UPDATE flight SET classA = ? WHERE id_flight = ?";
                    PreparedStatement pstmt2 = conexiune.getDB().prepareStatement(updateSQL);
                    pstmt2.setInt(1,bilet.getNrClasaA());
                    pstmt2.setInt(2,idZbor);
                    pstmt2.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        if(comboBoxClass.getName() == "Clasa B"){
            priceTotal = priceTotal + bilet.getPret();
            bilet.setPret(priceTotal);
            bilet.setNrClasaB(bilet.getNrClasaB() - 1);
            try(Conn conexiune = new Conn()) {
                if(conexiune.MyConn() == 0){
                    JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
                }else{
                    String updateSQL = "UPDATE flight SET classB = ? WHERE id_flight = ?";
                    PreparedStatement pstmt2 = conexiune.getDB().prepareStatement(updateSQL);
                    pstmt2.setInt(1,bilet.getNrClasaB());
                    pstmt2.setInt(2,idZbor);
                    pstmt2.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        }



        formPanel.add(new JLabel("Mancare:"));
        JComboBox<String> comboBoxMancare = new JComboBox<>();
        Mancare.getMancareDB();
        for(Mancare m : Mancare.listaMancare){
            String str = m.getNumeMancare() + " - " + m.getPretMancare();
            comboBoxMancare.addItem(str);
        }
        formPanel.add(comboBoxMancare);
        String tipMancare = comboBoxMancare.getSelectedItem().toString();
        String[] v = tipMancare.split(" - ");
        String numeMancare = v[0];
        double pretMancare = Double.parseDouble(v[1]);
        bilet.setPret(priceTotal + pretMancare);
        try(Conn conexiune = new Conn()) {
            if(conexiune.MyConn() == 0){
                JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
               String query3 = "SELECT id_food FROM food WHERE name = ? and price = ?";
               PreparedStatement pstmt3 = conexiune.getDB().prepareStatement(query3);
               pstmt3.setString(1,numeMancare);
               pstmt3.setDouble(2,pretMancare);
               ResultSet rs3 = pstmt3.executeQuery();
               while(rs3.next()){
                   int idMancare = rs3.getInt("id_food");
                   bilet.setIdMancare(idMancare);
               }
                rs3.close();
                pstmt3.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
        }



        formPanel.add(new JLabel("Bautura:"));
        JComboBox<String> comboBoxBautura = new JComboBox<>();
        Bautura.getBauturaDB();
        for(Bautura b : Bautura.listaBautura){
            String str2 = b.getNumeBautura() + " - " + b.getPretBautura();
            comboBoxBautura.addItem(str2);
        }
        formPanel.add(comboBoxBautura);
        String tipBautura = comboBoxBautura.getSelectedItem().toString();
        String[] vect = tipBautura.split(" - ");
        String numeBautura = vect[0];
        double pretBautura = Double.parseDouble(vect[1]);
        bilet.setPret(priceTotal + pretBautura);
        try(Conn conexiune = new Conn()) {
            if(conexiune.MyConn() == 0){
                JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
            }else{
                String query4 = "SELECT id_drink FROM food WHERE name = ? and price = ?";
                PreparedStatement pstmt4 = conexiune.getDB().prepareStatement(query4);
                pstmt4.setString(1,numeBautura);
                pstmt4.setDouble(2,pretBautura);
                ResultSet rs4 = pstmt4.executeQuery();
                while(rs4.next()){
                    int idBautura = rs4.getInt("id_drink");
                    bilet.setIdBautura(idBautura);
                }
                rs4.close();
                pstmt4.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Eroare la conectarea cu baza de date: ", "Eroare", JOptionPane.ERROR_MESSAGE);
        }



        formPanel.add(new JLabel("Film:"));
        JTextField film = new JTextField();
        formPanel.add(film);
        String tipFilm = film.getText();
        bilet.setFilm(tipFilm);


        JButton btnConfirm = new JButton("Confirmare");
        btnConfirm.addActionListener(e -> {
            Bilet.addReservationBD(bilet);
            JOptionPane.showMessageDialog(dialog, "Rezervarea pentru zborul cu ID " + idZbor + " a fost realizată!");
            dialog.dispose();
        });


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnConfirm);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buttonPanel);


        dialog.add(formPanel);
        dialog.setVisible(true);
    }

}
