import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFood extends MyFrame{
    private JPanel p2;
    private JTextField textDenumireMancare;
    private JTextField textPretMancare;
    private JButton btnAdaugareMancare;
    private JButton btnMain;

    public AddFood() {
        super();
        setTitle("Adauga bauturi!");
        setContentPane(p2);
        setLocationRelativeTo(null);
        setVisible(true);

        btnMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                SwingUtilities.invokeLater(VizualizareZborAdmin::new);
            }
        });

        btnAdaugareMancare.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                addMancare();
            }
        });
    }

    public void addMancare(){
        String denumireMancare = textDenumireMancare.getText();
        double pretMancare = Double.parseDouble(textPretMancare.getText());

        Mancare.addMancareDB(new Mancare(denumireMancare,pretMancare));

        textDenumireMancare.setText("");
        textPretMancare.setText("");
    }
}
