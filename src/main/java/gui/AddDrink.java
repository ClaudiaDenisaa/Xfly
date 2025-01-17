package gui;

import entitati.*;
import database.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDrink extends MyFrame{
    private JTextField textPretBautura;
    private JPanel p1;
    private JTextField textDenumireBautura;
    private JButton btnAdaugareBautura;
    private JLabel NumeBautura;
    private JLabel pretBautura;
    private JButton btnMain;

    public AddDrink(){
        super();
        setTitle("Adauga bauturi!");
        setContentPane(p1);
        setLocationRelativeTo(null);
        setVisible(true);

        btnMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                SwingUtilities.invokeLater(VizualizareZborAdmin::new);
            }
        });

        btnAdaugareBautura.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                addBautura();
            }
        });
    }

    public void addBautura(){
        String denumireBautura = textDenumireBautura.getText();
        double pretBautura = Double.parseDouble(textPretBautura.getText());

        BauturaDB.addBauturaDB(new Bautura(denumireBautura, pretBautura));

        textDenumireBautura.setText("");
        textPretBautura.setText("");
    }
}
