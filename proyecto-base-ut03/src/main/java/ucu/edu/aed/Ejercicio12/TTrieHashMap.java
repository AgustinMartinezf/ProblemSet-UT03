package ucu.edu.aed.Ejercicio12;

import ucu.edu.aed.tda.trie.Entry;
import java.util.List;
import java.util.function.Consumer;
 
public interface TTrieHashMap<T> {
 
    void recorrer(Consumer<Entry<T>> consumer);
 
    Entry<T> buscar(String palabra);
 
    boolean insertar(String palabra, T dato);
 
    List<Entry<T>> predecir(String prefijo);
 
    List<Integer> buscarPatron(String patron);
}
