package ucu.edu.aed.Ejercicio12;

import java.util.List;
import java.util.function.Consumer;
import ucu.edu.aed.tda.trie.Entry;
 
public interface TNodoTrieHashMap<T> {
 
    void recorrer(Consumer<Entry<T>> consumer);
 
    Entry<T> buscar(String palabra);
 
    boolean insertar(String palabra, T dato);
 
    List<Entry<T>> predecir(String prefijo);
 
    List<Entry<T>> buscarPatron(String patron, String textoAcumulado, int posicion, List<Integer> posiciones);
 
    T getDato();
 
    boolean esPalabra();
}
 






