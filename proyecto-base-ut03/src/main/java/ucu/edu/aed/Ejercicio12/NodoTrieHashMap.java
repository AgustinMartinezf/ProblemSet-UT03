package ucu.edu.aed.Ejercicio12;

import ucu.edu.aed.tda.trie.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class NodoTrieHashMap<T> implements TNodoTrieHashMap<T> {

    private T dato;
    private boolean esPalabra;
    private HashMap<Character, NodoTrieHashMap<T>> hijos;

    public NodoTrieHashMap() {
        this.dato = null;
        this.esPalabra = false;
        this.hijos = new HashMap<>();
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

        char letra = palabra.charAt(0);
        if (!hijos.containsKey(letra)) {
            hijos.put(letra, new NodoTrieHashMap<>());
        }
        return hijos.get(letra).insertar(palabra.substring(1), dato);
    }

    @Override
    public Entry<T> buscar(String palabra) {
        if (palabra.isEmpty()) {
            return new Entry<>(this.dato, this.esPalabra, "");
        }

        char letra = palabra.charAt(0);
        if (!hijos.containsKey(letra)) {
            return null;
        }
        return hijos.get(letra).buscar(palabra.substring(1));
    }

    @Override
    public List<Entry<T>> predecir(String prefijo) {
        List<Entry<T>> resultado = new ArrayList<>();

        if (this.esPalabra) {
            resultado.add(new Entry<>(this.dato, true, prefijo));
        }

        for (Map.Entry<Character, NodoTrieHashMap<T>> entrada : hijos.entrySet()) {
            char letra = entrada.getKey();
            List<Entry<T>> subResultado = entrada.getValue().predecir(prefijo + letra);
            resultado.addAll(subResultado);
        }

        return resultado;
    }

    public List<Entry<T>> predecir(String acumulado, String pendiente) {
        if (!pendiente.isEmpty()) {
            char letra = pendiente.charAt(0);
            if (!hijos.containsKey(letra)) {
                return new ArrayList<>();
            }
            return hijos.get(letra).predecir(acumulado + letra, pendiente.substring(1));
        }
        return predecir(acumulado);
    }

    @Override
    public List<Entry<T>> buscarPatron(String patron, String textoAcumulado, int posicion, List<Integer> posiciones) {
        List<Entry<T>> resultado = new ArrayList<>();

        if (this.esPalabra) {
            posiciones.add(posicion - textoAcumulado.length());
            resultado.add(new Entry<>(this.dato, true, textoAcumulado));
        }

        if (posicion >= patron.length()) {
            return resultado;
        }

        char letraActual = patron.charAt(posicion);

        if (hijos.containsKey(letraActual)) {
            List<Entry<T>> subResultado = hijos.get(letraActual).buscarPatron(
                patron, textoAcumulado + letraActual, posicion + 1, posiciones
            );
            resultado.addAll(subResultado);
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
}