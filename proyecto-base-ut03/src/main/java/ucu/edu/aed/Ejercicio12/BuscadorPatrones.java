package ucu.edu.aed.Ejercicio12;

import java.util.Collections;
import java.util.List;
 
public class BuscadorPatrones {
 
    private TTrieHashMap<Integer> arbolSufijos;
    private String texto;
 
    public BuscadorPatrones(String texto) {
        this.texto = texto.toLowerCase();
        this.arbolSufijos = new TrieHashMap<>();
        construirArbolSufijos();
    }
 
    private void construirArbolSufijos() {
        for (int i = 0; i < texto.length(); i++) {
            String sufijo = texto.substring(i);
            arbolSufijos.insertar(sufijo, i);
        }
    }
 
    public List<Integer> buscar(String patron) {
        if (patron == null || patron.isEmpty()) {
            return Collections.emptyList();
        }
 
        List<Integer> posiciones = arbolSufijos.buscarPatron(patron.toLowerCase());
        Collections.sort(posiciones);
        return posiciones;
    }
 
    public static void main(String[] args) {
        String texto = "el gato come con el gato negro y el gato blanco";
        BuscadorPatrones buscador = new BuscadorPatrones(texto);
 
        System.out.println("Texto: \"" + texto + "\"");
        System.out.println();
 
        String[] patrones = {"gato", "el", "con", "blanco", "xyz", "o"};
 
        for (String patron : patrones) {
            List<Integer> posiciones = buscador.buscar(patron);
            System.out.print("Patron \"" + patron + "\": ");
            if (posiciones.isEmpty()) {
                System.out.println("no encontrado");
            } else {
                System.out.println("encontrado en posiciones " + posiciones);
                for (int pos : posiciones) {
                    int fin = pos + patron.length();
                    System.out.println("  posicion " + pos + " -> \"" + texto.substring(pos, fin) + "\"");
                }
            }
            System.out.println();
        }
    }
}
 