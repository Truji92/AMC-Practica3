/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomataFND;

import java.util.HashSet;
import java.util.Set;

/**
 * @author alejandro
 */
public class TransicionAFND {

    private final int estadoOrigen;
    private final char simbolo;
    private final Set<Integer> estadosDestino;

    /**
     * Constructor que crea una transición con los atributos pasados por
     * parametro.
     *
     * @param estadoOrigen   Estado inicial de la transición.
     * @param simbolo        Simbolo de la transición.
     * @param estadosDestino Conjunto de estados destino a los que el autómata
     *                       puede ir desde el estado inicial con el mismo simbolo.
     */
    public TransicionAFND(int estadoOrigen, char simbolo, int[] estadosDestino) {
        this.estadoOrigen = estadoOrigen;
        this.simbolo = simbolo;
        this.estadosDestino = new HashSet<Integer>();
        for (int estado : estadosDestino) {
            this.estadosDestino.add(estado);
        }
    }


    /**
     * @return Estado origen de la transición.
     */
    public int getEstadoOrigen() {
        return estadoOrigen;
    }

    /**
     * @return Simbolo de la transición.
     */
    public char getSimbolo() {
        return simbolo;
    }

    /**
     * @return Conjunto de estados destino de la transición.
     */
    public Set<Integer> getEstadosDestino() {
        return estadosDestino;
    }

    /**
     * Compara dos objetos {@this} atendiendo a si representan el conjunto de transiciones para un
     * mismo estado inicial y simbolo. Si estadoInicial y simbolo coinciden,
     * devolverá true aunque el conjuntos de estados destino sea diferente.
     *
     * @param obj objeto a comparar
     * @return True si obj representa la misma Transición
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof TransicionAFND) {
            TransicionAFND ob = (TransicionAFND) obj;
            return this.estadoOrigen == ob.estadoOrigen
                    && this.simbolo == ob.simbolo;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = this.estadoOrigen;
        hash = 100 * hash + this.estadoOrigen;
        hash = 100 * hash + this.simbolo;
        return hash;
    }

    @Override
    public String toString() {
        String resultado = "";
        resultado += "(" + estadoOrigen + ", " + simbolo + ") -> ";
        for (int destino : estadosDestino) {
            resultado += destino + " ";
        }
        return resultado;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        TransicionAFND transicion = new TransicionAFND(this.estadoOrigen, this.simbolo, new int[]{});

        for (int estado : estadosDestino)
            transicion.estadosDestino.add(estado);

        return transicion;
    }
}
