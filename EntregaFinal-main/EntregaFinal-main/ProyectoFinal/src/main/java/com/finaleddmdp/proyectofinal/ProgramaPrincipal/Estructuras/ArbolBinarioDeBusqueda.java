package com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class ArbolBinarioDeBusqueda<K extends Comparable<K>, V> {
    private NodoABB<K, V> raiz;

    // Agregar nodo a la raíz si está vacía
    public boolean addNodoRaiz(K clave, V valor) {
        if (raiz != null) return false;
        raiz = new NodoABB<>(clave, valor);
        return true;
    }

    // Agregar nodos al árbol
    public boolean addNodos(K clave, V valor) {
        if (raiz == null) return addNodoRaiz(clave, valor);
        return addOtrosNodos(clave, valor);
    }

    private boolean addOtrosNodos(K clave, V valor) {
        NodoABB<K, V> candidato = buscarNodoAInsertar(clave, raiz);
        if (candidato == null) return false;

        NodoABB<K, V> nuevoNodo = new NodoABB<>(clave, valor);
        if (candidato.getClave().compareTo(clave) > 0) {
            candidato.setMenor(nuevoNodo);
        } else {
            candidato.setMayor(nuevoNodo);
        }
        return true;
    }

    private NodoABB<K, V> buscarNodoAInsertar(K clave, NodoABB<K, V> nodo) {
        if (nodo == null) {
            return null;
        }

        if (nodo.getClave().compareTo(clave) > 0) {
            if (nodo.getMenor() == null) {
                return nodo;
            } else {
                return buscarNodoAInsertar(clave, nodo.getMenor());
            }
        } else {
            if (nodo.getMayor() == null) {
                return nodo;
            } else {
                return buscarNodoAInsertar(clave, nodo.getMayor());
            }
        }
    }


    public int getGrado() { return getGrado(raiz); }

    private int getGrado(NodoABB<K, V> nodo) {
        if (nodo == null) return 0;
        int gradoActual = (nodo.getMenor() != null ? 1 : 0) + (nodo.getMayor() != null ? 1 : 0);
        return Math.max(gradoActual, Math.max(getGrado(nodo.getMenor()), getGrado(nodo.getMayor())));
    }

    public int getAltura() { return getAltura(raiz); }

    private int getAltura(NodoABB<K, V> nodo) {
        if (nodo == null) return -1;
        return 1 + Math.max(getAltura(nodo.getMenor()), getAltura(nodo.getMayor()));
    }

    public List<V> getListaDatosNivel(int nivel) {
        List<V> lista = new ArrayList<>();
        Queue<NodoABB<K, V>> cola = new LinkedList<>();
        if (raiz == null) return lista;
        cola.add(raiz);
        int nivelActual = 0;

        while (!cola.isEmpty()) {
            int tamanoNivel = cola.size();
            if (nivelActual == nivel) {
                for (NodoABB<K, V> nodo : cola) {
                    lista.add(nodo.getValor());
                }
                return lista;
            }
            for (int i = 0; i < tamanoNivel; i++) {
                NodoABB<K, V> nodo = cola.poll();
                if (nodo.getMenor() != null) cola.add(nodo.getMenor());
                if (nodo.getMayor() != null) cola.add(nodo.getMayor());
            }
            nivelActual++;
        }
        return lista;
    }

    public boolean isArbolHomogeneo() {
        for (int i = 0; i <= getAltura(); i++) {
            if (getListaDatosNivel(i).size() != 1) return false;
        }
        return true;
    }

    public boolean isArbolCompleto() {
        int altura = getAltura();
        if (altura == 0) return true;
        return getListaDatosNivel(altura).size() == Math.pow(2, altura);
    }

    public boolean isArbolCasiCompleto() {
        int altura = getAltura();
        if (altura == 0) return true;
        return getListaDatosNivel(altura).size() == Math.pow(2, altura) - 1;
    }

    public List<V> getCamino(K clave) {
        List<V> camino = new ArrayList<>();
        NodoABB<K, V> actual = raiz;
        while (actual != null && actual.getClave().compareTo(clave) != 0) {
            camino.add(actual.getValor());
            actual = (actual.getClave().compareTo(clave) > 0) ? actual.getMenor() : actual.getMayor();
        }
        if (actual != null) {
            camino.add(actual.getValor());
        }
        return camino;
    }


    public ArbolBinarioDeBusqueda<K, V> getSubArbolIzquierdo() {
        if (raiz == null || raiz.getMenor() == null) return null;
        ArbolBinarioDeBusqueda<K, V> subArbol = new ArbolBinarioDeBusqueda<>();
        subArbol.raiz = raiz.getMenor();
        return subArbol;
    }

    public ArbolBinarioDeBusqueda<K, V> getSubArbolDerecha() {
        if (raiz == null || raiz.getMayor() == null) return null;
        ArbolBinarioDeBusqueda<K, V> subArbol = new ArbolBinarioDeBusqueda<>();
        subArbol.raiz = raiz.getMayor();
        return subArbol;
    }
    public void preOrden() {
        preOrden(raiz);
    }

    private void preOrden(NodoABB<K, V> nodo) {
        if (nodo == null) return;
        System.out.println(nodo.getValor());
        preOrden(nodo.getMenor());
        preOrden(nodo.getMayor());
    }
    private int sumaPreOrden(NodoABB<K, V> nodo) {
        if (nodo == null) return 0;

        int sumaIzq = sumaPreOrden(nodo.getMenor());
        int sumaDer = sumaPreOrden(nodo.getMayor());
        int valorActual = Integer.parseInt((String) nodo.getValor());

        return sumaIzq + sumaDer + valorActual;
    }

    public int sumaPreOrden() {
        return sumaOrdenCentral(raiz);
    }

    public void postOrden() {
        postOrden(raiz);
    }

    private void postOrden(NodoABB<K, V> nodo) {
        if (nodo == null) return;
        postOrden(nodo.getMenor());
        postOrden(nodo.getMayor());
        System.out.println(nodo.getValor());
    }
    private int sumaPostOrden(NodoABB<K, V> nodo) {
        if (nodo == null) return 0;

        int sumaIzq = sumaPostOrden(nodo.getMenor());
        int sumaDer = sumaPostOrden(nodo.getMayor());
        int valorActual = Integer.parseInt((String) nodo.getValor());

        return sumaIzq + sumaDer + valorActual;
    }

    public int sumaPostOrden() {
        return sumaPostOrden(raiz);
    }

    public void ordenCentral() {
        ordenCentral(raiz);
    }

    private void ordenCentral(NodoABB<K, V> nodo) {
        if (nodo == null) return;
        ordenCentral(nodo.getMenor());
        System.out.println(nodo.getValor());
        ordenCentral(nodo.getMayor());

    }
    private int sumaOrdenCentral(NodoABB<K, V> nodo) {
        if (nodo == null) return 0;

        int sumaIzq = sumaOrdenCentral(nodo.getMenor());
        int sumaDer = sumaOrdenCentral(nodo.getMayor());
        int valorActual = Integer.parseInt((String) nodo.getValor());

        return sumaIzq + sumaDer + valorActual;
    }

    public int sumaOrdenCentral() {
        return sumaOrdenCentral(raiz);
    }
}