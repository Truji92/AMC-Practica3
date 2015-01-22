package Interface;

import AutomataFD.AFD;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by caenrique93 on 21/01/15.
 */
public class Automata extends JFrame {

    JFileChooser fileChooser;
    AFD automata;

    public Automata() {
        automata = new AFD();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Práctica de Autómatas - AyMC");
        setSize(600, 600);
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new BorderLayout());
        JPanel bottom = new JPanel(new BorderLayout());
        final JTextPane middle = new JTextPane();
        StyleContext sc = StyleContext.getDefaultStyleContext();

        middle.setCharacterAttributes(sc.addAttribute(
                SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground,
                Color.WHITE), false);

        JScrollPane middleContainer = new JScrollPane(middle);
        middle.setBackground(Color.DARK_GRAY);

        final String[] opc = {"Autómata Finito Determinista",
            "Autómata Finito No Determinista"};

        final JTextField input = new JTextField();
        final JComboBox<String> opciones = new JComboBox<String>(opc);

        JButton bSelec = new JButton("Seleccionar");
        bSelec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int returVal = fileChooser.showOpenDialog(null);

                if(returVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String opcion = (String) opciones.getSelectedItem();
                    if(opcion.equals(opc[0])) {
                        automata = AFD.contenido(file);
                    } else {

                    }
                }
            }
        });

        JButton bEjecutar = new JButton("Ejecutar");
        bEjecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String texto = input.getText();
                String opcion = (String) opciones.getSelectedItem();
                boolean resultado;
                if(opcion.equals(opc[0])) {
                    resultado = automata.reconocer(texto);
                    middle.setText("resultado: " + resultado);
                } else {
                    //añadir
                }

            }
        });

        top.add(opciones, BorderLayout.CENTER);
        top.add(bSelec, BorderLayout.EAST);

        bottom.add(input, BorderLayout.CENTER);
        bottom.add(bEjecutar, BorderLayout.EAST);

        this.add(top, BorderLayout.NORTH);
        this.add(middleContainer, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }
}
