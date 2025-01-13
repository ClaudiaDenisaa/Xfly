import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class VizualizareZborAdmin extends MyFrame {
    private JButton BtnMeniuAdmin;
    private JLabel MainView;
    private JButton btnAddZbor;
    private JButton btnExitAdmin;
    private JPanel panel;
    private JScrollPane scrolPanel;
    private JPanel panelDeComponente;
    private JButton btnAddFood;
    private JButton btnAddDrink;

    /**
     * Constructor VizualizareZborAdmin
     */
    public VizualizareZborAdmin() {
        super();
        setTitle("Vizualizarea zborurilor");
        setContentPane(panel);
        setLocationRelativeTo(null);
        setVisible(true);
        panelDeComponente.setLayout(new BoxLayout(panelDeComponente, BoxLayout.Y_AXIS));

        Avion.takeAvionDB();
        Zbor.addZborDinBDinListaZboruri();
        componentaZbor();

        BtnMeniuAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(Avion::new);
            }
        });

        btnAddZbor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(AddZborAdmin::new);
            }
        });

        btnAddFood.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(AddFood::new);
            }
        });

        btnAddDrink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(AddDrink::new);
            }
        });

        btnExitAdmin.addActionListener(e -> System.exit(0));
    }

    public void componentaZbor(){
        panelDeComponente.removeAll();
        panelDeComponente.revalidate();
        panelDeComponente.repaint();

        ArrayList<Zbor> listaZboruri = Zbor.getListaZboruri();
        if (listaZboruri == null || listaZboruri.isEmpty()) {
            panelDeComponente.add(new JLabel("Nu există zboruri disponibile."));
            return;
        }

        for(Zbor z : listaZboruri){
            JPanel zborPanel = new JPanel();
            zborPanel.setLayout(new GridLayout(8,2,5,5));
            zborPanel.setBackground(new Color(0x96C2FF));
            zborPanel.setBorder(BorderFactory.createLineBorder( Color.GRAY));


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
            zborPanel.add(new JButton("Modifica"));

            zborPanel.add(new JLabel("Preț: " + z.getPret() + " euro"));
            JButton btnStergere = new JButton("Stergere");
            zborPanel.add(btnStergere);

            btnStergere.addActionListener(e -> {
               int confirmare = JOptionPane.showConfirmDialog(null,"Sigur doriți să ștergeți acest zbor?",
                       "Confirmare Ștergere",JOptionPane.YES_NO_OPTION);
               if(confirmare == JOptionPane.YES_OPTION){
                   Zbor.stergereZbor(z.getId_zbor());
                   componentaZbor();
               }
            });

            panelDeComponente.add(zborPanel);
        }
        panelDeComponente.revalidate();
        panelDeComponente.repaint();
    }
}
