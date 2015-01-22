package Interface;

import AutomataFD.AFD;
import AutomataFND.AFND;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 *  La clase Automata recoge todos los elementos de la interfaz gráfica.
 */
public class Automata extends JFrame {

    AFD automata;
    AFND automataND;
    boolean puedoEjecutar = false;

    JFileChooser fileChooser;
    JPanel top;
    JPanel bottom;
    JPanel middle;
    JTextPane middleIzq;
    JTextPane middleDer;
    JScrollPane middleIzqContainer;
    JScrollPane middleDerContainer;
    JTextField input;
    JComboBox<String> opciones;
    JButton bSelec;
    JButton bEjecutar;

    final String[] opc = {"Autómata Finito Determinista",
            "Autómata Finito No Determinista"};;

    /**
     *  Se encarga de llamar a los dos métodos que contruyen la interfaz.
     */
    public Automata() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Práctica de Autómatas - AyMC");
        setSize(600, 600);
        setLocationRelativeTo(null);

        initComponents();
        addListeners();
    }

    /**
     *  Se encarga de añadir las funciones que se ejecutarán cuando ocurran eventos en la interfaz.
     */
    private void addListeners() {
        String userDirLocation = System.getProperty("user.dir");
        final File userDir = new File(userDirLocation);

        bSelec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileChooser = new JFileChooser(userDir);
                int returVal = fileChooser.showOpenDialog(null);

                if(returVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String opcion = (String) opciones.getSelectedItem();
                    if(opcion.equals(opc[0])) {
                        try {
                            automata = AFD.contenido(file);
                            middleIzq.setText(automata.toString());
                            puedoEjecutar = true;
                        } catch (Exception ex) {
                            middleIzq.setText("No ha sido posible abrir el fichero");
                        }
                    } else {
                        try {
                            automataND = AFND.cargarArchivo(file);
                            middleIzq.setText(automataND.toString());
                            puedoEjecutar = true;
                        } catch (Exception ex) {
                            middleIzq.setText("No ha sido posible abrir el fichero");
                        }
                    }
                    middleDer.setText("");
                }
            }
        });

        bEjecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(puedoEjecutar) {
                    String texto = input.getText();
                    String opcion = (String) opciones.getSelectedItem();
                    boolean resultado;
                    if(opcion.equals(opc[0])) {
                        resultado = automata.reconocer(texto);

                    } else {
                        resultado = automataND.reconocer(texto);
                    }
                    texto = "Cadena de entrada: " + texto + "\n";
                    if(resultado) {
                        texto = texto + "El autómata reconoció la cadena\n\n";
                    } else {
                        texto = texto + "El autómata no reconoció la cadena\n\n";
                    }
                    texto = middleDer.getText() + texto;
                    middleDer.setText(texto);
                } else {
                    middleDer.setText("Antes de ejecutar tienes que cargar un autómata");
                }
            }
        });

        input.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
        input.getActionMap().put("check", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                bEjecutar.doClick();
            }
        });
    }

    /**
     *  Se encarga de inicializar todos los componentes
     */
    private void initComponents() {
        automata = new AFD();
        top = new JPanel(new BorderLayout());
        middle = new JPanel(new GridLayout());
        bottom = new JPanel(new BorderLayout());
        middleIzq = new JTextPane();
        middleDer = new JTextPane();
        middleIzqContainer = new JScrollPane(middleIzq);
        middleDerContainer = new JScrollPane(middleDer);
        input = new JTextField();
        opciones = new JComboBox<String>(opc);
        bSelec = new JButton("Seleccionar");
        bEjecutar = new JButton("Ejecutar");

        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet atrSet = sc.addAttribute(
                SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground,
                Color.WHITE);
        middleIzq.setCharacterAttributes(atrSet, false);
        middleDer.setCharacterAttributes(atrSet, false);

        middleIzq.setBackground(Color.DARK_GRAY);
        middleDer.setBackground(Color.DARK_GRAY);

        middle.add(middleIzqContainer);
        middle.add(middleDerContainer);

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
