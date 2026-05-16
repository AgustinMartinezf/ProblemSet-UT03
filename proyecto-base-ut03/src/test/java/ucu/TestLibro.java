package ucu;

import junit.framework.TestCase;
import ucu.edu.aed.Ejercicio15.Libro;

import java.util.HashSet;

public class TestLibro extends TestCase {

    public void testLibrosIguales() {

        Libro l1 =
            new Libro("123", "Java", "Ana", 2020);

        Libro l2 =
            new Libro("123", "POO", "Luis", 2023);

        assertTrue(l1.equals(l2));
    
    }

    public void testLibrosDistintos() {

        Libro l1 =
            new Libro("123", "Java", "Ana", 2020);

        Libro l2 =
            new Libro("456", "Java", "Ana", 2020);

        assertFalse(l1.equals(l2));
    }



    public void testHashCodeIgual() {

        Libro l1 =
            new Libro("123", "Java", "Ana", 2020);

        Libro l2 =
            new Libro("123", "Otro", "Luis", 2025);

        assertEquals(
            l1.hashCode(),
            l2.hashCode()
        );
    }

    public void testHashSet() {

        HashSet<Libro> libros =
            new HashSet<>();

        Libro l1 =
            new Libro("123", "Java", "Ana", 2020);

        Libro l2 =
            new Libro("123", "Otro", "Luis", 2025);

        libros.add(l1);
        libros.add(l2);

        assertEquals(1, libros.size());
    }
}
