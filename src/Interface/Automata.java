package Interface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by caenrique93 on 21/01/15.
 */
public class Automata extends JFrame {
    JPanel panel;

    JComboBox<String> selAutomatas;
    JLabel listAutomatas;
    JLabel labelFichero;
    JTextArea textArea;
    JTextField input;

    JButton bEjecutar;
    JButton bCargar;
    JButton bSeleccionar;

    public Automata() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Práctica de Autómatas - AyMC");
        setSize(600, 800);
        setLocationRelativeTo(null);

        String[] automatas_src = {"Autómata Finito Determinista",
            "Autómata Finito No Determinista"};

        panel = new JPanel();
        selAutomatas = new JComboBox<String>(automatas_src);
        input = new JTextField();
        labelFichero = new JLabel("Fichero");
        listAutomatas = new JLabel("Autómatas");
        textArea = new JTextArea();

        bCargar = new JButton("Cargar");
        bCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        bSeleccionar = new JButton("Seleccionar");
        bSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        bEjecutar = new JButton("Ejecutar");
        bEjecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        panel.add(listAutomatas);
        panel.add(selAutomatas);
        panel.add(labelFichero);
        panel.add(input);
        panel.add(bSeleccionar);
        panel.add(bCargar);
        panel.add(bEjecutar);
        panel.add(textArea);

        add(panel);
        setVisible(true);
    }
}
