package ucu.edu.aed.Ejercicio16;
import java.util.List;

import ucu.edu.aed.tda.generic_trie.TArbolGenerico;
import ucu.edu.aed.tda.generic_trie.impl.ArbolGenerico;

public class Main {
    public static void main(String[] args) {
        Persona abuela = new Persona("Abuela", 1940);
        Persona hijo1 = new Persona("Hijo1", 1970);
        Persona hijo2 = new Persona("Hijo2", 1975);
        Persona nieto1 = new Persona("Nieto1", 2000);
        Persona nieto2 = new Persona("Nieto2", 2005);
        Persona nieto3 = new Persona("Nieto3", 2010);

        ArbolGenerico<Persona> arbol = new ArbolGenerico<>(abuela);
        arbol.agregarHijo(abuela, hijo1);
        arbol.agregarHijo(abuela, hijo2);
        arbol.agregarHijo(hijo1, nieto1);
        arbol.agregarHijo(hijo1, nieto2);
        arbol.agregarHijo(hijo2, nieto3);

        System.out.println("Altura del árbol: " + arbol.altura(abuela)); //2
        System.out.println("Descendientes de hijo1: "+ arbol.listarDescendientes(hijo1)); //[Nieto1, Nieto2]
        System.out.println("Cantidad de personas en el árbol: " + arbol.contarNodos()); //6
        System.out.println("Todas las personas de la generación de nietos: "+ arbol.obtenerNivel(2)); //[Nieto1, Nieto2, Nieto3]
        System.out.println("Ancestro comun más cercano entre nieto1 y nieto2: " + arbol.ancestroComunMasCercano(nieto1,nieto2)); //Hijo1
        System.out.println("¿Es nieto1 descendiente de nieto2? " + arbol.esDescendiente(nieto2,nieto1)); //false
        System.out.println("¿Es nieto1 descendiente de hijo1? " + arbol.esDescendiente(hijo1,nieto1)); //true

    }
}
