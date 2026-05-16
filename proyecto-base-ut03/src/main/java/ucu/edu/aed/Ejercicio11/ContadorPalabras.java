package ucu.edu.aed.Ejercicio11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public class ContadorPalabras {

  private HashMap<String, Integer> frecuencias;

    public ContadorPalabras() {

        frecuencias = new HashMap<>();
    }

    public void contarPalabras()
            throws FileNotFoundException {

        File archivo =
                new File("src/ejercicio11/libro.txt");

        Scanner scanner = new Scanner(archivo);

        while (scanner.hasNext()) {

            String palabra = scanner.next();

            palabra = palabra.toLowerCase();

            palabra = palabra.replaceAll(
                    "[^a-záéíóúñ]",
                    ""
            );

            if (frecuencias.containsKey(palabra)) {

                frecuencias.put(
                        palabra,
                        frecuencias.get(palabra) + 1
                );

            } else {

                frecuencias.put(palabra, 1);
            }
        }

        scanner.close();
    }

    public void mostrarFrecuencias() {

        System.out.println(frecuencias);
    }

}
