import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VizualizareZborPasager extends MyFrame {

    private JPanel panelPrincipal;
    private JScrollPane scrollPanel;
    private JPanel panelComponente;
    private JButton btnIesire;

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

        btnIesire.addActionListener(e -> System.exit(0));

    }

    public void componenteInformatiiZbor() {
        panelComponente.removeAll();
        panelComponente.revalidate();
        panelComponente.repaint();

        ArrayList<Zbor> listaZboruri = Zbor.getListaZboruri();
        if (listaZboruri == null || listaZboruri.isEmpty()) {
            panelComponente.add(new JLabel("Nu există zboruri disponibile."));
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
            zborPanel.add(new JLabel());

            zborPanel.add(new JLabel());
            JButton btnRezervare = new JButton("Rezervare");
            zborPanel.add(btnRezervare);

            panelComponente.add(zborPanel);
        }
    }



}
