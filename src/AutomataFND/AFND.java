/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomataFND;

import practica3.Proceso;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author alejandro
 */
public class AFND implements Cloneable, Proceso {

    private Set<Integer> estadosFinales;
    private Set<TransicionAFND> transiciones;
    private Set<TransicionLambda> transicionesLambda;

    /**
     * Constructor por defecto, crea un autómata vacío.
     *
     */
    public AFND() {
        estadosFinales = new HashSet<Integer>();
        transiciones = new HashSet<TransicionAFND>();
        transicionesLambda = new HashSet<TransicionLambda>();
    }

    /**
     *
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

    private int[] transicion(int estado, char simbolo) {
        Set<Integer> macroestado = new HashSet<Integer>();
        for (TransicionAFND transicion : transiciones) {
            if (transicion.getEstadoOrigen() == estado && transicion.getSimbolo() == simbolo) {
                macroestado.addAll(transicion.getEstadosDestino());
                break;
            }

        }
        return macroestado;
        //TODO 
    }

    public Set<Integer> transicion(Set<Integer> macroestado, char simbolo) {
        return null;

    }

    public int[] transicionLambda(int estado) {
        return null;

    }

    @Override
    public boolean esFinal(int estado) {
        return false;

    }

    public boolean esFinal(Set<Integer> macroestado) {
        return false;

    }
    
    
    private Set<Integer> lambda_clausura(Set<Integer> macroestado, boolean primera_llamada) {
        Set<Integer> clausura = new HashSet<Integer>();
        if (!primera_llamada) 
            clausura.addAll(macroestado);
                    
        
        for (int estado : macroestado) {
            for(TransicionLambda transicion : transicionesLambda) {
                if (transicion.getEstadoOrigen() == estado) {
//                    System.out.println("Estado: " + estado + ", transicion: " + transicion.toString());
//                    System.out.println("Clausura: " + clausura);
                    if (clausura.addAll(transicion.getEstadosDestino())) {
//                        System.out.println("Añadir devuelve true");
//                        System.out.println("Clausura tras añadir: "+clausura);
                        clausura.addAll(lambda_clausura(clausura, false));
                    } 
//                    else System.out.println("Añadir devuelve falso");
                }
            }
        }
        
        return clausura;

    }

    @Override
    public boolean reconocer(String cadena) {
        char[] simbolos = cadena.toCharArray();
        Set<Integer> estado = new HashSet<Integer>();
        estado.add(0);//El estado inicial es el 0
        Set<Integer> macroestado = lambda_clausura(estado, true);
        for (char simbolo : simbolos) {
            macroestado = transicion(macroestado, simbolo);
        }
        return esFinal(macroestado);
    }

    public static AFND pedir() {
        return null;

    }

    public static void main(String[] args) {
        AFND automata = new AFND();
        int[] estadosFinales = {2, 4};
        automata.agregarTransicionLambda(0, estadosFinales);
        int[] estadosFinales2 = {7, 24};
        automata.agregarTransicionLambda(3, estadosFinales2);
        int[] estadosFinales3 = {8, 9, 3};
        automata.agregarTransicionLambda(24, estadosFinales3);

        Set<Integer> asdf = new HashSet<Integer>();
        asdf.add(0);
        asdf.add(24);
        System.out.println(automata.lambda_clausura(asdf,true));
    }

}
