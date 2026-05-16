package ucu.edu.aed.entregables;

import ucu.edu.aed.medible.lib.Medible;
import ucu.edu.aed.medible.lib.Medicion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Ejercicio 7 - Implementación compacta en un único archivo.
 * - Carga los ficheros de palabras del proyecto base
 * - Construye: LinkedList, ArrayList, SimpleTrie, HashMap, TreeMap
 * - Ejecuta 20 búsquedas por cada estructura (palabras de listado-general-palabrasBuscar.txt)
 * - Ejecuta 20 predicciones (prefijo "cas") por cada estructura Trie/LinkedList/HashMap
 * - Consolida y muestra resultados de memoria y tiempo
 */
public class Ejercicio7 {

    // --- Helper: simple Trie that implements the minimal needed API ---
    public static class SimpleTrie {
        private static class Node {
            Map<Character, Node> children = new HashMap<>();
            boolean isWord = false;
        }

        private final Node root = new Node();

        public void insert(String word) {
            Node cur = root;
            for (char c : word.toCharArray()) {
                cur = cur.children.computeIfAbsent(c, k -> new Node());
            }
            cur.isWord = true;
        }

        public boolean contains(String word) {
            Node cur = root;
            for (char c : word.toCharArray()) {
                cur = cur.children.get(c);
                if (cur == null) return false;
            }
            return cur.isWord;
        }

        public List<String> predict(String prefix) {
            List<String> res = new ArrayList<>();
            Node cur = root;
            for (char c : prefix.toCharArray()) {
                cur = cur.children.get(c);
                if (cur == null) return res;
            }
            collect(prefix, cur, res);
            return res;
        }

        private void collect(String soFar, Node node, List<String> out) {
            if (node.isWord) out.add(soFar);
            for (Map.Entry<Character, Node> e : node.children.entrySet()) {
                collect(soFar + e.getKey(), e.getValue(), out);
            }
        }
    }

    // --- Medible implementations for searching (20 repetitions over list of palabras) ---
    public static class MedicionBuscarArrayList extends Medible<List<String>> {
        private final ArrayList<String> list;

        public MedicionBuscarArrayList(ArrayList<String> list) { this.list = list; }

        @Override
        public void ejecutar(int repeticiones, List<String> palabras) {
            for (int i = 0; i < repeticiones; i++) {
                for (String p : palabras) list.contains(p);
            }
        }

        @Override
        public Object getObjetoAMedirMemoria() { return list; }
    }

    public static class MedicionBuscarTTrie extends Medible<String> {
        private final SimpleTrie trie;

        public MedicionBuscarTTrie(SimpleTrie trie) { this.trie = trie; }

        @Override
        public void ejecutar(int repeticiones, String palabrasConcat) {
            // palabrasConcat will be ignored; we expose an alternate medir call below
        }

        // helper used by main to perform measurement with list of palabras
        public Medicion medirConLista(int repeticiones, List<String> palabras) {
            long init = System.nanoTime();
            for (int i = 0; i < repeticiones; i++) {
                for (String p : palabras) trie.contains(p);
            }
            long fin = System.nanoTime();
            return new Medicion(this.getClass().getSimpleName(), ucu.edu.aed.medible.lib.ObjectSizeFetcher.getObjectSize(getObjetoAMedirMemoria()), fin - init);
        }

        @Override
        public Object getObjetoAMedirMemoria() { return trie; }
    }

    public static class MedicionBuscarHashMap extends Medible<List<String>> {
        private final HashMap<String,Boolean> map;

        public MedicionBuscarHashMap(HashMap<String,Boolean> map) { this.map = map; }

        @Override
        public void ejecutar(int repeticiones, List<String> palabras) {
            for (int i = 0; i < repeticiones; i++) {
                for (String p : palabras) map.containsKey(p);
            }
        }

        @Override
        public Object getObjetoAMedirMemoria() { return map; }
    }

    public static class MedicionBuscarTreeMap extends Medible<List<String>> {
        private final TreeMap<String,Boolean> map;

        public MedicionBuscarTreeMap(TreeMap<String,Boolean> map) { this.map = map; }

        @Override
        public void ejecutar(int repeticiones, List<String> palabras) {
            for (int i = 0; i < repeticiones; i++) {
                for (String p : palabras) map.containsKey(p);
            }
        }

        @Override
        public Object getObjetoAMedirMemoria() { return map; }
    }

    // --- Medible implementations for prediction (prefijo) ---
    public static class MedicionPredecirTrie extends Medible<String> {
        private final SimpleTrie trie;

        public MedicionPredecirTrie(SimpleTrie trie) { this.trie = trie; }

        @Override
        public void ejecutar(int repeticiones, String prefijo) {
            for (int i = 0; i < repeticiones; i++) trie.predict(prefijo);
        }

        @Override
        public Object getObjetoAMedirMemoria() { return trie; }
    }

    public static class MedicionPredecirLinkedList extends Medible<String> {
        private final LinkedList<String> list;

        public MedicionPredecirLinkedList(LinkedList<String> list) { this.list = list; }

        @Override
        public void ejecutar(int repeticiones, String prefijo) {
            for (int i = 0; i < repeticiones; i++) {
                List<String> tmp = new ArrayList<>();
                for (String s : list) if (s.startsWith(prefijo)) tmp.add(s);
            }
        }

        @Override
        public Object getObjetoAMedirMemoria() { return list; }
    }

    public static class MedicionPredecirHashMap extends Medible<String> {
        private final HashMap<String,Boolean> map;

        public MedicionPredecirHashMap(HashMap<String,Boolean> map) { this.map = map; }

        @Override
        public void ejecutar(int repeticiones, String prefijo) {
            for (int i = 0; i < repeticiones; i++) {
                List<String> tmp = new ArrayList<>();
                for (String k : map.keySet()) if (k.startsWith(prefijo)) tmp.add(k);
            }
        }

        @Override
        public Object getObjetoAMedirMemoria() { return map; }
    }

    // --- Main program: perform tasks and print tables ---
    public static void main(String[] args) throws IOException {
        String base = "ProblemSet-UT03/proyecto-base-ut03/src/main/resources/ut03/";
        List<String> palabras = readLines(base + "listado-general-desordenado.txt");
        List<String> palabrasBuscar = readLines(base + "listado-general-palabrasBuscar.txt");

        // Build structures
        LinkedList<String> linkedList = new LinkedList<>(palabras);
        ArrayList<String> arrayList = new ArrayList<>(palabras);
        SimpleTrie trie = new SimpleTrie();
        for (String w : palabras) trie.insert(w);
        HashMap<String,Boolean> hashMap = new HashMap<>();
        for (String w : palabras) hashMap.put(w, Boolean.TRUE);
        TreeMap<String,Boolean> treeMap = new TreeMap<>();
        for (String w : palabras) treeMap.put(w, Boolean.TRUE);

        int reps = 20;

        // Search measurements
        System.out.println("--- BÚSQUEDA (20 repeticiones por palabra) ---");

        // LinkedList example already exists in project base; we will reuse its class if present, else use our inline approach
        ucu.edu.aed.medible.medibles.MedicionBuscarLinkedList mLinked = new ucu.edu.aed.medible.medibles.MedicionBuscarLinkedList(linkedList);
        Medicion resLinked = mLinked.medir(reps, palabrasBuscar);
        Medicion resArray = new MedicionBuscarArrayList(arrayList).medir(reps, palabrasBuscar);
        Medicion resTrie = new MedicionBuscarTTrie(trie).medirConLista(reps, palabrasBuscar);
        Medicion resHash = new MedicionBuscarHashMap(hashMap).medir(reps, palabrasBuscar);
        Medicion resTree = new MedicionBuscarTreeMap(treeMap).medir(reps, palabrasBuscar);

        System.out.println("Estructura,Memoria,Tiempo");
        System.out.println("LinkedList," + resLinked.toCSV());
        System.out.println("ArrayList," + resArray.toCSV());
        System.out.println("Trie," + resTrie.toCSV());
        System.out.println("HashMap," + resHash.toCSV());
        System.out.println("TreeMap," + resTree.toCSV());

        // Prediction measurements for prefix "cas"
        String prefijo = "cas";
        System.out.println();
        System.out.println("--- PREDICCIÓN (20 repeticiones prefijo 'cas') ---");

        Medicion mTriePred = new MedicionPredecirTrie(trie).medir(reps, prefijo);
        Medicion mLinkedPred = new MedicionPredecirLinkedList(linkedList).medir(reps, prefijo);
        Medicion mHashPred = new MedicionPredecirHashMap(hashMap).medir(reps, prefijo);

        System.out.println("Estructura,Memoria,Tiempo");
        System.out.println("Trie," + mTriePred.toCSV());
        System.out.println("LinkedList," + mLinkedPred.toCSV());
        System.out.println("HashMap," + mHashPred.toCSV());
    }

    private static List<String> readLines(String path) throws IOException {
        List<String> res = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) res.add(line);
            }
        }
        return res;
    }
}
