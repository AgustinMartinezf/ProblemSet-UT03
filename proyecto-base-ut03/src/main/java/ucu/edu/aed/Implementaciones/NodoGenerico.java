package main.java.ucu.edu.aed.Implementaciones;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import ucu.edu.aed.impl.ListaEnlazada;
import ucu.edu.aed.tda.generic_trie.TNodoGenerico;

public class NodoGenerico<T extends Comparable<T>> implements TNodoGenerico<T> {

    private T dato;
    protected NodoGenerico<T> primerHijo;
    protected NodoGenerico<T> hermanoDerecho;

    //constructor
    public NodoGenerico(T dato) {
        this.dato = dato;
        this.primerHijo = null;
        this.hermanoDerecho = null;
    }

    @Override
    public boolean agregarHijo(T padre, T hijo) {
        NodoGenerico<T> nodoPadre =this.buscar(padre);
        if (nodoPadre == null) { // si no hay padre no podemos agregar hijo
            return false;
        }

        NodoGenerico<T> nuevoHijo =new NodoGenerico<>(hijo);

        if (nodoPadre.primerHijo == null) { //si no tiene hijos es el primero
            nodoPadre.primerHijo = nuevoHijo;
            return true;
        }

        NodoGenerico<T> ultimoHijo =nodoPadre.primerHijo;

        while (ultimoHijo.hermanoDerecho!= null) { //recorro los hijos hasta el ultimo
            ultimoHijo =ultimoHijo.hermanoDerecho;
        }
        ultimoHijo.hermanoDerecho =nuevoHijo; //agrego el nuevo hijo al final de los hermanos
        return true;
    }

    @Override
    public int altura() {
        if (primerHijo == null) { //si no tiene hijos la altura es 0
            return 0;
        }
        int max = 0;
        NodoGenerico<T> hijo =primerHijo;
        while (hijo != null) {
            max = Math.max(max,hijo.altura());
            hijo = hijo.hermanoDerecho;
        }
        return max + 1;
    }

    @Override
    public TNodoGenerico<T> buscar(Comparable<T> criterio) {
        if (criterio.compareTo(dato) == 0) { // si es este, lo devuelvo
            return this;
        }
        NodoGenerico<T> hijo =primerHijo;
        while (hijo != null) { // recorro los hijos
            TNodoGenerico<T> encontrado =hijo.buscar(criterio);
            if (encontrado != null) {
                return encontrado; // si encuentro devuelvo
            }
            hijo = hijo.hermanoDerecho;
        }
        return null;
    }

    @Override
    public TNodoGenerico<T> eliminar(Comparable<T> criterio) {
        
        if (primerHijo == null) { // si no tiene hijos no elimino nada
            return null;
        }

        if (criterio.compareTo(primerHijo.getDato()) == 0) { // si es el primer hijo

            TNodoGenerico<T> eliminado =primerHijo;
            primerHijo =primerHijo.hermanoDerecho;
            eliminado.vaciar(); //desconecto
            return eliminado;
        }

        
        NodoGenerico<T> hijo =primerHijo;
        while (hijo.hermanoDerecho!= null) { //recorro los hermanos

            if (criterio.compareTo(hijo.hermanoDerecho.getDato()) == 0) {

                TNodoGenerico<T> eliminado =hijo.hermanoDerecho;
                hijo.hermanoDerecho =hijo.hermanoDerecho.hermanoDerecho;
                eliminado.vaciar(); //desconecto
                return eliminado;
            }
            hijo = hijo.hermanoDerecho;
        }

        hijo = primerHijo;
        while (hijo != null) { // recorro los hijos
            TNodoGenerico<T> encontrado =hijo.eliminar(criterio);
            if (encontrado != null) {
                return encontrado;
            }
            hijo = hijo.hermanoDerecho;
        }

        return null;
    }

    @Override
    public T getDato() {
        return this.dato;
    }

    @Override
    public int grado() {
        int grado = 0;
        NodoGenerico<T> hijo = primerHijo;
        while (hijo != null) {
            grado++;
            hijo = hijo.hermanoDerecho;
        }
        return grado;
    }

    @Override
    public void inOrden(Consumer<TNodoGenerico<T>> consumidor) {
         if (primerHijo != null) {
            primerHijo.inOrden(consumidor); // recorro el primer hijo primero
            consumidor.accept(this); // dsp proceso el nodo actual
            NodoGenerico<T> hermano =primerHijo.hermanoDerecho;
            while (hermano != null) {
                hermano.inOrden(consumidor); // recorro los hermanos
                hermano =hermano.hermanoDerecho;
            }
        } else {
            consumidor.accept(this); // si no tiene hijos proceso el nodo actual
        }
    }

    @Override
    public List<T> obtenerHijos() {
        List<T> hijos =new LinkedList<>(); // usamos el de java por la firma del método
        NodoGenerico<T> hijo =primerHijo;
        while (hijo != null) {
            hijos.add(hijo.getDato());
            hijo = hijo.hermanoDerecho;
        }
        return hijos;
    }

    @Override
    public TNodoGenerico<T> obtenerPadre(Comparable<T> criterio) {
        NodoGenerico<T> hijo =primerHijo;

        while (hijo != null) { // recorro los hijos
            if (criterio.compareTo(hijo.getDato()) == 0) { // si es este, lo devuelvo
                return this;
            }
            TNodoGenerico<T> encontrado =hijo.obtenerPadre(criterio);
            if (encontrado != null) { // si encuentro devuelvo
                return encontrado;
            }
            hijo = hijo.hermanoDerecho;
        }
        return null;
    }

    @Override
    public void postOrden(Consumer<TNodoGenerico<T>> consumidor) {
        
        NodoGenerico<T> hijo = primerHijo;
        while (hijo != null) {
            hijo.postOrden(consumidor); // recorro los hijos primero
            hijo = hijo.hermanoDerecho;
        }
        consumidor.accept(this); // dsp proceso el nodo actual
        
    }

    @Override
    public void preOrden(Consumer<TNodoGenerico<T>> consumidor) {
        consumidor.accept(this); // proceso el nodo actual primero
        NodoGenerico<T> hijo =primerHijo;
        while (hijo != null) {
            hijo.preOrden(consumidor); // recorro los hijos
            hijo = hijo.hermanoDerecho;
        }
    }

    @Override
    public void vaciar() {
        primerHijo = null;
        hermanoDerecho = null;
    }


    //extras usados en el ejercicio 16

    // cuenta la cantidad de nodos que hay en el subarbol del nodo actual
    public int contarNodos() {
        int total = 1; //cuento el nodo actual
        NodoGenerico<T> hijo =primerHijo;
        while (hijo != null) { // recorro los hijos y sumo sus nodos
            total += hijo.contarNodos();
            hijo = hijo.hermanoDerecho;
        }
        return total;
    }
    
    //crea una lista con los nodos que forman el camino desde el nodo actual hasta el nodo buscado, devuelve true si lo encuentra
    public boolean obtenerCamino(Comparable<T> buscado,List<NodoGenerico<T>> camino) {

        camino.add(this); //agrego el nodo actual al camino

        if (buscado.compareTo(dato) == 0) { //si es este lo devuelvo
            return true;
        }
        NodoGenerico<T> hijo =primerHijo;

        while (hijo != null) { //recorro los hijos
            if (hijo.obtenerCamino(buscado,camino)) {
                return true;
            }
            hijo = hijo.hermanoDerecho;
        }
        camino.remove(camino.size() - 1); //si no lo encontre en este nodo ni en sus hijos, lo saco del camino
        return false; //no lo encontre
    } 

    //crea una lista de los nodos que estan en el nivel objetivo, actual es el nivel del nodo actual
    public void obtenerNivel(int objetivo,int actual,List<T> lista) {

        if (actual == objetivo) {
            lista.add(dato);
            return;
        }
        NodoGenerico<T> hijo =primerHijo;
        while (hijo != null) {
            hijo.obtenerNivel(objetivo,actual + 1,lista);
            hijo = hijo.hermanoDerecho;
        }
    }

    
}
