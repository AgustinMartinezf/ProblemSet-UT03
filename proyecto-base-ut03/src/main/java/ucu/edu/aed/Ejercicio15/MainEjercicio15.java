package ucu.edu.aed.Ejercicio15;

import java.util.HashSet;

public class MainEjercicio15 {

    public static void main(String[] args){

        //4- Ejemplo de 2 libros distintos
        //Sob objetos distintos y en distinta memoria, pero comparten el mismo ISBN. 
        //Por lo tanto, representan el mismo libro

        HashSet<Libro> libros = new HashSet<>();

        Libro l1 = new Libro("123", "Java Básico", "Ana", 2020);
        Libro l2 =new Libro("123", "Java Avanzado", "Ana", 2021);

        libros.add(l1);
        libros.add(l2);

        System.out.println(libros.size());
    }

}
