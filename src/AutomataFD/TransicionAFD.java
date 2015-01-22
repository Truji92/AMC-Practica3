/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomataFD;

/**
 *
 * @author Alejandro Trujillo Caballero
 */
public class TransicionAFD implements Cloneable {

    private final int estadoOrigen;
    private final int estadoDestino;
    private final char simbolo;



    /**
     * Constructor que crea una transición con los atributos pasados por
     * parametro
     *
     * @param estadoOrigen Estado inicial de la transición
     * @param simbolo Simbolo de la transición
     * @param estadoDestino Estado final de la transición
     */
    public TransicionAFD(int estadoOrigen, char simbolo, int estadoDestino) {
        this.estadoDestino = estadoDestino;
        this.estadoOrigen = estadoOrigen;
        this.simbolo = simbolo;
    }

    /**
     *  
     * @return Estado inicial de la transición
     */
    public int getOrigen() {
        return estadoOrigen;
    }

    /**
     *
     * @return Estado final de la transición
     */
    public int getDestino() {
        return estadoDestino;
    }

    /**
     *
     * @return Simbolo de la transición
     */
    public char getSimbolo() {
        return simbolo;
    }

    /**
     * 
     * @param obj objeto a comparar
     * @return True si obj representa la misma Transicion
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof TransicionAFD) {
            TransicionAFD ob = (TransicionAFD) obj;
            return this.estadoOrigen == ob.estadoOrigen
                    && this.estadoDestino == ob.estadoDestino
                    && this.simbolo == ob.simbolo;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = this.estadoOrigen;
        hash = 100 * hash + this.estadoOrigen;
        hash = 100 * hash + this.estadoDestino;
        hash = 100 * hash + this.simbolo;
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Devuelve la transición en formato origen: origen, simbolo: simbolo, destino: destino
     * 
     * @return String con el contenido del objeto.
     */
    public String toStringLong() {
        return String.format("origen: %1$d, simbolo: %2$c, destino: %3$d", estadoOrigen, simbolo, estadoDestino);
    }

    /**
     * Devuelve la transición en formato (origen, simbolo) -> destino
     * 
     * @return String con el contenido del objeto.
     */
    @Override
    public String toString() {
        return String.format("(%1$d, %2$c) -> %3$d", estadoOrigen, simbolo, estadoDestino);

    }

}
