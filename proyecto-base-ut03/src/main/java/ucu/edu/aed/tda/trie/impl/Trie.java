package ucu.edu.aed.tda.trie.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ucu.edu.aed.tda.trie.Entry;
import ucu.edu.aed.tda.trie.TTrie;

public class Trie<T> implements TTrie<T> {

    private NodoTrie<T> raiz;

    public Trie() {
        this.raiz = new NodoTrie<>();
    }

    @Override
    public boolean insertar(String palabra, T dato) {
        if (palabra == null || palabra.isEmpty()) {
            return false;
        }
        return raiz.insertar(palabra.toLowerCase(), dato);
    }

    @Override
    public Entry<T> buscar(String palabra) {
        if (palabra == null || palabra.isEmpty()) {
            return null;
        }

        Entry<T> entrada = raiz.buscar(palabra.toLowerCase());
        if (entrada == null) {
            return null;
        }
        return new Entry<>(entrada.getDato(), entrada.esPalabra(), palabra);
    }

    @Override
    public List<Entry<T>> predecir(String prefijo) {
    if (prefijo == null) {
        return new ArrayList<>();
    }
    return raiz.predecir("", prefijo.toLowerCase());
}

    @Override
    public void recorrer(Consumer<Entry<T>> consumer) {
        raiz.recorrer(consumer);
    }
}