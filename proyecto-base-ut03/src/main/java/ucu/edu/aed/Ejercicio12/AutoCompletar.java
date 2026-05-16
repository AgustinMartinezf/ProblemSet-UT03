package ucu.edu.aed.Ejercicio12;

import java.util.List;
import ucu.edu.aed.tda.trie.Entry;
 
public class AutoCompletar {
 
    private TTrieHashMap<String> trie;
 
    public AutoCompletar() {
        this.trie = new TrieHashMap<>();
    }
 
    public void agregarPalabra(String palabra) {
        trie.insertar(palabra, palabra);
    }
 
    public List<Entry<String>> sugerencias(String prefijo) {
        return trie.predecir(prefijo);
    }
 
    public static void main(String[] args) {
        AutoCompletar ac = new AutoCompletar();
 
        ac.agregarPalabra("casa");
        ac.agregarPalabra("casamiento");
        ac.agregarPalabra("casar");
        ac.agregarPalabra("caso");
        ac.agregarPalabra("casta");
        ac.agregarPalabra("perro");
        ac.agregarPalabra("perrera");
        ac.agregarPalabra("perra");
        ac.agregarPalabra("gato");
        ac.agregarPalabra("gatito");
 
        String[] prefijos = {"cas", "per", "ga", "z"};
 
        for (String prefijo : prefijos) {
            System.out.println("Sugerencias para \"" + prefijo + "\":");
            List<Entry<String>> resultados = ac.sugerencias(prefijo);
            if (resultados.isEmpty()) {
                System.out.println("  (ninguna)");
            } else {
                for (Entry<String> e : resultados) {
                    System.out.println("  - " + e.getPalabra());
                }
            }
            System.out.println();
        }
    }
}