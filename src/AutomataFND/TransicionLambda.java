/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomataFND;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author alejandro
 */
public class TransicionLambda {

    private final int estadoOrigen;
    private final Set<Integer> estadosDestino;

    /**
     * Constructor que crea una transición con los atributos pasados por
     * parametro.
     *
     * @param estadoOrigen Estado inicial de la transición.
     * @param estadosDestino Conjunto de estados destino a los que el autómata
     * puede ir desde el estado inicial mediante una transición lambda.
     */
    public TransicionLambda(int estadoOrigen, int[] estadosDestino) {
        this.estadoOrigen = estadoOrigen;
        this.estadosDestino = new HashSet<Integer>();
        for (int estado : estadosDestino) {
            this.estadosDestino.add(estado);
        }
    }

    /**
     *
     * @return Estado origen de la transición.
     */
    public int getEstadoOrigen() {  
        return estadoOrigen;
    }

    /**
     *
     * @return Conjunto de estados finales mediante una transición lambda.
     */
    public Set<Integer> getEstadosDestino() {
        return estadosDestino;
    }

    /**
     * Compara dos objetos TransicionLambda atendiendo a si representan el conjunto de transiciones lambda
     * para un mismo estado inicial. Si estadoInicial coincide, devolverá true
     * aunque el conjuntos de estados destino sea diferente.
     * @param obj objeto a comparar
     * @return True si obj representa la misma Transición lambda
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof TransicionLambda) {
            TransicionLambda ob = (TransicionLambda) obj;
            return this.estadoOrigen == ob.estadoOrigen;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.estadoOrigen;
        return hash;
    }

    @Override
    public String toString() {
        String resultado = "";
        resultado += "(" + estadoOrigen + ", " + 'λ' + ") -> ";
        for (int destino : estadosDestino) {
            resultado += destino + " ";
        }
        return resultado;
    }

    public static void main(String[] args) {
        int[] destinos = {1,2,3,4,5,6,7,8777,77};
        TransicionLambda t = new TransicionLambda(1, destinos);
        
        System.out.println(t);
    }
 
            
}
