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
     * Agega al autómata una transición.
     *
     * @param estadoOrigen   Estado origen de la transición.
     * @param simbolo        Simbolo de la transición
     * @param estadosFinales Estados finales a los que puede ir el autómata desde el estado origen utilizando el
     *                       simbolo de esta transición.
     */
    public void agregarTransicion(int estadoOrigen, char simbolo, int[] estadosFinales) {
        transiciones.add(new TransicionAFND(estadoOrigen, simbolo, estadosFinales));
    }

    /**
     * Agrega una transición lambda al autómata
     *
     * @param estadoOrigen   Estado origen de la transición
     * @param estadosFinales Conjunto de estados finales a los que puede ir el autómata mediante una transición
     *                       lambda desde el estado origen.
     */
    public void agregarTransicionLambda(int estadoOrigen, int[] estadosFinales) {
        transicionesLambda.add(new TransicionLambda(estadoOrigen, estadosFinales));
    }

    /**
     * Realiza una transición (si existe) para un estdo y un simbolo dados.
     *
     * @param estado  Estado origen
     * @param simbolo Simbolo
     * @return Conjunto de estados a los que el autómata puede ir tras la transición (incluidas transiciones lambda).
     */
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

    /**
     * Realiza todas las transiones posibles a partir de un macroestado y un simbolo.
     *
     * @param macroestado Macroestado inicial.
     * @param simbolo     símbolo.
     * @return Conjunto de estados a los que el autómata puede ir desde el macroestado incial (incluidas transiciones
     * lambda).
     */
    public Set<Integer> transicion(Set<Integer> macroestado, char simbolo) {
        Set<Integer> macroestadoDestino = new HashSet<Integer>();

        for (int estado : macroestado) {
            macroestadoDestino.addAll(transicion(estado, simbolo));
        }
        return macroestadoDestino;
    }

    /**
     * Realiza las transiciones lambda posibles a partir de un estado.
     *
     * @param estado Estado origen.
     * @return Conjunto de estados a los que el autómnata puede ir desde el estado inicial mediante unicamente transiciones
     * lambda
     */
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

    /**
     * Comprueba si algun estado de un macroestado es final, y por tanto lo es el macroestado.
     *
     * @param macroestado Macroestado que se desea analizar.
     * @return {@code true} si hay al menos un estado final en el macroestado, {@code false} en caso contrario.
     */
    public boolean esFinal(Set<Integer> macroestado) {
        for (int estado : macroestado)
            if (esFinal(estado))
                return true;
        return false;
    }

    /**
     * Calcula la lambda-clausura de un estado.
     *
     * @param estado Estado inicial.
     * @return lambda-calusura del estado inicial.
     */
    private Set<Integer> lambda_clausura(int estado) {
        Set<Integer> est = new HashSet<Integer>();
        est.add(estado);
        return lambda_clausura(est);
    }

    /**
     * Calcula la lambda-clausura de un macroestado
     *
     * @param macroestado Macroestado inicial.
     * @return lamda-clausura del macroestado.
     */
    private Set<Integer> lambda_clausura(Set<Integer> macroestado) {
        return lambda_clausura(macroestado, true);
    }

    /**
     * Método auxiliar para facilitar el cálculo de una lambda-clausura
     *
     * @param macroestado     Macroestado
     * @param primera_llamada Parametro de estado (necesario para llamadas recursivas)
     * @return lambda-clausura del macroestado.
     */
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

    /**
     * Crea un objeto AFND a partir de la inforación almacenada en un archivo con formato:
     * origen;simbolo;destino1,destino2,destino3,...
     * origen;simbolo;destino1,destino2,destino3,...
     * ...
     * #!
     * origen;destino1,destino2,destino3,...
     * origen;destino1,destino2,destino3,...
     * ...
     * #!!
     * estadoFinal
     * estadoFinal
     * ...
     *
     * @param archivo Objeto File en el que se encuentran los datos.
     * @return un automata creado mediante los datos cargados.
     * @throws IOException Si el formato del archivo no es adecuado o si hay algun error al acceder al archivo.
     */
    public static AFND cargarArchivo(File archivo) throws IOException {
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

    /**
     * Transforma el automata en String con formato:
     * <p/>
     * "transiciones:
     * (origen1, simbolo1) -> destino1 destino2 ...
     * (origen2, simbolo2) -> destino1 destino2 ...
     * ...
     * transicionesLambda:
     * (origen1, λ) -> destino1 destino2 ...
     * (origen2, λ) -> destino1 destino2 ...
     * estadosFinales:
     * estado1
     * estado2
     * ...
     *
     * @return String formateado con la información.
     */
    @Override
    public String toString() {
        String resultado = "";
        resultado += "Transiciones: \n";
        for (TransicionAFND transicion : transiciones) {
            resultado += "\t" + transicion.toString() + "\n";
        }
        resultado += "Trasicones Lambda: \n";
        for (TransicionLambda transicion : transicionesLambda)
            resultado += "\t" + transicion.toString() + "\n";
        resultado += "estadosFinales: \n";
        for (int estado : estadosFinales) {
            resultado += "\t" + estado + " \n";
        }
        return resultado;
    }
}