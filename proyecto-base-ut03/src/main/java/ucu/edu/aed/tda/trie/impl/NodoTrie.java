package ucu.edu.aed.tda.trie.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ucu.edu.aed.tda.trie.TNodoTrie;
import ucu.edu.aed.tda.trie.Entry;

public class NodoTrie<T> implements TNodoTrie<T> {

    private static final int TAMANIO = 26;

    private T dato;
    private boolean esPalabra;
    private NodoTrie<T>[] hijos;

    public NodoTrie() {
        this.dato = null;
        this.esPalabra = false;
        this.hijos = new NodoTrie[TAMANIO];
    }

    private int indice(char c) {
        return Character.toLowerCase(c) - 'a';
    }

    @Override
    public boolean insertar(String palabra, T dato) {
        if (palabra.isEmpty()) {
            if (this.esPalabra) {
                return false;
            }
            this.esPalabra = true;
            this.dato = dato;
            return true;
        }

        int idx = indice(palabra.charAt(0));
        if (hijos[idx] == null) {
            hijos[idx] = new NodoTrie<>();
        }
        return hijos[idx].insertar(palabra.substring(1), dato);
    }

    @Override
    public Entry<T> buscar(String palabra) {
        if (palabra.isEmpty()) {
            return new Entry<>(this.dato, this.esPalabra, "");
        }

        int idx = indice(palabra.charAt(0));
        if (hijos[idx] == null) {
            return null;
        }
        return hijos[idx].buscar(palabra.substring(1));
    }

    @Override
    public List<Entry<T>> predecir(String prefijo) {
        List<Entry<T>> resultado = new ArrayList<>();

        if (this.esPalabra) {
            resultado.add(new Entry<>(this.dato, true, prefijo));
        }

        for (int i = 0; i < TAMANIO; i++) {
            if (hijos[i] != null) {
                char letra = (char) ('a' + i);
                List<Entry<T>> subResultado = hijos[i].predecir(prefijo + letra);
                resultado.addAll(subResultado);
            }
        }
        return resultado;
    }

    @Override
    public void recorrer(Consumer<Entry<T>> consumer) {
        List<Entry<T>> todas = predecir("");
        for (Entry<T> e : todas) {
            consumer.accept(e);
        }
    }

    @Override
    public T getDato() {
        if (this.esPalabra) {
            return this.dato;
        }
        return null;
    }

    @Override
    public boolean esPalabra() {
        return this.esPalabra;
    }

    public List<Entry<T>> predecir(String acumulado, String pendiente) {
        if (!pendiente.isEmpty()) {
            int idx = indice(pendiente.charAt(0));
            if (hijos[idx] == null) {
                return new ArrayList<>();
            }
            char letra = pendiente.charAt(0);
            return hijos[idx].predecir(acumulado + letra, pendiente.substring(1));
        }
        return predecir(acumulado);
    }
}