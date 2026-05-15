package ucu.edu.aed.tda.generic_trie.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import ucu.edu.aed.tda.generic_trie.TArbolGenerico;
import ucu.edu.aed.tda.generic_trie.TNodoGenerico;

public class ArbolGenerico<T extends Comparable<T>> implements TArbolGenerico<T>{

    private NodoGenerico<T> raiz;

    //constructor
    public ArbolGenerico(T datoRaiz) {
        this.raiz = new NodoGenerico<>(datoRaiz);
    }
    public NodoGenerico<T> getRaiz() {
        return raiz;
    }

    @Override
    public boolean agregarHijo(Comparable<T> padre, T hijo) {
        if (raiz == null) { //si el árbol está vacío no se puede agregar un hijo
            return false;
        }
        return raiz.agregarHijo((T) padre,hijo);
    }

    @Override
    public int altura(Comparable<T> nodo) {
        if (raiz == null) { //si no tiene raiz no tiene altura, devolvemos -1
            return -1;
        }
        TNodoGenerico<T> encontrado = raiz.buscar(nodo); //buscamos el nodo
        if (encontrado == null) { //si no encontramos devolvemos -1
            return -1;
        }
        return encontrado.altura(); //calculamos altura
    }

    @Override
    public T buscar(Comparable<T> criterio) {
        if (raiz == null) { //si el árbol está vacío no se puede buscar nada
            return null;
        }
        TNodoGenerico<T> encontrado =raiz.buscar(criterio); //buscamos el nodo
        if (encontrado == null) { //si no está devuelvo null
            return null;
        }

        return encontrado.getDato(); //si esta lo devuelvo
    }

    @Override
    public void eliminar(Comparable<T> criterio) {
        if (raiz == null) { //si el árbol está vacío no se puede eliminar nada
            return;
        }
        if (criterio.compareTo(raiz.getDato()) == 0) { //si quiero eliminar la raiz
            raiz = null;
            return;
        }
        raiz.eliminar(criterio); //lo elimino
    }

    @Override
    public int grado(Comparable<T> nodo) {
        if (raiz == null) { //si no tiene raíz devuelvo -1
            return -1;
        }
        TNodoGenerico<T> encontrado = raiz.buscar(nodo); //busco el nodo
        if (encontrado == null) { //si no lo encuentro devuelvo -1
            return -1;
        }
        return encontrado.grado(); //calculo el grado
    }

    @Override
    public void inOrden(Consumer<T> consumidor) {
        if (raiz == null) {
            return;
        }
        raiz.inOrden(nodo -> consumidor.accept(nodo.getDato()));
    }

    @Override
    public T obtenerPadre(Comparable<T> criterio) {
        if (raiz == null) { //si esta vacio denuevlo null
            return null;
        }

        TNodoGenerico<T> padre =raiz.obtenerPadre(criterio); //busco el padre

        if (padre == null) { //si no lo encontro devuelvo null
            return null;
        }

        return padre.getDato();
    }

    @Override
    public void postOrden(Consumer<T> consumidor) {
        if (raiz == null) { //si esta vacio
            return;
        }
        raiz.postOrden(nodo -> consumidor.accept(nodo.getDato()));
    }

    @Override
    public void preOrden(Consumer<T> consumidor) {
        if (raiz == null) {
            return;
        }
        raiz.preOrden(nodo -> consumidor.accept(nodo.getDato()));
    }

    @Override
    public void vaciar() {
        raiz=null;
    }

    //extras usados en el ejercicio 16

    // cuenta la cantidad de nodos que hay en el árbol
    public int contarNodos() {
        if (raiz == null) {
            return 0;
        }
        return raiz.contarNodos();
    }
    
    // devuelve una lista con los nodos de ese nivel
    public List<T> obtenerNivel(int nivel) {
        List<T> lista =new LinkedList<>();
        if (raiz != null) {
            raiz.obtenerNivel(nivel,0,lista);
        }
        return lista;
    }

    // checkea si un nodo es descendiente de otro
    public boolean esDescendiente(T ancestro,T descendiente) {

        if (raiz == null) { //si esta vacio no hay descendientes
            return false;
        }

        TNodoGenerico<T> nodoAncestro =raiz.buscar(ancestro); //busco el nodo ancestro

        if (nodoAncestro == null) { //si no está devuelve false
            return false;
        }

        return nodoAncestro.buscar(descendiente) != null; //si encuentro el descendiente dentro del subarbol del ancestro devuelve true, sino false
    }
    
    // devuelve el ancestro común más cercano entre dos nodos, si no hay devuelve null
    public T ancestroComunMasCercano(T nodo1,T nodo2) {

        if (raiz == null) { //si esta vacio no hay ancestros
            return null;
        }
        List<NodoGenerico<T>> camino1 =new LinkedList<>(); //creo listas para los dos caminos
        List<NodoGenerico<T>> camino2 =new LinkedList<>();

        raiz.obtenerCamino(nodo1,camino1); //lleno las listas con los caminos
        raiz.obtenerCamino(nodo2,camino2);

        T ultimoComun = null;

        int min =Math.min(camino1.size(),camino2.size()); //voy a recorrer los dos pero el tamaño del más chico
        for (int i = 0; i < min; i++) { //mientras los nodos sean iguales sigo avanzando, el último nodo común es el ancestro común más cercano
            if (camino1.get(i) ==camino2.get(i)) {
                ultimoComun =camino1.get(i).getDato();
            } else {
                break;
            }
        }
        return ultimoComun;
    }

    //lista los descendientes
    public List<T> listarDescendientes(T persona) {

        List<T> descendientes =new LinkedList<>();

        if (raiz == null) {
            return descendientes;
        }
        TNodoGenerico<T> nodo =raiz.buscar(persona);
        if (nodo == null) {
            return descendientes;
        }
        nodo.preOrden(n -> { //a la misma persona no la incluyo
            if (!n.getDato().equals(persona))
                {descendientes.add(n.getDato());
            }
        });
        return descendientes;
    }

}
