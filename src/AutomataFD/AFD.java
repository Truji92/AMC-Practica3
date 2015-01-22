/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomataFD;

import practica3.Proceso;

import java.io.*;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        transiciones = new HashSet<TransicionAFD>();
        estadosFinales = new HashSet<Integer>();
    }

    /**
     * Constructor de copia. Crea un automata a partir de otro.
     *
     * @param otro Automata modelo.
     */
    public AFD(AFD otro) {
        transiciones = new HashSet<TransicionAFD>();
        estadosFinales = new HashSet<Integer>();
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
        char[] simbolos = cadena.toCharArray();
        int estado = 0; //El estado inicial es el 0
        for (char simbolo : simbolos) {
            estado = transicion(estado, simbolo);
        }
        return esFinal(estado);
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
        String resultado = "";
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

    /**
     * Crea un objeto AFD a partir de la inforación almacenada en un archivo con formato:
     *      origen;simbolo;destino
     *      origen;simbolo;destino
     *      ...
     *      #!
     *      estadoFinal
     *      estadoFinal
     *      ...
     * @param archivo Objeto File en el que se encuentran los datos.
     * @return un automata creado mediante los datos recogidos
     */
    public static AFD contenido(File archivo) {
        String contenido;
        boolean fi=false;
        String[] partes;
        AFD automata=new AFD();
        try {
            FileReader f = new FileReader(archivo);
            BufferedReader b = new BufferedReader(f);
            while((contenido = b.readLine())!=null)
            {
                partes=contenido.split(";");
                if(partes[0].compareTo("#!")==0)
                    fi=true;

                if(fi)
                {
                    if(partes[0].compareTo("#!")!=0&&partes[0].compareTo("")!=0)
                        automata.estadosFinales.add(Integer.parseInt(partes[0]));
                }

                else
                {
                    automata.agregarTransicion(Integer.parseInt(partes[0]),
                            partes[1].toCharArray()[0], Integer.parseInt(partes[2]));
                }
            }
            b.close();
        } catch (FileNotFoundException ex){

        } catch (IOException ex) {

        }
        return automata;
    }

}
