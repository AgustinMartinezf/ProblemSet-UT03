package ucu.edu.aed.Ejercicio16;

public class Persona implements Comparable<Persona>{

    private String nombre;
    private int añoNacimiento;
    public Persona(String nombre, int añoNacimiento) {
        this.nombre = nombre;
        this.añoNacimiento = añoNacimiento;
    }
    public String getNombre() {
        return nombre;
    }
    public int getAñoNacimiento() {
        return añoNacimiento;
    }

    @Override
    public int compareTo(Persona otra) {
       int cmp =this.nombre.compareTo(otra.nombre);
        if (cmp != 0) {
            return cmp;
        }
        return Integer.compare(this.añoNacimiento,otra.añoNacimiento);
    }
    @Override
    public String toString() {
        return "[Nombre=" + nombre + ", Año de Nacimiento=" + añoNacimiento + "]";
    }

    
}
