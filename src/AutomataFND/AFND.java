/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomataFND;

import practica3.Proceso;

import java.util.HashSet;
import java.util.Set;
import java.io.*;

/**
 * @author alejandro
 */
public class AFND implements Cloneable, Proceso {

    private Set<Integer> estadosFinales;
    private Set<TransicionAFND> transiciones;
    private Set<TransicionLambda> transicionesLambda;

    /**
     * Constructor por defecto, crea un autómata vacío.
     */
    public AFND() {
        estadosFinales = new HashSet<Integer>();
        transiciones = new HashSet<TransicionAFND>();
        transicionesLambda = new HashSet<TransicionLambda>();
    }

    /**
     * @param estadoOrigen
     * @param simbolo
     * @param estadosFinales
     */
    public void agregarTransicion(int estadoOrigen, char simbolo, int[] estadosFinales) {
        transiciones.add(new TransicionAFND(estadoOrigen, simbolo, estadosFinales));
    }

    public void agregarTransicionLambda(int estadoOrigen, int[] estadosFinales) {
        transicionesLambda.add(new TransicionLambda(estadoOrigen, estadosFinales));
    }

    private Set<Integer> transicion(int estado, char simbolo) {
        Set<Integer> macroestado = new HashSet<Integer>();
        for (TransicionAFND transicion : transiciones) {
            if (transicion.getEstadoOrigen() == estado && transicion.getSimbolo() == simbolo) {
                macroestado.addAll(transicion.getEstadosDestino());
                break;
            }
        }
        macroestado.addAll(lambda_clausura(macroestado));
        return macroestado;
    }

    public Set<Integer> transicion(Set<Integer> macroestado, char simbolo) {
        Set<Integer> macroestadoDestino = new HashSet<Integer>();

        for (int estado : macroestado) {
            macroestadoDestino.addAll(transicion(estado, simbolo));
        }
        return macroestadoDestino;
    }

    public Set<Integer> transicionLambda(int estado) {
        Set<Integer> macroestado = new HashSet<Integer>();
        for (TransicionLambda transicion : transicionesLambda)
            if (transicion.getEstadoOrigen() == estado) {
                macroestado.addAll(transicion.getEstadosDestino());
                break;
            }
        return macroestado;
    }

    @Override
    public boolean esFinal(int estado) {
        for (int estadoFinal : estadosFinales)
            if (estado == estadoFinal)
                return true;
        return false;
    }

    public boolean esFinal(Set<Integer> macroestado) {
        for (int estado : macroestado)
            if (esFinal(estado))
                return true;
        return false;
    }

    private Set<Integer> lambda_clausura(int estado) {
        Set<Integer> est = new HashSet<Integer>();
        est.add(estado);
        return lambda_clausura(est);
    }

    private Set<Integer> lambda_clausura(Set<Integer> macroestado) {
        return lambda_clausura(macroestado, true);
    }

    private Set<Integer> lambda_clausura(Set<Integer> macroestado, boolean primera_llamada) {
        Set<Integer> clausura = new HashSet<Integer>();
        if (!primera_llamada)
            clausura.addAll(macroestado);

        for (int estado : macroestado)
            for (TransicionLambda transicion : transicionesLambda)
                if (transicion.getEstadoOrigen() == estado)
                    if (clausura.addAll(transicion.getEstadosDestino()))
                        clausura.addAll(lambda_clausura(clausura, false));

        return clausura;

    }

    @Override
    public boolean reconocer(String cadena) {
        char[] simbolos = cadena.toCharArray();
        Set<Integer> macroestado = new HashSet<Integer>();
        macroestado.add(0);
        macroestado.addAll(lambda_clausura(0));
        for (char simbolo : simbolos)
            macroestado = transicion(macroestado, simbolo);
        return esFinal(macroestado);
    }

    public static AFND pedir() {
        return null;

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        AFND automata = new AFND();

        automata.estadosFinales = new HashSet<Integer>();
        for (int estadofinal : estadosFinales)
            automata.estadosFinales.add(estadofinal);


        automata.transiciones = new HashSet<TransicionAFND>();
        for (TransicionAFND transicion : transiciones)
            automata.transiciones.add((TransicionAFND) transicion.clone());

        automata.transicionesLambda = new HashSet<TransicionLambda>();
        for (TransicionLambda transicion : transicionesLambda)
            automata.transicionesLambda.add((TransicionLambda) transicion.clone());

        return automata;
    }


    public static AFND cargarArchivo(File archivo) throws FileNotFoundException, IOException {
        String contenido;
        String[] partes, partes2;
        int[] estf;
        boolean finales = false;
        boolean lambdas = false;
        AFND automata = new AFND();
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while ((contenido = b.readLine()) != null) {
            partes = contenido.split(";");
            if (partes[0].compareTo("#!") == 0)
                lambdas = true;

            if (partes[0].compareTo("#!!") == 0)
                finales = true;

            if (lambdas && !finales) {
                if (partes[0].compareTo("#!") != 0 && partes[0].compareTo("") != 0) {
                    partes2 = partes[1].split(",");
                    estf = new int[partes2.length];
                    for (int i = 0; i < partes2.length; i++)
                        estf[i] = Integer.parseInt(partes2[i]);

                    automata.agregarTransicionLambda(Integer.parseInt(partes[0]), estf);
                }
            } else if (finales) {
                if (partes[0].compareTo("#!!") != 0 && partes[0].compareTo("") != 0)
                    automata.estadosFinales.add(Integer.parseInt(partes[0]));
            } else {

                partes2 = partes[2].split(",");
                estf = new int[partes2.length];
                for (int i = 0; i < partes2.length; i++)
                    estf[i] = Integer.parseInt(partes2[i]);

                automata.agregarTransicion(Integer.parseInt(partes[0]), partes[1].toCharArray()[0], estf);
            }
        }
        b.close();
        return automata;
    }

    @Override
    public String toString() {
        String resultado = "";
        resultado += "Transiciones: \n";
        for (TransicionAFND transicion : transiciones) {
            resultado += "\t"+transicion.toString()+ "\n";
        }
        resultado += "Trasicones Lambda: \n";
        for (TransicionLambda transicion : transicionesLambda)
            resultado += "\t"+transicion.toString()+"\n";
        resultado +=  "estadosFinales: \n";
        for (int estado : estadosFinales) {
            resultado += "\t"+estado +" \n";
        }
        return resultado;
    }

    public static void main(String[] args) {
        AFND automata = new AFND();
        automata.estadosFinales.add(3);
        /*
        automata.agregarTransicion(0, '0', new int[]{0, 1});
        automata.agregarTransicion(0, '1', new int[]{0});
        automata.agregarTransicion(1, '0', new int[]{2});
        automata.agregarTransicion(1, '1', new int[]{2});
        automata.agregarTransicion(2, '0', new int[]{3});
        automata.agregarTransicion(2, '1', new int[]{3});*/

        String cadena = "001011011";
        System.out.println(cadena);
        System.out.println(automata.reconocer(cadena));

        AFND automata2 = new AFND();
        automata2.estadosFinales.add(2);
        automata2.estadosFinales.add(4);
        automata2.agregarTransicion(1, 'a', new int[]{2});
        automata2.agregarTransicion(2, 'a', new int[]{2});
        automata2.agregarTransicion(3, 'b', new int[]{4});
        automata2.agregarTransicion(4, 'b', new int[]{4});
        automata2.agregarTransicionLambda(0, new int[]{1, 3});

        cadena = "bbbb";
        System.out.println(cadena);
        System.out.println(automata2.reconocer(cadena));
    }

}
