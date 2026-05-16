package ucu.edu.aed.Ejercicio11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ContadorPalabras {

  private HashMap<String, Integer> frecuencias;

    public ContadorPalabras() {

        frecuencias = new HashMap<>();
    }

    public void contarPalabras() throws FileNotFoundException {

        File archivo = new File("proyecto-base-ut03\\src\\main\\java\\ucu\\edu\\aed\\Ejercicio11\\libro.txt");

        Scanner scanner = new Scanner(archivo);

        while (scanner.hasNext()) {

            String palabra = scanner.next();

            palabra = palabra.toLowerCase();

            palabra = palabra.replaceAll("[^a-záéíóúñ]","");

            if (frecuencias.containsKey(palabra)) {

                frecuencias.put(palabra, frecuencias.get(palabra) + 1);

            } else {

                frecuencias.put(palabra, 1);
            }
        }

        scanner.close();
    }

    public void mostrarFrecuencias() {

        System.out.println(frecuencias);

    }

    public void mostrarTop10(){

        ArrayList<Map.Entry<String, Integer>> lista = new ArrayList<>(frecuencias.entrySet());
        Collections.sort(lista, new Comparator<Map.Entry<String, Integer>>() {

        @Override
        public int compare( Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
            return b.getValue().compareTo(a.getValue());
        }
        });

        System.out.println("TOP 10 PALABRAS:");

        for (int i = 0; i < 10 && i < lista.size(); i++) {

            Map.Entry<String, Integer> entrada = lista.get(i);

            System.out.println(entrada.getKey() + " -> " + entrada.getValue());
        }

    }

    
}
