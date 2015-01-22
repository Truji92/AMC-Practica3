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
        final JTextPane middleIzq = new JTextPane();
        final JTextPane middleDer = new JTextPane();
        StyleContext sc = StyleContext.getDefaultStyleContext();

        middleIzq.setCharacterAttributes(sc.addAttribute(
                SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground,
                Color.WHITE), false);
        middleDer.setCharacterAttributes(sc.addAttribute(
                SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground,
                Color.WHITE), false);

        JScrollPane middleIzqContainer = new JScrollPane(middleIzq);
        JScrollPane middleDerContainer = new JScrollPane(middleDer);
        middleIzq.setBackground(Color.DARK_GRAY);
        middleDer.setBackground(Color.DARK_GRAY);

        JPanel middle = new JPanel(new GridLayout());
        middle.add(middleIzqContainer);
        middle.add(middleDerContainer);

        String userDirLocation = System.getProperty("user.dir");
        final File userDir = new File(userDirLocation);

        final String[] opc = {"Autómata Finito Determinista",
            "Autómata Finito No Determinista"};

        final JTextField input = new JTextField();
        final JComboBox<String> opciones = new JComboBox<String>(opc);

        JButton bSelec = new JButton("Seleccionar");
        bSelec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser(userDir);
                int returVal = fileChooser.showOpenDialog(null);

                if(returVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String opcion = (String) opciones.getSelectedItem();
                    if(opcion.equals(opc[0])) {
                        automata = AFD.contenido(file);
                        middleIzq.setText(automata.toString());
                    } else {
                        try {
                            automataND = AFND.cargarArchivo(file);
                            middleIzq.setText(automataND.toString());
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
                middleDer.setText(middleDer.getText() +
                        "Cadena de entrada: " + texto + "\n");
                if(resultado) {
                    texto = "El autómata reconoció la cadena";
                } else {
                    texto = "El autómata no reconoció la cadena";
                }
                middleDer.setText(middleDer.getText() + texto);
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
