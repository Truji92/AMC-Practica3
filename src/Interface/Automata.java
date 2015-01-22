package Interface;

import AutomataFD.AFD;
import AutomataFND.AFND;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by caenrique93 on 21/01/15.
 */
public class Automata extends JFrame {

    JFileChooser fileChooser;
    AFD automata;
    AFND automataND;

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
                        middle.setText(automata.toString() +
                                "----------------------------------------\n\n");
                    } else {
                        try {
                            automataND = AFND.cargarArchivo(file);
                            middle.setText(automataND.toString() +
                                "----------------------------------------\n\n");
                        } catch (FileNotFoundException ex) {

                        } catch (IOException ex) {

                        }
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

                } else {
                    resultado = automataND.reconocer(texto);
                }
                middle.setText(middle.getText() +
                        "\n\nCadena de entrada: " + texto + "\n");
                if(resultado) {
                    texto = "El autómata reconoció la cadena";
                } else {
                    texto = "El autómata no reconoció la cadena";
                }
                middle.setText(middle.getText() + texto);
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
