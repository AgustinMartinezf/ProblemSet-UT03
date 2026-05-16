package ucu.edu.aed.Ejercicio9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ucu.edu.aed.tda.hash.impl.*;
import ucu.edu.aed.tda.hash.Report;

public class MainEvaluacion {

    private static final int CAPACIDAD_FIJA = 10007;
    private static final long SEMILLA = 42L;

    public static void main(String[] args) {
        int[] factoresDeCarga = {70, 75, 80, 85, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99};

        System.out.printf("%-20s %-25s %-30s %-30s%n",
                "Factor de carga %",
                "Prom.Comp. Insercion",
                "Prom. Comp. Busq. Exitosa",
                "Prom. Comp. Busq. Sin Exito");
        System.out.println("-".repeat(110));

        for (int i = 0; i < factoresDeCarga.length; i++) {
            double factorObjetivo = factoresDeCarga[i] / 100.0;
            double[] resultados = evaluarFactorDeCarga(factorObjetivo);

            System.out.printf("%-20d %-25.2f %-30.2f %-30.2f%n",
                    factoresDeCarga[i],
                    resultados[0],
                    resultados[1],
                    resultados[2]);
        }
    }

    private static double[] evaluarFactorDeCarga(double factorObjetivo) {
        int elementosAInsertar = (int) (CAPACIDAD_FIJA * factorObjetivo);

        List<String> clavesInsertadas = generarClaves(elementosAInsertar, SEMILLA);

        double promInsercion = medirPromedioInsercion(clavesInsertadas);

        THashAbierto<String, Integer> tabla = construirTabla(clavesInsertadas);

        double promBusqExitosa = medirBusquedaExitosa(tabla, clavesInsertadas);
        double promBusqSinExito = medirBusquedaSinExito(tabla, elementosAInsertar);

        return new double[]{promInsercion, promBusqExitosa, promBusqSinExito};
    }

    private static List<String> generarClaves(int cantidad, long semilla) {
        Random random = new Random(semilla);
        List<String> claves = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            claves.add("clave_" + random.nextInt(Integer.MAX_VALUE));
        }
        return claves;
    }

    private static double medirPromedioInsercion(List<String> claves) {
        THashAbierto<String, Integer> tabla = crearTablaFija();
        Report report = new Report();
        int insertados = 0;

        for (int i = 0; i < claves.size(); i++) {
            if (tabla.insertar(claves.get(i), i, report)) {
                insertados++;
            }
        }

        if (insertados == 0) {
            return 0;
        }
        return (double) report.getCantidadComparaciones() / insertados;
    }

    private static THashAbierto<String, Integer> construirTabla(List<String> claves) {
        THashAbierto<String, Integer> tabla = crearTablaFija();
        for (int i = 0; i < claves.size(); i++) {
            tabla.insertar(claves.get(i), i);
        }
        return tabla;
    }

    private static double medirBusquedaExitosa(THashAbierto<String, Integer> tabla, List<String> claves) {
        Report report = new Report();
        int cantidad = claves.size();

        for (int i = 0; i < cantidad; i++) {
            tabla.buscar(claves.get(i), report);
        }

        if (cantidad == 0) {
            return 0;
        }
        return (double) report.getCantidadComparaciones() / cantidad;
    }

    private static double medirBusquedaSinExito(THashAbierto<String, Integer> tabla, int semillaOffset) {
        int intentos = 1000;
        Report report = new Report();
        Random random = new Random(SEMILLA + semillaOffset + 999999L);

        int realizados = 0;
        while (realizados < intentos) {
            String claveInexistente = "noexiste_" + random.nextInt(Integer.MAX_VALUE);
            tabla.buscar(claveInexistente, report);
            realizados++;
        }

        return (double) report.getCantidadComparaciones() / intentos;
    }

    private static THashAbierto<String, Integer> crearTablaFija() {
        return new THashAbierto<String, Integer>(0) {
            @Override
            protected int calcularCapacidadOptima(int elementosEsperados) {
                return CAPACIDAD_FIJA;
            }

            @Override
            protected boolean redimensionar() {
                return false;
            }
        };
    }
}