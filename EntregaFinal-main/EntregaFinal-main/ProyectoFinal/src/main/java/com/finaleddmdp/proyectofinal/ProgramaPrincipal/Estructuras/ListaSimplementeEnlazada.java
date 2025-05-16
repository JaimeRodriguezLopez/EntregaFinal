package com.finaleddmdp.proyectofinal.ProgramaPrincipal.Estructuras;

public class ListaSimplementeEnlazada<T> implements Lista<T> {
    private Elemento<T> cabeza;
    private int numElementos;

    public ListaSimplementeEnlazada() {
        this.cabeza = null;
        this.numElementos = 0;
    }

    @Override
    public boolean add(T elemento) {
        Elemento<T> nuevo = new Elemento<>(elemento);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Elemento<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        numElementos++;
        return true;
    }
    public boolean contiene(T elemento) {
        Elemento<T> actual = cabeza;
        while (actual != null) {
            if (actual.dato.equals(elemento)) {
                return true;
            }
            actual = actual.siguiente;

        }
        return false;
    }
    @Override
    public boolean delete(T elemento) {
        if (cabeza == null) return false;

        if (cabeza.dato.equals(elemento)) {
            cabeza = cabeza.siguiente;
            numElementos--;
            return true;
        }

        Elemento<T> actual = cabeza;
        while (actual.siguiente != null && !actual.siguiente.dato.equals(elemento)) {
            actual = actual.siguiente;
        }

        if (actual.siguiente == null) return false;

        actual.siguiente = actual.siguiente.siguiente;
        numElementos--;
        return true;
    }
    public void addCiclo(T elemento){
        Elemento<T> nuevo = new Elemento<>(elemento);
        if (cabeza == null) {
            cabeza = nuevo;
            cabeza.siguiente = nuevo;
        } else {
          Elemento<T> actual = cabeza;
          while ( actual.siguiente != cabeza){
              actual.siguiente = nuevo;
              nuevo.siguiente = cabeza;
          }
        }
        numElementos++;
    }
    @Override
    public Iterador<T> getIterador() {
        return new IteradorListaSimplementeEnlazada();
    }

    @Override
    public int getNumElementos() {
        return numElementos;
    }

    private class IteradorListaSimplementeEnlazada implements Iterador<T> {
        private Elemento<T> actual = cabeza;
        private Elemento<T> previo = null;

        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            T dato = actual.dato;
            previo = actual;
            actual = actual.siguiente;
            return dato;
        }

        @Override
        public void delete() {
            if (previo == null) {
                throw new IllegalStateException("Debe llamar a next() antes de eliminar.");
            }
            ListaSimplementeEnlazada.this.delete(previo.dato);
            previo = null;
        }
        public Object insert(T elemento) {
            Elemento<T> nuevo = new Elemento<>(elemento);
            if (previo == null) {
                nuevo.siguiente = cabeza;
                cabeza = nuevo;
            } else {
                nuevo.siguiente = previo.siguiente;
                previo.siguiente = nuevo;
            }
            numElementos++;
            return null;
        }
    }
}
