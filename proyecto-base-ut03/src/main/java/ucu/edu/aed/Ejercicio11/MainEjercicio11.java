package ucu.edu.aed.Ejercicio11;

import java.io.FileNotFoundException;

public class MainEjercicio11 {

    public static void main(String[] args) throws FileNotFoundException {

        ContadorPalabras contador = new ContadorPalabras();

        contador.contarPalabras();

        contador.mostrarFrecuencias();

        contador.mostrarTop10();
    }
}
