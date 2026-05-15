package main.java.ucu.edu.aed.Implementaciones;

import java.util.List;
import java.util.function.Consumer;

import ucu.edu.aed.Implementaciones.ListaEnlazada;
import ucu.edu.aed.tda.generic_trie.TNodoGenerico;

public class NodoGenerico<T extends Comparable<T>> implements TNodoGenerico<T> {

    private T dato;
    private TNodoGenerico<T> primerHijo;
    private TNodoGenerico<T> hermanoDerecho;

    //ponemos el contructor
    public NodoGenerico(T dato) {
        this.dato = dato;
        this.primerHijo = null;
        this.hermanoDerecho = null;
    }

    @Override
    public boolean agregarHijo(T padre, T hijo) {
        NodoGenerico<T> nodoPadre = this.buscar(padre);
        if (nodoPadre == null) {
            return false; // no encontramos el padre, no podemos entonces agregar un hijo
        }
        NodoGenerico<T> nuevoHijo = new NodoGenerico<>(hijo);
        if (nodoPadre.primerHijo == null) {
            nodoPadre.primerHijo = nuevoHijo; // si no tiene hijos, el nuevo hijo es el primer hijo
            return true;
        } else {
            NodoGenerico<T> ultimoHijo = nodoPadre.primerHijo;
            while (ultimoHijo.hermanoDerecho != null) {
                ultimoHijo = ultimoHijo.hermanoDerecho; // recorremos los hermanos hasta el último
            }
            ultimoHijo.hermanoDerecho = nuevoHijo; // agregamos el nuevo hijo al final de los hijos
            return true;
        }
    }

    @Override
    public int altura() {
        if (primerHijo == null) {
            return 0; // si no tiene hijos, la altura es 0
        }
        int altura = 0;
        NodoGenerico<T> hijo = primerHijo;
        while (hijo != null) {
            altura = Math.max(altura, hijo.altura()); // calculamos la altura de cada hijo y nos quedamos con la máxima
            hijo = hijo.hermanoDerecho; // recorremos los hermanos
        }
        return altura + 1; // sumamos 1 para contar el nivel de este nodo
    }

    @Override
    public TNodoGenerico<T> buscar(Comparable<T> criterio) {
        if (criterio.compareTo(this.dato) == 0) { //si es este lo devuelvo
            return this;
        }
        NodoGenerico<T> hijo = primerHijo;
        while (hijo != null) { //mientras tenga hijos busco en cada uno
            TNodoGenerico<T> encontrado = hijo.buscar(criterio);
            if (encontrado != null) { 
                return encontrado;
            }
            hijo =hijo.hermanoDerecho; //recorro los hermanos
        }
        return null;
    }

    @Override
    public TNodoGenerico<T> eliminar(Comparable<T> criterio) {
        if (primerHijo == null) {
            return null; // si no tiene hijos, no hay nada que eliminar
        }
        if (criterio.compareTo(primerHijo.getDato()) == 0) { //si es el primer hijo
            TNodoGenerico<T> eliminado = primerHijo;
            primerHijo = primerHijo.hermanoDerecho; //el nuevo primer hijo es el hermano derecho del eliminado
            eliminado.hermanoDerecho = null; // lo desconectamos
            return eliminado;
        }
        NodoGenerico<T> hijo = primerHijo;
        while (hijo.hermanoDerecho != null) { //mientras tenga hermanos busco en cada uno
            if (criterio.compareTo(hijo.hermanoDerecho.getDato()) == 0) { //si es el hermano derecho
                TNodoGenerico<T> eliminado = hijo.hermanoDerecho;
                hijo.hermanoDerecho = hijo.hermanoDerecho.hermanoDerecho; //el nuevo hermano derecho es el hermano derecho del eliminado
                eliminado.hermanoDerecho = null; // lo desconectamos
                return eliminado;
            }
            hijo = hijo.hermanoDerecho; //recorro los hermanos
        }
        hijo = primerHijo;
        while (hijo != null) { //mientras tenga hijos busco en cada uno
            TNodoGenerico<T> encontrado = hijo.eliminar(criterio);
            if (encontrado != null) {
                return encontrado;
            }
            hijo = hijo.hermanoDerecho; //recorro los hermanos
        }
        return null; // si no encontramos el nodo a eliminar, devolvemos null
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
            hijo = hijo.hermanoDerecho; //recorro los hermanos para contar cuantos hijos tiene
        }
        return grado;
    }

    @Override
    public void inOrden(Consumer<TNodoGenerico<T>> consumidor) {
        if (primerHijo != null) {
            primerHijo.inOrden(consumidor); //primero procesamos el primer hijo
            consumidor.accept(this); //despues procesamos este
            NodoGenerico<T> hermano = primerHijo.hermanoDerecho;
            while (hermano != null) {
                hermano.inOrden(consumidor); //despues procesamos los hermanos
                hermano = hermano.hermanoDerecho; //recorremos los hermanos
            }
        }
        else {
            consumidor.accept(this); //si no tiene hijos, procesamos este
        }
    }

    @Override
    public List<T> obtenerHijos() {
        List<T> hijos = new ListaEnlazada<>();
        NodoGenerico<T> hijo = primerHijo;
        while (hijo != null) {
            hijos.add(hijo.getDato()); //agrego el dato del hijo a la lista
            hijo = hijo.hermanoDerecho; //recorro los hermanos 
        }
        return hijos;
    }

    @Override
    public TNodoGenerico<T> obtenerPadre(Comparable<T> criterio) {
        NodoGenerico<T> hijo= primerHijo;
        while (hijo != null) {
            if (criterio.compareTo(hijo.getDato()) == 0) { //si el hijo es el que buscamos, devolvemos este nodo como padre
                return this;
            }
            TNodoGenerico<T> encontrado = hijo.obtenerPadre(criterio); //buscamos en los hijos
            if (encontrado != null) {
                return encontrado; //si lo encontramos, lo devolvemos
            }
            hijo = hijo.hermanoDerecho; //recorremos los hermanos
        }
        return null; //si no lo encontramos, devolvemos null
    }

    @Override
    public void postOrden(Consumer<TNodoGenerico<T>> consumidor) {
        NodoGenerico<T> hijo = primerHijo;
        while (hijo != null) {
            hijo.postOrden(consumidor); //primero procesamos este
            hijo = hijo.hermanoDerecho; //recorremos los hermanos
        }
        consumidor.accept(this); //dsp procesamos este
    }

    @Override
    public void preOrden(Consumer<TNodoGenerico<T>> consumidor) {
        consumidor.accept(this); //primero procesamos este
        NodoGenerico<T> hijo = primerHijo;
        while (hijo != null) {
            hijo.preOrden(consumidor); //dsp procesamos los hijos
            hijo = hijo.hermanoDerecho; //recorremos los hermanos
        }
    }

    @Override
    public void vaciar() { //lo desconecto
        primerHijo = null;
        hermanoDerecho = null;
    }
    
}
