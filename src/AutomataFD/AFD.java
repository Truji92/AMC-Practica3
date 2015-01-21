/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomataFD;

import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import practica3.Proceso;

/**
 *
 * @author alejandro
 */
public class AFD implements Cloneable, Proceso {

    private HashSet<Integer> estadosFinales;
    private HashSet<TransicionAFD> transiciones;

    /**
     * Constructor por defecto, Inicializa un automata sin reglas ni estados.
     */
    public AFD() {
        transiciones = new HashSet<>();
        estadosFinales = new HashSet<>();
    }

    /**
     * Constructor de copia. Crea un automata a partir de otro.
     *
     * @param otro Automata modelo.
     */
    public AFD(AFD otro) {
        transiciones = new HashSet<>();
        estadosFinales = new HashSet<>();
        for (TransicionAFD transicion : otro.transiciones) {
            try {
                transiciones.add((TransicionAFD) transicion.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(AFD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Integer estado : otro.estadosFinales) {
            estadosFinales.add(estado);
        }
    }

    /**
     * Agrega una transición al automata.
     *
     * @param estadoOrigen Estado inical del autómata en la transición.
     * @param simbolo Simbolo de la transición.
     * @param estadoDestino Estado final del autómata tras la transición.
     */
    public void agregarTransicion(int estadoOrigen, char simbolo,
            int estadoDestino) {
        transiciones.add(new TransicionAFD(estadoOrigen, simbolo, estadoDestino));
    }

    /**
     * Busca la transición del autómata para el estado y el simbolo dados.
     *
     * @param estado Estado actual del autómata.
     * @param simbolo Simbolo de transición.
     * @return Estadp del automata tras la transición o -1 si no existe la 
     * la misma.
     */
    public int transicion(int estado, char simbolo) {
        int estadoDestino = -1;
        for (TransicionAFD transicion : transiciones) {
            if (transicion.getOrigen() == estado
                    && transicion.getSimbolo() == simbolo) {
                estadoDestino = transicion.getDestino();
                break;
            }
        }
        return estadoDestino;
    }

    @Override
    public boolean esFinal(int estado) {
        return estadosFinales.contains(estado);
    }

    @Override
    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        int estado = 0; //El estado inicial es el 0
        for (int i = 0; i < simbolo.length; i++) {
            estado = transicion(estado, simbolo[i]);
        }
        return esFinal(estado);
    }

    /**
     * Solicita los datos de un autómata por teclado.
     * @return El objeto autómata AFD creado con la información recogida.
     */
    public static AFD pedir() {
        AFD automata = new AFD();
        // TODO pedir por teclado
        return automata;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new AFD(this);
    }

    /**
     * Transforma el automata en String con formato:
     * 
     * "transiciones:
     *          (origen1, simbolo1) -> destino1
     *          (origen2, simbolo2) -> destino2
     *          ...
     * estadosFinales:
     *          estado1
     *          estado2
     *          ...
     *           
     * @return String formateado con la información.
     */
    @Override
    public String toString() {
        String resultado = new String();
        resultado += "transiciones: \n";
        for (TransicionAFD transicion : transiciones) {
            resultado += "\t"+transicion.toString()+ "\n";
        }
        resultado +=  "estadosFinales: \n";
        for (int estado : estadosFinales) {
            resultado += "\t"+estado +" \n";
        }
        return resultado;
    }
    
    
    public static void main(String[] args) {
        AFD automata = new AFD();
        automata.agregarTransicion(0, 'A', 1);
        automata.agregarTransicion(1, 'B', 2);
        automata.agregarTransicion(2, 'C', 3);
        automata.estadosFinales.add(2);
        automata.estadosFinales.add(3);
        
        System.out.println(automata);
        
        System.out.println(automata.reconocer("ABB"));
    }
}
