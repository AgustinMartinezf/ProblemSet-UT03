package ucu.edu.aed.Implementaciones;

import ucu.edu.aed.tda.TDALista;
import java.util.Comparator;
import java.util.function.Predicate;

public class ListaEnlazada<T> implements TDALista<T> {

    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamaño;

    public ListaEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }

    @Override
    public void agregar(T elem) {
        Nodo<T> nuevoNodo = new Nodo<>(elem);
        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo);
            cola = nuevoNodo;
        }
        tamaño++;
    }

    @Override
    public void agregar(int index, T elem) {
        if (index < 0 || index > tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
        Nodo<T> nuevoNodo = new Nodo<>(elem);
        if (index == 0) {
            nuevoNodo.setSiguiente(cabeza);
            cabeza = nuevoNodo;
            if (tamaño == 0) {
                cola = nuevoNodo;
            }
        } else {
            Nodo<T> anterior = cabeza;
            for (int i = 0; i < index - 1; i++) {
                anterior = anterior.getSiguiente();
            }
            nuevoNodo.setSiguiente(anterior.getSiguiente());
            anterior.setSiguiente(nuevoNodo);
            if (anterior == cola) {
                cola = nuevoNodo;
            }
        }
        tamaño++;
    }

    @Override
    public T obtener(int index) {
        checkIndex(index);
        Nodo<T> actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    @Override
    public T remover(int index) {
        checkIndex(index);
        T datoEliminado;
        if (index == 0) {
            datoEliminado = cabeza.getDato();
            cabeza = cabeza.getSiguiente();
            if (cabeza == null) { // lista quedó vacía
                cola = null;
            }
        } else {
            Nodo<T> anterior = cabeza;
            for (int i = 0; i < index - 1; i++) {
                anterior = anterior.getSiguiente();
            }
            Nodo<T> aEliminar = anterior.getSiguiente();
            datoEliminado = aEliminar.getDato();
            anterior.setSiguiente(aEliminar.getSiguiente());
            if (anterior.getSiguiente() == null) { // se eliminó la cola
                cola = anterior;
            }
        }
        tamaño--;
        return datoEliminado;
    }

    @Override
    public boolean remover(T elem) {
        Nodo<T> actual = cabeza;
        Nodo<T> anterior = null;
        while (actual != null) {
            if (elem == null) {
                if (actual.getDato() == null) {
                    if (anterior == null) {
                        cabeza = actual.getSiguiente();
                        if (cabeza == null) { // lista quedó vacía
                            cola = null;
                        }
                    } else {
                        anterior.setSiguiente(actual.getSiguiente());
                        if (anterior.getSiguiente() == null) { // se eliminó la cola
                            cola = anterior;
                        }
                    }
                    tamaño--;
                    return true;
                }
            } else {
                if (elem.equals(actual.getDato())) {
                    if (anterior == null) {
                        cabeza = actual.getSiguiente();
                        if (cabeza == null) { // lista quedó vacía
                            cola = null;
                        }
                    } else {
                        anterior.setSiguiente(actual.getSiguiente());
                        if (anterior.getSiguiente() == null) { // se eliminó la cola
                            cola = anterior;
                        }
                    }
                    tamaño--;
                    return true;
                }
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return false;
    }

    @Override
    public boolean contiene(T elem) {
        return indiceDe(elem) != -1;
    }

    @Override
    public int indiceDe(T elem) {
        Nodo<T> actual = cabeza;
        int index = 0;

        while (actual != null) {
            if (elem == null) {
                if (actual.getDato() == null) {
                    return index;
                }
            } else {
                if (elem.equals(actual.getDato())) {
                    return index;
                }
            }

            actual = actual.getSiguiente();
            index++;
        }
        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            T dato = actual.getDato();
            try {
                if (criterio.test(dato)) {
                    return dato;
                }
            } catch (Exception e) {
                // Ignorar excepciones del predicado para este elemento
            }
            actual = actual.getSiguiente();
        }
        return null;
        }
        

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
            ListaEnlazada<T> copia = new ListaEnlazada<>();

        Nodo<T> actual = this.cabeza;
        while (actual != null) {//hacemos primero una copia de la lista
            copia.agregar(actual.getDato());
         actual = actual.getSiguiente();
        }
        for (int i = 0; i < copia.tamaño - 1; i++) {//con recorrerlo n-1 veces alcanza, no es necesario n veces
            Nodo<T> a = copia.cabeza;
            Nodo<T> b = a.getSiguiente();

            while (b != null) {
                if (comparator.compare(a.getDato(), b.getDato()) > 0) {//si a va después de b (osea es "mayor") lo arreglamos.
                    T temp = a.getDato();
                    a.setDato(b.getDato());
                    b.setDato(temp);
                }
                a = b;
                b = b.getSiguiente();
            }
    }
    return copia;
        }
    

    @Override
    public int tamaño() {
        return tamaño;
    }

    @Override
    public boolean esVacio() {
        return tamaño == 0;
    }

    @Override
    public void vaciar() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= tamaño) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
    }
}


