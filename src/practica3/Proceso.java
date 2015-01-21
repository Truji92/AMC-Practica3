/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

/**
 *
 * @author alejandro
 */
public interface Proceso {

    /**
     * Comprueba si un estado es final.
     * 
     * @param estado Estado a comprobar.
     * @return True si es final, False en caso contrario.
     */
    public abstract boolean esFinal(int estado); //true si estado es un estado final

    /**
     * Analiza una cadena de texto pasada por parámetro.
     * 
     * @param cadena Cadeta de texto a reconocer por el automata
     * @return True si el automata acepta la cadena, False en caso contrario
     *
     */
    public abstract boolean reconocer(String cadena); //true si la cadena es reconocida

    @Override
    public abstract String toString(); //muestra las transiciones y estados finales
}
