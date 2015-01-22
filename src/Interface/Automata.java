package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by caenrique93 on 21/01/15.
 */
public class Automata extends JFrame {

    JFileChooser fileChooser;

    public Automata() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Práctica de Autómatas - AyMC");
        setSize(600, 600);
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new BorderLayout());
        JPanel bottom = new JPanel(new BorderLayout());
        final JTextArea middle = new JTextArea();
        middle.setBackground(Color.DARK_GRAY);

        final String[] opc = {"Autómata Finito Determinista",
            "Autómata Finito No Determinista"};

        final JTextField input = new JTextField();
        final JComboBox<String> opciones = new JComboBox<String>(opc);

        JButton bSelec = new JButton("Seleccionar");
        bSelec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jFileChooser = new JFileChooser();
                int returVal = jFileChooser.showOpenDialog(middle);

                if(returVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                }
            }
        });

        JButton bEjecutar = new JButton("Ejecutar");
        bEjecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String texto = input.getText();
                String opcion = (String) opciones.getSelectedItem();
                if(opcion.equals(opc[0])) {

                } else {

                }
            }
        });

        top.add(opciones, BorderLayout.CENTER);
        top.add(bSelec, BorderLayout.EAST);

        bottom.add(input, BorderLayout.CENTER);
        bottom.add(bEjecutar, BorderLayout.EAST);

        this.add(top, BorderLayout.NORTH);
        this.add(middle, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }
}
