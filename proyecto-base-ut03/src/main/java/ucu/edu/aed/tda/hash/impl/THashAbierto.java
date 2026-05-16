package ucu.edu.aed.tda.hash.impl;

import java.util.ArrayList;
import java.util.List;
import ucu.edu.aed.tda.hash.THash;
import ucu.edu.aed.tda.hash.TNodoHash;
import ucu.edu.aed.tda.hash.Report;
import ucu.edu.aed.tda.hash.Entry;

public class THashAbierto<K, V> extends THash<K, V> {

    private int cantidadElementos;
    private static final double FACTOR_CARGA_MAXIMO = 0.75;

    public THashAbierto(int elementosEsperados) {
        super(elementosEsperados);
        cantidadElementos = 0;
    }

    @Override
    protected int calcularCapacidadOptima(int elementosEsperados) {
        int capacidad = (int) (elementosEsperados / FACTOR_CARGA_MAXIMO) + 1;
        return siguientePrimo(capacidad);
    }

    private boolean esPrimo(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private int siguientePrimo(int n) {
        while (!esPrimo(n)) {
            n++;
        }
        return n;
    }

    @Override
    protected int functionHashing(K clave) {
        int hash = clave.hashCode() % hashTable.length;
        if (hash < 0) {
            hash += hashTable.length;
        }
        return hash;
    }

    @Override
    public boolean insertar(K clave, V valor, Report report) {
        if (redimensionar()) {
            rehash();
        }

        int pos = functionHashing(clave);
        int comparaciones = 0;
        int intentos = 0;

        while (intentos < hashTable.length) {
            comparaciones++;
            if (hashTable[pos] == null || hashTable[pos].isLoteLibre()) {
                hashTable[pos] = new TNodoHash<>(clave, valor);
                cantidadElementos++;
                report.setCantidadComparaciones(report.getCantidadComparaciones() + comparaciones);
                return true;
            }
            if (hashTable[pos].getClave().equals(clave)) {
                report.setCantidadComparaciones(report.getCantidadComparaciones() + comparaciones);
                return false;
            }
            pos = (pos + 1) % hashTable.length;
            intentos++;
        }

        report.setCantidadComparaciones(report.getCantidadComparaciones() + comparaciones);
        return false;
    }

    @Override
    public V buscar(K clave, Report report) {
        int pos = functionHashing(clave);
        int comparaciones = 0;
        int intentos = 0;

        while (intentos < hashTable.length) {
            if (hashTable[pos] == null) {
                comparaciones++;
                report.setCantidadComparaciones(report.getCantidadComparaciones() + comparaciones);
                return null;
            }
            comparaciones++;
            if (!hashTable[pos].isLoteLibre() && hashTable[pos].getClave().equals(clave)) {
                report.setCantidadComparaciones(report.getCantidadComparaciones() + comparaciones);
                return hashTable[pos].getValor();
            }
            pos = (pos + 1) % hashTable.length;
            intentos++;
        }

        report.setCantidadComparaciones(report.getCantidadComparaciones() + comparaciones);
        return null;
    }

    @Override
    public boolean delete(K clave, Report report) {
        int pos = functionHashing(clave);
        int comparaciones = 0;
        int intentos = 0;

        while (intentos < hashTable.length) {
            if (hashTable[pos] == null) {
                report.setCantidadComparaciones(report.getCantidadComparaciones() + comparaciones);
                return false;
            }
            comparaciones++;
            if (!hashTable[pos].isLoteLibre() && hashTable[pos].getClave().equals(clave)) {
                hashTable[pos].setLoteLibre(true);
                cantidadElementos--;
                report.setCantidadComparaciones(report.getCantidadComparaciones() + comparaciones);
                return true;
            }
            pos = (pos + 1) % hashTable.length;
            intentos++;
        }

        report.setCantidadComparaciones(report.getCantidadComparaciones() + comparaciones);
        return false;
    }

    @Override
    public boolean esVacio() {
        return cantidadElementos == 0;
    }

    @Override
    public void vaciar() {
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = null;
        }
        cantidadElementos = 0;
    }

    @Override
    protected boolean redimensionar() {
        double factorActual = (double) cantidadElementos / hashTable.length;
        return factorActual >= FACTOR_CARGA_MAXIMO;
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        TNodoHash<K, V>[] tablaVieja = hashTable;
        int nuevaCapacidad = siguientePrimo(tablaVieja.length * 2);
        hashTable = new TNodoHash[nuevaCapacidad];
        cantidadElementos = 0;

        for (int i = 0; i < tablaVieja.length; i++) {
            if (tablaVieja[i] != null && !tablaVieja[i].isLoteLibre()) {
                insertar(tablaVieja[i].getClave(), tablaVieja[i].getValor());
            }
        }
    }

    public int getCantidadElementos() {
        return cantidadElementos;
    }

    public int getCapacidad() {
        return hashTable.length;
    }

    public double getFactorCarga() {
        return (double) cantidadElementos / hashTable.length;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        List<Entry<K, V>> lista = new ArrayList<>();
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null && !hashTable[i].isLoteLibre()) {
                lista.add(hashTable[i].getEntry());
            }
        }
        return lista;
    }

    @Override
    public Iterable<K> keys() {
        List<K> lista = new ArrayList<>();
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null && !hashTable[i].isLoteLibre()) {
                lista.add(hashTable[i].getClave());
            }
        }
        return lista;
    }

    @Override
    public Iterable<V> values() {
        List<V> lista = new ArrayList<>();
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null && !hashTable[i].isLoteLibre()) {
                lista.add(hashTable[i].getValor());
            }
        }
        return lista;
    }
}