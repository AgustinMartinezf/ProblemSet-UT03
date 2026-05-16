package ucu.edu.aed.Ejercicio12;
 
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ucu.edu.aed.tda.trie.Entry;
 
public class TrieHashMap<T> implements TTrieHashMap<T> {
 
    private NodoTrieHashMap<T> raiz;
 
    public TrieHashMap() {
        this.raiz = new NodoTrieHashMap<>();
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
 
    @Override
    public List<Integer> buscarPatron(String patron) {
        List<Integer> posiciones = new ArrayList<>();
        if (patron == null || patron.isEmpty()) {
            return posiciones;
        }
 
        String patronLower = patron.toLowerCase();
 
        for (int i = 0; i < patronLower.length(); i++) {
            raiz.buscarPatron(patronLower, "", i, posiciones);
        }
 
        return posiciones;
    }
}
